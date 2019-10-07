package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerThread extends Thread{
	
	Socket serverSocket;
	ServerHost server;
    TradingAccount liveTradingAccount;
    ActiveLobby currentLobby;
    int currentLobbyPlayerID;
    PurchaseOrSaleReceipt[] naturalFluxationArray = new PurchaseOrSaleReceipt[4];
    boolean isNaturalFluctuationReady = false;
    boolean isNewsReady = false;
	PurchaseOrSaleReceipt newsReceipt = null;
  
    
    
	public ServerThread(Socket serverSocket, ServerHost server) {
		
		this.serverSocket = serverSocket;
		this.server = server;
		
	}
	
	
	public ServerHost getServer() {
		return server;
	}

	@Override
    public void run(){
		
		System.out.println("Class: ServerThread - Method: run \nNew client connected.");
        int players = this.server.connectedClients.size();
        System.out.println("Current number of clients is: " + players + "\n");

        
        	try {
				handleServerSocket();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
	}

	public void setNaturalFluxationArray(PurchaseOrSaleReceipt[] x){
		naturalFluxationArray = x;
	}
	public void setIsNaturalFluctuationReady(boolean x){
		isNaturalFluctuationReady = x;
	}
	public void setIsNewsReady(boolean x){
		isNewsReady = x;
	}
	public void setNewsReceipt(PurchaseOrSaleReceipt x){
		newsReceipt = x;
	}

	/**
	 * Main logic of server thread. Handles Client requests.
	 */
    private void handleServerSocket() throws IOException, InterruptedException {
    	
    	int lobbyID = 0;
    	currentLobbyPlayerID = 0;

    	
    	try {
    		
			//BufferedReader dataIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			//PrintWriter dataOut = new PrintWriter(serverSocket.getOutputStream());
			ObjectInputStream dataInObject = new ObjectInputStream(serverSocket.getInputStream());
			ObjectOutputStream dataOutObject = new ObjectOutputStream(serverSocket.getOutputStream());

			System.out.println("Class: ServerThread - Method: handleServerSocket:                 dataIn and dataOut ready ");

			
			//Start the waiting to receive login details loop
			System.out.println("Class: ServerThread - Method: handleServerSocket:                  Waiting to recieve login details ");


			// STEP 1 - this loop keeps going until user successfully logins in
			while(true) {

				int loginOrSignup;
				String username;
				String password;

				System.out.println("Waiting for user to either log in or sign up..");
				loginOrSignup = dataInObject.read();


				// 1 means that client wants to sign in
				if(loginOrSignup == 1) {

					username = dataInObject.readUTF();
					password = dataInObject.readUTF();

					System.out.println("Recieved username: " + username);
					System.out.println("Recieved password: " + password);
					System.out.println("Login or SignUp: " + loginOrSignup);



				    //If authentication successful 1 is returned else x remains 0
					if(database.Authentication.serverAuthenticateUser(username , password) == 1) {
						
						//Send int 1 to the user that will break them out of the login verification loop client side
						dataOutObject.write(1);
						dataOutObject.flush();

						dataOutObject.writeUTF(username);
						dataOutObject.flush();
						this.liveTradingAccount = new TradingAccount(username);
						break;
						
					} else {
						dataOutObject.write(0);
						dataOutObject.flush();
						System.out.println("Class: ServerThread - Method: handleServerSocket:               Incorrect username or password ");
					}


				}

				// if the user clicked signup and now moves to signup screen
				if (loginOrSignup == 2) {

					System.out.println("ServerThread: waiting to receive user signup details.");

					String newEmail = dataInObject.readUTF();
					String newUsername = dataInObject.readUTF();
					String newPassword = dataInObject.readUTF();

					int dataBaseMessage = database.Signup.Signup(newUsername, newPassword, newEmail);

					if(dataBaseMessage == 1){
						dataOutObject.write(1);
						dataOutObject.flush();


					} else if (dataBaseMessage == 0){
						dataOutObject.write(0);
						dataOutObject.flush();
					}


				}
				Thread.sleep(100);

			}

			System.out.println("Class: ServerThread - Method: handleServerSocket:                Login Successful ");
			
			System.out.println("Class: ServerThread - Method: handleServerSocket:                Waiting for client to create or attempt to join a lobby ");

			//STEP 2 - Loop for creating a lobby or joining an existing lobby
			while(true) {
				
				int x = 0;
				String lobbyTestCode;
				//If they press create lobby then server receives 1 from client, if they wish to join they receive 2
				lobbyTestCode = dataInObject.readUTF();
				System.out.println("Recieved lobbyTestCode Pre: " + lobbyTestCode);
				x = Integer.parseInt(lobbyTestCode);
				//x= dataIn.read();
				
				System.out.println("Recieved lobbyTestCode: " + x);

				// CHOICE 1: if Client clicks "create lobby"
				if(x == 1) {
					
					//Create Lobby
					lobbyID = server.activeLobbies.size() + 1;
					currentLobby = new ActiveLobby(lobbyID);
					this.server.activeLobbies.add(currentLobby);
					this.liveTradingAccount.setCurrentLobby(currentLobby);
					//Add logged in player to lobby
					currentLobby.addPlayer(this.liveTradingAccount , this);
					currentLobby.toString();
                    currentLobbyPlayerID = currentLobby.getLobbySize();

					//Send 1 to client to break out of loop
					System.out.println("Class: ServerThread - Method: handleServerSocket:                Lobby created There are now " + this.server.activeLobbies.size() + " active lobbies, you are player: " + currentLobbyPlayerID);
					dataOutObject.write(1);
					dataOutObject.flush();

					System.out.println("Send Lobby Key to Client: " + currentLobby.getKey());

					String lobbyCodeString = Integer.toString(currentLobby.getKey());
					dataOutObject.writeUTF(lobbyCodeString);
					dataOutObject.flush();

					break;
				}

				// CHOICE 2: if Client wants to join an existing lobby
				if(x != 1) {
					
					for(ActiveLobby y: server.activeLobbies) {
						
						if(x == y.getKey()) {
							//Add logged in player to lobby
							currentLobby = y;
							y.addPlayer(this.liveTradingAccount, this);
							y.toString();
                            currentLobbyPlayerID = y.getLobbySize();
							this.liveTradingAccount.setCurrentLobby(y);
							dataOutObject.write(2);
							dataOutObject.flush();


							System.out.println("Send Lobby Key to Client: " + currentLobby.getKey());

							String lobbyCodeString = Integer.toString(currentLobby.getKey());
							dataOutObject.writeUTF(lobbyCodeString);
							dataOutObject.flush();


                            System.out.println("Class: ServerThread - Method: handleServerSocket:               Successfully joined lobby, you have player ID: " + currentLobbyPlayerID);
							break;
						}else {

//							dataOutObject.write(3);
//							dataOutObject.flush();

							System.out.println("Class: ServerThread - Method: handleServerSocket:                Wrong lobby key ");
						}
					}
					
				}
				// helps break joiners from while loop
				if (currentLobby != null){
					break;
				}
				
			}

			System.out.println("Class: ServerThread - Method: handleServerSocket:                Waiting for start button click ");

			//STEP 3 (HOST PERSPECTIVE) - Waiting to receive button click
			while(currentLobbyPlayerID == 1) {

				System.out.println("Host ServerThread waiting to receive button click");

				int x = 0;
				x = dataInObject.read();

				if(x == 1) {
					break;
				}

				if(x == 2 ){
					currentLobby.toString();
					this.currentLobby.start();
					break;
				}
			}

			//STEP 3 (JOINER PERSPECTIVE) - Waiting for host to start game
			while(currentLobbyPlayerID != 1){

				//System.out.println("Joiner ServerThread waiting to send");

				if(this.currentLobby.isInGame()) {
					dataOutObject.write(49);
					dataOutObject.flush();
					break;
				}

				// can delete this line later
				Thread.sleep(100);
			}


			//STEP 4 - GAMEPLAY LOOP, continues until game session ends
			System.out.println("Class: ServerThread - Method: handleServerSocket:                Game started ");

			String recievedStringArray = "empty";
			int isStringArrayReady = 0;
			int isTransactionReciptReady = 0;

			while(true){

				isStringArrayReady = dataInObject.read();

				if(isStringArrayReady == 1){

					recievedStringArray = dataInObject.readUTF();
					System.out.println("Game loop:                    Received StringArray inital:" + recievedStringArray);


					if(!recievedStringArray.equals("empty")){
						System.out.println("Game loop:                    Received StringArray   "  + recievedStringArray);
						//System.out.println(recievedStringArray);
						String[] commandString = recievedStringArray.split(" ");
						//System.out.println(commandString[0]);
						//System.out.println(commandString[1]);
						//System.out.println(commandString[2]);

						isTransactionReciptReady = 1;
						System.out.println("isTransactionReciptReady: " + isTransactionReciptReady);
						dataOutObject.write(isTransactionReciptReady);
						dataOutObject.flush();

						if(commandString[0].equals("1")){
							System.out.println("You want to buy something");
							PurchaseOrSaleReceipt transactionReciptServer = this.liveTradingAccount.buy(commandString[1] , commandString[2]);
							System.out.println("You want to buy something, transactionRecipt : " + transactionReciptServer.toString());
							dataOutObject.writeObject(transactionReciptServer);
							dataOutObject.flush();

						}
						if(commandString[0].equals("2")){
							System.out.println("You want to sell something");
							PurchaseOrSaleReceipt transactionReciptServer = this.liveTradingAccount.sell(commandString[1] , commandString[2]);
							System.out.println("You want to buy something, transactionRecipt : " + transactionReciptServer.toString());
							dataOutObject.writeObject(transactionReciptServer);
							dataOutObject.flush();

						}

					}

					//this.currentLobby.naturalPriceFluctuations();



				}

				if(isNaturalFluctuationReady){
					dataOutObject.write(2);  //Use 2 to let client know they are expecting an array of objects
					dataOutObject.flush();

					dataOutObject.writeObject(naturalFluxationArray);
					dataOutObject.flush();

					setIsNaturalFluctuationReady(false);
				}else{
					dataOutObject.write(0);
					dataOutObject.flush();
				}

				if(isNewsReady){
					dataOutObject.write(3);  //Use 2 to let client know they are expecting an array of objects
					dataOutObject.flush();

					dataOutObject.writeObject(newsReceipt);
					dataOutObject.flush();


					newsReceipt = null;
					setIsNewsReady(false);
				}





				if(recievedStringArray.equals("end")){
					break;
				}

				recievedStringArray = "empty";

				Thread.sleep(1000);

			}
			
			
			

			dataInObject.close();
			dataOutObject.close();
	        
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	


        System.out.println("Class: ServerThread - Method: handleServerSocket \n Connection closed \n");
        serverSocket.close();

	}
    
 
    

}

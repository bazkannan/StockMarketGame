package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import javafx.application.Application;
import sample.MainScreen;
import sample.ScreenController2;
import sample.ScreenInterface;
import server.*;

/**
 * This class creates a thread on the clients machine to handle their interaction with the game
 * @author Thomas Baker
 *
 */
public class ClientAppThread extends Thread {
	
	Socket clientSocketThread;
    DataInputStream dIn;
    DataOutputStream dOut;



	String email;
    String username;
    String password;
    String confirmPassword;
    String lobbyCommand;
    String buyCommandString;
    String sellCommandString;

    String buyOrSellSendToServerStringArray;
    public String buyOrSellCommand;
    public String buyOrSellCommodityID;
    public String buyOrSellQuantity;


	PurchaseOrSaleReceipt transactionReciptClient = null;
    PurchaseOrSaleReceipt[] transactionReciptArrayClient = null;

    int loginOrSignup = 0;
    int userVerified;
    public boolean signUpSuccessful;
    public int isInGame = 0;
    int lobbyHost;
    boolean lobbyStart;
	String lobbyCodeForGUI;

	public Date gameStartTime = new Date();
	public double cashAvailable;
	public String[] gameCommodities;
	public Double[] prices;
	public Double[] pricesMinusOneDay;
	public int[] commoditiesOwnedquantity;
	public int[] latestPriceUpdate;

	public boolean rumourStringReady;
	public String rumourString;
	public String playersLeaderboard = "";
	public boolean historyStringReady = false;
	public String historyString = "";
	public String portfolioGUI = "";

	public static MainScreen GUI;


	public static CopyOnWriteArrayList<PurchaseOrSaleReceipt> unsortedRecievedRecipts = new CopyOnWriteArrayList<>();
	public static ArrayList<PurchaseOrSaleReceipt> GUIGold = new ArrayList<>();
	public static ArrayList<PurchaseOrSaleReceipt> GUIOil = new ArrayList<>();
	public static ArrayList<PurchaseOrSaleReceipt> GUISoybeans = new ArrayList<>();
	public static ArrayList<PurchaseOrSaleReceipt> GUISugar = new ArrayList<>();
//	public static ArrayList<Purchases> GUISilver = new ArrayList<>();
   // TradingAccount currentUserTradingAccount;

	public ClientAppThread(Socket clientSocket, ClientApp clientApp, MainScreen GUI) {
		this.clientSocketThread = clientSocket;
		//this.currentUserTradingAccount = null;
		this.GUI = GUI;
	}
	
	@Override
    public void run(){
		System.out.println("Class: ClientAppThread - Method: run \nClient Machine Thread Running.\n");
		try {
			handleLocalClientSocket();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setUsername(String str){
		this.username = str;
	}

	public void setPassword(String str){
		this.password = str;
	}

	public void setLobbyCommand(String number){
		this.lobbyCommand = number;
	}

	public void setLobbyStart(boolean command){
		lobbyStart = command;
		System.out.println(lobbyStart);
	}

	public void setLoginOrSignup(int x){
		loginOrSignup = x;
	}

	public boolean isUserVerified(){
		if(userVerified == 1){
			return true;
		}else{
			return false;
		}
	}

	public boolean isPlayHost(){
		if(lobbyHost == 1){
			return true;
		}else{
			return false;
		}
	}

	public String lobbyKeyForGUI(){
		return lobbyCodeForGUI;
	}

	public void setbuyOrSellCommand(String command){
		buyOrSellCommand = command;
		System.out.println("command: " + command);
	}
	public void setbuyOrSellCommodityID(String commodityID){
		buyOrSellCommodityID = commodityID;
		System.out.println("commodityID: " + commodityID);
	}
	public void setbuyOrSellQuantity(String quantity){
		buyOrSellQuantity = quantity;
		System.out.println("quantity: " + quantity);
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSignUpSuccessful(boolean x){
		this.signUpSuccessful = x;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void buyOrSellSendToServerStringArrayClear(){
		buyOrSellSendToServerStringArray = "empty";
		buyOrSellCommand = "empty";
		buyOrSellCommodityID = "empty";
		buyOrSellQuantity = "empty";

	}
	public void buyOrSellSendToServerStringArrayCompose(){
		String x = "";
		x += buyOrSellCommand + " ";
		x += buyOrSellCommodityID + " ";
		x += buyOrSellQuantity;
		buyOrSellSendToServerStringArray = x;
		//System.out.println("buyOrSellSendToServerStringArray: " + buyOrSellSendToServerStringArray);
	}

	public void sortPurchaseRecipt(){

		for(PurchaseOrSaleReceipt x: unsortedRecievedRecipts){

			int commodityID = x.getCommodityID();

			if(commodityID == 1){
				GUIGold.add(x);
				System.out.println("Received GOLD receipt");
			}
			else if(commodityID == 2){
				GUIOil.add(x);
				System.out.println("Received SILVER receipt");
			}
			else if(commodityID == 3){
				GUISoybeans.add(x);
				System.out.println("Received COPPER receipt");
			}
			else if(commodityID == 4){
				GUISugar.add(x);
				System.out.println("Received SUGAR receipt");
			}

			unsortedRecievedRecipts.remove(x);
		}

	}

	public String generatePortfolio(){

		String portfolio = "";

		for(int i = 1 ; i < 9 ; i++){

			if(commoditiesOwnedquantity[i] != 0){
				portfolio += gameCommodities[i] + " " + commoditiesOwnedquantity[i] + "\n";
			}
		}


		portfolioGUI = portfolio;
		return portfolio;
	}

	/**
	 * Method to track and and react to user input to the GUI, also waits to receive data from the server to break out of defined loops
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void handleLocalClientSocket() throws IOException, InterruptedException {

		lobbyStart = false;
		try {

			ObjectOutputStream dataOutObject = new ObjectOutputStream(clientSocketThread.getOutputStream());
			ObjectInputStream dataInObject = new ObjectInputStream(clientSocketThread.getInputStream());
			System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     dataIn and dataOut ready ");

			email = "empty";
			username = "empty";
			password = "empty";
			confirmPassword = "empty";

			// STEP 1 - Input username + password and sent to server

			//waiting for client to fill fields and press sign in
			// or move to sign up and press sign up
			while (true){

				if(loginOrSignup == 1 && !username.equals("empty") && !password.equals("empty")) {

					System.out.println("New username input detected: " + username);
					System.out.println("New username input detected: " + password);

					dataOutObject.write(loginOrSignup);
					dataOutObject.flush();

					dataOutObject.writeUTF(username);
					dataOutObject.flush();

					dataOutObject.writeUTF(password );
					dataOutObject.flush();

					System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     Waiting for Verification");

					int x = dataInObject.read();

					if (x == 1){
						userVerified = x;
						// Proves that server has created TradingAccount using username
						String serverAuthenticatedUsername = dataInObject.readUTF();
						System.out.println("Autheticated Username: " + serverAuthenticatedUsername);

						break;
					} else if (x == 0){
						System.out.println("ClientAppThread: Username or password is wrong");
					}
				}

				if(loginOrSignup == 2 && !email.equals("empty") && !username.equals("empty")
						&& !password.equals("empty") && !confirmPassword.equals("empty")){

					System.out.println("New signup detected. Sending details to server");

					// check if passwords match!!!! REMEMBER

					dataOutObject.write(loginOrSignup);
					dataOutObject.flush();

					dataOutObject.writeUTF(email);
					dataOutObject.flush();

					dataOutObject.writeUTF(username);
					dataOutObject.flush();

					dataOutObject.writeUTF(password );
					dataOutObject.flush();

					int x = dataInObject.read();

					//if sign up is successful, returns user to login screen
					if (x == 1){
						System.out.println("Signup successful, please now sign in: " + username);
						this.GUI.mainContainer.setScreen(MainScreen.login_signup);
					} else if(x == 0){
						System.out.println("Username '" + username + "' is already taken, please try again.");
					}
				}

				email = "empty";
				username = "empty";
				password = "empty";
				confirmPassword = "empty";
				loginOrSignup = 0;
				Thread.sleep(10);

			}




			
			System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     Login Successful");
			


			System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     Please create or join a lobby");



            // STEP 2 - WAITING TO EITHER PRESS JOIN LOBBY OR CREATE LOBBY
			//1 will create a lobby 2 will ask for code to join
			lobbyCommand = "empty";
			while(true){

				if(!lobbyCommand.equals("empty")){
					break;
				}

				lobbyCommand = "empty";

				Thread.sleep(100);
			}

			System.out.println("lobbyCommand: " + lobbyCommand);
			dataOutObject.writeUTF(lobbyCommand);
			dataOutObject.flush();

			// STEP 2.5 - WAITING FOR VERIFICATION FROM SERVER
			lobbyHost = 0;
			while(true) {

				DecimalFormat numbrFormat = new DecimalFormat("#.00");
				
				int x = dataInObject.read();
				
				if(x == 1) {
					System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     You have created a lobby");
					lobbyHost = 1;

					lobbyCodeForGUI = dataInObject.readUTF();
					System.out.println("lobbyCodeForGUI:" + lobbyCodeForGUI);

					break;
				}
				if(x == 2) {
					System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     You have joined a lobby");

					lobbyCodeForGUI = dataInObject.readUTF();
					System.out.println("Client received lobbyCodeForGUI:" + lobbyCodeForGUI);

					break;
				}
//				if (x == 3){
//					System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     Incorrect lobby code");
//				}
				
			}

			// This chunk of code is responsible for updating the GUI label for lobby code
			// for both HOSTS and JOINERS
            // maybe we can move this code somewhere else during cleanup phase
            ScreenInterface controller2 = GUI.mainContainer.getController(MainScreen.screen2ID);
            ((ScreenController2) controller2).updateLabel();


			System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():     Waiting for game to start");

			// STEP 3 (HOST PERSPECTIVE) Waiting for host to press button
			while(lobbyHost == 1) {

				if(lobbyStart) {
					dataOutObject.write(2);
					dataOutObject.flush();
					break;
				}

				Thread.sleep(10);
			}

			// STEP 3 (JOINER PERSPECTIVE) wait for server to send signal that lobby started
			while (lobbyHost != 1){

				int x = 0;
				x = dataInObject.read();

				if (x == 49){
					this.GUI.myBackend.clientThread.setLobbyStart(true);
					this.GUI.mainContainer.setScreen(MainScreen.screen3ID);
					break;
				}

				Thread.sleep(10);

			}
			
			System.out.println("Class: ClientAppThread - Method: handleLocalClientSocket():         GAME HAS STARTED");


			//STEP 4 - GAME LOOP TILL END OF SESSION
			//Prices and quantity of owned commodities by user, updated each time a receipt is received
			cashAvailable = 1000000;
			gameCommodities = new String[]{ "empty" , "Gold" , "Oil" , "Soybean" , "Sugar" , "Microsoft" ,"JPMorgan" , "RoyalDutch" , "Tesla" , "empty" };
			prices = new Double[]{0.0 , 1000.0 , 60.0 , 920.0 , 100.0 , 120.0 , 100.0 , 30.0 , 260.0 , 0.0 , 0.0};
			pricesMinusOneDay = new Double[]{0.0 , 1000.0 , 500.0 , 250.0 , 100.0 , 120.0 , 100.0 , 30.0 , 260.0 , 0.0 , 0.0};
			commoditiesOwnedquantity = new int[]{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
			latestPriceUpdate = new int[]{1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1};

			buyCommandString = "empty";
			sellCommandString = "empty";
			buyOrSellSendToServerStringArray = "empty";
			DecimalFormat numbrFormat = new DecimalFormat("#.00");

			int gameLoop = 0;
			int isStringArrayReady = 0;
			int isTransactionReciptReady = 0;
			isInGame = 1;
			Thread.sleep(500);

			gameStartTime = new Date();
			System.out.println(gameStartTime);

			while(true){

				isStringArrayReady = 0;
				//isTransactionReciptReady = 0;

				if(!buyOrSellSendToServerStringArray.equals("empty")){
					System.out.println("Game loop:                    Sending StringArray:     " + buyOrSellSendToServerStringArray);
					dataOutObject.write(1);
					dataOutObject.flush();
					dataOutObject.writeUTF(buyOrSellSendToServerStringArray);
					dataOutObject.flush();
					//buyOrSellSendToServerStringArrayClear();
				}else{
					dataOutObject.write(0);
					dataOutObject.flush();
				}


				isTransactionReciptReady = dataInObject.read();
				//System.out.println("Game loop:                    isTransactionReciptReady " + isTransactionReciptReady);

				if(isTransactionReciptReady == 1){

					if(transactionReciptClient == null){
						//System.out.println("Game loop:                    Trying to read object");

						try {

							//Read receipt object from server
							transactionReciptClient = (PurchaseOrSaleReceipt)(dataInObject.readObject());
							System.out.println("TransactionReceipt received : " + transactionReciptClient.toString());


							//Sort purchase receipt to commodity
							//unsortedRecievedRecipts.add(transactionReciptClient);
							//sortPurchaseRecipt();

							//Make changes to latest price of commodity and user new quantity if their purchase/sale
							int commodityIDTemp = transactionReciptClient.getCommodityID();
							pricesMinusOneDay[commodityIDTemp] = prices[commodityIDTemp];
							prices[commodityIDTemp] = transactionReciptClient.getPrice();
							//latestPriceUpdate[commodityIDTemp] = transactionReciptClient.getTimestamp();

							if(transactionReciptClient.getPlayerName().equals(username)){

								if(transactionReciptClient.getPurchaseOrSale().equals("buy")){
									commoditiesOwnedquantity[commodityIDTemp] = commoditiesOwnedquantity[commodityIDTemp] + transactionReciptClient.getQuantity();
									cashAvailable = cashAvailable - transactionReciptClient.getTransactionPlayerCost();
									System.out.println("Purchase successful: You acquired " + transactionReciptClient.getQuantity() + " " + gameCommodities[transactionReciptClient.getCommodityID()] + " for £" + transactionReciptClient.getTransactionPlayerCost());



									double costDouble = transactionReciptClient.getTransactionPlayerCost();
									String costString = String.valueOf( numbrFormat.format(costDouble));

									historyString = "B: " + gameCommodities[commodityIDTemp] + " " + transactionReciptClient.getQuantity() + " for £" + costString;
									historyStringReady = true;
									System.out.println(historyString);

								}else{
									commoditiesOwnedquantity[commodityIDTemp] = commoditiesOwnedquantity[commodityIDTemp] - transactionReciptClient.getQuantity();
									cashAvailable = cashAvailable + transactionReciptClient.getTransactionPlayerCost();
									System.out.println("Sale successful: You acquired " + transactionReciptClient.getQuantity() + " " + gameCommodities[transactionReciptClient.getCommodityID()] + " for £" + transactionReciptClient.getTransactionPlayerCost());

									double costDouble = transactionReciptClient.getTransactionPlayerCost();
									String costString = String.valueOf( numbrFormat.format(costDouble));
									historyString = "S: " + gameCommodities[commodityIDTemp] + " " + transactionReciptClient.getQuantity() + " for £" + costString;
									historyStringReady = true;
								}


							}

							//UPDATE GUI:    GUIPriceUpdate(commodityID, price , timestamp)
							//this.GUI.
							//this.GUI.ScreenController3.updaeGUI(transactionReciptClient.getCommodityID() , transactionReciptClient.getPrice() , transactionReciptClient.getTimestamp());


						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

						playersLeaderboard = transactionReciptClient.getPlayers();

						transactionReciptClient = null;


					}

				}

                if(isTransactionReciptReady == 2){

                    if(transactionReciptArrayClient == null){

                        try{

                            //Read receipt array object from server
                            transactionReciptArrayClient = (PurchaseOrSaleReceipt[])(dataInObject.readObject());
                            System.out.println("Server has updated all prices (Flux) ");

                            for( int i = 1 ; i < (transactionReciptArrayClient.length) ; i++){
								pricesMinusOneDay[i] = prices[i];
                                prices[i] = transactionReciptArrayClient[i].getPrice();
                                latestPriceUpdate[i] = transactionReciptArrayClient[i].getTimestamp();
                                //System.out.println("New Price of " + transactionReciptArrayClient[i].getCommodityID() + " " + gameCommodities[i] + " is " + prices[i] + " Timestamp: " + latestPriceUpdate[i]);
                                //System.out.println(transactionReciptArrayClient[i].toString());
                            }

                        }catch(ClassNotFoundException e){
                            e.printStackTrace();
                        }

						playersLeaderboard = transactionReciptArrayClient[1].getPlayers();

                        transactionReciptArrayClient = null;

                    }
                }

                //Expecting news
				if(isTransactionReciptReady == 3){

					if(transactionReciptClient == null){
						//System.out.println("Game loop:                    Trying to read object");

						try {

							//Read receipt object from server
							transactionReciptClient = (PurchaseOrSaleReceipt)(dataInObject.readObject());
							System.out.println("News received : " + transactionReciptClient.toString());


							if(transactionReciptClient.getPurchaseOrSale().equals("rumour")){

								rumourString = transactionReciptClient.getRumourMessage();
								rumourStringReady = true;

							}



						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}

						transactionReciptClient = null;


					}


				}



				if(gameLoop == 99){
					break;
				}

				if(isInGame == 0){
					System.out.println("ClientAppThread: Game over");

					String endGame = "end";
					dataOutObject.writeUTF(endGame);
					dataOutObject.flush();
					Thread.sleep(1000);

					break;
				}

				generatePortfolio();

				buyOrSellSendToServerStringArray = "empty";



				Thread.sleep(1000);
			}
			
			dataInObject.close();
	        dataOutObject.close();

	        //clientSocketThread.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
        
	}

	
	


}

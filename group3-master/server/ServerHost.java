package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHost {

	public int port = 8082;
	public static ArrayList<ServerThread> connectedClients = new ArrayList<>();
	public static ArrayList<ActiveLobby> activeLobbies = new ArrayList<>();

	public ServerHost(){
        try {
            // listening for new connections to port
            ServerSocket serverSocket = new ServerSocket(port);

            while(true){

                System.out.println("--------------------\nClass: ServerHost - Method: Constructor" +
                        " \n Server is running. Now awaiting connections... \n");

                // when it detects a new connection, create a new Socket
                Socket clientSocket = serverSocket.accept();

                // passes socket to new server thread, whilst also referencing itself
                ServerThread newClient = new ServerThread(clientSocket, this);

                newClient.start();

                // add the server connection to the ArrayList
                connectedClients.add(newClient);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	public static void main(String[] args){
        new ServerHost();
    }

}

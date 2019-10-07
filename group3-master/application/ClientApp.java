package application;


import javafx.application.Application;
import sample.MainScreen;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientApp {
	
	public static ClientAppThread clientThread;

	//public static Application GUI;

	

	
	Socket clientSocket;
	static int GUIProgress = 1;
	
	public ClientApp(MainScreen GUI) {
		try {
            Socket clientSocket = new Socket("localhost", 8082);

            
            clientThread = new ClientAppThread(clientSocket, this, GUI);
            clientThread.start();

            

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

}

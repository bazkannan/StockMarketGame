package sample;

import application.ClientApp;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainScreen extends Application {

    public static String login_signup = "main";
    public static String loginSignupFxml = "login_signup.fxml";
    public static String signUp = "signUp";
    public static String signUpFxml = "signUp.fxml";
    public static String screenPreLobbyFXML = "preLobby.fxml";
    public static String screenPreLobby = "preLobby";
    public static String screenLobbyLeadershipBoard = "lobby_leaderboard";
    public static String screenLobbyLeadershipBoardFXML = "lobby_leaderboard.fxml";
    public static String screen2ID = "screen2";
    public static String screen2File = "Screen2.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "Screen3.1.fxml";

    public static String screenEnd = "leaderBoard";
    public static String screenEndFXML = "LeaderBoard.fxml";




    public static ClientApp myBackend;
    public static ScreenMapping mainContainer;


    @Override
    public void start(Stage primaryStage) {

        // Creating the client logic
        myBackend = new ClientApp(this);

        // Creating the GUI
        mainContainer = new ScreenMapping(this);
        mainContainer.loadScreen(MainScreen.login_signup, MainScreen.loginSignupFxml);
        mainContainer.loadScreen(MainScreen.signUp, MainScreen.signUpFxml);
        mainContainer.loadScreen(MainScreen.screenPreLobby, MainScreen.screenPreLobbyFXML);
        mainContainer.loadScreen(MainScreen.screenLobbyLeadershipBoard, MainScreen.screenLobbyLeadershipBoardFXML);
        mainContainer.loadScreen(MainScreen.screen2ID, MainScreen.screen2File);
        mainContainer.loadScreen(MainScreen.screen3ID, MainScreen.screen3File);

        mainContainer.loadScreen(MainScreen.screenEnd, MainScreen.screenEndFXML);

        mainContainer.setScreen(MainScreen.login_signup);

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Stock Market Challenge Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}




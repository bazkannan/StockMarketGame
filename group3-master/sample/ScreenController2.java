package sample;

import java.net.URL;
import java.util.ResourceBundle;

import application.ClientAppThread;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class ScreenController2 implements Initializable, ScreenInterface {

    ScreenMapping mainContainer;

    public void setScreenParent(ScreenMapping screenParent) {
        mainContainer = screenParent;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        waitingPlayersFlash();
        codeAdminLobbyLabel();
        previousRules1.setVisible(false);
        previousRules2.setVisible(false);
        nextRules2.setVisible(false);
        nextRules1.setVisible(true);
    }


    @FXML
    private Label waitingPlayers;

    public void waitingPlayersFlash() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.8), waitingPlayers);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
    }

    @FXML
    public Button continueTrading;




    @FXML
    public void continueTradingButton(ActionEvent event) {

        //Only host returns true and can satisfy condition
        if (MainScreen.myBackend.clientThread.isPlayHost()) {
            MainScreen.myBackend.clientThread.setLobbyStart(true);
            mainContainer.setScreen(MainScreen.screen3ID);
        }

    }

    @FXML
    private Label codeAdminLobby;

    @FXML
    private void codeAdminLobbyLabel() {

        ClientAppThread myParentThread = mainContainer.mainScreen.myBackend.clientThread;
        String lobbyCode = String.valueOf(myParentThread.lobbyKeyForGUI());

        codeAdminLobby.setText(lobbyCode);
    }

    public void updateLabel() {

        ClientAppThread myParentThread = mainContainer.mainScreen.myBackend.clientThread;
        String lobbyCode = myParentThread.lobbyKeyForGUI();

        System.out.println("Current location: ScreenController2: ");
        System.out.println(lobbyCode);

        codeAdminLobby.setText(lobbyCode);
    }

    @FXML
    private Label rulesLabel;

    @FXML
    private ImageView previousRules1;

    @FXML
    private ImageView previousRules2;

    @FXML
    private ImageView nextRules1;

    @FXML
    private ImageView nextRules2;


    @FXML
    private void changeText(MouseEvent event) {

        rulesLabel.setText("Keep an eye on the news and note any upcoming events that might affect the way your stock trades");
        previousRules1.setVisible(true);
        nextRules1.setVisible(false);
        nextRules2.setVisible(true);

    }

    @FXML
    private void backText1(MouseEvent event) {

        rulesLabel.setText("Make as much money as you can in 10 minutes");
        previousRules1.setVisible(false);
        nextRules1.setVisible(true);
        nextRules2.setVisible(false);
    }

    @FXML
    private void backText2(MouseEvent event) {

        rulesLabel.setText("Keep an eye on the news and note any upcoming events that might affect the way your stock trades");
        previousRules2.setVisible(false);
        previousRules1.setVisible(true);
        nextRules1.setVisible(false);
        nextRules2.setVisible(true);
    }


    @FXML
    private void changeText2(MouseEvent event) {

        rulesLabel.setText("Remember fake news drives stocks too... you'll need to realise which sources are real");
        previousRules1.setVisible(false);
        previousRules2.setVisible(true);
        nextRules2.setVisible(false);

    }

    @FXML
    private void lobbyBack (ActionEvent event) {
        mainContainer.setScreen(MainScreen.screenPreLobby);
    }
}
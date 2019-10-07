package sample;


import application.ClientAppThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class preLobby implements Initializable, ScreenInterface {

    ScreenMapping mainContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        joinCode.setVisible(false);
        existingCodeText.setVisible(false);
        incorrectCode.setVisible(false);
        joinSubmit.setVisible(false);

        joinExisting.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (joinExisting.isSelected() == true) {
                    joinCode.setVisible(false);
                    existingCodeText.setVisible(false);
                    incorrectCode.setVisible(false);
                    joinSubmit.setVisible(false);
                } else {

                    joinCode.setVisible(true);
                    existingCodeText.setVisible(false);
                    incorrectCode.setVisible(false);
                    joinSubmit.setVisible(true);

                }


            }
        });


    }

    public void setScreenParent(ScreenMapping screenParent) {
        mainContainer = screenParent;
    }

    @FXML
    private void createLobbyButton(ActionEvent event) {

        MainScreen.myBackend.clientThread.setLobbyCommand("1");

        mainContainer.setScreen(MainScreen.screen2ID);



        // SOME CODE TO CHANGE LABEL OF SCREEN 2
        //ScreenInterface controller2 = mainContainer.getController(MainScreen.screen2ID);
        //((ScreenController2) controller2).updateLabel();

    }

    @FXML
    private Button goLeaderBoardButton;

    @FXML
    private void goToLeaderBoard(ActionEvent event) {
        mainContainer.setScreen(MainScreen.screenLobbyLeadershipBoard);

    }

    @FXML
    private Label existingCodeText;

    @FXML
    private TextField joinCode;

    @FXML
    private Button joinSubmit;

    @FXML
    private ToggleButton joinExisting;

//    public void joinLobbyButton(ActionEvent event) {
//
//        joinExisting.setOnAction(actionEvent -> {
//
//            if (joinExisting.isPressed()) {
//
//                joinCode.setVisible(true);
//                existingCodeText.setVisible(true);
//                joinSubmit.setVisible(true);
//
////            } else {
////                joinCode.setVisible(false);
////                existingCodeText.setVisible(false);
////                joinSubmit.setVisible(false);
//            }
//        });
//    }

    public void submitLobbyCodeButton(ActionEvent event) {



        ScreenInterface controller2 = mainContainer.getController(MainScreen.screen2ID);
        ((ScreenController2) controller2).continueTrading.setVisible(false);

        String lobbyCode = joinCode.getText();

        // sends code you just typed into the server
        MainScreen.myBackend.clientThread.setLobbyCommand(lobbyCode);



        mainContainer.setScreen(MainScreen.screen2ID);

    }


    public void clearErrorMessage(ActionEvent event) {

        existingCodeText.setVisible(true);
        incorrectCode.setVisible(false);

    }

    @FXML
    Label incorrectCode;


}



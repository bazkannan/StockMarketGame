package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class leaderBoard implements Initializable, ScreenInterface {

    ScreenMapping mainContainer;

    public void setScreenParent(ScreenMapping screenParent) {

        mainContainer = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private Button exitButton;

    @FXML
    private void exitGame (ActionEvent event) {

        exitButton.setOnAction(actionEvent -> Platform.exit());
    }

    @FXML
    private Button replayButton;

    @FXML
    private void replayGame (ActionEvent event) {

        replayButton.setOnAction(actionEvent ->

                mainContainer.setScreen(MainScreen.screenPreLobby));

    }

    @FXML
    private ListView leaderList;

    public void updateLeaderBoard(){
        leaderList.getItems().add(MainScreen.myBackend.clientThread.playersLeaderboard);
    }
}

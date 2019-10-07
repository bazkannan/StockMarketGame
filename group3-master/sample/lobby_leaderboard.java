package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class lobby_leaderboard implements Initializable, ScreenInterface {

    ScreenMapping mainContainer;

    public void setScreenParent(ScreenMapping screenParent) {

        mainContainer = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lobbyLeaderboard.getItems().add(
                "Player             Wins\n" +
                        "Tom              7\n" +
                        "Anfan            5\n" +
                        "Baz               4\n" +
                        "Rocky            3\n" +
                        "Thomas2        1\n" +
                        "Kam              0\n"
        );
    }

    @FXML
    private Button backLobbyButton;

    @FXML
    private void returnLobby (ActionEvent event) {

        backLobbyButton.setOnAction(actionEvent ->

                mainContainer.setScreen(MainScreen.screenPreLobby));

    }

    @FXML
    public ListView lobbyLeaderboard;

}

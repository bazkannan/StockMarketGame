package sample;

import application.ClientAppThread;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class login_signup implements Initializable, ScreenInterface {

    public static String usernameG;

    ScreenMapping mainContainer;

    private PasswordField Username = new PasswordField();
    private PasswordField Password = new PasswordField();
    private Button btAddUser = new Button("Login");

    public static String retrieveUsername() {
        System.out.println("changed" + usernameG);
        return usernameG;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        wrongpasswordmessage.setVisible(false);
    }

    public void setScreenParent(ScreenMapping screenParent) {
        mainContainer = screenParent;
    }

    @FXML
    private Label wrongpasswordmessage;

    @FXML
    private void goToPreLobby(ActionEvent event) {
        mainContainer.setScreen(MainScreen.screenPreLobby);
    }

    @FXML
    private Button logIn;

    @FXML
    private Button signUpButton;

    @FXML
    private Label userlabel;

    @FXML
    private TextField userfield;

    @FXML
    private PasswordField pswdfield;

    @FXML
    private void typingUserfield() {

        userfield.pressedProperty().addListener((o, old, newValue) ->

                wrongpasswordmessage.setVisible(false));
    }

    @FXML
    private void typingPasswordfield() {
        pswdfield.pressedProperty().addListener((o, old, newValue) ->

                wrongpasswordmessage.setVisible(false));
    }

    @FXML
    public void loginButton(ActionEvent event) {

        ClientAppThread myParentThread = this.mainContainer.mainScreen.myBackend.clientThread;

        // set 'LoginOrSignup in ClientAppThread to "1"
        myParentThread.setLoginOrSignup(1);

        String username = userfield.getText();
        String password = pswdfield.getText();
        System.out.println(username);
        System.out.println(password);

        myParentThread.setUsername(username);
        myParentThread.setPassword(password);

        // sleep and wait for response
        try {
            myParentThread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //
        if (myParentThread.isUserVerified()) {
            mainContainer.setScreen(MainScreen.screenPreLobby);
        } else {
            wrongpasswordmessage.setVisible(true);
        }

    }

    @FXML
    public void signUpButton(ActionEvent event) {

        ClientAppThread myParentThread = this.mainContainer.mainScreen.myBackend.clientThread;

        myParentThread.setLoginOrSignup(2);
        mainContainer.setScreen((MainScreen.signUp));
    }


    private void goToScreen2() {
        mainContainer.setScreen(MainScreen.screen2ID);
    }
}



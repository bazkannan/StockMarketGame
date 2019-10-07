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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class signUp implements Initializable, ScreenInterface {

    ScreenMapping mainContainer;

    public void setScreenParent(ScreenMapping screenParent) {
        mainContainer = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        emailAlreadyExists.setVisible(false);
        usernameTaken.setVisible(false);
        passwordsNotMatch.setVisible(false);

    }

    @FXML
    private Button signedUpBtn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label userlabel;

    @FXML
    private Label pswdlabel;

    @FXML
    private Label passwordsNotMatch;

    @FXML
    private Label emailAlreadyExists;

    @FXML
    private TextField emailAddressField;

    @FXML
    private void emailAddressTaken() {

        if ( emailAddressField.getText() == ("this")) {

            emailAlreadyExists.setVisible(true);
        }
        else {
            emailAlreadyExists.setVisible(false);
        }
    }

    @FXML
    private void usernameTaken() {

        if ( usernameField.getText() == ("this")) {

            usernameTaken.setVisible(true);
        }
        else {
            usernameTaken.setVisible(false);
        }
    }

    @FXML
    private Label usernameTaken;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Button goToLogin;

    @FXML
    private void backToLogin (ActionEvent event) {

        ClientAppThread myParentThread = this.mainContainer.mainScreen.myBackend.clientThread;

        myParentThread.setLoginOrSignup(0);
        myParentThread.setEmail("empty");
        myParentThread.setUsername("empty");
        myParentThread.setPassword("empty");
        myParentThread.setConfirmPassword("empty");

        mainContainer.setScreen((MainScreen.login_signup));
    }


    @FXML
    public void signUpBtn(ActionEvent event) {

        ClientAppThread myParentThread = mainContainer.mainScreen.myBackend.clientThread;

        myParentThread.setLoginOrSignup(2);

        String newEmail = emailAddressField.getText();
        String newUsername = usernameField.getText();
        String newPassword = passwordField.getText();
        String confirmPassword = passwordField2.getText();

        // if the two passwords match, update ClientAppThread
        if (newPassword.equals(confirmPassword)) {

            passwordsNotMatch.setVisible(false);

            myParentThread.setEmail(newEmail);
            myParentThread.setUsername(newUsername);
            myParentThread.setPassword(newPassword);
            myParentThread.setConfirmPassword(confirmPassword);

            try {
                MainScreen.myBackend.clientThread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // if account creation is successful, return to login screen
            if (myParentThread.signUpSuccessful) {
                mainContainer.setScreen((MainScreen.login_signup));
                myParentThread.setLoginOrSignup(0);
                myParentThread.setSignUpSuccessful(false);

            }

        } else {
            // if passwords dont match, display error message
            System.out.println("signUp (GUI): Passwords don't match, please try again.");
            passwordsNotMatch.setVisible(true);

        }


    }
}


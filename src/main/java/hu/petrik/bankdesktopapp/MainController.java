package hu.petrik.bankdesktopapp;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static hu.petrik.bankdesktopapp.MainPage.setActiveUser;


public class MainController {


    public Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button loginBtn;
    @FXML
    private VBox mainLoginBox;
    @FXML
    private TextField emailInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Text registerText;
    @FXML
    private Text loginErrorText;


    /**
     * Handles the login process and loads the MainPage.fxml if the login is successful.
     * If the login fails, it displays the login error message.
     *
     * @param event the ActionEvent triggered by a button press or other user interaction
     * @throws IOException if an input or output exception occurs during the loading of FXML
     * @throws InterruptedException if the thread is interrupted during the process
     */
    @FXML
    public void loadSecondFxml(ActionEvent event) throws IOException, InterruptedException {

        RestApi restApi = new RestApi();
        String response = restApi.login(emailInput.getText(),passwordInput.getText());

        if( response.equals("Login Error") )
        {
            System.out.print("Login failed\n");
            loginErrorText.visibleProperty().setValue(true);
        }
        else
        {

            setActiveUser(response);
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setHeight(625);
            stage.setWidth(1015);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.centerOnScreen();
            stage.show();
        

        }

    }

    /**
     * Handles the action of navigating to the registration page by loading the RegisterPage.fxml layout.
     * Sets the current stage's scene root to the registration page and adjusts window properties.
     *
     * @param event the Event triggered by user interaction, such as a button press
     * @throws IOException if an error occurs during the loading of the FXML file
     */
    @FXML
    public void registerPage(Event event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setMinHeight(400);
        stage.setMinWidth(300);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();


    }
}
package hu.petrik.bankdesktopapp;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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


    @FXML
    public void loadSecondFxml(ActionEvent event) throws IOException, InterruptedException {

        String response = RestApi.Login(emailInput.getText(),passwordInput.getText());

        if( response.equals("Login Error") )
        {
            System.out.printf("Login failed\n");
            loginErrorText.visibleProperty().setValue(true);
        }
        else
        {
            setActiveUser(response);
            Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setHeight(600);
            stage.setWidth(1000);

            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.centerOnScreen();
            stage.show();
        }

    }

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
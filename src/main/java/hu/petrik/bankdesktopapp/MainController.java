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
    public void loadSecondFxml(ActionEvent event) throws IOException, InterruptedException {



        if(  RestApi.Login(emailInput.getText(),passwordInput.getText()) == 500)
        {
            System.out.printf("Login failed\n");
        }
        else
        {
            root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            scene = new Scene(root, 1000, 400);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(1000);
            stage.show();
        }










    }

    @FXML
    public void registerPage(Event event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("RegisterPage.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(300);
        stage.show();
    }
}
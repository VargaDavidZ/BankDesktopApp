package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterPage {

    @javafx.fxml.FXML
    private Button registerBtn;
    @javafx.fxml.FXML
    private VBox mainLoginBox;
    @javafx.fxml.FXML
    private TextField lastNameInput;
    @javafx.fxml.FXML
    private PasswordField passwordInput;
    @javafx.fxml.FXML
    private TextField emailInput;
    @javafx.fxml.FXML
    private TextField firstNameInput;

    public Stage stage;
    @javafx.fxml.FXML
    private Button backBtn;


    @javafx.fxml.FXML
    public void registerAccount(Event event) throws IOException, InterruptedException {
        try{
            System.out.println(RestApi.CreateUser(firstNameInput.getText(),lastNameInput.getText(),emailInput.getText(),passwordInput.getText()));
            Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setMinHeight(400);
            stage.setMinWidth(300);
            stage.show();

        }
        catch(Exception e){
            e.toString();
        }
    }

    @javafx.fxml.FXML
    public void backToLogin(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setMinHeight(400);
        stage.setMinWidth(300);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }
}

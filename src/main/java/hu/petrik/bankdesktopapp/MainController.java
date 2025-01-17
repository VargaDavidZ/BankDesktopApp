package hu.petrik.bankdesktopapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MainController {


    public Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameInput;
    @FXML
    private VBox mainLoginBox;


    @FXML
    public void loadSecondFxml(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));



        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(root, 800, 400);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(800);

        stage.show();
    }
}
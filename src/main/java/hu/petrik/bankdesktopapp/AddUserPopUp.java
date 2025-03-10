package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class AddUserPopUp {
    @javafx.fxml.FXML
    private VBox mainLoginBox;
    @javafx.fxml.FXML
    private TextField emailInput;
    @javafx.fxml.FXML
    private Button closeWindowBtn;

    RestApi restApi = new RestApi();


    @javafx.fxml.FXML
    public void AddUserToCard(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        String email = emailInput.getText();
        restApi.connectUser(MainPage.getActiveBankAccount().getId(),email,MainPage.getActiveUser().getAuthToken());
        System.out.println(Arrays.toString(MainPage.getActiveBankAccount().getUsers()));
        stage.close();

    }

    @javafx.fxml.FXML
    public void CancelUserAdd(ActionEvent actionEvent) {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        stage.close();
    }
}

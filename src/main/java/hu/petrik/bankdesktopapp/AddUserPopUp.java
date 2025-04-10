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

    /**
     * Adds a user to the currently active bank account by sending the user's email
     * to the back-end service through the RestApi. Closes the popup window after the
     * operation completes.
     *
     * @param actionEvent the ActionEvent triggered when the user clicks the associated button
     * @throws IOException if an I/O error occurs during the HTTP request
     * @throws InterruptedException if the HTTP request is interrupted
     */
    @javafx.fxml.FXML
    public void AddUserToCard(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        String email = emailInput.getText();
        restApi.connectUser(MainPage.getActiveBankAccount().getId(),email,MainPage.getActiveUser().getAuthToken());
        System.out.println(Arrays.toString(MainPage.getActiveBankAccount().getUsers()));
        stage.close();

    }

    /**
     * Cancels the add user operation and closes the popup window.
     *
     * @param actionEvent the ActionEvent triggered when the user clicks the associated button
     */
    @javafx.fxml.FXML
    public void CancelUserAdd(ActionEvent actionEvent) {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        stage.close();
    }
}

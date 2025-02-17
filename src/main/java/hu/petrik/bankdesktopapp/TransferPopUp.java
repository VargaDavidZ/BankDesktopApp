package hu.petrik.bankdesktopapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferPopUp {

    @javafx.fxml.FXML
    private Button transferBtn;
    @javafx.fxml.FXML
    private VBox mainLoginBox;
    @javafx.fxml.FXML
    private TextField transferAccNum;
    @javafx.fxml.FXML
    private TextField transferAmount;
    @javafx.fxml.FXML
    private Button closeWindowBtn;

    @javafx.fxml.FXML
    public void CancelUserAdd(ActionEvent actionEvent) {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        stage.close();

    }


    @javafx.fxml.FXML
    public void transferAmountBtn(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("asd");
        RestApi restApi = new RestApi();
        restApi.Transfer(MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(), transferAccNum.getText(),Integer.parseInt(transferAmount.getText()),MainPage.getActiveUser().getAuthToken());
        MainPage mainPage = new MainPage();

    }
}

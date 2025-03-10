package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class TransferConfirmation {

    @javafx.fxml.FXML
    private VBox container;

    @javafx.fxml.FXML
    public void ConfirmTransfer(ActionEvent actionEvent) throws IOException, InterruptedException {
        TransferPopUp transferPopUp = new TransferPopUp();
        RestApi restApi = new RestApi();
        System.out.println("jasbdkadbabdakb");
        System.out.println(TransferPopUp.getAccNum() + " " + TransferPopUp.getAmount() + "asdsadadas");
        restApi.transfer(MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(), TransferPopUp.getAccNum(),Integer.parseInt(TransferPopUp.getAmount()),MainPage.getActiveUser().getAuthToken());

        Stage stage = (Stage) container.getScene().getWindow();
        stage.close();
    }


    @javafx.fxml.FXML
    public void CancelTransfer(ActionEvent actionEvent) {
        Stage stage = (Stage) container.getScene().getWindow();
        stage.close();
    }


}

package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class TransferConfirmation {

    @javafx.fxml.FXML
    private VBox container;

    /**
     * Handles the confirmation of a transfer operation. This method uses user input
     * provided in {@code TransferPopUp} to initiate a transfer transaction via the
     * {@code RestApi} class.
     *
     * It retrieves the account number, amount, and active user/bank account details,
     * then communicates with the backend API to perform the transaction. After completing
     * the transfer, the current window is closed.
     *
     * @param actionEvent The event triggered when the transfer confirmation action
     *                    is initiated, typically by clicking a button.
     * @throws IOException If an I/O error occurs while interacting with the system or application resources.
     * @throws InterruptedException If the thread executing the transfer process is interrupted.
     */
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

    /**
     * Handles the cancellation of a transfer operation. This method closes the current
     * stage or window associated with the transfer process.
     *
     * @param actionEvent The action event triggered when the cancel action is initiated.
     */
    @javafx.fxml.FXML
    public void CancelTransfer(ActionEvent actionEvent) {
        Stage stage = (Stage) container.getScene().getWindow();
        stage.close();
    }


}

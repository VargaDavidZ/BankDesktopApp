package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Confirmation {
    @javafx.fxml.FXML
    private Button confirm;

    RestApi restApi = new RestApi();

    public Button getConfirm() {
        return confirm;
    }

    public void setConfirm(Button confirm) {
        this.confirm = confirm;
    }

    /**
     * Handles the action of confirming a user request, such as removing or deleting a card
     * associated with the user's active bank account. This method performs validation to
     * check ownership of the bank account and communicates with a REST API to perform
     * the corresponding operation. It also handles closing the current stage after confirmation
     * or on encountering an exception.
     *
     * @param actionEvent the ActionEvent triggered when the confirmation button is clicked
     */
    @javafx.fxml.FXML
    public void confirmAction(ActionEvent actionEvent) {
        Stage stage = (Stage)confirm.getScene().getWindow();
        try {
            System.out.printf("ddd");

            if(MainPage.getActiveBankAccount().getOwnerId().equals(MainPage.getActiveUser().getId())) {
                restApi.deleteCardFromUser(MainPage.getActiveBankAccount().getId(),MainPage.getActiveUser().getAuthToken());
                MainPage.setActiveBankAccount(MainPage.getBankAccounts()[0]);
                //confirmation
                stage.close();
            }
            else
            {
                restApi.removeCardFromUser(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getId(), MainPage.getActiveUser().getAuthToken());
                stage.close();
            }

        } catch (IOException | InterruptedException ex) {
            stage.close();
            throw new RuntimeException(ex);

        }
    }

    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent) {
        System.out.printf("close");
        Stage stage = (Stage)confirm.getScene().getWindow();
        stage.close();
    }
}

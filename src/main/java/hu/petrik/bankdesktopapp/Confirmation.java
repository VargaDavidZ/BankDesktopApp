package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Confirmation {
    @javafx.fxml.FXML
    private Button confirm;


    public Button getConfirm() {
        return confirm;
    }

    public void setConfirm(Button confirm) {
        this.confirm = confirm;
    }


    @javafx.fxml.FXML
    public void confirmAction(ActionEvent actionEvent) {
        Stage stage = (Stage)confirm.getScene().getWindow();
        try {
            System.out.printf("ddd");

            if(MainPage.getActiveBankAccount().getOwnerId().equals(MainPage.getActiveUser().getId())) {
                RestApi.DeleteCardFromUser(MainPage.getActiveBankAccount().getId(),MainPage.getActiveUser().getAuthToken());
                MainPage.setActiveBankAccount(MainPage.getBankAccounts()[0]);
                //confirmation
                stage.close();
            }
            else
            {
                RestApi.RemoveCardFromUser(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getId(), MainPage.getActiveUser().getAuthToken());
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

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
            if(MainPage.getActiveBankAccount().getOwnerName().equals(MainPage.getActiveUser().getFirstname() + " " + MainPage.getActiveUser().getLastname())){
                RestApi.DeleteCardFromUser(MainPage.getActiveBankAccount().getId(),MainPage.getActiveUser().getAuthToken());
                //confirmation
                stage.close();
            }
            else
            {
                RestApi.RemoveCardFromUser(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getId(), MainPage.getActiveUser().getAuthToken());
                stage.close();
            }

        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent) {
        Stage stage = (Stage)confirm.getScene().getWindow();
        stage.close();
    }
}

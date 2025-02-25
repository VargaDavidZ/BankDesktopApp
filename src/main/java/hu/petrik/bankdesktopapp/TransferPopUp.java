package hu.petrik.bankdesktopapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    private static String accNum;
    private static String amount;

    @javafx.fxml.FXML
    public void CancelUserAdd(ActionEvent actionEvent) {
        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        stage.close();

    }


    @javafx.fxml.FXML
    public void transferAmountBtn(ActionEvent actionEvent) throws IOException, InterruptedException {


        Stage stage = (Stage)closeWindowBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("TransferConfirmation.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setMinHeight(400);
        stage.setMinWidth(300);
        stage.resizableProperty().setValue(Boolean.FALSE);

        accNum = transferAccNum.getText();
        amount = transferAmount.getText();
        System.out.println(accNum + " " + amount);
        stage.show();

    }

    public static String getAmount() {
        return amount;
    }

    public static String getAccNum() {
        return accNum;
    }


}

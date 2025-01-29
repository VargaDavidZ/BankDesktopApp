package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PopupWindow {
    @javafx.fxml.FXML
    private TextField desciptionInput;
    @javafx.fxml.FXML
    private TextField totalInput;
    @javafx.fxml.FXML
    private Button closeBtn;
    @javafx.fxml.FXML
    private RadioButton expenseRadioBtn;
    @javafx.fxml.FXML
    private RadioButton incomeRadioBtn;
    @javafx.fxml.FXML
    private ToggleGroup transactionType;
    @javafx.fxml.FXML
    private ComboBox<String> categoryInput;

    @javafx.fxml.FXML
    public Button addTransactionBtn;

    MainPage mainPage = new MainPage();
    @javafx.fxml.FXML
    private Label errorMsg;


    public void initialize() {
        categoryInput.getItems().addAll("Food", "Rent","Transport","Other");

    }

    @javafx.fxml.FXML
    public void CancelTransaction(ActionEvent actionEvent) {
        Stage stage = (Stage)closeBtn.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void AddTransaction(ActionEvent actionEvent) throws IOException, InterruptedException {

        try{
            Integer.parseInt(totalInput.getText());
        }
        catch(NumberFormatException e){
            System.out.println("Not a number");
            errorMsg.setText("Az összegnek számot kell megadni!");
            errorMsg.setVisible(true);
            return;
        }

        //kezelni ha a felhasználó nem ad meg Category értéket
       try{
           categoryInput.getValue().toString();
       }
       catch(NullPointerException e){
          categoryInput.getSelectionModel().select("Other");
       }

        if(expenseRadioBtn.isSelected()){
            RestApi.CreateExpense(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),"Spár",desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.GetActiveBankAccount().getId());

        }
        else if(incomeRadioBtn.isSelected()){
            RestApi.CreateIncome(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),"Spár",desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.GetActiveBankAccount().getId());

        }
        else
        {
            System.out.println("Select a transaction type");
        }

        Stage stage = (Stage)closeBtn.getScene().getWindow();
        stage.close();

    }
}

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
    public Button addTransactionBtn = new Button();

    MainPage mainPage = new MainPage();
    @javafx.fxml.FXML
    private Label errorMsg;


    //only show the correct category types for income/expense
    public void initialize() {
        categoryInput.getItems().setAll("Shopping", "Rent","Transport","Transaction" ,"Salary" ,"Other");


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
            RestApi.CreateExpense(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(),MainPage.getActiveUser().getAuthToken());

        }
        else if(incomeRadioBtn.isSelected()){
            RestApi.CreateIncome(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getAuthToken());

        }
        else
        {
            errorMsg.setText("Válassz tranzakció típust");
            return;
        }

        Stage stage = (Stage)closeBtn.getScene().getWindow();
        
        stage.close();

    }

    @javafx.fxml.FXML
    public void setExpenseCategories(ActionEvent actionEvent) {
        categoryInput.getItems().setAll("Shopping", "Rent","Transport","Transaction" ,"Other");

    }

    @javafx.fxml.FXML
    public void setIncomeCategories(ActionEvent actionEvent) {
        categoryInput.getItems().setAll("Transaction" ,"Salary" ,"Other");
    }


    public Button getAddTransactionBtn() {
        return addTransactionBtn;
    }

    public void setAddTransactionBtn(Button addTransactionBtn) {
        this.addTransactionBtn = addTransactionBtn;
    }

    public TextField getDesciptionInput() {
        return desciptionInput;
    }

    public void setDesciptionInput(TextField desciptionInput) {
        this.desciptionInput = desciptionInput;
    }

    public TextField getTotalInput() {
        return totalInput;
    }

    public void setTotalInput(TextField totalInput) {
        this.totalInput = totalInput;
    }

    public Button getCloseBtn() {
        return closeBtn;
    }

    public void setCloseBtn(Button closeBtn) {
        this.closeBtn = closeBtn;
    }

    public RadioButton getExpenseRadioBtn() {
        return expenseRadioBtn;
    }

    public void setExpenseRadioBtn(RadioButton expenseRadioBtn) {
        this.expenseRadioBtn = expenseRadioBtn;
    }

    public RadioButton getIncomeRadioBtn() {
        return incomeRadioBtn;
    }

    public void setIncomeRadioBtn(RadioButton incomeRadioBtn) {
        this.incomeRadioBtn = incomeRadioBtn;
    }

    public ToggleGroup getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ToggleGroup transactionType) {
        this.transactionType = transactionType;
    }

    public ComboBox<String> getCategoryInput() {
        return categoryInput;
    }

    public void setCategoryInput(ComboBox<String> categoryInput) {
        this.categoryInput = categoryInput;
    }

    public Label getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Label errorMsg) {
        this.errorMsg = errorMsg;
    }
}

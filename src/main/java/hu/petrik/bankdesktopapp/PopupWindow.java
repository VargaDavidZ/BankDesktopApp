package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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
    @javafx.fxml.FXML
    private CheckBox repeatableCheckBox;
    @javafx.fxml.FXML
    private DatePicker endDatePicker;
    @javafx.fxml.FXML
    private DatePicker startDatePicker;
    @javafx.fxml.FXML
    private VBox datePickerContainer;

    RestApi restApi = new RestApi();
    @javafx.fxml.FXML
    private ComboBox<String> metricInput;
    @javafx.fxml.FXML
    private TextField repeatAmountInp;
    @javafx.fxml.FXML
    private TextField title;

    /**
     * Initializes the necessary components for the popup window.
     * - Populates the category selection input with predefined types (e.g., Shopping, Rent, Transport, etc.).
     * - Calls the method to hide UI elements related to repeatable dependencies.
     */
    //only show the correct category types for income/expense
    public void initialize() {
        categoryInput.getItems().setAll("Shopping", "Rent","Transport","Transaction" ,"Salary" ,"Other");
        hideRepeatableDependencies();


    }

    /**
     * Closes the popup window associated with the ongoing transaction cancellation.
     *
     * @param actionEvent the event object associated with the action of canceling the transaction
     */
    @javafx.fxml.FXML
    public void CancelTransaction(ActionEvent actionEvent) {
        Stage stage = (Stage)closeBtn.getScene().getWindow();
        stage.close();
    }


    /**
     * Adds a transaction (income or expense) based on user input and closes the popup window.
     * Handles validation for missing or invalid inputs and delegates transaction creation to the API.
     *
     * @param actionEvent the event object associated with triggering the action
     * @throws IOException if an input or output error occurs during the API call
     * @throws InterruptedException if the thread executing the API request is interrupted
     */
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

            if(!repeatableCheckBox.isSelected()){
                System.out.println("defa");
                restApi.createExpense(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(),MainPage.getActiveUser().getAuthToken());

            }
            else{
                System.out.println("rep");
                restApi.createRepeatableTransaction(Float.parseFloat(totalInput.getText()),categoryInput.getValue(),desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(),Integer.parseInt(repeatAmountInp.getText()), metricInput.getValue(),startDatePicker.getValue(),endDatePicker.getValue() , title.getText() ,MainPage.getActiveUser().getAuthToken());

            }
        }
        else if(incomeRadioBtn.isSelected()){


                restApi.createIncome(Integer.parseInt(totalInput.getText()),categoryInput.getValue(),desciptionInput.getText(),MainPage.getActiveUser().getId(),MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getAuthToken());


        }
        else
        {
            errorMsg.setText("Válassz tranzakció típust");
            return;
        }

        Stage stage = (Stage)closeBtn.getScene().getWindow();
        
        stage.close();

    }

    /**
     * Sets predefined expense categories to the category input field and makes the repeatable checkbox visible and managed.
     *
     * @param actionEvent the event object associated with the action triggering this method
     */
    @javafx.fxml.FXML
    public void setExpenseCategories(ActionEvent actionEvent) {
        categoryInput.getItems().setAll("Shopping", "Rent","Transport","Transaction" ,"Other");
        repeatableCheckBox.setVisible(true);
        repeatableCheckBox.setManaged(true);

    }

    /**
     * Sets predefined income categories to the category input field and hides UI elements
     * associated with repeatable dependencies.
     *
     * @param actionEvent the event object associated with triggering this method
     */
    @javafx.fxml.FXML
    public void setIncomeCategories(ActionEvent actionEvent) {
        categoryInput.getItems().setAll("Transaction" ,"Salary" ,"Other");
        hideRepeatableDependencies();
    }

    /**
     * Hides UI components related to repeatable dependencies in the popup window.
     * Sets these components to be invisible and unmanaged, ensuring that they do not occupy space in the layout.
     * The affected components include the repeatable checkbox, metric input, title label, and date picker container.
     */
    public void hideRepeatableDependencies(){
        repeatableCheckBox.setVisible(false);
        repeatableCheckBox.setManaged(false);
        metricInput.setVisible(false);
        metricInput.setManaged(false);
        title.setVisible(false);
        title.setManaged(false);
        datePickerContainer.setVisible(false);
        datePickerContainer.setManaged(false);
    }

    /**
     * Displays the date picker container by making it visible and manageable in the user interface.
     * This method ensures that the date pickers are part of the UI layout and can be interacted with by the user.
     */
    public void showDatePickers(){
        datePickerContainer.setVisible(true);
        datePickerContainer.setManaged(true);
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

    @javafx.fxml.FXML
    public void showDatePickerContainer(ActionEvent actionEvent) {
        metricInput.setVisible(true);
        metricInput.setManaged(true);
        title.setVisible(true);
        title.setManaged(true);
        metricInput.getItems().setAll("Day", "Week","Month","Year");
        showDatePickers();
    }
}

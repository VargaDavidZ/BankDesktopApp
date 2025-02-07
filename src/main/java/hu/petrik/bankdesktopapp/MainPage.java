package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;



public class MainPage {
    Stage stage;
    @FXML
    private HBox valtasCard;
    @FXML
    private HBox myhbox;
    @FXML
    private AreaChart<String,Number> EurChart;
    private  BankAccount[] bankAccounts;
    RestApi api = new RestApi();
    private static User activeUser;
    private static BankAccount activeBankAccount;
    private float total;
    private final ArrayList<Transaction> transactionArray = new ArrayList<>();
    private int focusedItem = 0;

    @FXML
    private ListView expenseList;
    @FXML
    private ListView<AccountCard> cardList;
    @FXML
    private Text eurText;
    @FXML
    private Text eurChange;
    @FXML
    private Pane eurIndicator;
    @FXML
    private Text usdText;
    @FXML
    private Text usdChange;
    @FXML
    private Pane usdIndicator;
    @FXML
    private VBox leftContainer;
    @FXML
    private HBox innerLeftContainer;
    @FXML
    private AreaChart<String, Number> UsdChart;
    @FXML
    private PieChart myPieChart;
    @FXML
    private HBox lineChartContainer;
    @FXML
    private VBox currencyExchangeContainer;
    @FXML
    private Button swapLeftContainerBtn;
    @FXML
    private StackPane myStackPane;
    @FXML
    private CategoryAxis asd;
    @FXML
    private VBox pieChartContainer;
    @FXML
    private VBox mainPage;
    @FXML
    private ImageView usdImg;
    @FXML
    private ImageView eurImg;

    private boolean pieChartIncome = false;
    private boolean pieChartExpense = true;
    private boolean pieChartCombined = false;

    //javadoc documentation
    public static void SetActiveUser(String activeUserInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        activeUser = mapper.readValue(activeUserInput,User.class);

    }

    public void initialize() throws IOException, InterruptedException {

        bankAccounts = api.GetAllBankAccounts(activeUser);
        cardList.setOrientation(Orientation.HORIZONTAL);

        Float currentEur = api.GetEur(0).getValue().get("huf");
        Float yesterdayEur = api.GetEur(1).getValue().get("huf");
        float change = ((currentEur-yesterdayEur) / currentEur * 100);
        Float currentUsd = api.GetUsd(0).getValue().get("huf");
        Float yesterdayUsd = api.GetUsd(1).getValue().get("huf");
        float changeUsd = ((currentUsd-yesterdayUsd) / currentUsd * 100);

        pieChartContainer.setVisible(false);
        //display EUR-HUF exchange rate and change %
        eurText.setText((Math.round(currentEur*100)/100) + " Ft");
        eurChange.setText("+"+Math.round(change*100)/100.0 + "%");
        if(currentEur-yesterdayEur < 0)
        {
            eurText.setText((Math.round(currentEur*100)/100) + " Ft");
            eurIndicator.setStyle("-fx-background-color: red;");
            eurChange.setText(Math.round(change*100)/100.0 + "%");
            eurChange.setStyle("-fx-fill: red;");
            eurImg.setImage(new Image(MainPage.class.getResourceAsStream("currencyNegInd.png")));
        }

        if(bankAccounts.length != 0) {
            activeBankAccount = bankAccounts[0];
        }


        //display USD-HUF exchange rate and change %
        System.out.printf(currentUsd + "---");
        usdText.setText((Math.round(currentUsd*100)/100) + "Ftttt");
        usdChange.setText("+"+Math.round(changeUsd*100)/100.0 + "%");
        if(currentUsd-yesterdayUsd < 0)
        {
            usdText.setText(Math.round(currentUsd * 100) / 100 + "Ft");
            usdIndicator.setStyle("-fx-background-color: #FF2424;");
            usdChange.setText(Math.round(changeUsd*100)/100.0 + "%");
            usdChange.setStyle("-fx-fill: #FF2424;");
            usdImg.setImage(new Image(MainPage.class.getResourceAsStream("currencyNegInd.png")));
        }

        if(bankAccounts.length != 0) {
            activeBankAccount = bankAccounts[0];
        }

        activeBankAccount.setExpenses(api.GetAccountExpenses(activeBankAccount.getId(), activeUser.getAuthToken()));
        activeBankAccount.setIncome(api.GetAccountIncomes(activeBankAccount.getId(), activeUser.getAuthToken()));

        CreatedSortedArray();
        ListTransactions();
        ListCards();

        Platform.runLater(() -> {
            cardList.getFocusModel().focus(0);
            try {
                CalcActiveTotal();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            cardList.getFocusModel().getFocusedItem().changeTotal(total);
            try {
                LoadCharts(15);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public void SetActiveAccount(BankAccount inpAcc) {
        activeBankAccount = inpAcc;
    }

    public void ListCards()
    {

        cardList.getItems().clear();
        for (int i = 0; i < bankAccounts.length; i++) {

            cardList.getItems().add(new AccountCard(bankAccounts[i],activeUser));
        }

        cardList.getFocusModel().focus(focusedItem);
        cardList.getItems().add(new AccountCard());
    }

    public void CreatedSortedArray(){
        transactionArray.addAll(Arrays.stream(activeBankAccount.getExpenses()).toList());
        transactionArray.addAll(Arrays.stream(activeBankAccount.getIncome()).toList());
        transactionArray.sort(new Comparator<Transaction>() {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });
    }

    public void ListTransactions()
    {
        for (int i = 0; i < transactionArray.size(); i++) {
            expenseList.getItems().add(0,new TransactionComponent(transactionArray.get(i)));
        }

        expenseList.scrollTo(0);
    }

    public void RefreshTransactions() throws IOException, InterruptedException {
        expenseList.getItems().clear();
        transactionArray.clear();
        activeBankAccount.setExpenses(api.GetAccountExpenses(activeBankAccount.getId(),activeUser.getAuthToken()));
        activeBankAccount.setIncome(api.GetAccountIncomes(activeBankAccount.getId(),activeUser.getAuthToken()));

        CreatedSortedArray();
        System.out.print("Refreshing transactions...");

        ListTransactions();
        CalcActiveTotal();
        cardList.getFocusModel().getFocusedItem().changeTotal(total);
        expenseList.refresh();
    }

    @FXML
    public void LogOut(ActionEvent event) throws IOException {
        activeUser = null;
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setHeight(400);
        stage.setWidth(300);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void PopUpWindow(ActionEvent actionEvent) throws IOException {

       Stage popupStage = new Stage();
       popupStage.initModality(Modality.APPLICATION_MODAL);
       Parent root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
       Scene popupScene = new Scene(root);
       popupStage.setScene(popupScene);
       popupStage.resizableProperty().setValue(Boolean.FALSE);
       popupStage.show();

       popupStage.setOnHidden(event -> {
           try {
               RefreshTransactions();
           } catch (IOException | InterruptedException e) {
               throw new RuntimeException(e);
           }
       });

    }


    public BankAccount[] getBankAccounts() {
        return bankAccounts;
    }

    public static BankAccount GetActiveBankAccount() {
        return activeBankAccount;
    }

    public void CalcActiveTotal() throws IOException, InterruptedException {
        float income = 0;
        float expense = 0;
        for (int i = 0; i < activeBankAccount.getExpenses().length; i++) {

           expense += activeBankAccount.getExpenses()[i].getTotal();
        }

        for (int i = 0; i < activeBankAccount.getIncome().length; i++) {

            income += activeBankAccount.getIncome()[i].getTotal();
        }
        total = income - expense;

        UpdatePieChart();
        //total = activeBankAccount.getTotal();

        RestApi.UpdateTotal(activeBankAccount.getId(),total,activeUser.getAuthToken());
    }

    public static User getActiveUser() {
        return activeUser;
    }


    @FXML
    public void ChangeActiveBankAccount(Event event) throws IOException, InterruptedException {
        try
        {
            SetActiveAccount(bankAccounts[cardList.getFocusModel().getFocusedIndex()]);
            focusedItem = cardList.getFocusModel().getFocusedIndex();

        }
        catch (Exception e)
        {
            AddNewAcc();
            return;
        }

        RefreshTransactions();
        CalcActiveTotal();
        RefreshCards();
        cardList.getFocusModel().getFocusedItem().changeTotal(total);

    }

    public void RefreshCards() throws IOException, InterruptedException {

        bankAccounts = api.GetAllBankAccounts(activeUser);
        ListCards();
    }

    public void AddNewAcc() throws IOException, InterruptedException {
        RestApi.CreateAccount(activeUser.getId(),"HUF",activeUser.getFirstname(),activeUser.getLastname(),activeUser.getAuthToken());
        RefreshCards();
    }

    @Deprecated
    public void currencyExchangeBtn(ActionEvent actionEvent) {

    }

    public void LoadCharts(int daysToShow) throws IOException, InterruptedException {
        XYChart.Series<String, Number> eurSeries = new XYChart.Series<String, Number>();
        XYChart.Series<String, Number> usdSeries = new XYChart.Series<String, Number>();
        int days = daysToShow;
        Float f = 100.5F;

        for (int i = 0; i < daysToShow; i++) {
            //series.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));
            eurSeries.getData().add(new XYChart.Data<>(Integer.toString(i), api.GetEur(days).getValue().get("huf")));
            usdSeries.getData().add(new XYChart.Data<>(Integer.toString(i), api.GetUsd(days).getValue().get("huf")));
            days--;

        }

        UsdChart.getYAxis().setAutoRanging(false);
        ((NumberAxis)UsdChart.getYAxis()).setUpperBound(450);
        ((NumberAxis)UsdChart.getYAxis()).setLowerBound(380);

        EurChart.getYAxis().setAutoRanging(false);
        ((NumberAxis)EurChart.getYAxis()).setUpperBound(450);
        ((NumberAxis) EurChart.getYAxis()).setLowerBound(380);

        EurChart.getData().add(eurSeries);
        UsdChart.getData().add(usdSeries);

    }

    public void UpdatePieChart() throws IOException, InterruptedException {

        if(pieChartExpense == true)
        {
            pieChartToExpense();
        }
        else if(pieChartIncome == true)
        {
            PieChartToIncome();
        }
        else{
            PieChartToIncomeExpense();

        }
       // myPieChart.setLegendVisible(false);
        
    }



    @FXML
    public void SwapLeftContainer(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(!pieChartContainer.isVisible())
        {
            UpdatePieChart();
            swapLeftContainerBtn.setText("Árfolyam");
            currencyExchangeContainer.setVisible(false);
            //myPieChart.setVisible(true);
            pieChartContainer.setVisible(true);
            System.out.println(pieChartContainer.isVisible());
            pieChartContainer.setTranslateZ(2);

            myPieChart.setLegendVisible(false);
            /*myStackPane.layout();
            pieChartContainer.layout();

             */
        }
        else {
            swapLeftContainerBtn.setText("Eloszlás");
            currencyExchangeContainer.setVisible(true);
            //myPieChart.setVisible(false);

            pieChartContainer.setVisible(false);
        }
    }

    @FXML
    public void showValuePieChart(Event event) {
        final Label caption = new Label("");
       // caption.setText(String.valueOf(myPieChart.getPieValue()) + "%");
    }

    @FXML
    public void AccountInfo(ActionEvent actionEvent) {
    }

    @FXML
    public void PieChartToIncomeExpense() {
        pieChartIncome = false;
        pieChartExpense = false;
        pieChartCombined = true;

        int income = 0;
        int expense = 0;

        for (int i = 0; i < transactionArray.size(); i++) {
            if(transactionArray.get(i).getClass() == Income.class) {
                income+=transactionArray.get(i).getTotal();
            }
            else
            {
                expense+=transactionArray.get(i).getTotal();
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        if(income != 0)
        {
            pieChartData.add(new PieChart.Data("Bevétel", income));
        }

        if(expense != 0)
        {
            pieChartData.add(new PieChart.Data("Kiadás", expense));
        }

        myPieChart.setData(pieChartData);


    }

    @FXML
    public void PieChartToIncome() {
        pieChartIncome = true;
        pieChartExpense = false;
        pieChartCombined = false;
        float salary = 0;
        float transaction = 0;
        float other = 0;

        for (int i = 0; i < activeBankAccount.getIncome().length; i++) {
            if(activeBankAccount.getIncome()[i].getCategory().equals("Transaction")){
                transaction += activeBankAccount.getIncome()[i].getTotal();

            }
            else if(activeBankAccount.getIncome()[i].getCategory().equals("Other")){
                other += activeBankAccount.getIncome()[i].getTotal();
            }
            else if(activeBankAccount.getIncome()[i].getCategory().equals("Salary")){
                salary += activeBankAccount.getIncome()[i].getTotal();
            }

        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


        if(salary != 0)
        {
            pieChartData.add(new PieChart.Data("Salary", salary));
        }
        if(transaction != 0){
            pieChartData.add(new PieChart.Data("Transaction", transaction));
        }
        if(other != 0){
            pieChartData.add(new PieChart.Data("Other", other));
        }

        myPieChart.setData(pieChartData);
    }

    @FXML
    public void pieChartToExpense() {
        pieChartIncome = false;
        pieChartExpense = true;
        pieChartCombined = false;
        float shopping = 0;
        float rent = 0;
        float transport = 0;
        float transaction = 0;
        float other = 0;

        for (int i = 0; i < activeBankAccount.getExpenses().length; i++) {
            if(activeBankAccount.getExpenses()[i].getCategory().equals("Shopping")){
                shopping += activeBankAccount.getExpenses()[i].getTotal();
            }
            else if(activeBankAccount.getExpenses()[i].getCategory().equals("Rent")){
                rent += activeBankAccount.getExpenses()[i].getTotal();
            }
            else if(activeBankAccount.getExpenses()[i].getCategory().equals("Transport")){
                transport += activeBankAccount.getExpenses()[i].getTotal();
            }
            else if(activeBankAccount.getExpenses()[i].getCategory().equals("Transaction")){
                transaction += activeBankAccount.getExpenses()[i].getTotal();

            }
            if(activeBankAccount.getExpenses()[i].getCategory().equals("Other")){
                other += activeBankAccount.getExpenses()[i].getTotal();
            }

        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();


        if(shopping != 0)
        {
            pieChartData.add(new PieChart.Data("Shopping", shopping));
        }
        if(rent != 0)
        {
            pieChartData.add(new PieChart.Data("Rent", rent));
        }
        if(transport != 0)
        {
            pieChartData.add(new PieChart.Data("Transport", transport));
        }
        if(transaction != 0){
            pieChartData.add(new PieChart.Data("Transaction", transaction));
        }
        if(other != 0){
            pieChartData.add(new PieChart.Data("Other", other));
        }



        myPieChart.setData(pieChartData);
    }
}


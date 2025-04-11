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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class MainPage {
    Stage stage;
    @FXML
    private HBox valtasCard;
    @FXML
    private HBox myhbox;
    @FXML
    private AreaChart<Number,Number> EurChart;
    @FXML
    private AreaChart<Number, Number> UsdChart;
    private static BankAccount[] bankAccounts = new BankAccount[0];
    RestApi api = new RestApi();
    private static User activeUser;
    private static BankAccount activeBankAccount;


    private float total;
    private final ArrayList<Transaction> transactionArray = new ArrayList<>();
    private int focusedItem = 0;

    @FXML
    private ListView transactionListView;
    @FXML
    private ListView<AccountCard> cardList = new ListView<>();
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
    @FXML
    private ImageView swapLeftContainerBtnImageView;
    @FXML
    private ImageView swapLeftContainerBtnImageView1;
    @FXML
    private Text ethText;
    @FXML
    private HBox lineChartContainer1;
    @FXML
    private Text btcChange;
    @FXML
    private Text ethChange;
    @FXML
    private VBox cryptoContainer;
    @FXML
    private Text btcText;
    @FXML
    private Pane btcIndicator;
    @FXML
    private Pane ethIndicator;
    @FXML
    private HBox cryptoCards;
    @FXML
    private ImageView ethImg;
    @FXML
    private ImageView btcImg;
    @FXML
    private Button cryptoBtn;
    @FXML
    private AreaChart BtcChart;
    @FXML
    private AreaChart EthChart;

    private static Transaction focusedTransaction;

    private boolean expenseFilter = false;
    private boolean incomeFilter = false;

    ArrayList<Transaction> expenseList = new ArrayList<>();
    ArrayList<Transaction> incomeList = new ArrayList<>();

    XYChart.Series<Number, Number> btcSeries = new XYChart.Series<Number, Number>();
    XYChart.Series<Number, Number> ethSeries = new XYChart.Series<Number, Number>();

    private  ArrayList<Transaction> transactions = new ArrayList<>();
    //duplicate card at user
    //confirmation on delete and transfer

    /**
     * Initializes the main page by fetching user accounts, transactions, and charts.
     *
     * @throws IOException If there is an issue accessing the REST API or resources.
     * @throws InterruptedException If data fetching is interrupted.
     */

    public void initialize() throws IOException, InterruptedException {

        cardList.setOrientation(Orientation.HORIZONTAL);
        this.bankAccounts = api.getAllBankAccounts(activeUser);

        System.out.println("Active User Id"+activeUser.getId());

        cryptoContainer.setVisible(false);
        pieChartContainer.setVisible(false);
        //display EUR-HUF exchange rate and change %


        if(bankAccounts.length != 0) {
            activeBankAccount = bankAccounts[0];
        }


      //  System.out.println(activeBankAccount.toString());
        System.out.println("Active acc id " +activeBankAccount.getId());
        System.out.println(activeUser.getAuthToken());
        //display USD-HUF exchange rate and change %
        //System.out.printf(currentUsd + "---");

        activeBankAccount.setExpenses(api.getAccountExpenses(activeBankAccount.getId(), activeUser.getAuthToken()));

        activeBankAccount.setIncome(api.getAccountIncomes(activeBankAccount.getId(), activeUser.getAuthToken()));

        createdSortedArray();
        listTransactions();
        listCards();

        //LoadCurrencyData(15);

        incomeList.addAll(Arrays.stream(activeBankAccount.getIncome()).toList());
        expenseList.addAll(Arrays.stream(activeBankAccount.getExpenses()).toList());
        Platform.runLater(() -> {
            cardList.getFocusModel().focus(0);
            transactionListView.getFocusModel().focus(0);
            try {
                setCurrencyCards();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(activeBankAccount.getExpenses()[0].toString() + "---------");
            try {
                calcActiveTotal();

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            cardList.getFocusModel().getFocusedItem().changeTotal(total, activeBankAccount.getCurrency());
            cardList.getFocusModel().getFocusedItem().getHamburgerMenu().setDisable(false);

            try {
                api.updateRepeatableTransaction(activeBankAccount.getId(),activeUser.getId(), activeUser.getAuthToken());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                loadCharts(15);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }


        });

    }

    /**
     * Sets the active user for the application based on JSON input.
     *
     * @param activeUserInput The JSON string containing the user data.
     * @throws JsonProcessingException If the input cannot be parsed into a `User` object.
     */

    public static void setActiveUser(String activeUserInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        activeUser = mapper.readValue(activeUserInput,User.class);

    }

    /**
     * Sets the input account for the application as the active account.
     *
     * @param inpAcc The bank account to set as active.
     */

    public void SetActiveAccount(BankAccount inpAcc) {
        activeBankAccount = inpAcc;
    }

    /**
     * Gets the total balance for the current account.
     *
     * @return The total balance.
     */

    public float getTotal() {
        return total;
    }

    /**
     * Sets the total balance for the current account.
     *
     * @param total The total balance amount.
     */

    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * Lists all bank accounts for the active user and updates the card list in the UI.
     *
     * @throws FileNotFoundException If the account data cannot be located or accessed.
     */

    public void listCards() throws FileNotFoundException {

        cardList.getItems().clear();

        for (int i = 0; i < bankAccounts.length; i++) {

            cardList.getItems().add(new AccountCard(bankAccounts[i],activeUser));
        }

        cardList.getFocusModel().focus(focusedItem);
        cardList.getItems().add(new AccountCard());
    }


    /**
     * Sorts the transaction array by date
     */
    public void createdSortedArray(){
        transactionArray.addAll(Arrays.stream(activeBankAccount.getExpenses()).toList());
        transactionArray.addAll(Arrays.stream(activeBankAccount.getIncome()).toList());
        transactionArray.sort(new Comparator<Transaction>() {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

    }


    /**
     * Filters and displays the transactions based on the active filter (e.g., income, expense).
     */

    public void listTransactions() {
        /*
        if(incomeFilter)
        {
            for (int i = 0; i < incomeList.size(); i++) {
                //System.out.println(transactionArray.get(i).getTotal());
                transactionListView.getItems().add(0,new TransactionComponent(incomeList.get(i)));
            }
        }
        else if(expenseFilter)
        {
            for (int i = 0; i < expenseList.size(); i++) {
                transactionListView.getItems().add(0,new TransactionComponent(expenseList.get(i)));
            }
        }
        else{
            for (int i = 0; i < transactionArray.size(); i++) {

                transactionListView.getItems().add(0,new TransactionComponent(transactionArray.get(i)));
            }
        }

         */
        for (int i = 0; i < transactionArray.size(); i++) {

            transactionListView.getItems().add(0,new TransactionComponent(transactionArray.get(i)));
        }


        transactionListView.scrollTo(0);
    }


    /**
     * Fetches the latest data from the database and refreshes the transaction list.
     *
     * @throws IOException If there is an issue accessing the REST API.
     * @throws InterruptedException If the thread is interrupted.
     */

    public void refreshTransactions() throws IOException, InterruptedException {
        transactionListView.getItems().clear();
        transactionArray.clear();
        activeBankAccount.setExpenses(api.getAccountExpenses(activeBankAccount.getId(),activeUser.getAuthToken()));
        activeBankAccount.setIncome(api.getAccountIncomes(activeBankAccount.getId(),activeUser.getAuthToken()));

        createdSortedArray();
        System.out.print("Refreshing transactions...");

        listTransactions();
        calcActiveTotal();
        cardList.getFocusModel().getFocusedItem().changeTotal(total, activeBankAccount.getCurrency());
        transactionListView.refresh();
    }

    /**
     * Logs the user out of the application and returns to the login screen.
     *
     * @param event The logout action event.
     * @throws IOException If the login screen cannot be loaded.
     */
    @FXML
    public void logOut(ActionEvent event) throws IOException {
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

    /**
     * Displays a pop-up window to create a new transaction for the active bank account.
     *
     * @param actionEvent The action event triggering the pop-up.
     * @throws IOException If the pop-up cannot be opened.
     */
    @FXML
    public void popUpWindow(ActionEvent actionEvent) throws IOException {

       Stage popupStage = new Stage();
       popupStage.initModality(Modality.APPLICATION_MODAL);
       Parent root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
       Scene popupScene = new Scene(root);
       popupStage.setScene(popupScene);
       popupStage.resizableProperty().setValue(Boolean.FALSE);
       popupStage.show();

       popupStage.setOnHidden(event -> {
           try {
               refreshTransactions();
           } catch (IOException | InterruptedException e) {
               throw new RuntimeException(e);
           }
       });
    }

    /**
     * @return bankAccounts
     */
    public static BankAccount[] getBankAccounts() {
        return bankAccounts;
    }

    /**
     * @return activeBankAccount
     */
    public static BankAccount getActiveBankAccount() {
        return activeBankAccount;
    }


    /**
     * sets the active bank account
     * @param activeBankAccount
     */
    public static void setActiveBankAccount(BankAccount activeBankAccount) {
        MainPage.activeBankAccount = activeBankAccount;
    }


    /**
     * Calculates and displays the total balance for the active bank account
     * by summing all incomes and subtracting expenses.
     */

    public void calcActiveTotal() throws IOException, InterruptedException {
        float income = 0;
        float expense = 0;
        for (int i = 0; i < activeBankAccount.getExpenses().length; i++) {

           expense += activeBankAccount.getExpenses()[i].getTotal();
        }

        for (int i = 0; i < activeBankAccount.getIncome().length; i++) {

            income += activeBankAccount.getIncome()[i].getTotal();
        }
        total = income - expense;
        activeBankAccount.setTotal(total);
        updatePieChart();
        //total = activeBankAccount.getTotal();

        //RestApi.UpdateTotal(activeBankAccount.getId(),total,activeUser.getAuthToken());
    }

    /**
     * returns the active user
     * @return activeUser
     */
    public static User getActiveUser() {
        return activeUser;
    }


    /**
     * Retrieves the list view containing account cards.
     *
     * @return a ListView of AccountCard objects representing the card list
     */
    public  ListView<AccountCard> getCardList() {
        return cardList;
    }

    /**
     * Updates the cardList with the specified ListView of AccountCard objects.
     *
     * @param cardList the ListView containing AccountCard instances to set
     */
    public void setCardList(ListView<AccountCard> cardList) {
        this.cardList = cardList;
    }

    /**
     * Changes the active bank account
     * Fetches the bank account's data from the database
     * Lists the bank account's transactions
     * @param event
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void changeActiveBankAccount(Event event) throws IOException, InterruptedException {
        try
        {
            SetActiveAccount(bankAccounts[cardList.getFocusModel().getFocusedIndex()]);
            transactionListView.getItems().clear();
            listTransactions();
            focusedItem = cardList.getFocusModel().getFocusedIndex();

        }
        catch (Exception e)
        {
            addNewAcc();
            return;
        }

        refreshTransactions();
        calcActiveTotal();
        refreshCards();
        cardList.getFocusModel().getFocusedItem().changeTotal(total, activeBankAccount.getCurrency());
        cardList.getFocusModel().getFocusedItem().getHamburgerMenu().setDisable(false);

        cardList.getFocusModel().getFocusedItem().getDeleteMenuOpt().setOnAction( e -> {

            //RestApi.RemoveCardFromUser(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getId(), MainPage.getActiveUser().getAuthToken());
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("confirmation.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene popupScene = new Scene(root);
            popupStage.setScene(popupScene);
            popupStage.resizableProperty().setValue(Boolean.FALSE);
            popupStage.show();

            popupStage.setOnHidden(windowEvent -> {
                try {
                    refreshCards();
                   // cardList.getFocusModel().getFocusedItem().changeTotal(total);
                    //cardList.getFocusModel().getFocusedItem().getHamburgerMenu().setDisable(false);

                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            });


        } );

    }


    /**
     * Refreshes the account details and reloads the cards in the UI.
     *
     * @throws IOException If the account data cannot be refreshed.
     * @throws InterruptedException If the thread is interrupted during the process.
     */

    public void refreshCards() throws IOException, InterruptedException {
        bankAccounts = api.getAllBankAccounts(activeUser);
        listCards();
    }

    /**
     * Adds a new account for the active user.
     *
     * @throws IOException If the account creation fails or an error occurs with the API.
     * @throws InterruptedException If the thread is interrupted during the API call.
     */

    public void addNewAcc() throws IOException, InterruptedException {

        api.createAccount(activeUser.getId(),"HUF",activeUser.getFirstname(),activeUser.getLastname(),activeUser.getAuthToken());
        refreshCards();
    }

    @Deprecated
    public void currencyExchangeBtn(ActionEvent actionEvent) {

    }

    /**
     * Loads cryptocurrency charts with data for the specified number of days.
     * Retrieves and displays Bitcoin (BTC) and Ethereum (ETH) exchange rates in Hungarian Forint (HUF).
     *
     * @param daysToShow The number of past days to include in the chart.
     * @throws IOException If an I/O error occurs while retrieving data.
     * @throws InterruptedException If the thread is interrupted while retrieving data.
     */
    public void loadCryptoCharts(int daysToShow) throws IOException, InterruptedException {


        int days = daysToShow;
        Float f = 100.5F;


        for (int i = 0; i <= daysToShow-1; i++) {
            //series.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));

            btcSeries.getData().add(new XYChart.Data<>(i,api.getBtc(days).getValue().get("huf")));
            ethSeries.getData().add(new XYChart.Data<>(i, api.getEth(days).getValue().get("huf")));
            days--;

        }

        /*
        btcSeries.getData().add(new XYChart.Data<>(15,10));
        ethSeries.getData().add(new XYChart.Data<>(15,10));

         */

        
        Optional<XYChart.Data<Number, Number>> minBtcValue = btcSeries.getData().stream().min(Comparator.comparingInt(a -> a.getYValue().intValue()));
        Optional<XYChart.Data<Number, Number>> minEthValue = ethSeries.getData().stream().min(Comparator.comparingInt(a -> a.getYValue().intValue()));
        /*
        System.out.println(n.get().getYValue().intValue());
        System.out.println(n.get().getXValue());
        System.out.println(n.get());

         */

        Optional<XYChart.Data<Number, Number>> maxBtcValue = btcSeries.getData().stream().max(Comparator.comparingInt(a -> a.getYValue().intValue()));
        Optional<XYChart.Data<Number, Number>> maxEthValue = ethSeries.getData().stream().max(Comparator.comparingInt(a -> a.getYValue().intValue()));

        Platform.runLater(() -> {

            if(btcSeries.getData().get(0).getYValue().intValue() > btcSeries.getData().get(btcSeries.getData().size()-1).getYValue().intValue())
            {
                Node fill = btcSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = btcSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }

            if(ethSeries.getData().get(0).getYValue().intValue() > ethSeries.getData().get(ethSeries.getData().size()-1).getYValue().intValue())
            {
                Node fill = ethSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = ethSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke: #ff0000");
            }

            /*
            System.out.println(minBtcValue.get().getXValue().intValue() + " " + btcSeries.getData().size());
            if(minBtcValue.get().getXValue().intValue() == btcSeries.getData().size()-1)
            {
                Node fill = btcSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = btcSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }

            if(minEthValue.get().getXValue().intValue() == ethSeries.getData().size()-1)
            {
                Node fill = ethSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = ethSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke: #ff0000");
            }

             */
        });

        BtcChart.getYAxis().setAutoRanging(true);
        ((NumberAxis)BtcChart.getYAxis()).setForceZeroInRange(false);

        BtcChart.getXAxis().setAutoRanging(false);
        ((NumberAxis)BtcChart.getXAxis()).setUpperBound(14.22);
        ((NumberAxis)BtcChart.getXAxis()).setLowerBound(0);

        EthChart.getYAxis().setAutoRanging(true);
        ((NumberAxis)EthChart.getYAxis()).setForceZeroInRange(false);

        EthChart.getXAxis().setAutoRanging(false);
        ((NumberAxis)EthChart.getXAxis()).setUpperBound(14.22);
        ((NumberAxis)EthChart.getXAxis()).setLowerBound(0);

        BtcChart.getData().add(btcSeries);
        EthChart.getData().add(ethSeries);

    }

    /**
     * Loads foreign currency exchange rate charts for the specified number of days.
     * Retrieves and displays exchange rates for Euro (EUR) and US Dollar (USD) in Hungarian Forint (HUF).
     *
     * @param daysToShow The number of past days to include in the chart.
     * @throws IOException If an I/O error occurs while retrieving data.
     * @throws InterruptedException If the thread is interrupted while retrieving data.
     */
    public void loadCharts(int daysToShow) throws IOException, InterruptedException {

        

        XYChart.Series<Number, Number> eurSeries = new XYChart.Series<Number, Number>();
        XYChart.Series<Number, Number> usdSeries = new XYChart.Series<Number, Number>();

        int days = daysToShow;
        Float f = 100.5F;



        for (int i = 0; i <= daysToShow-1; i++) {
            //series.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));
            eurSeries.getData().add(new XYChart.Data<>(i, api.getEur(days).getValue().get("huf")));
            usdSeries.getData().add(new XYChart.Data<>(i, api.getUsd(days).getValue().get("huf")));
            days--;

        }
        /*
        usdSeries.getData().add(new XYChart.Data<>(15,10));
        eurSeries.getData().add(new XYChart.Data<>(15,10));

         */
        Optional<XYChart.Data<Number, Number>> minUsdValue = usdSeries.getData().stream().min(Comparator.comparingInt(a -> a.getYValue().intValue()));
        Optional<XYChart.Data<Number, Number>> minEurValue = eurSeries.getData().stream().min(Comparator.comparingInt(a -> a.getYValue().intValue()));
        /*
        System.out.println(n.get().getYValue().intValue());
        System.out.println(n.get().getXValue());
        System.out.println(n.get());

         */

        Optional<XYChart.Data<Number, Number>> maxUsdValue = usdSeries.getData().stream().max(Comparator.comparingInt(a -> a.getYValue().intValue()));
        Optional<XYChart.Data<Number, Number>> maxEurValue = eurSeries.getData().stream().max(Comparator.comparingInt(a -> a.getYValue().intValue()));

        Platform.runLater(() -> {
            //System.out.println(minUsdValue.get().getXValue().intValue() + " " + usdSeries.getData().size());

            if(usdSeries.getData().get(0).getYValue().intValue() > usdSeries.getData().get(usdSeries.getData().size()-1).getYValue().intValue())
            {
                Node fill = usdSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = usdSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }


            if(eurSeries.getData().get(0).getYValue().intValue() > eurSeries.getData().get(eurSeries.getData().size()-1).getYValue().intValue())
            {
                Node fill = eurSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = eurSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke: #ff0000");
            }

            /*
            if(minUsdValue.get().getXValue().intValue() == usdSeries.getData().size()-1)
            {
                Node fill = usdSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = usdSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }

            if(minEurValue.get().getXValue().intValue() == eurSeries.getData().size()-1)
            {
                Node fill = eurSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = eurSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke: #ff0000");
            }

             */
        });

        UsdChart.getYAxis().setAutoRanging(true);
        ((NumberAxis)UsdChart.getYAxis()).setForceZeroInRange(false);
/*
        ((NumberAxis)UsdChart.getYAxis()).setUpperBound(450);
        ((NumberAxis)UsdChart.getYAxis()).setLowerBound(365);

 */


        UsdChart.getXAxis().setAutoRanging(false);
        ((NumberAxis)UsdChart.getXAxis()).setUpperBound(14.22);
        ((NumberAxis)UsdChart.getXAxis()).setLowerBound(-0.2);

        EurChart.getYAxis().setAutoRanging(true);
        ((NumberAxis)EurChart.getYAxis()).setForceZeroInRange(false);
        /*
        ((NumberAxis)EurChart.getYAxis()).setUpperBound(450);
        ((NumberAxis) EurChart.getYAxis()).setLowerBound(365);

         */
        EurChart.getXAxis().setAutoRanging(false);
        ((NumberAxis)EurChart.getXAxis()).setUpperBound(14.22);
        ((NumberAxis)EurChart.getXAxis()).setLowerBound(-0.2);

        UsdChart.getData().add(usdSeries);
        EurChart.getData().add(eurSeries);


    }

    /**
     * Updates the pie chart based on the current filter criteria and the selected account.
     *
     * The pie chart may display income, expenses, or a combination of both.
     */

    public void updatePieChart() throws IOException, InterruptedException {

        if(pieChartExpense)
        {
            pieChartToExpense();
        }
        else if(pieChartIncome)
        {
            pieChartToIncome();
        }
        else{
            pieChartToIncomeExpense();

        }
       // myPieChart.setLegendVisible(false);
        
    }

    /**
     * Swaps the visibility of the left container in the UI between two states.
     *
     * This functionality is typically used to collapse or expand the side panel
     * containing account or transaction lists.
     */

    @FXML
    public void swapLeftContainer(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(swapLeftContainerBtn.getText().equals("Eloszlás"))
        {

            updatePieChart();

            myStackPane.layout();
            swapLeftContainerBtn.setText("Árfolyam");
            swapLeftContainerBtnImageView.setImage(new Image(MainPage.class.getResourceAsStream("currency.png")));
            cryptoContainer.setVisible(false);
            currencyExchangeContainer.setVisible(false);
            //myPieChart.setVisible(true);
            pieChartContainer.setVisible(true);
            //System.out.println(pieChartContainer.isVisible());
            pieChartContainer.setTranslateZ(2);

            myPieChart.setLegendVisible(false);


        }
        else {

            updatePieChart();
            myPieChart.getData().clear();
            swapLeftContainerBtn.setText("Eloszlás");
            cryptoBtn.setText("Crypto");
            swapLeftContainerBtnImageView.setImage(new Image(MainPage.class.getResourceAsStream("PieChart.png")));
            pieChartContainer.setVisible(false);
            cryptoContainer.setVisible(false);
            currencyExchangeContainer.setVisible(true);

            //myPieChart.setVisible(false);

        }
    }

    /**
     * Displays a value pie chart and handles the specified event.
     *
     * @param event the event that triggers the pie chart to be displayed
     */
    @FXML
    public void showValuePieChart(Event event) {
        final Label caption = new Label("");
       // caption.setText(String.valueOf(myPieChart.getPieValue()) + "%");
    }

    /**
     * Displays account information in a new popup window.
     *
     * @param actionEvent the event triggered when the account information is requested
     * @throws IOException if there is an error loading the specified FXML resource
     */
    @FXML
    public void accountInfo(ActionEvent actionEvent) throws IOException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("cardDetail.fxml"));
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.resizableProperty().setValue(Boolean.FALSE);
        popupStage.show();
    }

    /**
     * Updates the pie chart visualization to display the breakdown of income and expenses.
     *
     * This method processes a list of transactions and calculates the total income
     * and expenses. Based on these totals, it updates the data in a pie chart to
     * visually represent the proportion of income and expense.
     *
     * Behavior:
     * - Sets the flags `pieChartIncome` and `pieChartExpense` to false, and `pieChartCombined` to true.
     * - Iterates through the `transactionArray` to calculate the total income and total expense.
     * - Uses the derived totals to populate the pie chart data.
     * - If there's no income or expense data, respective sections aren't added to the pie chart.
     */
    @FXML
    public void pieChartToIncomeExpense() {
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

    /**
     * Updates the pie chart to reflect income data from the active bank account.
     *
     * This method generates a pie chart representation of the user's income, categorized by
     * "Salary", "Transaction", and "Other". It calculates the total income for each category
     * from the active bank account's income data and constructs a pie chart with the respective
     * values. Categories with zero income are excluded from the chart.
     *
     * The method also ensures that the pie chart view is focused on*/
    @FXML
    public void pieChartToIncome() {
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

    /**
     * Generates and displays a pie chart visualization for categorizing expenses.
     *
     * This method calculates the total amount spent for each expense category
     * (Shopping, Rent, Transport, Transaction, and Other) within the expenses
     * of the active bank account. It then creates a pie chart based on these
     * calculated totals and sets the data to a PieChart component.
     *
     * Key behaviors:
     * - Updates boolean state variables to indicate that the displayed pie chart
     *   corresponds to expenses.
     * - Iterates through the list of expenses in the active bank account to
     *   calculate the total amounts for each category.
     * - Populates an ObservableList with PieChart.Data objects when a category
     *   has a non-zero total.
     * - Sets the generated data to the associated PieChart component.
     */
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


    /**
     * Deletes a card by refreshing the list of cards.
     * This method interacts with the underlying card management system
     * to refresh the current card collection, ensuring that the latest
     * state of cards is reflected.
     *
     * @throws IOException if an input or output exception occurs during the process.
     * @throws InterruptedException if the thread executing the method is interrupted.
     */
    public void deleteCard() throws IOException, InterruptedException {
        refreshCards();
    }

    /**
     * Opens the transfer window to allow the user to perform transfer operations.
     * This method initializes the necessary resources and displays the transfer
     * interface for the user. Ensure that all pre-requisite conditions are met
     * before invoking this method.
     *
     * @throws IOException if an input or output exception occurs during the
     *         processing of the transfer window.
     */
    public void OpenTransferWindow() throws IOException {

    }

    /**
     * Retrieves the currently focused transaction.
     *
     * @return the transaction that is currently focused, or null*/
    public static Transaction getFocusedTransaction() {
        return focusedTransaction;
    }

    /**
     * Sets the specified transaction as the currently focused transaction.
     *
     * @param focusedTransaction the transaction to set as the focused*/
    public static void setFocusedTransaction(Transaction focusedTransaction) {
        MainPage.focusedTransaction = focusedTransaction;
    }

    /**
     * Retrieves the ListView containing the transaction data.
     *
     * @return the ListView displaying transaction information
     */
    public ListView getTransactionListView() {
        return transactionListView;
    }

    /**
     * Sets the transaction list view to display a collection of transactions.
     *
     * @param transactionListView the ListView that represents the transaction list to be displayed
     */
    public void setTransactionListView(ListView transactionListView) {
        this.transactionListView = transactionListView;
    }

    /**
     * Displays the details of a selected transaction in a popup window when an event occurs.
     * This method loads the transaction details from a specified FXML file and sets up
     * the popup window as a modal dialog. It also refreshes the transactions list
     * upon closing the popup window.
     *
     * @param event the event that triggers the display of the transaction detail popup
     * @throws IOException if an error occurs while loading the FXML file or refreshing the transactions
     */
    @FXML
    public void showTransactionDetail(Event event) throws IOException {
        focusedTransaction = transactionArray.get(transactionArray.size() -  transactionListView.getFocusModel().getFocusedIndex()-1);
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("transactionDetail.fxml"));
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.resizableProperty().setValue(Boolean.FALSE);
        popupStage.show();

        popupStage.setOnHidden(windowEvent->{
            try {
                refreshTransactions();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Changes the container to show crypto charts
     * @param actionEvent
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void showCrypto(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(cryptoBtn.getText().equals("Crypto"))
        {
            if((long) btcSeries.getData().size() == 0)
            {
                System.out.println("No series found");
                loadCryptoCharts(15);
            }

            cryptoBtn.setText("Árfolyam");
            cryptoContainer.setVisible(true);
            currencyExchangeContainer.setVisible(false);
            pieChartContainer.setVisible(false);
            Float currentBtc = api.getBtc(0).getValue().get("huf");
            Float yesterdayBtc = api.getBtc(1).getValue().get("huf");
            float change = ((currentBtc-yesterdayBtc) / currentBtc * 100);

            Float currentEth = api.getEth(0).getValue().get("huf");
            Float yesterdayEth = api.getEth(1).getValue().get("huf");
            float changeEth = ((currentEth-yesterdayEth) / currentEth * 100);

            btcText.setText((Math.round(currentBtc*100)/100) + " Ft");
            btcChange.setText("+"+Math.round(change*100)/100.0 + "%");
            if(currentBtc-yesterdayBtc < 0)
            {
                btcText.setText((Math.round(currentBtc*100)/100) + " Ft");
                btcIndicator.setStyle("-fx-background-color: red;");
                btcChange.setText(Math.round(change*100)/100.0 + "%");
                btcChange.setStyle("-fx-fill: red;");
                btcImg.setImage(new Image(MainPage.class.getResourceAsStream("currencyNegInd.png")));
            }

            ethText.setText((Math.round(currentEth*100)/100) + " Ft");
            ethChange.setText("+"+Math.round(changeEth*100)/100.0 + "%");
            if(currentEth-yesterdayEth < 0)
            {
                ethText.setText((Math.round(currentEth*100)/100) + " Ft");
                ethIndicator.setStyle("-fx-background-color: red;");
                ethChange.setText(Math.round(changeEth*100)/100.0 + "%");
                ethChange.setStyle("-fx-fill: red;");
                ethImg.setImage(new Image(MainPage.class.getResourceAsStream("currencyNegInd.png")));
            }

        }
        else
        {
            updatePieChart();
            myPieChart.getData().clear();
            cryptoBtn.setText("Crypto");
            swapLeftContainerBtn.setText("Eloszlás");
            cryptoContainer.setVisible(false);
            currencyExchangeContainer.setVisible(true);
            pieChartContainer.setVisible(false);
        }

    }

    /**
     * sets the currency cards' value to the current eur and usd values
     * @throws IOException
     * @throws InterruptedException
     */
    public void setCurrencyCards() throws IOException, InterruptedException {

        Float currentEur = api.getEur(0).getValue().get("huf");
        Float yesterdayEur = api.getEur(1).getValue().get("huf");
        float change = ((currentEur-yesterdayEur) / currentEur * 100);
        Float currentUsd = api.getUsd(0).getValue().get("huf");
        Float yesterdayUsd = api.getUsd(1).getValue().get("huf");
        float changeUsd = ((currentUsd-yesterdayUsd) / currentUsd * 100);


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

        usdText.setText((Math.round(currentUsd*100)/100) + "Ft");
        usdChange.setText("+"+Math.round(changeUsd*100)/100.0 + "%");
        if(currentUsd-yesterdayUsd < 0)
        {
            usdText.setText(Math.round(currentUsd * 100) / 100 + "Ft");
            usdIndicator.setStyle("-fx-background-color: #FF2424;");
            usdChange.setText(Math.round(changeUsd*100)/100.0 + "%");
            usdChange.setStyle("-fx-fill: #FF2424;");
            usdImg.setImage(new Image(MainPage.class.getResourceAsStream("currencyNegInd.png")));
        }

    }

    /**
     * Opens a new window to show the repeatable transactoin list
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void showRepeatable(ActionEvent actionEvent) throws IOException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("repeatableList.fxml"));
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.resizableProperty().setValue(Boolean.FALSE);
        popupStage.show();
        
        popupStage.setOnHidden(event -> {
            try {
                refreshTransactions();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

    }

    /**
     * Updates the transaction list to show only expense transactions.
     *
     * This method sets the expense filter to active and disables the income filter.
     * It sorts the `expenseList` by creation date in ascending order, clears the
     * current transaction view, adds the sorted transactions as components, and
     * scrolls to the top of the list.
     *
     * @param actionEvent The ActionEvent triggered when the expense filter is applied.
     */
    @FXML
    public void setFilterToExpenses(ActionEvent actionEvent) {
        expenseFilter = true;
        incomeFilter = false;

        transactions.clear();
        transactionListView.getItems().clear();
        expenseList.sort(new Comparator<Transaction>() {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });



        transactions.addAll(transactionArray.stream().filter(transaction -> transaction.getClass().toString().contains("Expense")).toList());
        for (int i = 0; i < expenseList.size(); i++) {
            System.out.println(expenseList.get(i));
            //System.out.println(transactionArray.get(i).getTotal());
            transactionListView.getItems().add(0,new TransactionComponent(transactions.get(i)));
        }

        transactionListView.scrollTo(0);

    }

    /**
     * Updates the transaction list to show only income transactions.
     *
     * This method sets the income filter to active and disables the expense filter.
     * It sorts the `incomeList` by creation date, clears the current transaction view,
     * adds the sorted transactions as components, and scrolls to the top of the list.
     *
     * @param actionEvent The ActionEvent triggered when the income filter is applied.
     */
    @FXML
    public void setFilterToIncome(ActionEvent actionEvent) {
        expenseFilter = false;
        incomeFilter = true;

      transactions.clear();
        System.out.println(  transactionArray.get(0).getClass().toString());
        transactions.addAll(transactionArray.stream().filter(transaction -> transaction.getClass().toString().contains("Income")).toList());
        incomeList.sort(new Comparator<Transaction>() {

            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getCreatedAt().compareTo(o2.getCreatedAt());
            }
        });

       transactionListView.getItems().clear();

        for (int i = 0; i < incomeList.size(); i++) {
            //System.out.println(transactionArray.get(i).getTotal());
            transactionListView.getItems().add(0,new TransactionComponent(transactions.get(i)));
        }

        transactionListView.scrollTo(0);
    }

    /**
     * Resets the filters on the transaction list to show all transactions.
     *
     * This method disables both the income and expense filters by setting
     * their respective boolean flags to false. After resetting the filters,
     * it refreshes the transaction list to display all transactions.
     *
     * @param actionEvent The ActionEvent triggered when the "Show All" filter option is selected.
     */
    @FXML
    public void setFilterToAll(ActionEvent actionEvent) {
        expenseFilter = false;
        incomeFilter = false;
        listTransactions();
    }


/*
    public void LoadCurrencyData(int daysToShow)
    {
        Task<Void> task = new Task<>() {
            @Override public Void call() throws IOException, InterruptedException {
                Platform.runLater(() -> {
                    int days = daysToShow;
                    for (int i = 0; i < daysToShow; i++) {

                        //series.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));
                        try {
                            eurSeries.getData().add(new XYChart.Data<>(Integer.toString(i), api.GetEur(days).getValue().get("huf")));
                            System.out.println(eurSeries.getData());
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            usdSeries.getData().add(new XYChart.Data<>(Integer.toString(i), api.GetUsd(days).getValue().get("huf")));
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        days--;

                    }
                });

                EurChart.getData().add(eurSeries);
                UsdChart.getData().add(usdSeries);

                return null;
            }
        };

        new Thread(task).start();
    }

 */


}


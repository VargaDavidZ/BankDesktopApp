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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;


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
    private ListView expenseList;
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

    XYChart.Series<Number, Number> btcSeries = new XYChart.Series<Number, Number>();
    XYChart.Series<Number, Number> ethSeries = new XYChart.Series<Number, Number>();
    //duplicate card at user
    //confirmation on delete and transfer

    public void initialize() throws IOException, InterruptedException {

        this.bankAccounts = api.getAllBankAccounts(activeUser);
        cardList.setOrientation(Orientation.HORIZONTAL);
        Float currentEur = api.getEur(0).getValue().get("huf");
        Float yesterdayEur = api.getEur(1).getValue().get("huf");
        float change = ((currentEur-yesterdayEur) / currentEur * 100);
        Float currentUsd = api.getUsd(0).getValue().get("huf");
        Float yesterdayUsd = api.getUsd(1).getValue().get("huf");
        float changeUsd = ((currentUsd-yesterdayUsd) / currentUsd * 100);

        System.out.println("Active User Id"+activeUser.getId());

        cryptoContainer.setVisible(false);
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



        System.out.println(activeBankAccount.toString());
        System.out.println("Active acc id " +activeBankAccount.getId());
        System.out.println(activeUser.getAuthToken());
        //display USD-HUF exchange rate and change %
        //System.out.printf(currentUsd + "---");
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

        if(bankAccounts.length != 0) {
            activeBankAccount = bankAccounts[0];
        }

        activeBankAccount.setExpenses(api.getAccountExpenses(activeBankAccount.getId(), activeUser.getAuthToken()));

        activeBankAccount.setIncome(api.getAccountIncomes(activeBankAccount.getId(), activeUser.getAuthToken()));

        createdSortedArray();
        listTransactions();
        listCards();

        //LoadCurrencyData(15);


        Platform.runLater(() -> {
            cardList.getFocusModel().focus(0);
            expenseList.getFocusModel().focus(0);

            //System.out.println(activeBankAccount.getExpenses()[0].toString() + "---------");
            try {
                calcActiveTotal();

            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            cardList.getFocusModel().getFocusedItem().changeTotal(total, activeBankAccount.getCurrency());
            cardList.getFocusModel().getFocusedItem().getHamburgerMenu().setDisable(false);

            try {
                loadCharts(15);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }


        });

    }

    public static void setActiveUser(String activeUserInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        activeUser = mapper.readValue(activeUserInput,User.class);

    }


    public void SetActiveAccount(BankAccount inpAcc) {
        activeBankAccount = inpAcc;
    }

    public void listCards() throws FileNotFoundException {



        cardList.getItems().clear();

        for (int i = 0; i < bankAccounts.length; i++) {

            cardList.getItems().add(new AccountCard(bankAccounts[i],activeUser));
        }

        cardList.getFocusModel().focus(focusedItem);
        cardList.getItems().add(new AccountCard());
    }

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

    public void listTransactions() {
        for (int i = 0; i < transactionArray.size(); i++) {
            //System.out.println(transactionArray.get(i).getTotal());
            expenseList.getItems().add(0,new TransactionComponent(transactionArray.get(i)));
        }

        expenseList.scrollTo(0);
    }

    public void refreshTransactions() throws IOException, InterruptedException {
        expenseList.getItems().clear();
        transactionArray.clear();
        activeBankAccount.setExpenses(api.getAccountExpenses(activeBankAccount.getId(),activeUser.getAuthToken()));
        activeBankAccount.setIncome(api.getAccountIncomes(activeBankAccount.getId(),activeUser.getAuthToken()));

        createdSortedArray();
        System.out.print("Refreshing transactions...");

        listTransactions();
        calcActiveTotal();
        cardList.getFocusModel().getFocusedItem().changeTotal(total, activeBankAccount.getCurrency());
        expenseList.refresh();
    }

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

    public static BankAccount[] getBankAccounts() {
        return bankAccounts;
    }

    public static BankAccount getActiveBankAccount() {
        return activeBankAccount;
    }



    public static void setActiveBankAccount(BankAccount activeBankAccount) {
        MainPage.activeBankAccount = activeBankAccount;
    }

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

        updatePieChart();
        //total = activeBankAccount.getTotal();

        //RestApi.UpdateTotal(activeBankAccount.getId(),total,activeUser.getAuthToken());
    }

    public static User getActiveUser() {
        return activeUser;
    }

    public  ListView<AccountCard> getCardList() {
        return cardList;
    }

    public void setCardList(ListView<AccountCard> cardList) {
        this.cardList = cardList;
    }

    @FXML
    public void changeActiveBankAccount(Event event) throws IOException, InterruptedException {
        try
        {
            SetActiveAccount(bankAccounts[cardList.getFocusModel().getFocusedIndex()]);
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

    public void refreshCards() throws IOException, InterruptedException {

        bankAccounts = api.getAllBankAccounts(activeUser);
        listCards();
    }

    public void addNewAcc() throws IOException, InterruptedException {

        api.createAccount(activeUser.getId(),"HUF",activeUser.getFirstname(),activeUser.getLastname(),activeUser.getAuthToken());
        refreshCards();
    }

    @Deprecated
    public void currencyExchangeBtn(ActionEvent actionEvent) {

    }

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

        btcSeries.getData().forEach(series -> {
            System.out.println(series.toString());
        });



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

            if(btcSeries.getData().getFirst().getYValue().intValue() > btcSeries.getData().getLast().getYValue().intValue())
            {
                Node fill = btcSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = btcSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }

            if(ethSeries.getData().getFirst().getYValue().intValue() > ethSeries.getData().getLast().getYValue().intValue())
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

            if(usdSeries.getData().getFirst().getYValue().intValue() > usdSeries.getData().getLast().getYValue().intValue())
            {
                Node fill = usdSeries.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
                Node line = usdSeries.getNode().lookup(".chart-series-area-line");
                fill.setStyle("-fx-fill:rgba(255,0,0,0.5) ");
                line.setStyle("-fx-stroke:#ff0000 ");
            }

            if(eurSeries.getData().getFirst().getYValue().intValue() > eurSeries.getData().getLast().getYValue().intValue())
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
        ((NumberAxis)UsdChart.getXAxis()).setLowerBound(0);

        EurChart.getYAxis().setAutoRanging(true);
        ((NumberAxis)EurChart.getYAxis()).setForceZeroInRange(false);
        /*
        ((NumberAxis)EurChart.getYAxis()).setUpperBound(450);
        ((NumberAxis) EurChart.getYAxis()).setLowerBound(365);

         */
        EurChart.getXAxis().setAutoRanging(false);
        ((NumberAxis)EurChart.getXAxis()).setUpperBound(14.22);
        ((NumberAxis)EurChart.getXAxis()).setLowerBound(0);

        UsdChart.getData().add(usdSeries);
        EurChart.getData().add(eurSeries);


    }

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

    @FXML
    public void showValuePieChart(Event event) {
        final Label caption = new Label("");
       // caption.setText(String.valueOf(myPieChart.getPieValue()) + "%");
    }

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


    public void deleteCard() throws IOException, InterruptedException {
        refreshCards();
    }

    public void OpenTransferWindow() throws IOException {

    }

    @FXML
    public void showTransactionDetail(Event event) {
        System.out.println(transactionArray.get(transactionArray.size() -  expenseList.getFocusModel().getFocusedIndex()-1));
    }

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


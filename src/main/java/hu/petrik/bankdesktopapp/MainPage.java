package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class MainPage {
    Stage stage;

    @javafx.fxml.FXML
    private VBox mainPage;
    @FXML
    private LineChart myChart;
    @FXML
    private HBox valtasCard;
    @FXML
    private HBox myhbox;
    @FXML
    private LineChart EurChart;
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    private  BankAccount[] bankAccounts;
    private Integer eurIndex = 5;
    private Integer eurValue = 500;
    RestApi api = new RestApi();
    private static User activeUser;
    private static BankAccount activeBankAccount;
    private float total;
    private ArrayList<Transaction> transactionArray = new ArrayList<>();

    @FXML
    private ListView incomeList;
    @FXML
    private ListView expenseList;
    @FXML
    private ListView<AccountCard> cardList;
    @FXML
    private ImageView createAccBtn;

    public static void SetActiveUser(String activeUserInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        User user = mapper.readValue(activeUserInput,User.class);
        activeUser = user;

    }

    public void initialize() throws IOException, InterruptedException {

        bankAccounts = api.GetAllBankAccounts(activeUser.getId());
        cardList.setOrientation(Orientation.HORIZONTAL);

        if(bankAccounts.length != 0) {
            activeBankAccount = bankAccounts[0];
        }

       // System.out.printf(api.GetAccountExpenses(activeBankAccount.id).toString());
        activeBankAccount.setExpenses(api.GetAccountExpenses(activeBankAccount.getId()));
        //System.out.printf(api.GetAccountIncomes(activeBankAccount.id).toString());
        activeBankAccount.setIncome(api.GetAccountIncomes(activeBankAccount.getId()));

        CreatedSortedArray();
        ListTransactions();
        ListCards();

        XYChart.Series<String,Number> series = new XYChart.Series<String,Number>();
        XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();

        series.setName("USD");
        series.getData().add(new XYChart.Data("1", 100));
        series.getData().add(new XYChart.Data("2", 150));
        series.getData().add(new XYChart.Data("3", 200));
        series.getData().add(new XYChart.Data("4", 300));

        series2.getData().add(new XYChart.Data("1", 50));
        series2.getData().add(new XYChart.Data("2", 400));
        series2.getData().add(new XYChart.Data("3", 350));
        series2.getData().add(new XYChart.Data("4", 500));
        series2.setName("EUR");

        myChart.getData().add(series);
        EurChart.getData().add(series2);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
            series2.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));
            eurIndex += 1;
            eurValue +=100;


        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Platform.runLater(() -> {
            cardList.getFocusModel().focus(0);
            CalcActiveTotal();
            cardList.getFocusModel().getFocusedItem().changeTotal(total);

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
    }

    public void RefreshTransactions() throws IOException, InterruptedException {
        expenseList.getItems().clear();
        transactionArray.clear();
        activeBankAccount.setExpenses(api.GetAccountExpenses(activeBankAccount.getId()));
        activeBankAccount.setIncome(api.GetAccountIncomes(activeBankAccount.getId()));

        CreatedSortedArray();
        System.out.printf("Refreshing transactions...");

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
           } catch (IOException e) {
               throw new RuntimeException(e);
           } catch (InterruptedException e) {
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

    public void CalcActiveTotal()
    {
        float income = 0;
        float expense = 0;
        for (int i = 0; i < activeBankAccount.getExpenses().length; i++) {

           expense += activeBankAccount.getExpenses()[i].getTotal();
        }

        for (int i = 0; i < activeBankAccount.getIncome().length; i++) {

            income += activeBankAccount.getIncome()[i].getTotal();
        }
        total = income - expense;
    }

    public static User getActiveUser() {
        return activeUser;
    }


    @FXML
    public void ChangeActiveBankAccount(Event event) throws IOException, InterruptedException {


        try
        {
            SetActiveAccount(bankAccounts[cardList.getFocusModel().getFocusedIndex()]);
        }
        catch (Exception e)
        {
            AddNewAcc();
            return;
        }


        RefreshTransactions();
        CalcActiveTotal();
        cardList.getFocusModel().getFocusedItem().changeTotal(total);


    }

    public void RefreshCards() throws IOException, InterruptedException {

        bankAccounts = api.GetAllBankAccounts(activeUser.getId());
        ListCards();
    }

    public void AddNewAcc() throws IOException, InterruptedException {
        api.CreateAccount(activeUser.getId(),"HUF");
        RefreshCards();
    }
}


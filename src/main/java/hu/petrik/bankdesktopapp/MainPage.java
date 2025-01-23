package hu.petrik.bankdesktopapp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class MainPage {
    Stage stage;

    @javafx.fxml.FXML
    private VBox mainPage;
    @javafx.fxml.FXML
    private ListView myList;
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


    private static BankAccount[] bankAccounts;

    private Integer eurIndex = 5;
    private Integer eurValue = 500;

    RestApi api = new RestApi();


    private static User activeUser;




    public static void setActiveUser(String activeUserInput) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        User user = mapper.readValue(activeUserInput,User.class);
        activeUser = user;
        System.out.println(activeUser.Firstname + "MŰKÖDIK!");
    }




    public void initialize() throws IOException, InterruptedException {


        bankAccounts = api.GetAllBankAccounts(activeUser.id);

        System.out.printf(bankAccounts[0].toString());

        //System.out.printf(api.GetOneUser("678a363ecdb04bd08a6ad434").toString());

        //System.out.println(api.CreateUser("David","Varga","MyEmail","TestPass"));

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

        myList.getItems().add(new Transactions("1000"));
        myList.getItems().add(new Transactions("-1000"));
        myList.getItems().add(new Transactions("-1000"));
        myList.getItems().add(new Transactions("1000"));




        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), ev -> {
            series2.getData().add(new XYChart.Data(Integer.toString(eurIndex), eurValue));
            eurIndex += 1;
            eurValue +=100;

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();




    }


    @FXML
    public void addToList(ActionEvent actionEvent) {
        myList.getItems().addAll(new Transactions("1000"),new Transactions("1000"));
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
    public void popupWindow(ActionEvent actionEvent) throws IOException {
       Stage popupStage = new Stage();
       popupStage.initModality(Modality.APPLICATION_MODAL);
       Parent root = FXMLLoader.load(getClass().getResource("popupWindow.fxml"));
       Scene popupScene = new Scene(root);
       popupStage.setScene(popupScene);
       popupStage.resizableProperty().setValue(Boolean.FALSE);
       popupStage.show();

    }


    public static BankAccount[] getBankAccounts() {
        return bankAccounts;
    }

    public static User getActiveUser() {
        return activeUser;
    }
}

package hu.petrik.bankdesktopapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainPage {
    @javafx.fxml.FXML
    private VBox mainPage;
    @javafx.fxml.FXML
    private ListView myList;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();

    @FXML
    private LineChart myChart;
    @FXML
    private HBox valtasCard;
    @FXML
    private HBox myhbox;
    @FXML
    private LineChart EurChart;


    public void initialize() {




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




    }


    @FXML
    public void addToList(ActionEvent actionEvent) {
        myList.getItems().addAll(new Transactions("1000"),new Transactions("1000"));
    }
}

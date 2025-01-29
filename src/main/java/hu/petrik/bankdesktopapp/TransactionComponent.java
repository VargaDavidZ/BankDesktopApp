package hu.petrik.bankdesktopapp;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class TransactionComponent extends AnchorPane {
    @javafx.fxml.FXML
    private Text amount;
    @javafx.fxml.FXML
    private Text type;
    @javafx.fxml.FXML
    private Pane circle;
    @javafx.fxml.FXML
    private Text dateText;

    @FXML
    private AnchorPane controller;

    TranslateTransition translation;


    public TransactionComponent(Transaction inputTransaction) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(TransactionComponent.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        dateText.setText(new SimpleDateFormat("yyyy/MM/dd/hh:mm").format(inputTransaction.getCreatedAt()));

            if(inputTransaction.getClass() == Income.class) {
                amount.setText(inputTransaction.getTotal() + "Ft");
                amount.setFill(Paint.valueOf("#41ff24"));
                circle.setStyle("-fx-background-color: #41ff24");
            }
            else
            {
                amount.setText("-"+inputTransaction.getTotal() + "Ft");
                amount.setFill(Paint.valueOf("#ff2424"));
                circle.setStyle("-fx-background-color: #ff2424");
            }




    }

    public TransactionComponent(Expense inputExpense) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(TransactionComponent.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }






    }






}

package hu.petrik.bankdesktopapp;

import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class Transactions extends AnchorPane {
    @javafx.fxml.FXML
    private Text amount;
    @javafx.fxml.FXML
    private Text type;
    @javafx.fxml.FXML
    private Pane circle;

    @FXML
    private AnchorPane controller;

    TranslateTransition translation;


    public Transactions(String inpAmount) {
        translation = new TranslateTransition(Duration.millis(200), controller);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transactions.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(Transactions.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        if(Integer.parseInt(inpAmount) < 0) {
            amount.setText(inpAmount + "Ft");
            amount.setFill(Paint.valueOf("#ff2424"));
            circle.setStyle("-fx-background-color: #ff2424");
        }
        else
        {
            amount.setText(inpAmount + "Ft");
            amount.setFill(Paint.valueOf("#65df65"));
            circle.setStyle("-fx-background-color: #65df65");
        }

    }




}

package hu.petrik.bankdesktopapp;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView transactionImage;

    @FXML
    private AnchorPane controller;

    TranslateTransition translation;

    /**
     * Creates an instance of the TransactionComponent, initializing its display
     * based on the provided Transaction details such as date, amount, and category.
     * The component's UI is loaded from the FXML file and visually reflects
     * whether the transaction is an income or an expense.
     *
     * @param inputTransaction The transaction object containing details such as
     *                         creation date, total amount, category, and type.
     */
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
                //circle.setStyle("-fx-background-color: #41ff24");
            }
            else
            {
                amount.setText("-"+inputTransaction.getTotal() + "Ft");
                amount.setFill(Paint.valueOf("#ff2424"));
               // circle.setStyle("-fx-background-color: #ff2424");
                transactionImage.setImage(new Image(getClass().getResourceAsStream("Expense.png")));

            }


            type.setText(inputTransaction.getCategory());


    }

    /**
     * Constructs an instance of the TransactionComponent using the provided Expense object.
     * The UI of the component is loaded from the "transactions.fxml" file.
     *
     * @param inputExpense The Expense object containing details such as total amount, category,
     *                     vendor, and date, which will be used to populate the transaction display.
     */
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

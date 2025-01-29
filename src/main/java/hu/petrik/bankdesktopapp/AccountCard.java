package hu.petrik.bankdesktopapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;



public class AccountCard extends AnchorPane {
    @FXML
    private AnchorPane container;
    @FXML
    private Text cardTotal;
    @FXML
    private Text cardUsername;

    @FXML
    private ImageView addCardImg;

    RestApi api;

    public AccountCard(BankAccount account,User user) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(AccountCard.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        cardUsername.setText(user.getFirstname() + " " + user.getLastname());
        cardTotal.setText("*******");

    }

    public  AccountCard() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAccountCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(AccountCard.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void changeTotal(float amount) {
        cardTotal.setText(String.valueOf(amount));
    }

    public void resetTotal() {
        cardTotal.setText("*******");
    }
}

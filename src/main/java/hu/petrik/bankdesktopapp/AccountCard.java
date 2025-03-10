package hu.petrik.bankdesktopapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;


public class AccountCard extends AnchorPane {
    @FXML
    private AnchorPane container;
    @FXML
    private Text cardTotal;
    @FXML
    private Text cardUsername;

    @FXML
    private ImageView addCardImg;

    @FXML
    private MenuButton hamburgerMenu;

    @FXML
    private MenuItem deleteMenuOpt;

    @FXML
    private MenuItem openTransferPopUp;

    RestApi api;

    private ListView<AccountCard> cardList;

    public AccountCard(BankAccount account,User user) throws FileNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("accountCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(AccountCard.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        hamburgerMenu.setDisable(true);
        cardUsername.setText(account.getOwnerName());
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

    public void changeTotal(float amount, String currency) {
        cardTotal.setText(new BigDecimal(amount).toPlainString() + " " + currency); //avoid scientific notation
    }

    public void resetTotal() {
        cardTotal.setText("*******");
    }

    @FXML
    public void OpenAddUserTab() throws IOException {
        System.out.println("Open AddUserTab");
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("addUserPopUp.fxml"));
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.resizableProperty().setValue(Boolean.FALSE);
        popupStage.show();
    }

    @FXML
    public void DeleteCard() throws IOException, InterruptedException {

    }

    //Handle the new window in main because this action will need to refresh components in main
    @FXML
    public void transferCurrency() throws IOException, InterruptedException {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("transferPopUp.fxml"));
        Scene popupScene = new Scene(root);
        popupStage.setScene(popupScene);
        popupStage.resizableProperty().setValue(Boolean.FALSE);
        popupStage.show();

    }


    public MenuButton getHamburgerMenu() {
        return hamburgerMenu;
    }

    public void setHamburgerMenu(MenuButton hamburgerMenu) {
        this.hamburgerMenu = hamburgerMenu;
    }

    public MenuItem getDeleteMenuOpt() {
        return deleteMenuOpt;
    }

    public void setDeleteMenuOpt(MenuItem deleteMenuOpt) {
        this.deleteMenuOpt = deleteMenuOpt;
    }

    public MenuItem getOpenTransferPopUp() {
        return openTransferPopUp;
    }

    public void setOpenTransferPopUp(MenuItem openTransferPopUp) {
        this.openTransferPopUp = openTransferPopUp;
    }
}

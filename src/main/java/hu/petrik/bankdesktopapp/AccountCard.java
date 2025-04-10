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

    /**
     * Constructs an AccountCard instance by initializing its components with the specified
     * bank account and user. The FXML loader is also used to load the associated FXML layout.
     *
     * @param account the BankAccount object containing details about the account
     * @param user the User object representing the user tied to this card
     * @throws FileNotFoundException if the FXML file associated with the AccountCard cannot be found
     */
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

    /**
     * Default constructor for the AccountCard class.
     *
     * Initializes a new instance of AccountCard by loading its associated FXML layout file,
     * setting its root and controller to this object. In case the FXML file cannot be loaded,
     * an exception is thrown. This constructor sets up the initial UI layout for the AccountCard.
     *
     * @throws RuntimeException if an IOException occurs while loading the FXML file.
     */
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

    /**
     * Updates the total amount displayed on the card with the specified value and currency.
     * The amount is converted to a plain string representation to avoid scientific notation.
     *
     * @param amount   the monetary value to be displayed
     * @param currency the currency type associated with the amount
     */
    public void changeTotal(float amount, String currency) {
        cardTotal.setText(new BigDecimal(amount).toPlainString() + " " + currency); //avoid scientific notation
    }

    /**
     * Resets the displayed card total to a default obfuscated value.
     *
     * This method sets the text of the `cardTotal` field to a placeholder value,
     * indicating that the card total amount is not currently displayed.
     */
    public void resetTotal() {
        cardTotal.setText("*******");
    }

    /**
     * Opens a new modal window to display the "Add User" tab.
     *
     * This method creates a new pop-up stage and loads the FXML layout for adding a user.
     * The newly created window is set to be non-resizable and modal, preventing interaction
     * with the primary window while the pop-up is open.
     *
     * @throws IOException if the FXML file for the "Add User" screen cannot be loaded.
     */
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

    /**
     * Opens a modal window for transferring currency.
     *
     * This method creates and displays a new pop-up window by loading the associated FXML file.
     * The window is set to be non-resizable and modal, which means it requires user interaction
     * and blocks access to the main application stage until the pop-up is closed.
     *
     * It is intended for use when currency transfer functionality is invoked. The opened
     * window refreshes components in the main application as part of the operation.
     *
     * @throws IOException if the FXML file for the pop-up window cannot be loaded.
     * @throws InterruptedException if the operation is interrupted during its execution.
     */
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

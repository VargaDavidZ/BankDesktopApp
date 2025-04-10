package hu.petrik.bankdesktopapp;

import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class CardDetail {
    @javafx.fxml.FXML
    private ListView userList;
    @javafx.fxml.FXML
    private VBox mainPage;
    @javafx.fxml.FXML
    private HBox myhbox;
    @javafx.fxml.FXML
    private VBox leftContainer;
    @javafx.fxml.FXML
    private Text total;
    @javafx.fxml.FXML
    private Text accNum;
    @javafx.fxml.FXML
    private Text ownerName;

    final Clipboard clipboard = Clipboard.getSystemClipboard();
    final ClipboardContent content = new ClipboardContent();
    @javafx.fxml.FXML
    private Text clipboardNotif;

    /**
     * Initializes the elements of the CardDetail view.
     * <ul>
     *  - Sets the total amount text by retrieving the value from the currently active bank account.
     *  - Updates the account number display using the active bank account's ID.
     *  - Displays the owner's name associated with the active bank account.
     *  - Hides the clipboard notification text by default.
     *  - Invokes the ListUsers method to populate the user list.
     * </ul>
     *
     * @throws IOException if an I/O error occurs during the execution of ListUsers.
     * @throws InterruptedException if the thread executing the ListUsers method is interrupted.
     */
    public void initialize() throws IOException, InterruptedException {
        total.setText("Számla összeg: " + MainPage.getActiveBankAccount().getTotal());
        accNum.setText("Számlaszám: " + MainPage.getActiveBankAccount().getId());
        ownerName.setText("Számlatulajdonos: " + MainPage.getActiveBankAccount().getOwnerName());
        clipboardNotif.setVisible(false);

        ListUsers();

    }

    /**
     * Retrieves and displays the list of users associated with the active bank account.
     *
     * This method interacts with a REST API to fetch users linked to the currently active
     * bank account by utilizing the bank account ID and the authentication token of the
     * active user. After retrieving the user data, it populates the `userList` with the
     * full names of the users (first name and last name).
     *
     * Clearing the existing items in the `userList` ensures that the list only contains
     * the latest fetched data.
     *
     * @throws IOException if an I/O error occurs during the API request.
     * @throws InterruptedException if the thread executing the API request is interrupted.
     */
    public void ListUsers() throws IOException, InterruptedException {
        RestApi api = new RestApi();
        User[] users = api.getAllUsers(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getAuthToken());

        userList.getItems().clear();
        for (int i = 0; i < users.length ; i++) {
            userList.getItems().add(users[i].getFirstname() + " " + users[i].getLastname());
        }

    }

    /**
     * Copies the ID of the currently active bank account to the system clipboard.
     *
     * The method retrieves the ID of the active bank account, places it in the clipboard,
     * and makes the clipboard notification visible to indicate the completion of the action.
     *
     * @param event the event that triggered the invocation of this method
     */
    @javafx.fxml.FXML
    public void copyAccId(Event event) {
        content.putString(MainPage.getActiveBankAccount().getId());
        clipboard.setContent(content);
        clipboardNotif.setVisible(true);
    }
}

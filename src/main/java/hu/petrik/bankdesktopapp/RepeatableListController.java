package hu.petrik.bankdesktopapp;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;



public class RepeatableListController {
    @javafx.fxml.FXML
    private VBox mainPage;
    @javafx.fxml.FXML
    private ListView<RepeatableComponent> repeatableListView;


    ArrayList<RepeatableTransaction> repeatableList;

    /**
     * Initializes the controller by setting up the components and loading the repeatable transaction list.
     *
     * This method prepares the `repeatableList` with a new empty ArrayList
     * and invokes the `loadRepeatableList` method to populate and display
     * repeatable transactions in the associated ListView.
     *
     * @throws IOException if an I/O error occurs during the loading of the repeatable list
     * @throws InterruptedException if the thread is interrupted during the loading process
     */
    public void initialize() throws IOException, InterruptedException {
        repeatableList = new ArrayList<>();
        loadRepeatableList();
    }

    /**
     * Loads the list of repeatable transactions associated with the active bank account
     * and updates the corresponding ListView component to display them.
     *
     * This method clears the current `repeatableList` and the items in the `repeatableListView`,
     * fetches the repeatable transactions from the server via the `RestApi` class for the active
     * user and bank account, and populates the `repeatableList` with the retrieved data. For each
     * fetched transaction, it creates a `RepeatableComponent` and adds it to the `repeatableListView`.
     * The method also calls `addDelBtnAction` to set up any required actions for delete buttons.
     *
     * @throws IOException if there is an error in retrieving repeatable transactions from the server
     * @throws InterruptedException if the thread is interrupted while accessing the server or processing the data
     */
    public void loadRepeatableList() throws IOException, InterruptedException {
        repeatableList.clear();
        repeatableListView.getItems().clear();
        RestApi restApi = new RestApi();
        repeatableList = new ArrayList<>();
        repeatableList.addAll( restApi.getAllRepeatableByAccId(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getAuthToken() ));
        for (int i = 0; i < repeatableList.size(); i++) {
            //System.out.println(transactionArray.get(i).getTotal());
            repeatableListView.getItems().add(0,new RepeatableComponent(repeatableList.get(i)));
        }

        addDelBtnAction();

    }

    /**
     * Adds action handlers to the stop and delete buttons for each item in the repeatable transaction list.
     *
     * This method iterates through all items in the `repeatableListView` and sets up action events for their
     * associated stop and delete buttons. The stop button triggers the `stopRepeatableTransaction` method
     * of the `RestApi` class, while the delete button triggers the `deleteRepeatableTransactions` method.
     * Both actions are performed with the corresponding repeatable transaction ID and the active user's
     * authentication token. After each operation, the `loadRepeatableList` method is invoked to refresh
     * the list of repeatable transactions displayed in the UI.
     *
     * Exceptions raised during API calls or list reloading are caught and propagated as runtime exceptions.
     */
    public void addDelBtnAction() {
        RestApi restApi = new RestApi();
        for (int i = 0; i < repeatableListView.getItems().size(); i++) {
            int finalI = i;
            //System.out.println(repeatableListView.getItems().get(i).getRepeatableId());
            repeatableListView.getItems().get(i).getStopBtn().setOnAction(e -> {
                System.out.println(repeatableListView.getItems().get(finalI).getRepeatableId());
                try {
                    restApi.stopRepeatableTransaction(repeatableListView.getItems().get(finalI).getRepeatableId(), MainPage.getActiveUser().getAuthToken());
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    loadRepeatableList();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            });

            repeatableListView.getItems().get(i).getDeleteBtn().setOnAction(e -> {
                System.out.println(repeatableListView.getItems().get(finalI).getRepeatableId());
                try {
                    restApi.deleteRepeatableTransactions(repeatableListView.getItems().get(finalI).getRepeatableId(), MainPage.getActiveUser().getAuthToken());
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    loadRepeatableList();
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            });



        }
    }

}

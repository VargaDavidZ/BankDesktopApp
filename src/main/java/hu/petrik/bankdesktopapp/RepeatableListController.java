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


    public void initialize() throws IOException, InterruptedException {
        repeatableList = new ArrayList<>();
        loadRepeatableList();
    }


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

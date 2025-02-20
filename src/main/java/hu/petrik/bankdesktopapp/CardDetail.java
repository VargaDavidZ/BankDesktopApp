package hu.petrik.bankdesktopapp;

import javafx.scene.control.ListView;
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


    public void initialize() throws IOException, InterruptedException {
        total.setText("Számla összeg: " + MainPage.getActiveBankAccount().getTotal());
        accNum.setText("Számlaszám: " + MainPage.getActiveBankAccount().getId());
        ownerName.setText("Számlatulajdonos: " + MainPage.getActiveBankAccount().getOwnerName());

        ListUsers();


    }

    public void ListUsers() throws IOException, InterruptedException {
        RestApi api = new RestApi();
        User[] users = api.GetAllUsers(MainPage.getActiveBankAccount().getId(), MainPage.getActiveUser().getAuthToken());

        userList.getItems().clear();
        for (int i = 0; i < users.length ; i++) {
            userList.getItems().add(users[i].getFirstname() + " " + users[i].getLastname());
        }

    }


}

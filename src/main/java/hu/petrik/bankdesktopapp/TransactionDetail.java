package hu.petrik.bankdesktopapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionDetail {
    @javafx.fxml.FXML
    private Text total;
    @javafx.fxml.FXML
    private HBox myhbox;
    @javafx.fxml.FXML
    private Text createdAt;
    @javafx.fxml.FXML
    private Text endDate;
    @javafx.fxml.FXML
    private Text interval;
    @javafx.fxml.FXML
    private Text category;
    @javafx.fxml.FXML
    private Text user;
    @javafx.fxml.FXML
    private Text startDate;
    @javafx.fxml.FXML
    private Text desc;
    MainPage main = new MainPage();
    RestApi restApi = new RestApi();
    @javafx.fxml.FXML
    private VBox mainPage;
    @javafx.fxml.FXML
    private Button deleteTransactionBtn;


    public void initialize(){

        Platform.runLater(()->{
            if(MainPage.getFocusedTransaction() instanceof Expense && ((Expense) MainPage.getFocusedTransaction()).getRepeatableTransactionId() != null){

                    RepeatableExpense rep;
                    try {
                        rep = restApi.getRepeatableTransactionById(((Expense) MainPage.getFocusedTransaction()).getRepeatableTransactionId(), MainPage.getActiveUser().getAuthToken());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.total.setText("Összeg: " + MainPage.getFocusedTransaction().getTotal().toString());
                    this.createdAt.setText("Létrehozva: " + MainPage.getFocusedTransaction().getCreatedAt().toString());
                    this.endDate.setText("Befejeződési dátum: " + rep.getRepeatEnd().toString());
                    this.startDate.setText("Kezdési dátum: " + rep.getRepeatStart().toString());
                    this.category.setText("Kategória: " + MainPage.getFocusedTransaction().getCategory());
                    this.desc.setText("Leírás: " + MainPage.getFocusedTransaction().getDescription());

                    if(rep.getRepeatMetric().equalsIgnoreCase("day"))
                    {
                        this.interval.setText("Gyakoriság: " + rep.getRepeatAmount() + " naponta");
                    }
                    else if (rep.getRepeatMetric().equalsIgnoreCase("week")) {
                        this.interval.setText("Gyakoriság: " + rep.getRepeatAmount() + " hetente");
                    }
                    else if(rep.getRepeatMetric().equalsIgnoreCase("month"))
                    {
                        this.interval.setText("Gyakoriság: " + rep.getRepeatAmount() + " havonta");
                    }
                    else if(rep.getRepeatMetric().equalsIgnoreCase("year"))
                    {
                        this.interval.setText("Gyakoriság: " + rep.getRepeatAmount() + " évente");
                    }


                    this.desc.setText("Leírás: " + MainPage.getFocusedTransaction().getDescription());
                    try {
                        User user = restApi.getOneUser(MainPage.getFocusedTransaction().getUserId(), MainPage.getActiveUser().getAuthToken());
                        this.user.setText("Felhasználó: " + user.getFirstname() + " " + user.getLastname());
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

            }
            else
            {

                this.total.setText("Összeg: " + MainPage.getFocusedTransaction().getTotal().toString());
                this.createdAt.setText("Létrehozva: " + MainPage.getFocusedTransaction().getCreatedAt().toString());
                this.category.setText("Kategória: " + MainPage.getFocusedTransaction().getCategory());
                this.desc.setText("Leírás: " + MainPage.getFocusedTransaction().getDescription());
                try {
                    User user = restApi.getOneUser(MainPage.getFocusedTransaction().getUserId(),MainPage.getActiveUser().getAuthToken());
                    this.user.setText("Felhasználó: " + user.getFirstname() + " " +user.getLastname() );
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.endDate.setVisible(false);
                this.endDate.setManaged(false);
                this.startDate.setVisible(false);
                this.startDate.setManaged(false);
                this.interval.setVisible(false);
                this.interval.setManaged(false);

            }
        });
    }

    public Text getTotal() {
        return total;
    }

    public void setTotal(Text total) {
        this.total = total;
    }

    public HBox getMyhbox() {
        return myhbox;
    }

    public void setMyhbox(HBox myhbox) {
        this.myhbox = myhbox;
    }

    public Text getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Text createdAt) {
        this.createdAt = createdAt;
    }

    public Text getEndDate() {
        return endDate;
    }

    public void setEndDate(Text endDate) {
        this.endDate = endDate;
    }

    public Text getInterval() {
        return interval;
    }

    public void setInterval(Text interval) {
        this.interval = interval;
    }

    public Text getCategory() {
        return category;
    }

    public void setCategory(Text category) {
        this.category = category;
    }

    public Text getUser() {
        return user;
    }

    public void setUser(Text user) {
        this.user = user;
    }

    public Text getStartDate() {
        return startDate;
    }

    public void setStartDate(Text startDate) {
        this.startDate = startDate;
    }

    public Text getDesc() {
        return desc;
    }

    public void setDesc(Text desc) {
        this.desc = desc;
    }

    @javafx.fxml.FXML
    public void deleteTransaction(ActionEvent actionEvent) {

            System.out.printf("Delete transaction: %s\n", MainPage.getFocusedTransaction().getId());
            try {
                restApi.deleteTransaction(MainPage.getFocusedTransaction().getId(),MainPage.getFocusedTransaction().getClass().getSimpleName() ,MainPage.getActiveUser().getAuthToken());
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        Stage stage = (Stage)deleteTransactionBtn.getScene().getWindow();
        stage.close();




    }
}

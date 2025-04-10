package hu.petrik.bankdesktopapp;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.util.Calendar;

public class RepeatableComponent extends AnchorPane {
    @javafx.fxml.FXML
    private Text dateText;
    @javafx.fxml.FXML
    private Text amount;
    @javafx.fxml.FXML
    private Text nameText;
    @javafx.fxml.FXML
    private AnchorPane container;
    @javafx.fxml.FXML
    private Button deleteBtn;
    @javafx.fxml.FXML
    private Button stopBtn;
    RestApi restApi;
    private String repeatableId;


    /**
     * Initializes an instance of the RepeatableComponent class, setting up the UI and populating initial data
     * based on the provided {@link RepeatableTransaction} object.
     *
     * @param repeatable the RepeatableTransaction object containing transaction details such as start date, end date,
     *                   total amount, name, and ID, which are used to populate the component's UI elements.
     */
    public RepeatableComponent(RepeatableTransaction repeatable) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("repeatableTransaction.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(RepeatableComponent.this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(repeatable.getRepeatStart());
        end.setTime(repeatable.getRepeatEnd());
        String convertedStart = start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH) + 1) + "-" + start.get(Calendar.DAY_OF_MONTH);
        String convertedEnd = end.get(Calendar.YEAR) + "-" + end.get(Calendar.MONTH) + "-" + end.get(Calendar.DAY_OF_MONTH);
        this.dateText.setText( convertedStart + " - " +  convertedEnd);
        this.amount.setText(repeatable.getTotal().toString());
        this.nameText.setText(repeatable.getName());
        this.repeatableId = repeatable.getId();
    }


    public Button getDeleteBtn() {
        return deleteBtn;
    }

    public void setDeleteBtn(Button deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public String getRepeatableId() {
        return repeatableId;
    }

    public void setRepeatableId(String repeatableId) {
        this.repeatableId = repeatableId;
    }

    public Button getStopBtn() {
        return stopBtn;
    }

    public void setStopBtn(Button stopBtn) {
        this.stopBtn = stopBtn;
    }


    /*
    @javafx.fxml.FXML
    public void deleteRepeatable(ActionEvent actionEvent) throws IOException, InterruptedException {
        restApi = new RestApi();
        restApi.deleteRepeatableTransactions(repeatableId, MainPage.getActiveUser().getAuthToken());

    }

     */
}

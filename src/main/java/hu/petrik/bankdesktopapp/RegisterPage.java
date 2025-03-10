package hu.petrik.bankdesktopapp;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterPage {

    @javafx.fxml.FXML
    private Button registerBtn;
    @javafx.fxml.FXML
    private VBox mainLoginBox;
    @javafx.fxml.FXML
    private TextField lastNameInput;
    @javafx.fxml.FXML
    private PasswordField passwordInput;
    @javafx.fxml.FXML
    private TextField emailInput;
    @javafx.fxml.FXML
    private TextField firstNameInput;

    public Stage stage;
    @javafx.fxml.FXML
    private Button backBtn;
    @javafx.fxml.FXML
    private Text errorText;

   RestApi restApi = new RestApi();

    @javafx.fxml.FXML
    public void registerAccount(Event event) throws IOException, InterruptedException {
        try{
            if(lastNameInput.getText().equals("") || passwordInput.getText().equals("")  || emailInput.getText().equals("")  || firstNameInput.getText().equals("")   ){

                //System.out.println("Minden mezőt meg kell adni");
                errorText.setText("Minden meg kell adni");
                errorText.setVisible(true);
            }
            else {
                if(validate(emailInput.getText())) {
                    System.out.println(restApi.createUser(firstNameInput.getText(), lastNameInput.getText(), emailInput.getText(), passwordInput.getText()));
                    Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.getScene().setRoot(root);
                    stage.setMinHeight(400);
                    stage.setMinWidth(300);
                    stage.show();
                }
                else{
                    //System.out.println("Helytelen email");
                    errorText.setText("Helytelen email");
                    errorText.setVisible(true);
                }
            }


        }
        catch(Exception e){
            //System.out.println("Foglalt email");
            errorText.setText("Foglalt email");
            errorText.setVisible(true);
            e.toString();
        }
    }

    @javafx.fxml.FXML
    public void backToLogin(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.setMinHeight(400);
        stage.setMinWidth(300);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    public static final Pattern emailRegex =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = emailRegex.matcher(emailStr);
        return matcher.matches();
    }
}

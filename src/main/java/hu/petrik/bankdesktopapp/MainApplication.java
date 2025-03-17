package hu.petrik.bankdesktopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

// ctrl+ctrl -> mvn javafx:jlink , mvn package - snapshot.jar
/*
jpackage --input target/ \
        --name JPackageDemoApp \
        --main-jar BankDesktopApp-1.0-SNAPSHOT.jar \
        --main-class hu.petrik.bankdesktopapp.MainApplication \
        --type EXE \
        --java-options '--enable-preview'

 */
        public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        stage.setTitle("Bank Desktop App!");
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}
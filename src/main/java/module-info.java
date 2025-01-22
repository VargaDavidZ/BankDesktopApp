module hu.petrik.bankdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;

    requires com.fasterxml.jackson.databind;


    opens hu.petrik.bankdesktopapp to javafx.fxml;
    exports hu.petrik.bankdesktopapp;
}
module hu.petrik.bankdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens hu.petrik.bankdesktopapp to javafx.fxml;
    exports hu.petrik.bankdesktopapp;
}
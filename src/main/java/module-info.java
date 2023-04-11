module com.juanjochat {
    requires javafx.controls;
    requires javafx.fxml;
    requires SharedLibrary;


    opens com.juanjochat to javafx.fxml;
    exports com.juanjochat;
    exports com.juanjochat.model;
    opens com.juanjochat.model to javafx.fxml;
    exports com.juanjochat.service;
    opens com.juanjochat.service to javafx.fxml;
    exports com.juanjochat.controller;
    opens com.juanjochat.controller to javafx.fxml;
}
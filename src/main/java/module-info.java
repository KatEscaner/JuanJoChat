module com.juanjochat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.juanjochat to javafx.fxml;
    exports com.juanjochat;
}
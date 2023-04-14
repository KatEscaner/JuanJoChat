package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Message;
import model.UserCredentials;

import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {
    @FXML
    TextField txtEmail;

    @FXML
    PasswordField txtPassword;

    @FXML
    Button btnLogin;

    @FXML
    Button btnClear;

    @FXML
    AnchorPane acpLogin;

    boolean isLogged = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ChatApplication.socket = new Socket(InetAddress.getLocalHost(), ChatApplication.PORT);
            ChatApplication.objOut = new ObjectOutputStream(ChatApplication.socket.getOutputStream());
            ChatApplication.dataIn = new DataInputStream(ChatApplication.socket.getInputStream());
            ChatApplication.objIn = new ObjectInputStream(ChatApplication.socket.getInputStream());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginButtonAction(){
        if (!isLogged) {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            if (isValidCredentials(email, password)) {
                UserCredentials credentials = new UserCredentials(email, password);
                try {
                    ChatApplication.objOut.writeObject(credentials);
                    ChatApplication.objOut.flush();
                    if (ChatApplication.dataIn.readBoolean()) {
                        isLogged = true;
                        ChatApplication.creteListener();
                        showChatWindow();
                    } else {
                        showAlert("Incorrect credentials", "Account not fount");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Bad credentials", "Write correctly credentials");
            }
        }
    }

    private boolean isValidCredentials(String email, String password) {
        return isValidEmail(email) && password != null && !password.isEmpty();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if(email != null) {
            Matcher matcher = pattern.matcher(email);
            return matcher.find();
        }
        return false;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void showChatWindow() {
        try {
            ChatApplication.setEmail(txtEmail.getText());

            Stage currentStage = (Stage) acpLogin.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
            Stage stage = new Stage();
            stage.setTitle("JuanJo's Chat");
            stage.setScene(scene);
            Image image = new Image("file:src/img/icon.png");
            stage.getIcons().add(image);

            stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> {
                Platform.exit();
                System.exit(0);
            });

            ChatApplication.controller = fxmlLoader.getController();
            stage.show();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleClearButtonAction(){
        txtEmail.clear();
        txtPassword.clear();
    }
}
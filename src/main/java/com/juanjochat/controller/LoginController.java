package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    public void getCredentials(){
        if (!isLogged) {
            String email = txtEmail.getText();
            String password = txtPassword.getText();
            if (checkEmail(email) && password != null && !password.equals("")) {
                UserCredentials credentials = new UserCredentials(email, password);
                try {
                    ChatApplication.objOut.writeObject(credentials);
                    ChatApplication.objOut.flush();
                    if (ChatApplication.dataIn.readBoolean()) {
                        isLogged = true;
                        ChatApplication.creteListener();
                        showChat();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Bad credentials");
                        alert.setContentText("Account not found");
                        alert.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Bad credentials");
                alert.setContentText("Write correctly credentials");
                alert.show();
            }
        }
    }

    private void showChat() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chat.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 400);
            Stage stage = new Stage();
            stage.setTitle("JuanJo's Chat");
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) acpLogin.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        if(email != null) {
            Matcher matcher = pattern.matcher(email);
            return matcher.find();
        }
        return false;
    }

    public void clear(){
        txtEmail.clear();
        txtPassword.clear();
    }
}
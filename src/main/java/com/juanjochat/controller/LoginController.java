package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import com.juanjochat.model.UserCredentials;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
    TextField txtPassword;

    @FXML
    Button btnLogin;

    @FXML
    Button btnClear;

    boolean isLogged = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ChatApplication.socket = new Socket(InetAddress.getLocalHost(), ChatApplication.PORT);
            ChatApplication.objOut = new ObjectOutputStream(ChatApplication.socket.getOutputStream());
            ChatApplication.objIn = new ObjectInputStream(ChatApplication.socket.getInputStream());
            ChatApplication.dataIn = new DataInputStream(ChatApplication.socket.getInputStream());
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
                        ChatApplication.creteListener();
                        showChat();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Bad credentials");
                alert.setContentText("Incorrect credentials");
                alert.show();
            }
        }
    }

    private void showChat() {

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
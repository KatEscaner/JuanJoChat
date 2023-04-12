package com.juanjochat;


import com.juanjochat.controller.ChatController;
import com.juanjochat.model.MessageGroup;
import com.juanjochat.service.ListenThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Group;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ChatApplication extends Application {
    public static final int PORT = 7000;
    private static volatile boolean close = false;
    private static final Set<Group> groups = new HashSet<>();
    public static MessageGroup messageGroup = new MessageGroup();
    public static Socket socket;
    public static ObjectOutputStream objOut;
    public static ObjectInputStream objIn;
    public static DataInputStream dataIn;
    public static ChatController controller = new ChatController();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("JuanJo's Chat");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    public static boolean isClose() {
        return close;
    }

    public static void setClose(boolean close) {
        ChatApplication.close = close;
    }
    public static void addGroup(Group group){
        if(groups.add(group)) {
            messageGroup.addGroup(group.getId());
            try {
                controller.updateGroupList();
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }

    public static void creteListener(){
        ListenThread lt = new ListenThread(objIn);
        lt.start();
    }

    public static Set<Group> getAllGroups(){
        return groups;
    }
}
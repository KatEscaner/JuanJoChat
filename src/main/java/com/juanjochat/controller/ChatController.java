package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import com.juanjochat.utils.GroupListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import model.Group;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.WeakHashMap;

public class ChatController implements Initializable {
    @FXML
    AnchorPane acpChat;

    @FXML
    ListView<Group> lvGroups;

    public void updateGroupList() {
        ObservableList<Group> items = FXCollections.observableArrayList(ChatApplication.getAllGroups());
        try {
            if (lvGroups != null) {
                Platform.runLater(() -> lvGroups.setItems(items));

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGroupList();
        Platform
                .runLater(() -> lvGroups.setCellFactory(param -> new GroupListCell()));

    }

    @FXML
    private Group handleGroupSelection() {
        Group selectedGroup = lvGroups.getSelectionModel().getSelectedItem();
        if (selectedGroup != null) {
            return selectedGroup;
        }
        return null;
    }

}

package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Group;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    @FXML
    ListView<Group> lvGroups;

    public void updateGroupList() {
        ObservableList<model.Group> items = FXCollections.observableArrayList(ChatApplication.getAllGroups());
        lvGroups.setItems(items);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGroupList();
    }


}

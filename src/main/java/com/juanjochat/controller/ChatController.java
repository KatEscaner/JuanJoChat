package com.juanjochat.controller;

import com.juanjochat.ChatApplication;
import com.juanjochat.utils.GroupListCell;
import com.juanjochat.utils.MessageListCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.Group;
import model.Message;
import model.User;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChatController implements Initializable {
    @FXML
    AnchorPane acpChat;

    @FXML
    ListView<Group> lvGroups;

    @FXML
    ListView<Message> lvMessage;

    @FXML
    Label lblGroupName;

    @FXML
    Label lblGroupMembers;

    @FXML
    Label lblGroupTypeFilter;

    @FXML
    TextArea txtMessage;

    @FXML
    Button btnSend;

    @FXML
    Button btnClear;

    public void updateGroupList() {
        ObservableList<Group> items = FXCollections.observableArrayList(ChatApplication.getAllGroups());
        try {
            if (lvGroups != null) {
                Platform.runLater(() -> lvGroups.setItems(items));
            }

            // Get selected group
            Group selectedGroup = lvGroups.getSelectionModel().getSelectedItem();
            // Check if group is not null
            if (selectedGroup != null) {
                // Update message list
                lvMessage.setItems(null);
                lvMessage.setItems(FXCollections
                        .observableArrayList(ChatApplication.messageGroup.getMessageList(selectedGroup.getId())));
            }

        } catch (Exception e){}
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateGroupList();
        Platform.runLater(() -> lvGroups.setCellFactory(param -> new GroupListCell()));
        Platform.runLater(() -> lvMessage.setCellFactory(param -> new MessageListCell(getSelectedGroup())));

    }

    @FXML
    private void handleGroupSelection() {
        Group selectedGroup = getSelectedGroup();
        if (selectedGroup != null) {
            lvMessage.setItems(FXCollections
                    .observableArrayList(ChatApplication.messageGroup.getMessageList(selectedGroup.getId())));
            Platform.runLater(() -> lvMessage.setCellFactory(param -> new MessageListCell(selectedGroup)));
            lblGroupName.setText(selectedGroup.getType());

            if (selectedGroup.getType().equals("private")) {
                lblGroupName.setText(selectedGroup.members
                        .stream().filter(e -> !Objects.equals(e.getEmail(), ChatApplication.getEmail()))
                        .findFirst().get().getName());
            } else {
                lblGroupName.setText(selectedGroup.getName());
            }

            lblGroupMembers.setText(selectedGroup.members.stream()
                    .map(User::getName)
                    .collect(Collectors.joining(", ")));

            lblGroupTypeFilter.setText(selectedGroup.getType() + " / " +
                    (selectedGroup.getFilter() > 0 ? "Filtered" : "No filtered" ));
        }
    }

    private Group getSelectedGroup(){
        return lvGroups.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleSendButtonAction(){
        try {
            if (!txtMessage.getText().equals("")) {

                Message message = new Message(getSelectedGroup().getId(), txtMessage.getText());
                ChatApplication.objOut.writeObject(message);

                handleClearButtonAction();
            }
        } catch (Exception e){}
    }

    @FXML
    private void  handleClearButtonAction(){
        if (!txtMessage.getText().equals("")) {
            txtMessage.clear();
        }
    }
}


package com.juanjochat.utils;

import com.juanjochat.ChatApplication;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.Group;
import model.Message;
import model.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupListCell extends ListCell<Group> {
    private final Label title = new Label();
    private final Label detail = new Label();
    private final HBox layout = new HBox(title, detail);

    public GroupListCell() {
        super();
        title.setStyle("-fx-font-size: 16px;");
        detail.setStyle("-fx-font-size: 14px;");
        layout.setAlignment(Pos.CENTER_LEFT);
        setAlignment(Pos.CENTER_LEFT);
        setPadding(new Insets(10, 0, 10, 10));
    }

    @Override
    protected void updateItem(Group item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            setText(null);
        } else {
            if (item.getType().equals("private")) {
                title.setText(item.members
                        .stream().filter(e -> !Objects.equals(e.getEmail(), ChatApplication.getEmail()))
                        .findFirst().get().getName());
            } else {
                title.setText(item.getName());
            }

            List<Message> messageList = ChatApplication.messageGroup.getMessageList(item.getId());
            if (messageList.isEmpty()) {
                detail.setText("");
            } else {
                Message latestMessage = messageList.get(messageList.size() - 1);
                detail.setText(": " + latestMessage.getContent());
            }

            Platform.runLater(() -> setGraphic(layout));
            title.setTooltip(createTooltip(item)); // Update toolTip text
            animateHover();
        }
    }

    private Tooltip createTooltip(Group item) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(item.members.stream()
                .map(User::getName)
                .collect(Collectors.joining(", ")));
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(400);
        return tooltip;
    }

    private void animateHover() {
        setOnMouseEntered(event -> {
            if (!isEmpty()) {
                setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
                title.setTextFill(Color.WHITE);
                detail.setTextFill(Color.WHITE);
            }
        });

        setOnMouseExited(event -> {
            if (!isEmpty()) {
                setBackground(Background.EMPTY);
                title.setTextFill(Color.BLACK);
                detail.setTextFill(Color.BLACK);
            }
        });
    }
}



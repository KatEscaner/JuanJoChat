package com.juanjochat.utils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Group;
import model.Message;
import model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MessageListCell extends ListCell<Message> {
    private Group group;
    private static Map<Long, Color> userColors = new HashMap<>();
    private static Random random = new Random();

    public MessageListCell(Group group) {
        this.group = group;
    }

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        Label lblSenderName = new Label("");
        Label lblContent = new Label("");
        Label lblTimestampLabel = new Label("");
        VBox messageContainer = new VBox();


        if (empty || message == null) {
            messageContainer.getChildren().addAll(lblSenderName, lblContent, lblTimestampLabel);
            messageContainer.setStyle("-fx-font-weight: bold; -fx-background-color: #ffffff; -fx-padding: 10px; -fx-font-size: 13px;");
        } else {
            // Search user by ID in group members list
            User sender = group.members.stream()
                    .filter(user -> user.getId() == message.getSender())
                    .findFirst()
                    .orElse(null);
            String senderName = sender != null ? sender.getName() : String.valueOf(message.getSender());
            lblSenderName.setText(senderName);
            lblSenderName.setStyle("-fx-font-weight: bold; -fx-text-fill: #2f2f2f; -fx-font-size: 15px;");
            lblContent.setText(message.getContent());
            lblContent.setStyle("-fx-text-fill: #444444; -fx-font-size: 14px; -fx-font-weight: bold;");
            LocalDateTime timestamp = message.getTimestamp();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            lblTimestampLabel.setText(timestamp.format(formatter));
            lblTimestampLabel.setStyle("-fx-font-size: 10pt; -fx-text-fill: #000;");
            messageContainer.getChildren().addAll(lblSenderName, lblContent, lblTimestampLabel);
            messageContainer.setStyle("-fx-font-weight: bold; -fx-padding: 10px; -fx-font-size: 13px;");

            // Get user color or generate a new one if not exists
            long userID = message.getSender();
            Color userColor = userColors.get(userID);
            if (userColor == null) {
                userColor = generateRandomColor();
                userColors.put(userID, userColor);
            }

            // Set user color as background of message container
            messageContainer.setBackground(new Background(new BackgroundFill(userColor, null, null)));
            messageContainer.setStyle("-fx-padding: 10px;");

        }
        Platform.runLater(() -> setGraphic(messageContainer));
    }

    private Color generateRandomColor() {
        double hue = random.nextDouble() * 360;
        double saturation = 0.3 + random.nextDouble() * 0.7;
        double brightness = 0.9 + random.nextDouble() * 0.1;
        return Color.hsb(hue, saturation, brightness);
    }

}


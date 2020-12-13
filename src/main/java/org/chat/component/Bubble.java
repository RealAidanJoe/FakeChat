package org.chat.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.chat.App;
import org.chat.utils.MessageProcessing;

import java.io.IOException;

public class Bubble extends HBox {
    @FXML
    private Label msgLab;
    @FXML
    private HBox aliBox;

    public Bubble(String msg) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("bubble.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        if (MessageProcessing.isMyMessage(msg)) {
            msgLab.setText(msg);
            msgLab.setStyle("-fx-background-color: #9eea6a");
            aliBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            msgLab.setText(MessageProcessing.getFriendMessage(msg));
            msgLab.setStyle("-fx-background-color: white");
            aliBox.setAlignment(Pos.CENTER_LEFT);
        }
    }
}

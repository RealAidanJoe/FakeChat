package org.chat.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.utils.ProcessMessage;

import java.io.IOException;

public class FriendComponent extends AnchorPane {
    @FXML
    private Label friendNameLab;
    @FXML
    private Circle hasNewMsgCir;

    Friend info;

    public FriendComponent(String name) {
//        info = new Friend(address, 2077, name);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("friend.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setFriendName(name);
    }

    public void setFriendName(String name) {
        friendNameLab.setText(name);
    }

    public void setHasNewMsg(boolean hasNewMsg) {
        hasNewMsgCir.setVisible(hasNewMsg);
    }


    @FXML
    private void chooseFriend() {
//        System.out.println("hello");
        setHasNewMsg(false);
    }
}

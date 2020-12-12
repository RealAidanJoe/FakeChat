package org.chat.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.chat.App;
import org.chat.bean.Friend;

import java.io.IOException;
import java.util.ArrayList;

public class FriendListCell extends ListCell<Friend> {
    @FXML
    private Label friendNameLab;
    @FXML
    private Circle hasNewMsgCir;

    @Override
    protected void updateItem(Friend friend, boolean b) {
        super.updateItem(friend, b);
        if (!b) {
            AnchorPane pane = null;
            try {
                pane = (AnchorPane) App.loadFXML("friend");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Circle cir = (Circle) pane.lookup("#hasNewMsgCir");
            Label nameLab = (Label) pane.lookup("#nameLab");
            Label chatLab = (Label) pane.lookup("#chatLab");
            nameLab.setText(friend.friendName);
            ArrayList<String> chattingRecords = friend.getChattingRecords();
            if (chattingRecords.size() != 0) {
                chatLab.setText(chattingRecords.get(chattingRecords.size() - 1));
            }
//            cir.setVisible(true);
//            System.out.println(cir);
            setGraphic(pane);
        }
    }
}

package org.chat.component;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.utils.MessageProcessing;

import java.io.IOException;
import java.util.ArrayList;

public class FriendListCell extends ListCell<Friend> {
    private AnchorPane pane;
    Label nameLab, chatLab;

    public FriendListCell() {
        try {
            pane = (AnchorPane) App.loadFXML("friend");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        横向自适应
        setPrefWidth(0);
        pane.prefWidthProperty().bind(widthProperty());
    }

    @Override
    protected void updateItem(Friend friend, boolean b) {
//        System.out.println("update");
        super.updateItem(friend, b);
        if (!b) {

//            Circle cir = (Circle) pane.lookup("#hasNewMsgCir");
            nameLab = (Label) pane.lookup("#nameLab");
            nameLab.setText(friend.friendName);
            chatLab = (Label) pane.lookup("#chatLab");


            ArrayList<String> chattingRecords = friend.exportChattingRecords();
            String s = "";

            if (chattingRecords.size() != 0) {
                String msg = chattingRecords.get(chattingRecords.size() - 1);
                if (MessageProcessing.isMyMessage(msg)) {
                    s = msg;
                } else {
                    s = MessageProcessing.getFriendMessage(msg);
                }
            }
            String finalS = s;
            Platform.runLater(() -> {
                chatLab.setText(finalS);
            });
            setGraphic(pane);
        }
    }


}



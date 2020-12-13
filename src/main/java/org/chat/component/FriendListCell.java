package org.chat.component;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.utils.MessageProcessing;

import java.io.IOException;

public class FriendListCell extends ListCell<Friend> {
    private AnchorPane pane;
    Label nameLab, chatLab;
    Circle cir;

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

            cir = (Circle) pane.lookup("#hasNewMsgCir");
            nameLab = (Label) pane.lookup("#nameLab");
            nameLab.setText(friend.friendName);
            chatLab = (Label) pane.lookup("#chatLab");
            setOnMouseClicked((event) -> {
                cir.setVisible(false);
            });

            ObservableList<String> chattingRecords = friend.exportChattingRecords();

//            聊天记录改变代表有新消息
            chattingRecords.addListener((ListChangeListener<? super String>) change -> {

                String s;
                String msg = chattingRecords.get(chattingRecords.size() - 1);
                if (MessageProcessing.isMyMessage(msg)) {
                    s = msg;
                } else {
                    cir.setVisible(true);
                    s = MessageProcessing.getFriendMessage(msg);
                }
                Platform.runLater(() -> {
                    chatLab.setText(s);
                });
            });

            setGraphic(pane);
        }
    }


}



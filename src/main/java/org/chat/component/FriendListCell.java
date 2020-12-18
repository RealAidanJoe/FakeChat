package org.chat.component;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.nio.channels.SelectableChannel;

public class FriendListCell extends ListCell<Friend> {
    private AnchorPane pane;
    Label nameLab, chatLab;
    Circle cir;

    private boolean isFirst;

    public FriendListCell() {
        try {
            pane = (AnchorPane) App.loadFXML("friend");
            cir = (Circle) pane.lookup("#hasNewMsgCir");
            nameLab = (Label) pane.lookup("#nameLab");
            chatLab = (Label) pane.lookup("#chatLab");
        } catch (IOException e) {
            e.printStackTrace();
        }

//        横向自适应
        setPrefWidth(0);
        pane.prefWidthProperty().bind(widthProperty());

        setOnMouseClicked((event) -> cir.setVisible(false));
        isFirst = true;
    }

    @Override
    protected void updateItem(Friend friend, boolean empty) {
//        System.out.println("update");
        super.updateItem(friend, empty);

//        System.out.println(getIndex() + "\t" + friend);
        if (empty) {
            setGraphic(null);
        } else {
//            避免重复添加监听器
            if (isFirst) {
//                System.out.println("first");
                ObservableList<String> chattingRecords = friend.exportChattingRecords();

//            聊天记录改变代表有新消息
                chattingRecords.addListener((ListChangeListener<? super String>) change -> {
//                    System.out.println(getIndex());
                    String s;
                    String msg = chattingRecords.get(chattingRecords.size() - 1);
                    if (MessageProcessing.isMyMessage(msg)) {
                        s = msg;
                    } else {
                        if (!isSelected()) {
                            cir.setVisible(true);
                        }
                        s = MessageProcessing.getFriendMessage(msg);
                    }
                    Platform.runLater(() -> chatLab.setText(s));
                });
                isFirst = false;
            }

            nameLab.setText(friend.friendName);
            setGraphic(pane);
        }

    }


}



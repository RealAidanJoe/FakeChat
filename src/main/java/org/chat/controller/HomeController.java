package org.chat.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.chat.bean.Friend;
import org.chat.component.FriendListCell;
import org.chat.utils.ProcessMessage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public Label friendNameLab;
    @FXML
    private ListView<Friend> friendListView;
    ObservableList<Friend> friendList = FXCollections.observableArrayList();


    public HomeController() {
        friendList.add(new Friend(ProcessMessage.getAddress(), 2077, "aidan joe"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendListView.setCellFactory((ListView<Friend> l) -> new FriendListCell());
        friendListView.setItems(friendList);

//        切换好友
        friendListView.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
//            System.out.println(newVal);
            friendNameLab.setText(newVal.friendName);
        });
    }


}

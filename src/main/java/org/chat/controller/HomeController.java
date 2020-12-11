package org.chat.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.chat.component.FriendComponent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    public VBox friendList;


    public HomeController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendList.getChildren().add(new FriendComponent("snake"));
        friendList.getChildren().add(new FriendComponent("anjo"));
        friendList.getChildren().add(new FriendComponent("ant"));
        FriendComponent f = (FriendComponent) friendList.getChildren().get(1);
        f.setFriendName("haha");

//        friendList.
    }


}

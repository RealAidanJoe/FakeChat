package org.chat.controller;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.chat.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {
    public VBox userList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent node = null;
        try {
            node = App.loadFXML("friend");
        } catch (IOException e) {
            e.printStackTrace();
        }

        userList.getChildren().add(node);
    }
}

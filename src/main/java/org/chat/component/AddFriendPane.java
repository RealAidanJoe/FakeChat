package org.chat.component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.chat.App;
import org.chat.bean.Friend;

import java.io.IOException;

public class AddFriendPane extends AnchorPane {
    @FXML
    private TextField friendNameTex, ipTex,
            msgPortTex, filePortTex;

    public AddFriendPane() {
        FXMLLoader fxmlLoader = App.getFXMLLoader("addFriend");
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Friend createFriend() {
        String name = friendNameTex.getText();
        int msgPort = Integer.parseInt(msgPortTex.getText());
        int filePort = Integer.parseInt(filePortTex.getText());
        String ip = ipTex.getText();
        return new Friend(name, msgPort, filePort, ip);
    }

    @FXML
    private void closeWin() {
        setVisible(false);
    }


    private double xOffset;
    private double yOffset;

    @FXML
    public void pressWin(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX() - getLayoutX();
        yOffset = mouseEvent.getSceneY() - getLayoutY();
    }

    @FXML
    private void dragWin(MouseEvent mouseEvent) {
//        System.out.println(mouseEvent.getSceneX() + "\t" + mouseEvent.getScreenX());
//        xOffset + mouseEvent.getSceneX()
        setLayoutX(mouseEvent.getSceneX() - xOffset);
        if (mouseEvent.getSceneY() - yOffset < 0) {
            setLayoutY(0);
        } else {
            setLayoutY(mouseEvent.getSceneY() - yOffset);
        }
    }

    @FXML
    private void confirm(ActionEvent event){
        Parent parent= (Parent) event.getSource();
        System.out.println(parent);
        System.out.println("confirm");
    }
}

package org.chat.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.bean.User;
import org.chat.component.Bubble;
import org.chat.component.FriendListCell;
import org.chat.utils.ProcessMessage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private VBox chartList;
    @FXML
    private Button sendBtn;
    @FXML
    private Label friendNameLab;
    @FXML
    private TextArea msgText;
    @FXML
    private ListView<Friend> friendListView;
    ObservableList<Friend> friendList = FXCollections.observableArrayList();
    private Friend selectedFriend;
    User user;

    public HomeController() {
        user = new User(2077);
        friendList.add(new Friend(ProcessMessage.getAddress(), 2077, "aidan joe"));
        friendList.add(new Friend("172.21.14.201", 2077, "snake"));
//        friendList.add(new Friend(ProcessMessage.getAddress(), 2077, "snake"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendListView.setCellFactory((ListView<Friend> l) -> new FriendListCell());
        friendListView.setItems(friendList);

        user.getMessage((msg) -> {
//            System.out.println(msg);
            Platform.runLater(() -> {
                chartList.getChildren().add(new Bubble(msg));
            });
        });

//        切换好友
        friendListView.getSelectionModel().selectedItemProperty().addListener((ov, oldVal, newVal) -> {
//            System.out.println(newVal);
            selectedFriend = newVal;
//            更改显示好友名
            friendNameLab.setText(newVal.friendName);
//            可以发送消息
            msgText.setDisable(false);
            sendBtn.setDisable(false);
//            显示聊天记录
            chartList.getChildren().clear();
            for (String msg : newVal.getChattingRecords()) {
                chartList.getChildren().add(new Bubble(msg));
            }
        });
    }

    @FXML
    private void sendMsg(Event event) {
        String msg = msgText.getText();
//        能输入证明已选择好友
        if (!Objects.equals(msg, "")) {
//            System.out.println(msg);

            chartList.getChildren().add(new Bubble(msg));
            selectedFriend.sendMessage(msg);
            msgText.setText("");
        }
    }

    @FXML
    private void openSetting() {
        try {
            Scene setting = new Scene(App.loadFXML("setting"));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.setScene(setting);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage stage;

    private Stage getStageByEvent(Event event) {
        if (stage == null) {
            Parent parent = (Parent) event.getSource();
            stage = (Stage) parent.getScene().getWindow();
        }
        return stage;
    }

    @FXML
    private void closeWin(ActionEvent event) {
        getStageByEvent(event).close();
    }

    @FXML
    private void minWin(ActionEvent event) {
        getStageByEvent(event).setIconified(true);
    }

    @FXML
    private void maxWin(ActionEvent event) {
        Stage stage = getStageByEvent(event);
        stage.setMaximized(!stage.isMaximized());
    }

    private double xOffset;
    private double yOffset;

    @FXML
    public void pressWin(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    @FXML
    private void dragWin(MouseEvent mouseEvent) {
        Stage stage = getStageByEvent(mouseEvent);
        stage.setX(mouseEvent.getScreenX() - xOffset);
        if (mouseEvent.getScreenY() - yOffset < 0) {
            stage.setY(0);
        } else {
            stage.setY(mouseEvent.getScreenY() - yOffset);
        }
    }
}

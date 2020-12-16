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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.bean.User;
import org.chat.component.Bubble;
import org.chat.component.FriendListCell;
import org.chat.utils.MessageProcessing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    private int selectedIndex;
    private Friend selectedFriend;
    User user;

    FileChooser chooser;

    public HomeController() {
        user = new User(2077, 2088, new File("c:/"));
        friendList.add(new Friend("AidanJoe", 2077, 2088, MessageProcessing.getAddress()));
        friendList.add(new Friend("snake", 2077, 2088, "172.21.14.200"));
        friendList.add(new Friend("ant", 2077, 2088, MessageProcessing.getAddress()));

        chooser = new FileChooser();
        chooser.setTitle("发送文件");
        chooser.setInitialDirectory(new File("C:/")); // 初始目录
        // 文件类型过滤器
//        chooser.getExtensionFilters().add(
//                new FileChooser.ExtensionFilter("所有音乐", "*.wav", "*.mp3"));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        friendListView.setCellFactory((ListView<Friend> l) -> new FriendListCell());
        friendListView.setItems(friendList);

        user.listenMessage((msg) -> {
            int sendIndex = MessageProcessing.messageReceive(msg, new ArrayList<>(friendList));
//            System.out.println(sendIndex);
//            System.out.println(msg);
//            selectedFriend.addChattingRecords(msg);
//            未知好友发送消息
            if (sendIndex == MessageProcessing.NOT_ON_LIST) {
                System.out.println("未知");

            } else if (sendIndex == selectedIndex) {  //  当前正在聊天好友
                Platform.runLater(() -> chartList.getChildren().add(new Bubble(msg)));
            }
//            else { // 其他好友
//
//            }
        });

//        监听切换好友
        friendListView.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> {
//            System.out.println(newVal.intValue());
            selectedIndex = newVal.intValue();
//            避免刷新时出错
            selectedFriend = friendList.get(selectedIndex);

//            更改显示好友名
            friendNameLab.setText(selectedFriend.friendName);
//            可以发送消息
            msgText.setDisable(false);
            sendBtn.setDisable(false);
//            显示聊天记录
            chartList.getChildren().clear();
            for (String msg : selectedFriend.exportChattingRecords()) {
                chartList.getChildren().add(new Bubble(msg));
            }
        });

        friendListView.getSelectionModel().select(0);
    }

    @FXML
    private void sendMsg() {
//        System.out.println(event.getSource());
        String msg = msgText.getText();
//        能输入证明已选择好友
        if (!Objects.equals(msg, "")) {
//            System.out.println(msg);

            chartList.getChildren().add(new Bubble(msg));
            selectedFriend.sendMessage(msg);
            msgText.setText("");
        }
    }

    private final Stage secStage = new Stage(StageStyle.TRANSPARENT);

    @FXML
    private void openAddFriend() {
        try {
            Scene setting = new Scene(App.loadFXML("addFriend"));
            secStage.setScene(setting);
            secStage.setTitle("添加好友");
            secStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSetting() {
        try {
            Scene setting = new Scene(App.loadFXML("setting"));
            secStage.setScene(setting);
            secStage.setTitle("设置");
            secStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage rootStage;

    private Stage getStageByEvent(Event event) {
        if (rootStage == null) {
            Parent parent = (Parent) event.getSource();
            rootStage = (Stage) parent.getScene().getWindow();
        }
        return rootStage;
    }

    @FXML
    private void closeWin(ActionEvent event) {
        Platform.exit();
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

    @FXML
    private void inputMsg(KeyEvent keyEvent) {
//        System.out.println("get:" + keyEvent.getCode());
        if (keyEvent.getCode() == KeyCode.ENTER) {
//            System.out.println("enter");
            sendMsg();
        }
    }

    @FXML
    private void sendFile(ActionEvent event) {
        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {
            // TODO: 2020/12/13 发送文件
            System.out.println(file.getAbsolutePath());
        }
    }
}

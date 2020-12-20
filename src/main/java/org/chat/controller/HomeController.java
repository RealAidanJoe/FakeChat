package org.chat.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.chat.App;
import org.chat.bean.Friend;
import org.chat.bean.User;
import org.chat.component.Bubble;
import org.chat.component.FriendListCell;
import org.chat.utils.ImportExportProcessing;
import org.chat.utils.MessageProcessing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomeController {
    @FXML
    private AnchorPane root;
    @FXML
    private VBox chartList;
    @FXML
    private Button sendMsgBtn, sendFileBtn;
    @FXML
    private Label friendNameLab;
    @FXML
    private TextArea msgText;
    @FXML
    private ListView<Friend> friendListView;
    ObservableList<Friend> friendList;
    private int selectedIndex;
    private Friend selectedFriend;
    User user;

    AnchorPane addFriendPane, settingPane;
    @FXML
    private TextField friendNameTex, ipTex,
            friendMsgPortTex, friendFilePortTex;

    @FXML
    private TextField pathTex, userMsgPortTex, userFilePortTex;

    FileChooser fileChooser;
    DirectoryChooser folderChooser;

    public HomeController() {
//        用户初始化
//        user = new User(2077, 2088, new File("c:/"));
        user = ImportExportProcessing.importUser();

//        发送文件选择框
        fileChooser = new FileChooser();
        fileChooser.setTitle("发送文件");
        fileChooser.setInitialDirectory(new File("C:/")); // 初始目录

//        接收文件保存路径选择框
        folderChooser = new DirectoryChooser();
        folderChooser.setTitle("保存文件夹");
        folderChooser.setInitialDirectory(new File("C:/")); // 初始目录

//        好友列表
        friendList = FXCollections.observableArrayList();
//        friendList.add(new Friend("默认本机", 2077, 2088, MessageProcessing.getAddress()));
        friendList.addAll(ImportExportProcessing.importFriends());

//        friendList = FXCollections.observableList(ImportExportProcessing.importFriends());
//        friendList = FXCollections.observableArrayList();
//        friendList.add(new Friend("snake", 2077, 2088, "172.21.14.200"));
//        friendList.add(new Friend("ant", 2077, 2088, MessageProcessing.getAddress()));

//        监听信息到来
        user.listenMessage(msg -> {
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

        user.listenFile(file -> {

        });


        addFriendPane = new AnchorPane();
        FXMLLoader fxmlLoader = App.getFXMLLoader("addFriend");
        fxmlLoader.setRoot(addFriendPane);
//        共用controller导致运行initialize 2次
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addFriendPane.setVisible(false);

        settingPane = new AnchorPane();
        fxmlLoader = App.getFXMLLoader("setting");
        fxmlLoader.setRoot(settingPane);
//        共用controller导致运行initialize 2次
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingPane.setVisible(false);

        Platform.runLater(() -> {
            root.getChildren().addAll(addFriendPane, settingPane);

//            监听设置用户端口取消焦点时
            userMsgPortTex.focusedProperty().addListener((ov, oldVal, newVal) -> {
                if (!newVal) {
//                    System.out.println("!focus");
//                    System.out.println(userMsgPortTex.getText());
                    user.chatPortNumber = Integer.parseInt(userMsgPortTex.getText());
                }
            });
            userFilePortTex.focusedProperty().addListener((ov, oldVal, newVal) -> {
                if (!newVal) {
//                    System.out.println("!focus");
//                    System.out.println(userFilePortTex.getText());
                    user.filePortNumber = Integer.parseInt(userFilePortTex.getText());
                }
            });

            friendListView.setCellFactory((ListView<Friend> l) -> new FriendListCell());
            friendListView.setItems(friendList);

//        监听切换好友
            friendListView.getSelectionModel().selectedIndexProperty().addListener((ov, oldVal, newVal) -> {
//            System.out.println(newVal.intValue());
                selectedIndex = newVal.intValue();
//            避免刷新时出错
                if (selectedIndex == -1) return;

                selectedFriend = friendList.get(selectedIndex);

//            更改显示好友名
                friendNameLab.setText(selectedFriend.friendName);
//            可以发送消息
                msgText.setDisable(false);
                sendMsgBtn.setDisable(false);
                sendFileBtn.setDisable(false);

//            显示聊天记录
                chartList.getChildren().clear();
                for (String msg : selectedFriend.exportChattingRecords()) {
                    chartList.getChildren().add(new Bubble(msg));
                }
            });

//            System.out.println(friendListView.getSelectionModel().getSelectedIndex());
//            if (friendListView.getSelectionModel().getSelectedIndex() == -1) {
//            friendListView.getSelectionModel().select(0);
//            }

            ipTex.setText(MessageProcessing.getAddress());
            userMsgPortTex.setText(user.chatPortNumber + "");
            userFilePortTex.setText(user.filePortNumber + "");
            pathTex.setText(user.fileSaveFolder.getAbsolutePath());

        });
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
//        Platform.exit();
        ImportExportProcessing.exportUser(user);
        ImportExportProcessing.exportFriends(friendList);
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
    private void closewin(ActionEvent event) {
//        HBox->AnchorPane
        Parent parent = ((Parent) event.getSource()).getParent().getParent();
        parent.setVisible(false);
    }

    @FXML
    public void presswin(MouseEvent mouseEvent) {
        Parent parent = ((Parent) mouseEvent.getSource()).getParent();
        xOffset = mouseEvent.getSceneX() - parent.getLayoutX();
        yOffset = mouseEvent.getSceneY() - parent.getLayoutY();
    }

    @FXML
    private void dragwin(MouseEvent mouseEvent) {
//        System.out.println(mouseEvent.getSceneX() + "\t" + mouseEvent.getScreenX());
//        xOffset + mouseEvent.getSceneX()
        Parent parent = ((Parent) mouseEvent.getSource()).getParent();
        parent.setLayoutX(mouseEvent.getSceneX() - xOffset);
        if (mouseEvent.getSceneY() - yOffset < 0) {
            parent.setLayoutY(0);
        } else {
            parent.setLayoutY(mouseEvent.getSceneY() - yOffset);
        }
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

    @FXML
    private void openAddFriend() {
//        friendList.add(new Friend("haha", 2077, 2088, MessageProcessing.getAddress()));
        addFriendPane.setVisible(!addFriendPane.isVisible());
    }

    @FXML
    private void openSetting() {
        settingPane.setVisible(!settingPane.isVisible());
    }

    @FXML
    private void inputMsg(KeyEvent keyEvent) {
//        System.out.println("get:" + keyEvent.getCode());
        if (keyEvent.getCode() == KeyCode.ENTER && !Objects.equals(msgText.getText(), "\n")) {
//            System.out.println("enter");
            sendMsg();
        }
    }

    @FXML
    private void sendFile(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
//            System.out.println(file.getAbsolutePath());
            selectedFriend.sendFile(file);
            chartList.getChildren().add(new Bubble("发送文件:" + file.getName()));
        }
    }

    @FXML
    private void addFriend() {
        String name = friendNameTex.getText();
        String msgPort = friendMsgPortTex.getText();
        String filePort = friendFilePortTex.getText();
        String ip = ipTex.getText();
//
        if (name.equals("") || ip.equals("") || msgPort.equals("") || filePort.equals("")) {
            System.out.println("empty");
            return;
        }

        friendList.add(new Friend(name, Integer.parseInt(msgPort), Integer.parseInt(filePort), ip));
        friendNameTex.setText("");
        addFriendPane.setVisible(false);
    }

    @FXML
    private void chooseSaveFolder() {
        File file = folderChooser.showDialog(new Stage());
        if (file != null) {
            pathTex.setText(file.getAbsolutePath());
            user.fileSaveFolder = file;
        }
    }

    @FXML
    private void openFolder() {
        try {
            Runtime.getRuntime().exec("explorer /select," + pathTex.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

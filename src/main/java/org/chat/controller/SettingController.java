package org.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SettingController {
    @FXML
    private TextField pathTex;



    private Stage stage;
    DirectoryChooser chooser;

    public SettingController() {
        chooser = new DirectoryChooser();
        chooser.setTitle("保存文件夹");
        chooser.setInitialDirectory(new File("C:/")); // 初始目录
    }

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
    private void chooseFolder(ActionEvent event) {

    }
}

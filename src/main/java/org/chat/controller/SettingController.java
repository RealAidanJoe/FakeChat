package org.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SettingController {

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

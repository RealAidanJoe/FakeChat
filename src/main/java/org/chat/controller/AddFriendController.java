package org.chat.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFriendController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    private void submit(){

    }
}

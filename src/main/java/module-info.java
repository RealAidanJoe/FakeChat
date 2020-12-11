module org.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires fastjson;

    opens org.chat to javafx.fxml;
    exports org.chat;
    exports org.chat.controller;
}
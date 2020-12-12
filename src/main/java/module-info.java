module org.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires fastjson;
    requires java.sql;

    opens org.chat to javafx.fxml;
    opens org.chat.component to javafx.fxml;
    opens org.chat.controller to javafx.fxml;
    exports org.chat;
    exports org.chat.controller;
    exports org.chat.bean;
}
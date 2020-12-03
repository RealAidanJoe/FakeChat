module org.chat {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.chat to javafx.fxml;
    exports org.chat;
}
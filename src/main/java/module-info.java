module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media; //Import Media library for background music

    opens brickGame to javafx.fxml;
    exports brickGame;
}
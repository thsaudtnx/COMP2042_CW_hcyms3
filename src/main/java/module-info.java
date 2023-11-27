module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media; //Import Media library for background music

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.constants;
    opens brickGame.constants to javafx.fxml;
    exports brickGame.components;
    opens brickGame.components to javafx.fxml;
    exports brickGame.effects;
    opens brickGame.effects to javafx.fxml;
}
module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media; //Import Media library for background music

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.GameStyle;
    opens brickGame.GameStyle to javafx.fxml;
    exports brickGame.GameComponents;
    opens brickGame.GameComponents to javafx.fxml;
    exports brickGame.GameEffects;
    opens brickGame.GameEffects to javafx.fxml;
    exports brickGame.GameUtils;
    opens brickGame.GameUtils to javafx.fxml;

}
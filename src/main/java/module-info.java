module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media; //Import Media library for background music

    opens brickGame to javafx.fxml;
    exports brickGame;
    exports brickGame.gameStyle;
    opens brickGame.gameStyle to javafx.fxml;
    exports brickGame.gameComponents;
    opens brickGame.gameComponents to javafx.fxml;
    exports brickGame.gameEffects;
    opens brickGame.gameEffects to javafx.fxml;
    exports brickGame.gameUtils;
    opens brickGame.gameUtils to javafx.fxml;
    exports brickGame.gameItems;
    opens brickGame.gameItems to javafx.fxml;

}
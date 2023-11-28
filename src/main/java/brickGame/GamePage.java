package brickGame;

import javafx.stage.Stage;

/**
 * The {@code Page} interface defines methods for drawing various pages on a JavaFX {@code Stage}.
 */
public interface GamePage {
    /**
     * Draws the home page on the specified {@code Stage}.
     *
     * @param primaryStage The JavaFX {@code Stage} to draw the home page on.
     */
    void drawHomePage(Stage primaryStage);
    /**
     * Draws the game page on the specified {@code Stage}.
     *
     * @param primaryStage The JavaFX {@code Stage} to draw the game page on.
     */
    void drawGamePage(Stage primaryStage);
    /**
     * Draws the end menu page on the specified {@code Stage}.
     *
     * @param primaryStage The JavaFX {@code Stage} to draw the end menu page on.
     */
    void drawMenuPageEnd(Stage primaryStage);
    /**
     * Draws the next level menu page on the specified {@code Stage}.
     *
     * @param primaryStage The JavaFX {@code Stage} to draw the next level menu page on.
     */
    void drawMenuPageNextLevel(Stage primaryStage);
}

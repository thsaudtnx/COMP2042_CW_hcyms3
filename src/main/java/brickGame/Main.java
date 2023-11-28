package brickGame;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class that serves as the entry point for the Brick Game application.
 * It extends JavaFX's Application class and launches the game by initializing
 * the Game class.
 */
public class Main extends Application {
    /**
     * The main method of the application, which launches the JavaFX application.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The overridden start method from the Application class.
     * It initializes the Game class and starts the game by invoking its start method.
     *
     * @param primaryStage The primary stage for the application (the game window).
     * @throws Exception If an exception occurs during the start process.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Game game = new Game();
        game.start(primaryStage);
    }
}

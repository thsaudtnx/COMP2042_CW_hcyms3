package brickGame;

/**
 * The GameController interface defines methods for controlling different pages or sections of a game.
 */
public interface GameController {
    /**
     * Controls the home page of the game.
     */
    void homePageController();

    /**
     * Controls the game page of the game.
     */
    void gamePageController();

    /**
     * Controls the menu page of the game.
     */
    void menuPageController();
}

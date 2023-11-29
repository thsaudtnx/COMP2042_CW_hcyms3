package brickGame;

import brickGame.GameComponents.*;
import brickGame.GameUtils.LoadSave;
import brickGame.GameUtils.Ranking;
import brickGame.GameUtils.Show;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * The Game class represents a JavaFX game implementation.
 * It includes methods for handling different game pages, game logic, game controller
 * and UI updates using a GameEngine.
 */
public class Game implements GameEngine.OnAction, GameController{
    private GameEngine engine;
    private GameVariables gameVariables = new GameVariables();
    private GameView gameView = new GameView();
    Stage primaryStage;
    /**
     * Handles the controller logic for the home page, including event
     * handlers for the new game and load game buttons.
     */
    @Override
    public void homePageController(){
        gameView.loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (LoadSave.isExistSavedFile()){
                    try {
                        gameView.backgroundMusic.stop();
                        gameVariables.page = 1;
                        gameVariables.isLoad = true;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("There is no saved game");
                }
            }
        });
        gameView.newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    gameView.backgroundMusic.stop();
                    gameVariables.page = 1;
                    gameVariables.level=1;
                    start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * Handles the controller logic for the game page, including event
     * handlers for key presses and pop-up interactions.
     */
    @Override
    public void gamePageController(){
        gameView.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case LEFT:
                        gameVariables.breakClass.move(KeyCode.LEFT);
                        break;
                    case RIGHT:
                        gameVariables.breakClass.move(KeyCode.RIGHT);
                        break;
                    case S:
                        LoadSave.saveGame(
                                gameVariables.level,
                                gameVariables.score,
                                gameVariables.time,
                                gameVariables.heart,
                                gameVariables.blockClass,
                                gameVariables.heartClass,
                                gameVariables.bonusClass,
                                gameVariables.ballClass,
                                gameVariables.breakClass
                        );
                        break;
                    case SPACE:
                        engine.stop();

                        //Set the pop up stage
                        Stage popupStage = new Stage();
                        popupStage.initModality(Modality.APPLICATION_MODAL);
                        popupStage.setResizable(false);
                        popupStage.initStyle(StageStyle.UNDECORATED);
                        popupStage.setX(630);  // Set X coordinate
                        popupStage.setY(300);  // Set Y coordinate

                        //Set a buttons
                        Button continueButton = new Button();
                        Button resetButton = new Button();
                        Button homeButton = new Button();
                        Image continueImage = new Image("continueButton.png");
                        Image resetImage = new Image("resetButton.png");
                        Image homeImage = new Image("homeButton.png");
                        ImageView continueImageView = new ImageView(continueImage);
                        ImageView resetImageView = new ImageView(resetImage);
                        ImageView homeImageView = new ImageView(homeImage);
                        continueImageView.setFitWidth(continueImage.getWidth() * 0.3);
                        continueImageView.setFitHeight(continueImage.getHeight() * 0.3);
                        resetImageView.setFitWidth(resetImage.getWidth() * 0.3);
                        resetImageView.setFitHeight(resetImage.getHeight() * 0.3);
                        homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
                        homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
                        continueButton.setGraphic(continueImageView);
                        resetButton.setGraphic(resetImageView);
                        homeButton.setGraphic(homeImageView);
                        continueButton.setTranslateX(60);
                        continueButton.setTranslateY(30);
                        resetButton.setTranslateX(60);
                        resetButton.setTranslateY(30);
                        homeButton.setTranslateX(60);
                        homeButton.setTranslateY(30);
                        continueButton.setVisible(true);
                        resetButton.setVisible(true);
                        homeButton.setVisible(true);

                        //Set the event on each buttons
                        continueButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                System.out.println("continue");
                                engine.go();
                                popupStage.close();
                            }
                        });
                        resetButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                System.out.println("reset");
                                popupStage.close();
                                try {
                                    gameVariables.page = 1;
                                    start(primaryStage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        homeButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                System.out.println("home");
                                popupStage.close();
                                gameView.backgroundMusic.stop();

                                try {
                                    gameVariables.page = 0;
                                    start(primaryStage);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        Platform.runLater(() -> {
                            // Design the content of the popup (a simple example with a label)
                            VBox popupLayout = new VBox();
                            popupLayout.setSpacing(10);
                            popupLayout.getChildren().addAll(continueButton, resetButton, homeButton);
                            popupLayout.setStyle(
                                    "-fx-background-color: black; " +
                                            "-fx-border-color: white; " +
                                            "-fx-border-width: 2px; "
                            );

                            //Set a popup scene
                            Scene popupScene = new Scene(popupLayout, 300, 200);
                            popupStage.setScene(popupScene);

                            // Show the popup
                            popupStage.show();
                        });
                        break;
                }
            }
        });

        //Set Labels
        gameView.scoreLabel.setText("Score: " + gameVariables.score);
        gameView.levelLabel.setText("Level " + gameVariables.level);
        gameView.heartLabel.setText("Heart : " + gameVariables.heart);
        gameView.timeLabel.setText("Time : " + gameVariables.time);

        Platform.runLater(() -> {
            gameView.root.getChildren().addAll(
                    gameVariables.breakClass.rect
            );
            for (Ball.BallEntry ball : gameVariables.ballClass.balls){
                gameView.root.getChildren().add(ball.circle);
            }
            for (Block.BlockEntry block : gameVariables.blockClass.blocks) {
                gameView.root.getChildren().add(block.rect);
            }
            for (Bonus.BonusEntry bonus : gameVariables.bonusClass.bonuses){
                gameView.root.getChildren().add(bonus.rect);
            }
            for (Heart.HeartEntry heart : gameVariables.heartClass.hearts){
                gameView.root.getChildren().add(heart.rect);
            }

            //Set the main scene
            gameView.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case LEFT:
                            gameVariables.breakClass.move(KeyCode.LEFT);
                            break;
                        case RIGHT:
                            gameVariables.breakClass.move(KeyCode.RIGHT);
                            break;
                        case S:
                            LoadSave.saveGame(
                                    gameVariables.level,
                                    gameVariables.score,
                                    gameVariables.time,
                                    gameVariables.heart,
                                    gameVariables.blockClass,
                                    gameVariables.heartClass,
                                    gameVariables.bonusClass,
                                    gameVariables.ballClass,
                                    gameVariables.breakClass
                            );
                            break;
                        case SPACE:
                            engine.stop();

                            //Set the pop up stage
                            Stage popupStage = new Stage();
                            popupStage.initModality(Modality.APPLICATION_MODAL);
                            popupStage.setResizable(false);
                            popupStage.initStyle(StageStyle.UNDECORATED);
                            popupStage.setX(630);  // Set X coordinate
                            popupStage.setY(300);  // Set Y coordinate

                            //Set a buttons
                            Button continueButton = new Button();
                            Button resetButton = new Button();
                            Button homeButton = new Button();
                            Image continueImage = new Image("continueButton.png");
                            Image resetImage = new Image("resetButton.png");
                            Image homeImage = new Image("homeButton.png");
                            ImageView continueImageView = new ImageView(continueImage);
                            ImageView resetImageView = new ImageView(resetImage);
                            ImageView homeImageView = new ImageView(homeImage);
                            continueImageView.setFitWidth(continueImage.getWidth() * 0.3);
                            continueImageView.setFitHeight(continueImage.getHeight() * 0.3);
                            resetImageView.setFitWidth(resetImage.getWidth() * 0.3);
                            resetImageView.setFitHeight(resetImage.getHeight() * 0.3);
                            homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
                            homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
                            continueButton.setGraphic(continueImageView);
                            resetButton.setGraphic(resetImageView);
                            homeButton.setGraphic(homeImageView);
                            continueButton.setTranslateX(60);
                            continueButton.setTranslateY(30);
                            resetButton.setTranslateX(60);
                            resetButton.setTranslateY(30);
                            homeButton.setTranslateX(60);
                            homeButton.setTranslateY(30);
                            continueButton.setVisible(true);
                            resetButton.setVisible(true);
                            homeButton.setVisible(true);

                            //Set the event on each buttons
                            continueButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    System.out.println("continue");
                                    engine.go();
                                    popupStage.close();
                                }
                            });
                            resetButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    System.out.println("reset");
                                    popupStage.close();
                                    try {
                                        gameVariables.page = 1;
                                        start(primaryStage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            homeButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    System.out.println("home");
                                    popupStage.close();
                                    gameView.backgroundMusic.stop();

                                    try {
                                        gameVariables.page = 0;
                                        start(primaryStage);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                            Platform.runLater(() -> {
                                // Design the content of the popup (a simple example with a label)
                                VBox popupLayout = new VBox();
                                popupLayout.setSpacing(10);
                                popupLayout.getChildren().addAll(continueButton, resetButton, homeButton);
                                popupLayout.setStyle(
                                        "-fx-background-color: black; " +
                                                "-fx-border-color: white; " +
                                                "-fx-border-width: 2px; "
                                );

                                //Set a popup scene
                                Scene popupScene = new Scene(popupLayout, 300, 200);
                                popupStage.setScene(popupScene);

                                // Show the popup
                                popupStage.show();
                            });
                            break;
                    }
                }
            });

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(gameView.scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        });
    }
    /**
     * Handles the controller logic for the menu page, including event
     * handlers for different scenarios such as game over or advancing
     * to the next level.
     */
    @Override
    public void menuPageController(){
        if (gameVariables.level==11 || gameVariables.heart==0){
            //Set Label
            gameView.scoreLabel.setText("Score : " + gameVariables.score);
            gameView.timeLabel.setText("Time : " + gameVariables.time);
            //Set Image
            gameView.titleImage = gameVariables.heart==0 ? new Image("gameOver.png") : new Image("completeTitle.png");
            //Control Button
            gameView.submitButton.setOnAction(event -> {
                String username = gameView.usernameField.getText();
                System.out.println("Entered Username: " + username);
                // You can handle the entered username as needed

                Ranking ranking = new Ranking();
                ranking.addEntry(username, gameVariables.score, gameVariables.time);
            });
            gameView.homeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        gameVariables.page = 0;
                        gameVariables.level = 1;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //Next level
        else {
            gameView.nextGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        gameVariables.page = 1;
                        //level++;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            gameView.homeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        gameVariables.page = 0;
                        gameVariables.level = 1;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    /**
     * Sets up and displays the home page of the game.
     * This page includes buttons for loading a saved game or starting a new game.
     */
    public void homePage(){
        //View
        gameView.drawHomePage(primaryStage);

        //Controller
        homePageController();

        //Set the game engine
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(120);
    }
    /**
     * Sets up and displays the game page, handling both new games and loaded games.
     * If a game is loaded, it loads the saved data. Otherwise, it initializes the game state.
     * Listens for key events, such as movement and save actions, and displays relevant UI components.
     */
    public void gamePage(){
        if (gameVariables.isLoad){
            gameVariables.isLoad = false;
            LoadSave.loadGame();
            gameVariables.level = LoadSave.Data.level;
            gameVariables.score = LoadSave.Data.score;
            gameVariables.time = LoadSave.Data.time;
            gameVariables.heart = LoadSave.Data.heart;
            gameVariables.blockClass = LoadSave.Data.blockClass;
            gameVariables.heartClass = LoadSave.Data.heartClass;
            gameVariables.bonusClass = LoadSave.Data.bonusClass;
            gameVariables.ballClass = LoadSave.Data.ballClass;
            gameVariables.breakClass = LoadSave.Data.breakClass;
        }
        else {
            if (gameVariables.level==1){
                gameVariables.heart = 3;
                gameVariables.score = 0;
                gameVariables.time = 0;
            }
            else {
                //initialize except score and heart and time
                gameVariables.time = 0;
            }
            //Initialize
            onInit();
        }

        //View
        gameView.drawGamePage(primaryStage);

        //Controller
        gamePageController();

        //Set the game engine
        engine = new GameEngine();
        engine.setOnAction(this);
        engine.setFps(120);

        //Count down 3 seconds and engine.start()
        new Show().showCountDown(gameView.root, engine);
    }
    /**
     * Displays the menu page, handling both game completion and level progression.
     * Clears the last level or displays a game over message based on the game state.
     * Provides options to submit scores, return to the home page, or proceed to the next level.
     */
    public void menuPage(){
        //Clear the last level or GameOver
        if (gameVariables.level==11 || gameVariables.heart==0){
            gameView.drawMenuPageEnd(primaryStage);
            menuPageController();
        }
        //Next level
        else {
            gameView.drawMenuPageNextLevel(primaryStage);
            menuPageController();
        }
    }
    /**
     * Initializes and starts the application based on the current game page.
     *
     * @param primaryStage The primary stage for the application window.
     */
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;

        if (gameVariables.page==0) homePage();
        else if (gameVariables.page==1) gamePage();
        else if (gameVariables.page==2) menuPage();
    }
    /**
     * Initializes the game variables on start.
     */
    @Override
    public void onInit(){
        gameVariables.onInit();
    }
    /**
     * Updates the game state during each frame.
     * Checks for collisions, updates positions, and handles game events.
     */
    @Override
    public void onUpdate() {
        //Clear the level
        if (gameVariables.blockClass.checkClearLevel()) {
            //TODO win level todo...
            System.out.println("Next Level");
            engine.stop();
            try {
                gameVariables.level++;
                gameVariables.page = 2;
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Check ball
        Ball.BallEntry newBall = null;
        for (Ball.BallEntry ball : gameVariables.ballClass.balls){
            //Update the movement of the ball and break
            ball.setPhysicsToBall(gameVariables.breakClass, gameVariables.level, gameVariables.blockClass.blocks);

            //Check if the ball hits the block
            if (ball.collideToBlock){
                Block.BlockEntry block = ball.collideBlock;
                gameVariables.score += block.point;
                new Show().showScore(block.x, block.y, block.point, gameView.root);
                if (block.type == Block.BLOCK_NORMAL){}
                if (block.type == Block.BLOCK_CHOCO) {
                    Bonus.BonusEntry newBonus = new Bonus.BonusEntry(block.row, block.column, gameVariables.time);
                    gameVariables.bonusClass.addBonus(newBonus);
                    Platform.runLater(() -> {
                        gameView.root.getChildren().add(newBonus.rect);
                    });
                }
                if (block.type == Block.BLOCK_STAR) {
                    gameVariables.ballClass.setGoldTime(gameVariables.time);
                }
                if (block.type == Block.BLOCK_HEART) {
                    Heart.HeartEntry newHeart = new Heart.HeartEntry(block.row, block.column, gameVariables.time);
                    gameVariables.heartClass.addHeart(newHeart);
                    Platform.runLater(() -> {
                        gameView.root.getChildren().add(newHeart.rect);
                    });
                }
                if (block.type == Block.BLOCK_BALL){
                    newBall = new Ball.BallEntry(block.row, block.column);
                }
            }

            //Check if any of the ball hits the bottom with golden ball
            if (ball.isMinusHeart()) {
                //TODO gameover
                gameVariables.heart--;
                new Show().showScore(gameView.sceneWidth / 2, gameView.sceneHeight / 2, -1, gameView.root);

                if (gameVariables.heart == 0) {
                    try {
                        gameVariables.page = 2;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    engine.stop();
                }
            }
        }
        if (newBall != null){
            gameVariables.ballClass.addBall(newBall);
            Ball.BallEntry finalNewBall = newBall;
            Platform.runLater(() -> {
                gameView.root.getChildren().add(finalNewBall.circle);
            });
        }

        // Check goldtime is over
        gameVariables.ballClass.checkGoldTimeOver(gameVariables.time);

        //Check break hits the bonus falling and add the score
        for (Bonus.BonusEntry choco : gameVariables.bonusClass.bonuses) {
            if (choco.taken){
                continue;
            }
            if (choco.y > gameView.sceneHeight) {
                choco.rect.setVisible(false);
                continue;
            }
            if (gameVariables.breakClass.yBreak <= choco.y + Bonus.height && choco.x >= gameVariables.breakClass.xBreak && choco.x <= gameVariables.breakClass.xBreak + gameVariables.breakClass.breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.rect.setVisible(false);
                new Show().showScore(choco.x, choco.y, 3, gameView.root);
                continue;
            }
            choco.y += ((gameVariables.time - choco.timeCreated) / 1000.000) + 1.000;
        }

        //Check break hits the heartItem falling and add the heart
        for (Heart.HeartEntry heartItem : gameVariables.heartClass.hearts) {
            if (heartItem.taken){
                continue;
            }
            if (heartItem.y > gameView.sceneHeight) {
                heartItem.rect.setVisible(false);
                continue;
            }
            if (gameVariables.breakClass.yBreak <= heartItem.y + Heart.height && heartItem.x >= gameVariables.breakClass.xBreak && heartItem.x <= gameVariables.breakClass.xBreak + gameVariables.breakClass.breakWidth) {
                heartItem.taken = true;
                heartItem.rect.setVisible(false);
                new Show().showMessage("+ Heart", gameView.root);
                gameVariables.heart++;
                continue;
            }
            heartItem.y += ((gameVariables.time - heartItem.timeCreated) / 1000.000) + 1.000;
        }

        //Update UI components
        Platform.runLater(() -> {
            //Update the labels
            gameView.scoreLabel.setText("Score: " + gameVariables.score);
            gameView.heartLabel.setText("Heart : " + gameVariables.heart);
            gameView.levelLabel.setText("Level : " + gameVariables.level);
            gameView.timeLabel.setText("Time : " + gameVariables.time);

            //Update the position of the break
            gameVariables.breakClass.rect.setX(gameVariables.breakClass.xBreak);
            gameVariables.breakClass.rect.setY(gameVariables.breakClass.yBreak);

            //Update the position of balls
            for (Ball.BallEntry ball : gameVariables.ballClass.balls){
                ball.circle.setCenterX(ball.xBall);
                ball.circle.setCenterY(ball.yBall);
            }

            //Update the position of bonuses
            for (Bonus.BonusEntry bonus : gameVariables.bonusClass.bonuses) {
                bonus.rect.setY(bonus.y);
            }

            //Update the position of heartItems
            for(Heart.HeartEntry heart : gameVariables.heartClass.hearts){
                heart.rect.setY(heart.y);
            }
        });
    }
    /**
     * Updates the game time.
     *
     * @param time The current game time.
     */
    @Override
    public void onTime(long time) {
        gameVariables.time = time;
    }
}

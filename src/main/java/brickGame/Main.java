package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application implements GameEngine.OnAction {
    final private int sceneWidth = 500;
    final private int sceneHeight = 700;
    private GameEngine engine;
    private Ranking ranking;

    private Ball ballClass;
    private Break breakClass;
    private Block blockClass;
    private Bonus bonusClass;
    private Heart heartClass;
    private boolean isLoad;

    private int page = 0; //0 home, 1 inGame, 2 after game
    private int level = 1;
    private int heart = 3;
    private int  score = 0;
    private long time = 0;

    public Pane root;
    private Scene scene;
    private Label titleLabel;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Label timeLabel;

    Stage primaryStage;
    Button loadGameButton;
    Button newGameButton;
    Button nextGameButton;
    Button quitButton;
    Button instructionButton;
    Button homeButton;
    Button rankingButton;
    BackgroundMusic backgroundMusic;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Home Page
        if (page==0){
            this.primaryStage = primaryStage;

            //Set Label
            titleLabel = new Label();
            Image titleImage = new Image("brickGameTitle.png");
            ImageView titleImageView = new ImageView(titleImage);
            titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
            titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
            titleLabel.setGraphic(titleImageView);
            titleLabel.setTranslateX(120);
            titleLabel.setTranslateY(120);
            titleLabel.setVisible(true);

            //Set Buttons
            newGameButton = new Button();
            loadGameButton = new Button();
            rankingButton = new Button();
            quitButton = new Button();
            instructionButton = new Button();
            Image newGameImage = new Image("newGameButton.png");
            Image loadGameImage = new Image("loadGameButton.png");
            Image rankingImage = new Image("rankingButton.png");
            Image quitImage = new Image("quitButton.png");
            Image instructionImage = new Image("instructionButton.png");
            ImageView newGameImageView = new ImageView(newGameImage);
            ImageView loadGameImageView = new ImageView(loadGameImage);
            ImageView rankingImageView = new ImageView(rankingImage);
            ImageView quitImageView = new ImageView(quitImage);
            ImageView instructionImageView = new ImageView(instructionImage);
            newGameImageView.setFitWidth(newGameImage.getWidth() * 0.3);
            newGameImageView.setFitHeight(newGameImage.getHeight() * 0.3);
            loadGameImageView.setFitWidth(loadGameImage.getWidth() * 0.3);
            loadGameImageView.setFitHeight(loadGameImage.getHeight() * 0.3);
            rankingImageView.setFitWidth(rankingImage.getWidth() * 0.3);
            rankingImageView.setFitHeight(rankingImage.getHeight() * 0.3);
            quitImageView.setFitWidth(quitImage.getWidth() * 0.3);
            quitImageView.setFitHeight(quitImage.getHeight() * 0.3);
            instructionImageView.setFitWidth(instructionImage.getWidth() * 0.3);
            instructionImageView.setFitHeight(instructionImage.getHeight() * 0.3);
            newGameButton.setGraphic(newGameImageView);
            loadGameButton.setGraphic(loadGameImageView);
            rankingButton.setGraphic(rankingImageView);
            instructionButton.setGraphic(instructionImageView);
            quitButton.setGraphic(quitImageView);
            newGameButton.setTranslateX(170);
            newGameButton.setTranslateY(300);
            loadGameButton.setTranslateX(170);
            loadGameButton.setTranslateY(360);
            rankingButton.setTranslateX(170);
            rankingButton.setTranslateY(420);
            instructionButton.setTranslateX(170);
            instructionButton.setTranslateY(480);
            quitButton.setTranslateX(185);
            quitButton.setTranslateY(540);
            loadGameButton.setVisible(true);
            newGameButton.setVisible(true);
            rankingButton.setVisible(true);
            quitButton.setVisible(true);
            instructionButton.setVisible(true);
            rankingButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ranking = new Ranking();
                    System.out.println(ranking.getFormattedRanking());
                    //Set popup stage
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setResizable(false);
                    popupStage.setTitle("Ranking");
                    popupStage.setX(600);  // Set X coordinate
                    popupStage.setY(200);  // Set Y coordinate

                    Platform.runLater(() -> {
                        // Design the content of the popup (a simple example with a label)
                        VBox popupLayout = new VBox();
                        popupLayout.setSpacing(10);
                        Label headLabel = new Label("Ranking\tUsername\tScore\tRecord\tDateTime");
                        Label rankingLabel = new Label(ranking.getFormattedRanking());
                        popupLayout.getChildren().addAll(headLabel, rankingLabel);

                        //Set a popup scene
                        Scene popupScene = new Scene(popupLayout, 300, 200);
                        popupStage.setScene(popupScene);

                        // Show the popup
                        popupStage.show();
                    });
                }
            });
            loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (LoadSave.isExistSavedFile()){
                        try {
                            backgroundMusic.stop();
                            page = 1;
                            isLoad = true;
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        //There is no saved file
                    }
                }
            });
            newGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        backgroundMusic.stop();
                        page = 1;
                        level=1;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            instructionButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("instruction");
                    //Set the pop up stage
                    Stage popupStage = new Stage();
                    popupStage.initModality(Modality.APPLICATION_MODAL);
                    popupStage.setResizable(false);
                    popupStage.setTitle("Instruction");
                    popupStage.setX(630);  // Set X coordinate
                    popupStage.setY(300);  // Set Y coordinate

                    Platform.runLater(() -> {
                        // Design the content of the popup (a simple example with a label)
                        Pane popupLayout = new Pane();
                        BackgroundImage background = new BackgroundImage(
                                new Image("instructionPage.png"),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
                        );
                        popupLayout.setBackground(new Background(background));

                        //Set a popup scene
                        Scene popupScene = new Scene(popupLayout, 300, 200);
                        popupStage.setScene(popupScene);

                        // Show the popup
                        popupStage.show();
                    });

                }
            });
            quitButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    primaryStage.close();
                }
            });

            //Set the root pane
            root = new Pane();
            BackgroundImage background = new BackgroundImage(
                    new Image("spaceBg.jpg"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            root.setBackground(new Background(background));

            //Set background music
            backgroundMusic = new BackgroundMusic(page);
            backgroundMusic.start();

            //Draw UI Components
            Platform.runLater(() -> {
                //Add components into root
                root.getChildren().addAll(
                        titleLabel,
                        newGameButton,
                        loadGameButton,
                        rankingButton,
                        instructionButton,
                        quitButton
                );
                //Set the main scene
                scene = new Scene(root, sceneWidth, sceneHeight);
                //scene.getStylesheets().add("style.css");

                //Set the primary stage
                primaryStage.setTitle("Brick Game");
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });

            //Set the game engine
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
        }
        //In Game
        else if (page==1){
            if (isLoad){
                isLoad = false;
                LoadSave.loadGame();
                level = LoadSave.data.level;
                score = LoadSave.data.score;
                time = LoadSave.data.time;
                heart = LoadSave.data.heart;
                blockClass = LoadSave.data.blockClass;
                heartClass = LoadSave.data.heartClass;
                bonusClass = LoadSave.data.bonusClass;
                ballClass = LoadSave.data.ballClass;
                breakClass = LoadSave.data.breakClass;
            }
            else {
                if (level==1){
                    heart = 3;
                    score = 0;
                    time = 0;
                }
                else {
                    //initialize except score and heart and time
                    time = 0;
                }
                //Initialize
                onInit();
            }

            //Set Labels
            scoreLabel = new Label("Score: " + score);
            levelLabel = new Label("Level " + level);
            heartLabel = new Label("Heart : " + heart);
            timeLabel = new Label("Time : " + time);
            levelLabel.setTranslateX(5);
            scoreLabel.setTranslateX(sceneWidth / 4);
            timeLabel.setTranslateX(sceneWidth / 2);
            heartLabel.setTranslateX(sceneWidth*3 / 4);

            //Set the root pane
            root = new Pane();
            BackgroundImage background = new BackgroundImage(
                    new Image("bg.jpg"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            root.setBackground(new Background(background));

            //Set background music
            backgroundMusic = new BackgroundMusic(page);
            backgroundMusic.start();

            Platform.runLater(() -> {
                root.getChildren().addAll(
                        scoreLabel,
                        heartLabel,
                        levelLabel,
                        timeLabel,
                        breakClass.rect
                );
                for (Ball.BallEntry ball : ballClass.balls){
                    root.getChildren().add(ball.circle);
                }
                for (Block.BlockEntry block : blockClass.blocks) {
                    root.getChildren().add(block.rect);
                }
                for (Bonus.BonusEntry bonus : bonusClass.bonuses){
                    root.getChildren().add(bonus.rect);
                }
                for (Heart.HeartEntry heart : heartClass.hearts){
                    root.getChildren().add(heart.rect);
                }

                //Set the main scene
                scene = new Scene(root, sceneWidth, sceneHeight);
                scene.getStylesheets().add("style.css");
                scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        switch (keyEvent.getCode()) {
                            case LEFT:
                                breakClass.move(KeyCode.LEFT);
                                break;
                            case RIGHT:
                                breakClass.move(KeyCode.RIGHT);
                                break;
                            case S:
                                LoadSave.saveGame(
                                        level,
                                        score,
                                        time,
                                        heart,
                                        blockClass,
                                        heartClass,
                                        bonusClass,
                                        ballClass,
                                        breakClass
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
                                            page = 1;
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
                                        backgroundMusic.stop();

                                        try {
                                            page = 0;
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
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.show();
            });

            //Set the game engine
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);

            //Count down 3 seconds and engine.start()
            new Score().showCountDown(this, engine);
        }
        //After the game
        else if (page==2){
            //Clear the last level or GameOver
            if (level==11 || heart==0){
                //Set Label
                titleLabel = new Label();
                scoreLabel = new Label("Score : " + score);
                timeLabel = new Label("Time : " + time);
                scoreLabel.setTextFill(Color.WHITE);
                timeLabel.setTextFill(Color.WHITE);
                scoreLabel.setStyle("-fx-font-size: 24px;");
                timeLabel.setStyle("-fx-font-size: 24px;");
                Image titleImage = heart==0 ? new Image("gameOver.png") : new Image("completeTitle.png");
                ImageView titleImageView = new ImageView(titleImage);
                titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
                titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
                titleLabel.setGraphic(titleImageView);
                titleLabel.setTranslateX(120);
                titleLabel.setTranslateY(120);
                scoreLabel.setTranslateX(200);
                scoreLabel.setTranslateY(300);
                timeLabel.setTranslateX(200);
                timeLabel.setTranslateY(350);
                titleLabel.setVisible(true);
                scoreLabel.setVisible(true);
                timeLabel.setVisible(true);

                // Create a TextField for username input
                TextField usernameField = new TextField();
                usernameField.setPromptText("Enter your username"); // Set a prompt text

                // Create a Button to submit the username
                Button submitButton = new Button("Submit");
                submitButton.setOnAction(event -> {
                    String username = usernameField.getText();
                    System.out.println("Entered Username: " + username);
                    // You can handle the entered username as needed

                    ranking = new Ranking();
                    ranking.addEntry(username, score, time);
                });

                HBox inputLayout = new HBox(usernameField, submitButton);
                inputLayout.setSpacing(10);
                inputLayout.setTranslateX(170);
                inputLayout.setTranslateY(400);

                //set buttons
                homeButton = new Button();
                Image homeImage = new Image("homeButton.png");
                ImageView homeImageView = new ImageView(homeImage);
                homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
                homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
                homeButton.setGraphic(homeImageView);
                homeButton.setTranslateX(170);
                homeButton.setTranslateY(450);
                homeButton.setVisible(true);
                homeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            page = 0;
                            level = 1;
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                //Set the root pane
                root = new Pane();
                root.setStyle("-fx-background-color: black;");
                Platform.runLater(() -> {
                    root.getChildren().addAll(
                            titleLabel,
                            scoreLabel,
                            timeLabel,
                            inputLayout,
                            homeButton
                    );

                    //Set the main scene
                    scene = new Scene(root, sceneWidth, sceneHeight);
                    //scene.getStylesheets().add("style.css");

                    //Set the primary stage
                    primaryStage.setTitle("Brick Game");
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                });
            }
            //Next level
            else {
                //Set Label
                titleLabel = new Label();
                Image titleImage = new Image("levelUp.png");
                ImageView titleImageView = new ImageView(titleImage);
                titleImageView.setFitWidth(titleImage.getWidth() * 0.5);
                titleImageView.setFitHeight(titleImage.getHeight() * 0.5);
                titleLabel.setGraphic(titleImageView);
                titleLabel.setTranslateX(120);
                titleLabel.setTranslateY(120);
                titleLabel.setVisible(true);

                //set buttons
                nextGameButton = new Button();
                homeButton = new Button();
                Image nextGameImage = new Image("nextLevelButton.png");
                Image homeImage = new Image("homeButton.png");
                ImageView nextGameImageView = new ImageView(nextGameImage);
                ImageView homeImageView = new ImageView(homeImage);
                nextGameImageView.setFitWidth(nextGameImage.getWidth() * 0.3);
                nextGameImageView.setFitHeight(nextGameImage.getHeight() * 0.3);
                homeImageView.setFitWidth(homeImage.getWidth() * 0.3);
                homeImageView.setFitHeight(homeImage.getHeight() * 0.3);
                nextGameButton.setGraphic(nextGameImageView);
                homeButton.setGraphic(homeImageView);
                nextGameButton.setTranslateX(170);
                nextGameButton.setTranslateY(300);
                homeButton.setTranslateX(170);
                homeButton.setTranslateY(360);
                nextGameButton.setVisible(true);
                homeButton.setVisible(true);
                nextGameButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            page = 1;
                            //level++;
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                homeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            page = 0;
                            level = 1;
                            start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                //Set the root pane
                root = new Pane();
                root.setStyle("-fx-background-color: black;");
                Platform.runLater(() -> {
                    root.getChildren().addAll(
                            titleLabel,
                            nextGameButton,
                            homeButton
                    );

                    //Set the main scene
                    scene = new Scene(root, sceneWidth, sceneHeight);
                    //scene.getStylesheets().add("style.css");

                    //Set the primary stage
                    primaryStage.setTitle("Brick Game");
                    primaryStage.setScene(scene);
                    primaryStage.setResizable(false);
                    primaryStage.show();
                });
            }
            //Turn off background music
            backgroundMusic.stop();
        }
    }
    @Override
    public void onInit(){
        //init ball
        ballClass = new Ball();
        //init break
        breakClass = new Break();
        //init blocks
        blockClass = new Block(level);
        //init bonus
        bonusClass = new Bonus();
        //init heartItem
        heartClass = new Heart();
    }
    @Override
    public void onUpdate() {
        //Clear the level
        if (blockClass.checkClearLevel()) {
            //TODO win level todo...
            System.out.println("Next Level");
            engine.stop();
            try {
                level++;
                page = 2;
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Check ball
        Ball.BallEntry newBall = null;
        for (Ball.BallEntry ball : ballClass.balls){
            //Update the movement of the ball and break
            ball.setPhysicsToBall(breakClass, level, blockClass.blocks);

            //Check if the ball hits the block
            if (ball.collideToBlock){
                Block.BlockEntry block = ball.collideBlock;
                score += block.point;
                new Score().showScore(block.x, block.y, block.point, this);
                if (block.type == Block.BLOCK_NORMAL){}
                if (block.type == Block.BLOCK_CHOCO) {
                    Bonus.BonusEntry newBonus = new Bonus.BonusEntry(block.row, block.column, time);
                    bonusClass.addBonus(newBonus);
                    Platform.runLater(() -> {
                        root.getChildren().add(newBonus.rect);
                    });
                }
                if (block.type == Block.BLOCK_STAR) {
                    ballClass.setGoldTime(time);
                }
                if (block.type == Block.BLOCK_HEART) {
                    Heart.HeartEntry newHeart = new Heart.HeartEntry(block.row, block.column, time);
                    heartClass.addHeart(newHeart);
                    Platform.runLater(() -> {
                        root.getChildren().add(newHeart.rect);
                    });
                }
                if (block.type == Block.BLOCK_BALL){
                    newBall = new Ball.BallEntry(block.row, block.column);
                }
            }

            //Check if any of the ball hits the bottom with golden ball
            if (ball.isMinusHeart()) {
                //TODO gameover
                heart--;
                new Score().showScore(sceneWidth / 2, sceneHeight / 2, -1, this);

                if (heart == 0) {
                    try {
                        page = 2;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    engine.stop();
                }
            }
        }
        if (newBall != null){
            ballClass.addBall(newBall);
            Ball.BallEntry finalNewBall = newBall;
            Platform.runLater(() -> {
                root.getChildren().add(finalNewBall.circle);
            });
        }

        // Check goldtime is over
        ballClass.checkGoldTimeOver(time);

        //Check break hits the bonus falling and add the score
        for (Bonus.BonusEntry choco : bonusClass.bonuses) {
            if (choco.taken){
                continue;
            }
            if (choco.y > sceneHeight) {
                choco.rect.setVisible(false);
                continue;
            }
            if (breakClass.yBreak <= choco.y + Bonus.height && choco.x >= breakClass.xBreak && choco.x <= breakClass.xBreak + breakClass.breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.rect.setVisible(false);
                new Score().showScore(choco.x, choco.y, 3, this);
                continue;
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }

        //Check break hits the heartItem falling and add the heart
        for (Heart.HeartEntry heartItem : heartClass.hearts) {
            if (heartItem.taken){
                continue;
            }
            if (heartItem.y > sceneHeight) {
                heartItem.rect.setVisible(false);
                continue;
            }
            if (breakClass.yBreak <= heartItem.y + Heart.height && heartItem.x >= breakClass.xBreak && heartItem.x <= breakClass.xBreak + breakClass.breakWidth) {
                heartItem.taken = true;
                heartItem.rect.setVisible(false);
                new Score().showMessage("+ Heart", this);
                heart++;
                continue;
            }
            heartItem.y += ((time - heartItem.timeCreated) / 1000.000) + 1.000;
        }

        //Update UI components
        Platform.runLater(() -> {
            //Update the labels
            scoreLabel.setText("Score: " + score);
            heartLabel.setText("Heart : " + heart);
            levelLabel.setText("Level : " + level);
            timeLabel.setText("Time : " + time);

            //Update the position of the break
            breakClass.rect.setX(breakClass.xBreak);
            breakClass.rect.setY(breakClass.yBreak);

            //Update the position of balls
            for (Ball.BallEntry ball : ballClass.balls){
                ball.circle.setCenterX(ball.xBall);
                ball.circle.setCenterY(ball.yBall);
            }

            //Update the position of bonuses
            for (Bonus.BonusEntry bonus : bonusClass.bonuses) {
                bonus.rect.setY(bonus.y);
            }

            //Update the position of heartItems
            for(Heart.HeartEntry heart : heartClass.hearts){
                heart.rect.setY(heart.y);
            }
        });
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}

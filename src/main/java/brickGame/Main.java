package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;


import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements GameEngine.OnAction {
    private int page = 0; //0 home, 1 inGame, 2afterGame
    private int level = 1;
    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private Ball ball;
    private Break rect;
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    private boolean isExistBonusBlock = false;
    private boolean isExistStarBlock = false;
    private int destroyedBlockCount = 0;

    private int  heart = 3;
    private int  score = 0;
    private long time = 0;
    private long hitTime = 0;
    private long goldTime = 0;

    private GameEngine engine;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    private Color[] colors = new Color[]{
        Color.MAGENTA,
        Color.RED,
        Color.GOLD,
        Color.CORAL,
        Color.AQUA,
        Color.VIOLET,
        Color.GREENYELLOW,
        Color.ORANGE,
        Color.PINK,
        Color.SLATEGREY,
        Color.YELLOW,
        Color.TOMATO,
        Color.TAN,
    };
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
    Button homeButton;
    Button pauseButton;
    Button rankingButton;
    Button saveButton;

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
            Image newGameImage = new Image("newGameButton.png");
            Image loadGameImage = new Image("loadGameButton.png");
            Image rankingImage = new Image("rankingButton.png");
            Image quitImage = new Image("quitButton.png");
            ImageView newGameImageView = new ImageView(newGameImage);
            ImageView loadGameImageView = new ImageView(loadGameImage);
            ImageView rankingImageView = new ImageView(rankingImage);
            ImageView quitImageView = new ImageView(quitImage);
            newGameImageView.setFitWidth(newGameImage.getWidth() * 0.3);
            newGameImageView.setFitHeight(newGameImage.getHeight() * 0.3);
            loadGameImageView.setFitWidth(loadGameImage.getWidth() * 0.3);
            loadGameImageView.setFitHeight(loadGameImage.getHeight() * 0.3);
            rankingImageView.setFitWidth(rankingImage.getWidth() * 0.3);
            rankingImageView.setFitHeight(rankingImage.getHeight() * 0.3);
            quitImageView.setFitWidth(quitImage.getWidth() * 0.3);
            quitImageView.setFitHeight(quitImage.getHeight() * 0.3);
            newGameButton.setGraphic(newGameImageView);
            loadGameButton.setGraphic(loadGameImageView);
            rankingButton.setGraphic(rankingImageView);
            quitButton.setGraphic(quitImageView);
            newGameButton.setTranslateX(170);
            newGameButton.setTranslateY(300);
            loadGameButton.setTranslateX(170);
            loadGameButton.setTranslateY(360);
            rankingButton.setTranslateX(170);
            rankingButton.setTranslateY(420);
            quitButton.setTranslateX(185);
            quitButton.setTranslateY(480);
            loadGameButton.setVisible(true);
            newGameButton.setVisible(true);
            rankingButton.setVisible(true);
            quitButton.setVisible(true);
            rankingButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Ranking On");
                }
            });
            loadGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    page=1;
                    loadGame();
                }
            });
            newGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        page = 1;
                        level=1;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            root.getChildren().addAll(
                    titleLabel,
                    newGameButton,
                    loadGameButton,
                    rankingButton,
                    quitButton
            );
            BackgroundImage background = new BackgroundImage(
                    new Image("spaceBg.jpg"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            root.setBackground(new Background(background));


            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeigt);
            //scene.getStylesheets().add("style.css");

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            //Set the game engine
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
        }

        //In Game
        else if (page==1){
            //If it is in the first level
            if (level==1){
                //Initialize
                initBall();
                initBreak();
                initBoard();
                initSetting();
            }
            //Set pause button
            pauseButton = new Button();
            pauseButton.setTranslateY(5);
            pauseButton.setTranslateX(sceneWidth - 32);
            pauseButton.setGraphic(new ImageView(new Image("pauseButton.png")));
            pauseButton.setVisible(true);
            pauseButton.setFocusTraversable(false);
            pauseButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Pause");
                    engine.stop();
                }
            });

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
            root.getChildren().addAll(
                    scoreLabel,
                    heartLabel,
                    levelLabel,
                    timeLabel,
                    pauseButton,
                    rect.rect,
                    ball.ball
            );
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
            }

            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeigt);
            scene.getStylesheets().add("style.css");
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    switch (keyEvent.getCode()) {
                        case LEFT:
                            rect.move(KeyCode.LEFT);
                            break;
                        case RIGHT:
                            rect.move(KeyCode.RIGHT);
                            break;
                    }
                }
            });

            //Set the primary stage
            primaryStage.setTitle("Brick Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            engine.start();

            //Clear the last level
            if (level==11){
                new Score().showWin(this);
                Platform.runLater(() -> {
                    homeButton = new Button("Home");
                    homeButton.setTranslateX(220);
                    homeButton.setTranslateY(300);
                    homeButton.setVisible(true);

                    homeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            homeButton.setVisible(false);
                            try {
                                level=1;
                                start(primaryStage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    root.getChildren().add(homeButton);
                });
            }

        }

        //When its middle term in the game
        if (page==2){
            //initialize
            heart = 3;
            destroyedBlockCount = 0;
            isGoldStatus = false;
            isExistHeartBlock = false;
            isExistBonusBlock = false;
            isExistStarBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;
            initBreak();
            initBall();
            initBoard();

            //set buttons
            nextGameButton = new Button("Next Game");
            homeButton = new Button("Home");
            saveButton = new Button("Save");
            nextGameButton.setTranslateX(220);
            nextGameButton.setTranslateY(340);
            homeButton.setTranslateX(220);
            homeButton.setTranslateY(300);
            saveButton.setTranslateX(220);
            saveButton.setTranslateY(380);
            nextGameButton.setVisible(true);
            homeButton.setVisible(true);
            nextGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    homeButton.setVisible(false);
                    nextGameButton.setVisible(false);
                    saveButton.setVisible(false);
                    try {
                        engine.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            homeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    homeButton.setVisible(false);
                    nextGameButton.setVisible(false);
                    try {
                        level=1;
                        start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            saveButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    saveGame();
                }
            });

            //Set Labels
            scoreLabel = new Label("Score: " + score);
            levelLabel = new Label("Level : " + level);
            heartLabel = new Label("Heart : " + heart);
            timeLabel = new Label("Time : " + time);
            scoreLabel.setTranslateX(0);
            levelLabel.setTranslateY(20);
            heartLabel.setTranslateX(sceneWidth - 70);
            timeLabel.setTranslateY(20);
            timeLabel.setTranslateX(sceneWidth - 70);

            //Set the root pane
            Platform.runLater(() -> {
                root = new Pane();
                root.getChildren().addAll(
                        rect.rect,
                        ball.ball,
                        scoreLabel,
                        heartLabel,
                        levelLabel,
                        timeLabel,
                        nextGameButton,
                        homeButton,
                        saveButton
                );

                //Set blocks
                for (Block block : blocks) {
                    root.getChildren().add(block.rect);
                }

                //Set the main scene
                scene = new Scene(root, sceneWidth, sceneHeigt);
                scene.getStylesheets().add("style.css");

                //Set the primary stage
                primaryStage.setScene(scene);
            });

            //Set the game engine
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
        }
    }
    private void initBall() {
        balls.clear();
        ball = new Ball(sceneWidth, sceneHeigt);
        balls.add(ball);
    }
    private void initBreak() {
        rect = new Break();
    }
    private void initBoard() {
        blocks.clear();
        chocos.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(4);
                int type;
                if (r==1 && !isExistBonusBlock) {
                    type = Block.BLOCK_CHOCO;
                    isExistBonusBlock = true;
                } else if (r==2 && !isExistHeartBlock) {
                    type = Block.BLOCK_HEART;
                    isExistHeartBlock = true;
                } else if (r==3 && !isExistStarBlock){
                    type = Block.BLOCK_STAR;
                    isExistStarBlock = true;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }
    private void initSetting(){
        level = 1;
        heart = 3;
        score = 0;
        destroyedBlockCount = 0;
        isGoldStatus = false;
        isExistHeartBlock = false;
        isExistBonusBlock = false;
        isExistStarBlock = false;
        hitTime = 0;
        time = 0;
        goldTime = 0;
    }

    private void saveGame() {
        LoadSave loadSave = new LoadSave(level, time, score, heart);
        loadSave.saveGame();
    }
    private void loadGame() {
        LoadSave loadSave = new LoadSave(level, time, score, heart);
        loadSave.loadGame();

        level = loadSave.level;
        time = loadSave.time;
        score = loadSave.score;
        heart = loadSave.heart;

        engine.start();
    }

    public void restartGame() {
        //restart the same level or restart from the level 1
        try {
            initSetting();
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);
                levelLabel.setText("Level : " + level);
                timeLabel.setText("Time : " + time);

                rect.rect.setX(rect.xBreak);
                rect.rect.setY(rect.yBreak);
                ball.ball.setCenterX(ball.xBall);
                ball.ball.setCenterY(ball.yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });
    }

    @Override
    public void onPhysicsUpdate() {
        //Clear the level
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            System.out.println("Next Level");
            engine.stop();
            try {
                level++;
                start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Update the movement of the ball and break
        ball.setPhysicsToBall(rect, level, blocks);

        //Check if the ball hits the block
        if (ball.collideToBlock){
            destroyedBlockCount++;
            Block block = ball.collideBlock;
            score += block.point;
            if (block.type == Block.BLOCK_NORMAL){
                new Score().show(block.x, block.y, block.point, this);
            }
            if (block.type == Block.BLOCK_CHOCO) {
                new Score().show(block.x, block.y, block.point, this);
                final Bonus choco = new Bonus(block.row, block.column);
                choco.timeCreated = time;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        root.getChildren().add(choco.choco);
                    }
                });
                chocos.add(choco);
            }

            if (block.type == Block.BLOCK_STAR) {
                new Score().show(block.x, block.y, block.point, this);
                goldTime = time;
                ball.ball.setFill(new ImagePattern(new Image("goldball.png")));
                System.out.println("gold ball");
                root.getStyleClass().add("goldRoot");
                isGoldStatus = true;
            }

            if (block.type == Block.BLOCK_HEART) {
                new Score().show(block.x, block.y, block.point, this);
                heart++;
            }

        }

        //Check if the ball hits the bottom with golden ball
        if (!isGoldStatus && ball.collideToBottomWall) {
            //TODO gameover
            heart--;
            new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);

            if (heart == 0) {
                new Score().showGameOver(this);
                engine.stop();
            }
        }

        // Check goldtime is over
        if (isGoldStatus && time - goldTime > 5) {
            ball.ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

        //Check break hits the bonus falling
        for (Bonus choco : chocos) {
            if (choco.taken){
                continue;
            }
            if (choco.y > sceneHeigt) {
                choco.choco.setVisible(false);
                continue;
            }
            if (rect.yBreak <= choco.y + Bonus.height && choco.x >= rect.xBreak && choco.x <= rect.xBreak + rect.breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
                continue;
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
    }
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}

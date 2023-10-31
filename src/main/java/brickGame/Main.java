package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    private int level = 1;
    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private static int LEFT  = 1;
    private static int RIGHT = 2;

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
    private Pane barPane;
    private Pane gamePane;
    private Scene scene;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Label timeLabel;

    Stage primaryStage;
    Button loadButton;
    Button newGameButton;
    Button nextGameButton;
    Button homeButton;
    Button pauseButton;
    Button rankingButton;
    Button saveButton;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (level==1){
            this.primaryStage = primaryStage;

            //initialize
            initBreak();
            initBall();
            initSetting();
            initBoard();

            //Set Buttons
            newGameButton = new Button("Start New Game");
            loadButton = new Button("Load Game");
            rankingButton = new Button("Rankings");
            newGameButton.setTranslateX(220);
            newGameButton.setTranslateY(340);
            loadButton.setTranslateX(220);
            loadButton.setTranslateY(300);
            rankingButton.setTranslateX(220);
            rankingButton.setTranslateY(380);
            loadButton.setVisible(true);
            newGameButton.setVisible(true);
            rankingButton.setVisible(true);
            rankingButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println("Ranking On");
                }
            });
            loadButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();

                    loadButton.setVisible(false);
                    newGameButton.setVisible(false);
                    rankingButton.setVisible(false);
                }
            });
            newGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine.start();

                    loadButton.setVisible(false);
                    newGameButton.setVisible(false);
                    rankingButton.setVisible(false);
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
            root = new Pane();
            root.getChildren().addAll(
                    rect.rect,
                    ball.ball,
                    scoreLabel,
                    heartLabel,
                    levelLabel,
                    timeLabel,
                    newGameButton,
                    loadButton,
                    rankingButton
            );

            //Set blocks
            for (Block block : blocks) {
                root.getChildren().add(block.rect);
            }

            //Set the main scene
            scene = new Scene(root, sceneWidth, sceneHeigt);
            scene.getStylesheets().add("style.css");
            scene.setOnKeyPressed(this);

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

        //If clear the last level
        if (level==2){
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
            return;
        }

        //When its middle term in the game
        if (level>1){
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
                scene.setOnKeyPressed(this);

                //Set the primary stage
                primaryStage.setScene(scene);
            });

            //Set the game engine
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
        }
    }
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                rect.move(LEFT);
                break;
            case RIGHT:
                rect.move(RIGHT);
                break;
        }
    }
    private void initBall() {
        balls.clear();
        ball = new Ball(sceneWidth, sceneHeigt);
        balls.add(ball);
    }
    private void initBreak() {
        rect = new Break(sceneWidth, sceneHeigt);
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
        if (destroyedBlockCount == 2) {
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

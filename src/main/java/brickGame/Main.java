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
    private int destroyedBlockCount = 0;

    private int  heart = 3;
    private int  score = 0;
    private long time = 0;
    private long hitTime = 0;
    private long goldTime = 0;

    private GameEngine engine;
    private boolean loadFromSave = false;
    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";
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
    Button loadButton = null;
    Button newGameButton = null;
    Button restartButton = null;
    Button homeButton = null;
    Button pauseButton = null;
    Button rankingButton = null;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        //initialize
        initBreak();
        initBall();
        initSetting();
        initBoard();

        //Set Buttons
        newGameButton = new Button("Start New Game");
        loadButton = new Button("Load Game");
        newGameButton.setTranslateX(220);
        newGameButton.setTranslateY(340);
        loadButton.setTranslateX(220);
        loadButton.setTranslateY(300);
        loadButton.setVisible(true);
        newGameButton.setVisible(true);
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                loadGame();

                loadButton.setVisible(false);
                newGameButton.setVisible(false);
            }
        });
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                engine.start();

                loadButton.setVisible(false);
                newGameButton.setVisible(false);
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
        root.getChildren().addAll(rect.rect, ball.ball, scoreLabel, heartLabel, levelLabel, timeLabel, newGameButton, loadButton);

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
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                rect.move(LEFT);
                break;
            case RIGHT:
                rect.move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
                //saveGame();
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
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
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
        hitTime = 0;
        time = 0;
        goldTime = 0;
    }
    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            System.out.println("Next Level");

            nextLevel();
        }
    }

    private void saveGame() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);


                    outputStream.writeDouble(ball.xBall);
                    outputStream.writeDouble(ball.yBall);
                    outputStream.writeDouble(rect.xBreak);
                    outputStream.writeDouble(rect.yBreak);
                    outputStream.writeDouble(rect.centerBreakX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(ball.vX);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStatus);
                    outputStream.writeBoolean(ball.goDownBall);
                    outputStream.writeBoolean(ball.goRightBall);
                    outputStream.writeBoolean(ball.collideToBlock);
                    outputStream.writeBoolean(ball.collideToBottomWall);
                    outputStream.writeObject(blocks);

                    new Score().showMessage("Game Saved", Main.this);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void loadGame() {

        LoadSave loadSave = new LoadSave();
        loadSave.read();

        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStauts;
        ball.goDownBall = loadSave.goDownBall;
        ball.goRightBall = loadSave.goRightBall;
        ball.collideToBottomWall = loadSave.collideToBottomWall;
        ball.collideToBlock = loadSave.collideToBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        ball.xBall = loadSave.xBall;
        ball.yBall = loadSave.yBall;
        rect.xBreak = loadSave.xBreak;
        rect.yBreak = loadSave.yBreak;
        rect.centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        ball.vX = loadSave.vX;

        blocks.clear();
        chocos.clear();

        for (Block block : loadSave.blocks) {
            blocks.add(block);
        }

        try {
            loadFromSave = true;
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void nextLevel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    ball.vX = 1.000;

                    engine.stop();
                    ball.resetCollideFlags();
                    ball.goDownBall = true;

                    isGoldStatus = false;
                    isExistHeartBlock = false;

                    hitTime = 0;
                    time = 0;
                    goldTime = 0;

                    engine.stop();
                    blocks.clear();
                    chocos.clear();
                    destroyedBlockCount = 0;
                    start(primaryStage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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

        //Update score and block if the ball hits the block
        if (ball.collideToBlock) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.xBall, ball.yBall, ball.ballRadius);
                if (hitCode != Block.NO_HIT) {
                    if (block.type == Block.BLOCK_NORMAL){
                        System.out.println("Hit the Normal Block");
                        score += 1;
                        new Score().show(block.x, block.y, 1, this);
                    }
                    if (block.type == Block.BLOCK_CHOCO) {
                        score += 3;
                        System.out.println("Hit the Choco Block");
                        new Score().show(block.x, block.y, 3, this);
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
                        System.out.println("Hit the Star Block");
                        score += 1;
                        new Score().show(block.x, block.y, 1, this);
                        goldTime = time;
                        ball.ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        root.getStyleClass().add("goldRoot");
                        isGoldStatus = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        System.out.println("Hit the Heart Block");
                        score += 1;
                        new Score().show(block.x, block.y, 1, this);
                        heart++;
                    }
                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    @Override
    public void onPhysicsUpdate() {
        //Update the movement of the ball and break
        checkDestroyedCount();
        ball.setPhysicsToBall(rect, level, blocks);

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

        // Check goldTime is over
        if (time - goldTime > 5000) {
            ball.ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

        //Check break hits the bonus falling
        for (Bonus choco : chocos) {
            if (choco.y > sceneHeigt || choco.taken) {
                continue;
            }
            if (rect.yBreak <= choco.y && choco.y <= rect.yBreak + rect.breakHeight && choco.x >= rect.xBreak && choco.x <= rect.xBreak + rect.breakWidth) {
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
    }

    @Override
    public void onInit(){}
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}

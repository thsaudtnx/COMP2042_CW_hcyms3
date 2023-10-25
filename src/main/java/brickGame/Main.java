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
    private int level = 0;
    private int sceneWidth = 500;
    private int sceneHeigt = 700;

    private static int LEFT  = 1;
    private static int RIGHT = 2;

    private Ball ball;
    private Break rect;
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    private int destroyedBlockCount = 0;

    //private double v = 1.000;

    private int  heart = 3;
    private int  score = 0;
    private long time = 0;
    private long hitTime = 0;
    private long goldTime = 0;

    private GameEngine engine;
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
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    private boolean loadFromSave = false;

    Stage primaryStage;
    Button load = null;
    Button newGame = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        if (!loadFromSave) {
            level++;
            if (level >1){
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            initBreak();
            initBall();
            initBoard();

            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220);
            load.setTranslateY(300);
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);

        }

        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);

        if (!loadFromSave) {
            root.getChildren().addAll(rect.rect, ball.ball, scoreLabel, heartLabel, levelLabel, newGame, load);
        } else {
            root.getChildren().addAll(rect.rect, ball.ball, scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
        Scene scene = new Scene(root, sceneWidth, sceneHeigt);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game"); //Title of the game
        primaryStage.setScene(scene); //Set the scene
        primaryStage.setResizable(false); // Cannot resize the screen
        primaryStage.show(); //Show the stage

        if (!loadFromSave) {
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }


    }

    private void initBoard() {
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

    public static void main(String[] args) {
        launch(args);
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
                saveGame();
                break;
        }
    }
    private void initBall() {
        ball = new Ball();
        ball.xBall = (sceneWidth - ball.ballRadius) / 2;
        ball.yBall = sceneHeigt - rect.breakHeight*2 - 30;
        ball.ball.setRadius(ball.ballRadius);
        ball.ball.setFill(new ImagePattern(new Image("ball.png")));
    }
    private void initBreak() {
        rect = new Break();
        rect.xBreak = (sceneWidth - rect.breakWidth )/ 2;
        rect.yBreak = sceneHeigt - rect.breakHeight*2;
        rect.rect.setWidth(rect.breakWidth);
        rect.rect.setHeight(rect.breakHeight);
        rect.rect.setX(rect.xBreak);
        rect.rect.setY(rect.yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));

        rect.rect.setFill(pattern);
    }

    private void setPhysicsToBall(Ball ball) {

        //v = ((time - hitTime) / 1000.000) + 1.000;
        if (ball.goDownBall) {
            ball.yBall += ball.vY;
        } else {
            ball.yBall -= ball.vY;
        }

        if (ball.goRightBall) {
            ball.xBall += ball.vX;
        } else {
            ball.xBall -= ball.vX;
        }

        //Ball collides to the top wall
        if (ball.yBall - ball.ballRadius <= 0) {
            System.out.println("Ball hit the top wall");
            ball.resetCollideFlags();
            ball.goDownBall = true;
            return;
        }
        //Ball collides to the bottom wall
        if (ball.yBall + ball.ballRadius >= sceneHeigt) {
            System.out.println("Ball hit the bottom wall");
            ball.resetCollideFlags();
            ball.goDownBall = false;
            if (!isGoldStatus) {
                //TODO gameover
                heart--;
                new Score().show(sceneWidth / 2, sceneHeigt / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }
            }
            return;
        }

        //Ball collides the right wall
        if (ball.xBall + ball.ballRadius >= sceneWidth) {
            System.out.println("Ball hit the right wall");
            ball.resetCollideFlags();
            ball.collideToRightWall = true;
        }

        //Ball collides the left wall
        if (ball.xBall - ball.ballRadius <= 0) {
            System.out.println("Ball hit the left wall");
            ball.resetCollideFlags();
            ball.collideToLeftWall = true;
        }

        //Ball hits the break
        if (ball.yBall >= rect.yBreak - ball.ballRadius && rect.xBreak <= ball.xBall + ball.ballRadius && ball.xBall - ball.ballRadius <= rect.xBreak + rect.breakWidth) {
            System.out.println("Ball hit the break");
            hitTime = time;
            ball.resetCollideFlags();
            ball.collideToBreak = true;

            double relation = (ball.xBall - rect.centerBreakX) / (rect.breakWidth / 2);

            System.out.println("vX : " + ball.vX + " vY : " + ball.vY);
            //If the relation==0 then hit the center [0 ~ 1]

            if (Math.abs(relation) <= 0.3) {
                //vX = 0;
                ball.vX = Math.abs(relation);
            } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                ball.vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                //System.out.println("vX " + vX);
            } else {
                ball.vX = (Math.abs(relation) * 2) + (level / 3.500);
                //System.out.println("vX " + vX);
            }

            if (ball.xBall - rect.centerBreakX > 0) {
                ball.collideToBreakAndMoveToRight = true;
            } else {
                ball.collideToBreakAndMoveToRight = false;
            }
            //System.out.println("Colide2");
        }

        //Break collide
        if (ball.collideToBreak) {
            ball.goDownBall = false;
            if (ball.collideToBreakAndMoveToRight) {
                ball.goRightBall = true;
            } else {
                ball.goRightBall = false;
            }
            return;
        }

        //Wall Collide
        if (ball.collideToRightWall) {
            ball.goRightBall = false;
        }

        if (ball.collideToLeftWall) {
            ball.goRightBall = true;
        }

        //Block Collide
        if (ball.collideToRightBlock) {
            ball.goRightBall = true;
        }

        if (ball.collideToLeftBlock) {
            ball.goRightBall = false;
        }

        if (ball.collideToTopBlock) {
            ball.goDownBall = false;
        }

        if (ball.collideToBottomBlock) {
            ball.goDownBall = true;
        }
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
                    outputStream.writeBoolean(ball.collideToBreak);
                    outputStream.writeBoolean(ball.collideToBreakAndMoveToRight);
                    outputStream.writeBoolean(ball.collideToRightWall);
                    outputStream.writeBoolean(ball.collideToLeftWall);
                    outputStream.writeBoolean(ball.collideToRightBlock);
                    outputStream.writeBoolean(ball.collideToBottomBlock);
                    outputStream.writeBoolean(ball.collideToLeftBlock);
                    outputStream.writeBoolean(ball.collideToTopBlock);

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            continue;
                        }
                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                    }

                    outputStream.writeObject(blockSerializables);

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
        ball.collideToBreak = loadSave.colideToBreak;
        ball.collideToBreakAndMoveToRight = loadSave.colideToBreakAndMoveToRight;
        ball.collideToRightWall = loadSave.colideToRightWall;
        ball.collideToLeftWall = loadSave.colideToLeftWall;
        ball.collideToRightBlock = loadSave.colideToRightBlock;
        ball.collideToBottomBlock = loadSave.colideToBottomBlock;
        ball.collideToLeftBlock = loadSave.colideToLeftBlock;
        ball.collideToTopBlock = loadSave.colideToTopBlock;
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

        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        }


        try {
            loadFromSave = true;
            start(primaryStage);
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

        try {
            level = 0;
            heart = 3;
            score = 0;
            ball.vX = 1.000;
            destroyedBlockCount = 0;
            ball.resetCollideFlags();
            ball.goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            blocks.clear();
            chocos.clear();

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

                rect.rect.setX(rect.xBreak);
                rect.rect.setY(rect.yBreak);
                ball.ball.setCenterX(ball.xBall);
                ball.ball.setCenterY(ball.yBall);

                for (Bonus choco : chocos) {
                    choco.choco.setY(choco.y);
                }
            }
        });


        if (ball.yBall + ball.ballRadius >= Block.getPaddingTop() && ball.yBall - ball.ballRadius <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(ball.xBall, ball.yBall, ball.ballRadius);
                if (hitCode != Block.NO_HIT) {
                    block.rect.setVisible(false);
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    ball.resetCollideFlags();

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

                    if (hitCode == Block.HIT_RIGHT) {
                        ball.collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        ball.collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        ball.collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        ball.collideToTopBlock = true;
                    }

                }

                //TODO hit to break and some work here....
                //System.out.println("Break in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall(ball);

        if (time - goldTime > 5000) {
            ball.ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

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

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }

    @Override
    public void onTime(long time) {
        this.time = time;
    }
}

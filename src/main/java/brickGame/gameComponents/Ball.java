package brickGame.gameComponents;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the behavior of balls in the game.
 */
public class Ball{
    public ArrayList<BallEntry> balls;
    private static int sceneWidth = 500;
    private static int sceneHeight = 700;
    private static boolean isGoldStatus = false;
    private static long goldTime = 0;
    private static MediaPlayer ballSound;
    private static String ballSoundPath = "/ball.mp3";
    /**
     * Constructor for the Ball class.
     */
    public Ball(){
        balls = new ArrayList<BallEntry>();
        isGoldStatus = false;
        goldTime = 0;
        balls.add(new BallEntry());

        String path = getClass().getResource(ballSoundPath).toExternalForm();
        ballSound = new MediaPlayer(new Media(path));

    }
    /**
     * Checks if the gold time is over and updates the ball appearance accordingly.
     *
     * @param time The current time in the game.
     */
    public void checkGoldTimeOver(long time){
        if (isGoldStatus && time - goldTime > 5) {
            for (BallEntry ball : balls){
                ball.circle.setFill(new ImagePattern(new Image("ball.png")));
            }
            //root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }
    }
    /**
     * Sets the gold time status and updates the appearance of balls accordingly.
     *
     * @param time The current time in the game.
     */
    public void setGoldTime(long time){
        goldTime = time;
        isGoldStatus = true;
        for (BallEntry ball : balls){
            ball.circle.setFill(new ImagePattern(new Image("goldball.png")));
        }
        System.out.println("gold ball");
        //root.getStyleClass().add("goldRoot");
    }
    /**
     * Adds a new ball to the list of balls.
     *
     * @param newBall The new BallEntry object to be added.
     */
    public void addBall(BallEntry newBall){
        balls.add(newBall);
    }
    /**
     * Represents an individual ball in the game.
     */
    public static class BallEntry implements Serializable {
        public Circle circle;
        public double xBall;
        public double yBall;
        public int ballRadius = 5;
        public boolean goDownBall = true;
        public boolean goRightBall = true;
        public boolean collideToBlock = false;
        public boolean collideToBottomWall = false;
        public Block.BlockEntry collideBlock;
        public double vX = 1.000;
        public double vY = 1.000;

        /**
         * Resets the collision flags for the ball.
         */
        public void resetCollideFlags() {
            collideToBlock = false;
            collideToBottomWall = false;
            collideBlock = null;
        }
        /**
         * Constructs a new BallEntry with default values.
         */
        public BallEntry() {
            circle = new Circle();
            xBall = (sceneWidth - ballRadius) / 2;
            yBall = sceneHeight - 50;
            circle.setRadius(ballRadius);
            circle.setFill(new ImagePattern(new Image("ball.png")));
            vX = 1.000;
            vY = 1.000;
        }
        /**
         * Constructs a new BallEntry with specified row and column positions.
         *
         * @param row    The row position of the ball.
         * @param column The column position of the ball.
         */
        public BallEntry(int row, int column){
            circle = new Circle();
            xBall = (column * (Block.width)) + Block.paddingHeight + (Block.width / 2) - 15;
            yBall = (row * (Block.height)) + Block.paddingTop + (Block.height / 2) - 15;
            circle.setRadius(ballRadius);
            circle.setFill(new ImagePattern(new Image("ball.png")));
            vX = 1.000;
            vY = 1.000;
        }
        /**
         * Updates the physics of the ball based on its movement, collisions, and interactions with other game elements.
         *
         * @param rect   The Break object representing the break in the game.
         * @param level  The current level in the game.
         * @param blocks The list of BlockEntry objects representing the blocks in the game.
         */
        public void setPhysicsToBall(Break rect, int level, ArrayList<Block.BlockEntry> blocks) {
            //v = ((time - hitTime) / 1000.000) + 1.000;
            if (goDownBall) {
                yBall += vY;
            } else {
                yBall -= vY;
            }

            if (goRightBall) {
                xBall += vX;
            } else {
                xBall -= vX;
            }

            resetCollideFlags();

            //Ball hits the wall
            //Ball collides to the top wall
            if (yBall - ballRadius <= 0) {
                goDownBall = true;
                ballSound.seek(Duration.ZERO);
                ballSound.play();
                return;
            }
            //Ball collides to the bottom wall
            if (yBall + ballRadius >= 700) {
                ballSound.seek(Duration.ZERO);
                ballSound.play();
                collideToBottomWall = true;
                goDownBall = false;
                return;
            }

            //Ball collides the right wall
            if (xBall + ballRadius >= sceneWidth) {
                ballSound.seek(Duration.ZERO);
                ballSound.play();
                goRightBall = false;
                return;
            }

            //Ball collides the left wall
            if (xBall - ballRadius <= 0) {
                ballSound.seek(Duration.ZERO);
                ballSound.play();
                goRightBall = true;
                return;
            }

            //Ball hits the block
            if (yBall + ballRadius >= Block.paddingTop && yBall - ballRadius <= (Block.height * (level + 1)) + Block.paddingTop) {
                ballSound.seek(Duration.ZERO);
                ballSound.play();
                for (final Block.BlockEntry block : blocks) {
                    //if the block is already destroyed
                    if (block.isDestroyed){
                        continue;
                    }

                    double ballLeft = xBall - ballRadius;
                    double ballRight = xBall + ballRadius;
                    double ballTop = yBall - ballRadius;
                    double ballBottom = yBall + ballRadius;

                    double blockLeft = block.x;
                    double blockRight = block.x + Block.width;
                    double blockTop = block.y;
                    double blockBottom = block.y + Block.height;

                    //If the ball hits the block
                    if (ballRight >= blockLeft && ballLeft <= blockRight && ballTop <= blockBottom && ballBottom >= blockTop){
                        if (ballBottom == blockTop) {
                            System.out.println("Ball hit the top block");
                            goDownBall = false;
                        }
                        else if (ballTop == blockBottom) {
                            System.out.println("Ball hit the bottom block");
                            goDownBall = true;
                        }
                        else if (ballRight >= blockLeft && ballLeft < blockLeft) {
                            System.out.println("Ball hit the left block");
                            goRightBall = false;
                        }
                        else if (ballLeft <= blockRight && ballRight > blockRight){
                            System.out.println("Ball hit the right block");
                            goRightBall = true;
                        }
                        collideToBlock = true;

                        block.isDestroyed = true;
                        Block.destroyedBlockCount++;
                        block.rect.setVisible(false);
                        collideBlock = block;
                        return;
                    }
                }
            }

            //Ball hits the break
            if (yBall >= rect.yBreak - ballRadius && rect.xBreak <= xBall + ballRadius && xBall - ballRadius <= rect.xBreak + rect.breakWidth) {
                ballSound.seek(Duration.ZERO);
                ballSound.play();

                //hitTime = time;
                double relation = (xBall - rect.centerBreakX) / (rect.breakWidth / 2);

                System.out.println("vX : " + vX + " vY : " + vY);
                //If the relation==0 then hit the center [0 ~ 1]

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (xBall - rect.centerBreakX > 0) {
                    goRightBall = true;
                } else {
                    goRightBall = false;
                }
                goDownBall = false;
                return;
            }
        }
        /**
         * Checks if the ball hits the bottom wall without being in gold status.
         *
         * @return True if the ball hits the bottom wall, false otherwise.
         */
        public boolean isMinusHeart(){
            return !isGoldStatus && collideToBottomWall;
        }
    }
}

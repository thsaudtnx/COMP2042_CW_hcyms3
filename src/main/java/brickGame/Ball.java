package brickGame;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

public class Ball {
    public ArrayList<BallEntry> balls;
    private int sceneWidth = 500;
    private int sceneHeight = 700;
    public Ball(){
        balls = new ArrayList<BallEntry>();
        balls.add(new BallEntry());
    }
    public class BallEntry implements Serializable {
        public Circle ball;
        public double xBall;
        public double yBall;
        public static int ballRadius = 5;
        public boolean goDownBall = true;
        public boolean goRightBall = true;
        public boolean collideToBlock = false;
        public boolean collideToBottomWall = false;
        public Block.BlockEntry collideBlock;
        public double vX = 1.000;
        public double vY = 1.000;
        //public double v = 1.000;
        public void resetCollideFlags() {
            collideToBlock = false;
            collideToBottomWall = false;
            collideBlock = null;
        }

        public BallEntry() {
            ball = new Circle();
            xBall = (sceneWidth - ballRadius) / 2;
            yBall = sceneHeight - 50;
            ball.setRadius(ballRadius);
            ball.setFill(new ImagePattern(new Image("ball.png")));
            vX = 1.000;
            vY = 1.000;
        }

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
                //System.out.println("Ball hit the top wall");
                goDownBall = true;
                return;
            }
            //Ball collides to the bottom wall
            if (yBall + ballRadius >= sceneHeight) {
                //System.out.println("Ball hit the bottom wall");
                collideToBottomWall = true;
                goDownBall = false;
                return;
            }

            //Ball collides the right wall
            if (xBall + ballRadius >= sceneWidth) {
                //System.out.println("Ball hit the right wall");
                goRightBall = false;
                return;
            }

            //Ball collides the left wall
            if (xBall - ballRadius <= 0) {
                //System.out.println("Ball hit the left wall");
                goRightBall = true;
                return;
            }

            //Ball hits the block
            if (yBall + ballRadius >= Block.paddingTop && yBall - ballRadius <= (Block.height * (level + 1)) + Block.paddingTop) {
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
                            //System.out.println("Ball hit the top block");
                            goDownBall = false;
                        }
                        else if (ballTop == blockBottom) {
                            //System.out.println("Ball hit the bottom block");
                            goDownBall = true;
                        }
                        else if (ballRight >= blockLeft) {
                            //System.out.println("Ball hit the left block");
                            goRightBall = false;
                        }
                        else { //ballLeft <= blockRight
                            //System.out.println("Ball hit the right block");
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
                //System.out.println("Ball hit the break");

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
    }
}

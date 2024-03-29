package brickGame.gameComponents;

import javafx.scene.shape.Circle;

import java.io.Serializable;
import java.util.ArrayList;

class BallWithoutUI {
    private static int sceneWidth = 500;
    private static int sceneHeight = 700;
    public ArrayList<BallEntry> balls;
    public static boolean isGoldStatus = false;
    public static long goldTime = 0;

    public BallWithoutUI() {
        balls = new ArrayList<BallEntry>();
        isGoldStatus = false;
        goldTime = 0;
        balls.add(new BallEntry());

    }

    public void checkGoldTimeOver(long time) {
        if (isGoldStatus && time - goldTime > 5) {
            //root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }
    }

    public void setGoldTime(long time) {
        goldTime = time;
        isGoldStatus = true;
        System.out.println("gold ball");
        //root.getStyleClass().add("goldRoot");
    }

    public void addBall(BallEntry newBall) {
        balls.add(newBall);
    }

    public static class BallEntry implements Serializable {
        public Circle circle;
        public double xBall;
        public double yBall;
        public int ballRadius = 5;
        public boolean goDownBall = true;
        public boolean goRightBall = true;
        public boolean collideToBlock = false;
        public boolean collideToBottomWall = false;
        public BlockWithoutUI.BlockEntry collideBlock;
        public double vX = 1.000;
        public double vY = 1.000;

        public void resetCollideFlags() {
            collideToBlock = false;
            collideToBottomWall = false;
            collideBlock = null;
        }

        public BallEntry() {
            xBall = (sceneWidth - ballRadius) / 2;
            yBall = sceneHeight - 50;
            vX = 1.000;
            vY = 1.000;
        }

        public BallEntry(int row, int column) {
            xBall = (column * (Block.width)) + Block.paddingHeight + (Block.width / 2) - 15;
            yBall = (row * (Block.height)) + Block.paddingTop + (Block.height / 2) - 15;
            vX = 1.000;
            vY = 1.000;
        }

        public void setPhysicsToBall(BreakWithoutUI rect, int level, ArrayList<BlockWithoutUI.BlockEntry> blocks) {
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
                return;
            }
            //Ball collides to the bottom wall
            if (yBall + ballRadius >= 700) {
                collideToBottomWall = true;
                goDownBall = false;
                return;
            }

            //Ball collides the right wall
            if (xBall + ballRadius >= sceneWidth) {
                goRightBall = false;
                return;
            }

            //Ball collides the left wall
            if (xBall - ballRadius <= 0) {
                goRightBall = true;
                return;
            }

            //Ball hits the block
            if (yBall + ballRadius >= Block.paddingTop && yBall - ballRadius <= (Block.height * (level + 1)) + Block.paddingTop) {
                for (final BlockWithoutUI.BlockEntry block : blocks) {
                    //if the block is already destroyed
                    if (block.isDestroyed) {
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
                    if (ballRight >= blockLeft && ballLeft <= blockRight && ballTop <= blockBottom && ballBottom >= blockTop) {
                        if (ballBottom == blockTop) {
                            System.out.println("Ball hit the top block");
                            goDownBall = false;
                        } else if (ballTop == blockBottom) {
                            System.out.println("Ball hit the bottom block");
                            goDownBall = true;
                        } else if (ballRight >= blockLeft && ballLeft < blockLeft) {
                            System.out.println("Ball hit the left block");
                            goRightBall = false;
                        } else if (ballLeft <= blockRight && ballRight > blockRight) {
                            System.out.println("Ball hit the right block");
                            goRightBall = true;
                        }
                        collideToBlock = true;

                        block.isDestroyed = true;
                        Block.destroyedBlockCount++;
                        collideBlock = block;
                        return;
                    }
                }
            }

            //Ball hits the break
            if (yBall >= rect.yBreak - ballRadius && rect.xBreak <= xBall + ballRadius && xBall - ballRadius <= rect.xBreak + rect.breakWidth) {

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

        public boolean isMinusHeart() {
            return !isGoldStatus && collideToBottomWall;
        }
    }
}

package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball {
    public Circle ball = new Circle();
    public double xBall;
    public double yBall;
    public static int ballRadius = 5;
    public boolean goDownBall = true;
    public boolean goRightBall = true;
    public boolean collideToBreak = false;
    public boolean collideToBreakAndMoveToRight = true;
    public boolean collideToRightWall = false;
    public boolean collideToLeftWall = false;
    public boolean collideToRightBlock = false;
    public boolean collideToBottomBlock = false;
    public boolean collideToLeftBlock = false;
    public boolean collideToTopBlock = false;
    public double vX = 1.000;
    public double vY = 1.000;
    public void resetCollideFlags() {
        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }
}

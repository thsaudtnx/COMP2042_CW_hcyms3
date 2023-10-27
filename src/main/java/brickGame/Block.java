package brickGame;


import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    //private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);
    public int row;
    public int column;
    public boolean isDestroyed = false;
    public Color color;
    public int type;
    public int x;
    public int y;

    public static int destroyedBlockCount = 0;

    public static int width = 100;
    public static int height = 20;
    public static int paddingTop = 50;
    public static int paddingH = 50;
    public Rectangle rect;
    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;
    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;


    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }
    }

    public int checkHitToBlock(double xBall, double yBall, double ballRadius) {

        double ballLeft = xBall - ballRadius;
        double ballRight = xBall + ballRadius;
        double ballTop = yBall - ballRadius;
        double ballBottom = yBall + ballRadius;

        double blockLeft = x;
        double blockRight = x + width;
        double blockTop = y;
        double blockBottom = y + height;

        if (isDestroyed) {
            return NO_HIT;
        }

        //If the ball hits the block
        if (ballRight >= blockLeft && ballLeft <= blockRight && ballTop <= blockBottom && ballBottom >= blockTop) {
            rect.setVisible(false);
            isDestroyed = true;
            destroyedBlockCount++;

            if (ballBottom == blockTop) {
                System.out.println("Ball hit the top block");
                return HIT_TOP;
            }
            else if (ballTop == blockBottom) {
                System.out.println("Ball hit the bottom block");
                return HIT_BOTTOM;
            }
            else if (ballRight >= blockLeft) {
                System.out.println("Ball hit the left block");
                return HIT_LEFT;
            }
            else { //ballLeft <= blockRight
                System.out.println("Ball hit the right block");
                return HIT_RIGHT;
            }
        }

        return NO_HIT;
    }

    public static int getPaddingTop() {
        return paddingTop;
    }

    public static int getPaddingH() {
        return paddingH;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

}

package brickGame;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Break {
    private int sceneWidth = 500;
    private int sceneHeight = 700;
    public int breakWidth = 100;
    public int breakHeight = 10;
    public int halfBreakWidth = breakWidth / 2;
    public double centerBreakX;
    public double xBreak;
    public double yBreak;
    public Rectangle rect;
    public Break() {
        rect = new Rectangle();
        xBreak = (sceneWidth - breakWidth )/ 2;
        yBreak = sceneHeight - breakHeight*2;
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("break.png"));
        rect.setFill(pattern);
    }
    public void move(final KeyCode keyCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    //if break hits the right wall
                    if (xBreak == (sceneWidth - breakWidth) && keyCode == KeyCode.RIGHT) {
                        return;
                    }
                    //if break hits the left wall
                    if (xBreak == 0 && keyCode == KeyCode.LEFT) {
                        return;
                    }
                    if (keyCode == KeyCode.RIGHT) {
                        xBreak++;
                    } else {
                        xBreak--;
                    }
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();
    }
}

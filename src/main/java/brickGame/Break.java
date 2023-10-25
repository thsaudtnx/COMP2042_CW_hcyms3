package brickGame;

import javafx.scene.shape.Rectangle;

public class Break {
    private int sceneWidth = 500;
    public int breakWidth = 100;
    public int breakHeight = 10;
    public int halfBreakWidth = breakWidth / 2;
    public double centerBreakX;
    public double xBreak;
    public double yBreak;
    float oldXBreak;
    private static int LEFT  = 1;
    private static int RIGHT = 2;
    public Rectangle rect = new Rectangle();
    public void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    //if break hits the right wall
                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    //if break hits the left wall
                    if (xBreak == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
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

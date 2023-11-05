package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

public class Heart {
    public ArrayList<Heart.HeartEntry> hearts;
    public static int width = 30;
    public static int height = 30;
    public static int sceneHeight = 700;
    public static int sceneWidth = 500;
    public Heart(){
        hearts = new ArrayList<Heart.HeartEntry>();
    };
    public void addHeart(Heart.HeartEntry newHeart){
        hearts.add(newHeart);
    }
    public static class HeartEntry implements Serializable {
        public Rectangle rect;
        public double x;
        public double y;
        public long timeCreated;
        public boolean taken = false;

        public HeartEntry(int row, int column, long timeCreated) {
            x = (column * (Block.width)) + Block.paddingHeight + (Block.width / 2) - 15;
            y = (row * (Block.height)) + Block.paddingTop + (Block.height / 2) - 15;
            this.timeCreated = timeCreated;

            draw();
        }

        private void draw() {
            rect = new Rectangle();
            rect.setWidth(width);
            rect.setHeight(height);
            rect.setX(x);
            rect.setY(y);

            rect.setFill(new ImagePattern(new Image("heart.png")));
        }
    }

}

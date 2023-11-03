package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bonus {
    public ArrayList<BonusEntry> bonuses;
    public static int width = 30;
    public static int height = 30;
    public static int sceneHeight = 700;
    public static int sceneWidth = 500;
    public Bonus(){
        bonuses = new ArrayList<BonusEntry>();
    };
    public void addBonus(BonusEntry newBonus){
        bonuses.add(newBonus);
    }
    public static class BonusEntry implements Serializable{
        public Rectangle rect;
        public double x;
        public double y;
        public long timeCreated;
        public boolean taken = false;

        public BonusEntry(int row, int column, long timeCreated) {
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

            String url;
            if (new Random().nextInt(20) % 2 == 0) {
                url = "bonus1.png";
            } else {
                url = "bonus2.png";
            }

            rect.setFill(new ImagePattern(new Image(url)));
        }
    }
}

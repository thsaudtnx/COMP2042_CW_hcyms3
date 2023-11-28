package brickGame.components;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents the hearts in the game that the player can collect for extra lives.
 */
public class Heart {
    public ArrayList<Heart.HeartEntry> hearts;
    public static int width = 30;
    public static int height = 30;
    public static int sceneHeight = 700;
    public static int sceneWidth = 500;
    /**
     * Constructs a Heart object, initializing the list of hearts.
     */
    public Heart(){
        hearts = new ArrayList<Heart.HeartEntry>();
    };
    /**
     * Adds a new heart to the list of hearts.
     *
     * @param newHeart The HeartEntry object to be added.
     */
    public void addHeart(Heart.HeartEntry newHeart){
        hearts.add(newHeart);
    }
    /**
     * Represents an individual heart in the game.
     */
    public static class HeartEntry implements Serializable {
        public Rectangle rect;
        public double x;
        public double y;
        public long timeCreated;
        public boolean taken = false;
        /**
         * Constructs a HeartEntry object, initializing its position and creation time.
         *
         * @param row          The row position of the heart.
         * @param column       The column position of the heart.
         * @param timeCreated  The time when the heart was created.
         */
        public HeartEntry(int row, int column, long timeCreated) {
            x = (column * (Block.width)) + Block.paddingHeight + (Block.width / 2) - 15;
            y = (row * (Block.height)) + Block.paddingTop + (Block.height / 2) - 15;
            this.timeCreated = timeCreated;

            draw();
        }
        /**
         * Draws the heart by creating a rectangle with an image pattern.
         */
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

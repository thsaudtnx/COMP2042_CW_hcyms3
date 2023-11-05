package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Block {
    public ArrayList<BlockEntry> blocks;
    public static int destroyedBlockCount;
    public Block(int level){
        blocks = new ArrayList<>();
        destroyedBlockCount = 0;

        boolean isExistBallBlock = false;
        boolean isExistHeartBlock = false;
        boolean isExistBonusBlock = false;
        boolean isExistStarBlock = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(5);
                int type;
                if (r==1 && !isExistBonusBlock) {
                    type = Block.BLOCK_CHOCO;
                    isExistBonusBlock = true;
                } else if (r==2 && !isExistHeartBlock) {
                    type = Block.BLOCK_HEART;
                    isExistHeartBlock = true;
                } else if (r==3 && !isExistStarBlock){
                    type = Block.BLOCK_STAR;
                    isExistStarBlock = true;
                } else if (r==4 && !isExistBallBlock){
                  type = Block.BLOCK_BALL;
                  isExistBallBlock = true;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new BlockEntry(j, i, Colour.colors[new Random().nextInt(Colour.colors.length)], type));
            }
        }
    }
    public boolean checkClearLevel(){
        return destroyedBlockCount == blocks.size();
    }
    public static int width = 80;
    public static int height = 20;
    public static int paddingTop = 50;
    public static int paddingHeight = 50;
    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;
    public static int BLOCK_BALL = 103;
    public class BlockEntry implements Serializable{
        public int row;
        public int column;
        public boolean isDestroyed = false;
        public Color color;
        public int type;
        public int point;
        public int x;
        public int y;
        public Rectangle rect;

        public BlockEntry(int row, int column, Color color, int type) {
            this.row = row;
            this.column = column;
            this.color = color;
            this.type = type;

            draw();
        }

        private void draw() {
            x = (column * width) + paddingHeight;
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
                point = 3;
            } else if (type == BLOCK_HEART) {
                Image image = new Image("heart.jpg");
                ImagePattern pattern = new ImagePattern(image);
                rect.setFill(pattern);
                point = 1;
            } else if (type == BLOCK_STAR) {
                Image image = new Image("star.jpg");
                ImagePattern pattern = new ImagePattern(image);
                rect.setFill(pattern);
                point = 1;
            } else if (type == BLOCK_BALL) {
              Image image = new Image("ballBlock.jpg");
              ImagePattern pattern = new ImagePattern(image);
              rect.setFill(pattern);
              point = 1;
            } else { //type == BLOCK_NORMAL
                rect.setFill(color);
                point = 1;
            }
        }
    }

}

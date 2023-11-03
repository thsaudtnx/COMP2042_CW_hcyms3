package brickGame;

import java.io.*;

public class LoadSave {
    final public static String savePath = "C:/Users/messi/saveGame";
    final public static String fileName = "data.txt";
    public static class Data implements Serializable{
        public static int level;
        public static long time;
        public static int score;
        public static int heart;
        public Data(int level, int score, int heart, long time){
            super();
            this.level = level;
            this.time = time;
            this.score = score;
            this.heart = heart;
        }
    }
    public static void saveGame(int level, long time, int score, int heart){
        Data data = new Data(level, score, heart, time);

        //Create a directory
        File directory = new File(savePath);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory created successfully.");
            } else {
                System.err.println("Failed to create the directory.");
            }
        } else {
            System.out.println("Directory already exists.");
        }

        //Create a file inside a directory
        File file = new File(directory, fileName);

        try {
            if (!file.exists()){
                if (file.createNewFile()) {
                    System.out.println("File created successfully: " + file.getAbsolutePath());
                } else {
                    System.err.println("Failed to create the file.");
                }
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Write the information in the file
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeInt(data.level);
            outputStream.writeInt(data.score);
            outputStream.writeInt(data.heart);
            outputStream.writeLong(data.time);

            //new Score().showMessage("Game Saved", Main.this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath, fileName)));

            Data data = new Data(inputStream.readInt(), inputStream.readInt(), inputStream.readInt(), inputStream.readLong());

            System.out.println("level : " + data.level + ", score : " + data.score + ", heart : " + data.heart + ", time : " + data.time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

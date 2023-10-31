package brickGame;

import java.io.*;

public class LoadSave implements Serializable {
    public int level;
    public long time;
    public int score;
    public int heart;
    public static String savePath = "C:/Users/messi/saveGame";
    public static String fileName = "data.txt";

    public LoadSave(int level, long time, int score, int heart){
        super();
        this.level = level;
        this.time = time;
        this.score = score;
        this.heart = heart;
    }
    public void saveGame(){

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
            outputStream.writeInt(level);
            outputStream.writeInt(score);
            outputStream.writeInt(heart);
            outputStream.writeLong(time);

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
    public void loadGame() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath, fileName)));

            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            time = inputStream.readLong();

            System.out.println("level : " + level + ", score : " + score + ", heart : " + heart + ", time : " + time);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

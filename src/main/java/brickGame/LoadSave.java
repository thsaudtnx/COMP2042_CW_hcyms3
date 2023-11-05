package brickGame;

import java.io.*;
import java.util.ArrayList;

public class LoadSave {
    final public static String savePath = "C:/Users/messi/Desktop/brickGameData";
    final public static String fileName = "savedData.obj";
    static Data data;
    public static class Data implements Serializable{
        public static int level;
        public static int score;
        public static long time;
        public static int heart;
        public static Block blockClass;
        public static Heart heartClass;
        public static Bonus bonusClass;
        public static Ball ballClass;
        public static Break breakClass;
        public Data(
                int level,
                int score,
                long time,
                int heart,
                Block blockClass,
                Heart heartClass,
                Bonus bonusClass,
                Ball ballClass,
                Break breakClass

        ){
            super();
            this.level = level;
            this.score = score;
            this.time = time;
            this.heart = heart;
            this.blockClass = blockClass;
            this.heartClass = heartClass;
            this.bonusClass = bonusClass;
            this.ballClass = ballClass;
            this.breakClass = breakClass;
        }
    }
    public static void saveGame(
            int level,
            int score,
            long time,
            int heart,
            Block blockClass,
            Heart heartClass,
            Bonus bonusClass,
            Ball ballClass,
            Break breakClass
    ){
        data = new Data(
                level,
                score,
                time,
                heart,
                blockClass,
                heartClass,
                bonusClass,
                ballClass,
                breakClass
        );


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
            outputStream.writeObject(data);
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
        System.out.println("Game saved!");
    }

    public static boolean isExistSavedFile(){
        File saveFile = new File(savePath, fileName); // Create a File object for the save file
        if (!saveFile.exists()) {
            System.out.println("Save file does not exist.");
            return false; // Exit the method if the file doesn't exist
        }
        return true;
    }
    public static void loadGame() {
        // Exit the method if the file doesn't exist
        if (!isExistSavedFile()) return;

        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(savePath, fileName)));
            data = (Data) inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

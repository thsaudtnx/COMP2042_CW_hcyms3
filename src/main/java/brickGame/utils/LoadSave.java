package brickGame.utils;

import brickGame.components.*;

import java.io.*;
/**
 * The LoadSave class manages the saving and loading of game data to and from a file.
 */
public class LoadSave {
    final public static String savePath = "C:/Users/messi/Desktop/brickGameData";
    final public static String fileName = "savedData.obj";
    static Data data;
    /**
     * The Data class represents the serialized game data.
     */
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
        /**
         * Constructs a Data object with the given game data.
         *
         * @param level      The level of the game.
         * @param score      The score of the game.
         * @param time       The time of the game.
         * @param heart      The number of hearts in the game.
         * @param blockClass The Block object in the game.
         * @param heartClass The Heart object in the game.
         * @param bonusClass The Bonus object in the game.
         * @param ballClass  The Ball object in the game.
         * @param breakClass The Break object in the game.
         */
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
    /**
     * Saves the current game data to a file.
     *
     * @param level      The level of the game.
     * @param score      The score of the game.
     * @param time       The time of the game.
     * @param heart      The number of hearts in the game.
     * @param blockClass The Block object in the game.
     * @param heartClass The Heart object in the game.
     * @param bonusClass The Bonus object in the game.
     * @param ballClass  The Ball object in the game.
     * @param breakClass The Break object in the game.
     */
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
    /**
     * Checks if a saved game file exists.
     *
     * @return True if the saved game file exists, false otherwise.
     */
    public static boolean isExistSavedFile(){
        File saveFile = new File(savePath, fileName); // Create a File object for the save file
        if (!saveFile.exists()) {
            System.out.println("Save file does not exist.");
            return false; // Exit the method if the file doesn't exist
        }
        return true;
    }
    /**
     * Loads the saved game data from the file.
     */
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

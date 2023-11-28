package brickGame.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * The Ranking class manages the ranking of game entries, including methods to add entries,
 * retrieve the ranking, and save/load entries from/to a file.
 */
public class Ranking {
    private List<RankingEntry> entries;
    private String savePath = "C:/Users/messi/Desktop/brickGameData";
    private String fileName = "ranking.obj";
    /**
     * Constructs a Ranking object and loads existing entries from a file.
     */
    public Ranking() {
        entries = loadRankingFromFile();
    }
    /**
     * Adds a new entry to the ranking.
     *
     * @param username The username associated with the entry.
     * @param score    The score of the entry.
     * @param time     The timestamp of when the entry was added.
     */
    public void addEntry(String username, int score, long time) {
        RankingEntry entry = new RankingEntry(username, score, time);
        entries.add(entry);
        saveRankingToFile();
    }
    /**
     * Retrieves the current ranking, sorted by score in descending order.
     *
     * @return The sorted list of ranking entries.
     */
    public List<RankingEntry> getRanking() {
        // Sort the entries by score (you can customize the sorting logic)
        entries.sort((e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));
        return entries;
    }
    /**
     * Formats the ranking entries into a string for display.
     *
     * @return A formatted string representing the ranking.
     */
    public String getFormattedRanking() {
        List<RankingEntry> ranking = getRanking();
        AtomicInteger index = new AtomicInteger();
        String formattedRanking = ranking.stream()
                .map(entry -> index.incrementAndGet() + "\t" + entry.getUsername() + "\t" + entry.getScore() + "\t" + entry.getTime() + "\t" + entry.getDateTime())
                .collect(Collectors.joining("\n"));
        return formattedRanking;
    }
    /**
     * Loads ranking entries from a file. If the file doesn't exist or an error occurs during loading,
     * an empty list is returned.
     *
     * @return The list of ranking entries loaded from the file, or an empty list if an error occurs.
     */
    private List<RankingEntry> loadRankingFromFile() {
        List<RankingEntry> loadedEntries = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedEntries = (List<RankingEntry>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, or it's okay if the file doesn't exist yet
        }
        return loadedEntries;
    }
    /**
     * Saves the current ranking entries to a file. The method first creates the necessary directory
     * and file if they don't exist. If the file already exists, it will be overwritten.
     *
     * @throws IOException If an I/O error occurs during the file creation or writing process.
     */
    private void saveRankingToFile() {
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(entries);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    /**
     * The RankingEntry class represents an entry in the ranking with a username, score, time, and timestamp.
     */
    public static class RankingEntry implements Serializable {
        private String username;
        private int score;
        private long time;
        private String dateTime;
        /**
         * Constructs a RankingEntry with the given username, score, and time.
         *
         * @param username The username associated with the entry.
         * @param score    The score of the entry.
         * @param time     The timestamp of when the entry was added.
         */
        public RankingEntry(String username, int score, long time) {
            this.username = username;
            this.score = score;
            this.time = time;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");

            // Get the current date and time
            Date currentDate = new Date();

            // Format the current date and time as a string
            String formattedDate = dateFormat.format(currentDate);
            this.dateTime = formattedDate;
        }
        /**
         * Gets the username associated with the entry.
         *
         * @return The username.
         */
        public String getUsername() {
            return username;
        }
        /**
         * Gets the score of the entry.
         *
         * @return The score.
         */
        public int getScore() {
            return score;
        }
        /**
         * Gets the timestamp of when the entry was added.
         *
         * @return The timestamp.
         */
        public long getTime() {
            return time;
        }
        /**
         * Gets the formatted date and time of when the entry was added.
         *
         * @return The formatted date and time.
         */
        public String getDateTime() {return dateTime; }
    }
}

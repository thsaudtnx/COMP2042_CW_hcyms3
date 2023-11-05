package brickGame;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Ranking {
    private List<RankingEntry> entries;
    private String savePath = "C:/Users/messi/Desktop/brickGameData";
    private String fileName = "ranking.obj";

    public Ranking() {
        entries = loadRankingFromFile();
    }
    public void addEntry(String username, int score, long time) {
        RankingEntry entry = new RankingEntry(username, score, time);
        entries.add(entry);
        saveRankingToFile();
    }
    public List<RankingEntry> getRanking() {
        // Sort the entries by score (you can customize the sorting logic)
        entries.sort((e1, e2) -> Integer.compare(e2.getScore(), e1.getScore()));
        return entries;
    }
    public String getFormattedRanking() {
        List<RankingEntry> ranking = getRanking();
        AtomicInteger index = new AtomicInteger();
        String formattedRanking = ranking.stream()
                .map(entry -> index.incrementAndGet() + "\t" + entry.getUsername() + "\t" + entry.getScore() + "\t" + entry.getTime() + "\t" + entry.getDateTime())
                .collect(Collectors.joining("\n"));
        return formattedRanking;
    }
    private List<RankingEntry> loadRankingFromFile() {
        List<RankingEntry> loadedEntries = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedEntries = (List<RankingEntry>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions, or it's okay if the file doesn't exist yet
        }
        return loadedEntries;
    }
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
    public static class RankingEntry implements Serializable {
        private String username;
        private int score;
        private long time;
        private String dateTime;

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

        public String getUsername() {
            return username;
        }

        public int getScore() {
            return score;
        }

        public long getTime() {
            return time;
        }

        public String getDateTime() {return dateTime; }
    }
}

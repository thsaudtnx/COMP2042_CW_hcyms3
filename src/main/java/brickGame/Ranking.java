package brickGame;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Ranking {
    private List<RankingEntry> entries;
    private String savePath = "C:/Users/messi/saveGame";
    private String fileName = "data.txt";

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

        String formattedRanking = ranking.stream()
                .map(entry -> entry.getUsername() + " - Score: " + entry.getScore() + ", Time: " + entry.getTime())
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
    }
}

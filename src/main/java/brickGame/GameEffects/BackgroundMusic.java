package brickGame.GameEffects;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class represents the background music player for the game.
 */
public class BackgroundMusic {
    private String homeMusicPath = "/backgroundMusic.mp3";
    private String gameMusicPath = "/gameMusic.mp3";
    private MediaPlayer mediaPlayer;
    /**
     * Constructs a BackgroundMusic object based on the specified page.
     *
     * @param page The page for which the background music is intended (0 for home, 1 for game).
     */
    public BackgroundMusic(int page){
        String path = getClass().getResource(page==0 ? homeMusicPath : gameMusicPath).toExternalForm();
        mediaPlayer = new MediaPlayer(new Media(path));
    }
    /**
     * Starts playing the background music.
     */
    public void start(){
        mediaPlayer.play();
    }
    /**
     * Stops the background music.
     */
    public void stop(){
        mediaPlayer.stop();
    }
}

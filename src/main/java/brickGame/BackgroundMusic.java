package brickGame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusic {
    private String homeMusicPath = "/backgroundMusic.mp3";
    private String gameMusicPath = "/gameMusic.mp3";
    private MediaPlayer mediaPlayer;
    public BackgroundMusic(int page){
        String path = getClass().getResource(page==0 ? homeMusicPath : gameMusicPath).toExternalForm();
        mediaPlayer = new MediaPlayer(new Media(path));
    }
    public void start(){
        mediaPlayer.play();
    }
    public void stop(){
        mediaPlayer.stop();
    }
}

package root;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import model.Music;
import utillities.FormatUtils;

public class Player {

    private MediaPlayer mediaPlayer;
    private Media file;
    private Music music;

    public Player(String mediaFilePath) {
	File file = new File(mediaFilePath);
	music = FormatUtils.toMusic(file);
	this.file = new Media(file.toURI().toString());
	mediaPlayer = new MediaPlayer(this.file);
	// initializeEventHandlers();
    }

    public void play() {
	mediaPlayer.play();
    }

    public void play(String mediaFilePath) {
	file = new Media(new File(mediaFilePath).toURI().toString());
	mediaPlayer = new MediaPlayer(file);
	play();
    }
    
    public void reset(){
	mediaPlayer.seek(Duration.millis(0));
    }

    /*
     * Returns current playback time of media file in milliseconds.
     */
    public double getCurrentTime() {
	return mediaPlayer.getCurrentTime().toMillis();
    }

    /*
     * Returns total duration time of media file in milliseconds.
     */
    public double getTotalTime() {
	return mediaPlayer.getTotalDuration().toMillis();
    }

    public void setCurrentTime(double value) {
	mediaPlayer.seek(Duration.millis(value));
    }

    public boolean isPlaying() {
	if (mediaPlayer != null) {
	    return mediaPlayer.getStatus().equals(Status.PLAYING);
	} else {
	    return false;
	}
    }

    public void stop() {
	mediaPlayer.stop();
    }

    public void pause() {
	mediaPlayer.pause();
    }

    public void resume() {
	mediaPlayer.play();
    }

    public String getCurrentFile() {
	if (file != null) {
	    return music.getName();
	} else {
	    return null;
	}
    }

    public void setVolume(double value) {
	mediaPlayer.setVolume(value);
    }

    public double getVolume() {
	return mediaPlayer.getVolume();
    }

    /*
     * public void initializeEventHandlers() { mediaPlayer.setOnEndOfMedia(new
     * Runnable() {
     * 
     * @Override public void run() { mediaPlayer.stop(); } }); }
     */
}
package root;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;




public class Player {
    
    private MediaPlayer mediaPlayer;
    private Media file;
    
    public Player(String filePath){
	file = new Media(new File(filePath).toURI().toString());
	mediaPlayer = new MediaPlayer(file);
    }
    
    public void play(){
	mediaPlayer.play();
    }
    
    public void play(String filePath){
	file = new Media(new File(filePath).toURI().toString());
	mediaPlayer = new MediaPlayer(file);
	play();
    }
    
    public boolean isPlaying(){
	if(mediaPlayer != null){
	    return mediaPlayer.getStatus().equals(Status.PLAYING);
	} else {
	    return false;
	}
    }
    
    public void stop(){
	mediaPlayer.stop();
    }
    
    public void pause(){
	mediaPlayer.pause();
    }
    
    public void resume(){
	mediaPlayer.play();
    }
    
    public String getCurrentFile(){
	if(file != null){
	    return file.getSource();
	} else {
	    return null;
	}
    }
}

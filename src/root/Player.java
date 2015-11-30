package root;

import java.io.File;

 
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;


public class Player  {
    
    private MediaPlayer mediaPlayer;
    private Media file;
    private double getTotaltime;

  
    
    public Player(String filePath){
		file = new Media(new File(filePath).toURI().toString());
		mediaPlayer = new MediaPlayer(file);
		getTotaltime = mediaPlayer.getTotalDuration().toSeconds();	
    }
    
    public void setTotaltime(){
    }
    public double getTotaltime() {
    	return mediaPlayer.getTotalDuration().toMillis();	
    }
    public double getCrrenttime(){
    	return mediaPlayer.getCurrentTime().toMillis() / getTotaltime;
    }
    public void setCrrenttime(double changeTime) {
    	mediaPlayer.seek(Duration.millis(changeTime));
    	
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
			System.out.println("it is in playing...");
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

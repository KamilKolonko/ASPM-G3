package root;

import java.io.File;
import java.text.DecimalFormat;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;


public class Player {
    
    private MediaPlayer mediaPlayer;
    private Media file;

    
    public Player(String filePath){
	file = new Media(new File(filePath).toURI().toString());
	mediaPlayer = new MediaPlayer(file);
    
    }
    public double returnCurrentTimeProperty()
    {
    	return mediaPlayer.getCurrentTime().toMillis();
    }

    public MediaPlayer returnMediaPlayer(){
		return mediaPlayer;
    	
    }
	
    public double synchronizeSliderValue(){
    	return mediaPlayer.getCurrentTime().toMillis()/mediaPlayer.getTotalDuration().toMillis()*2000;
    }
     
    public double getTotaltime() {
    	return mediaPlayer.getTotalDuration().toMillis();	
    }
    
    public String getActualTime(){
    	  String cad="";
    	  int horas= (int) mediaPlayer.getCurrentTime().toHours();
    	  
    	  int minutos=(int) mediaPlayer.getCurrentTime().toMinutes()-(horas*60);
    	  int segundos = (int) mediaPlayer.getCurrentTime().toSeconds()-((horas*60*60)+(minutos*60));

    	  DecimalFormat formateador = new DecimalFormat("00");
    	  formateador.format(horas);
    	  formateador.format(minutos);
    	  formateador.format(segundos);
    	  
    	  cad=formateador.format(horas)+":"+formateador.format(minutos)+":"+formateador.format(segundos);
    	  System.out.println("ACTUAL" +cad);
    	  return cad;
    	  
    	 }
    	  
    	 public String getTotalTime(){
    	  String cad=" ";
    	  
    	  int horas= (int) mediaPlayer.getTotalDuration().toHours();
    	  int minutos=(int) mediaPlayer.getTotalDuration().toMinutes()-(horas*60);
    	  int segundos = (int) mediaPlayer.getTotalDuration().toSeconds()-((horas*60*60)+(minutos*60));
    	 
    	  DecimalFormat formateador = new DecimalFormat("00");
    	  formateador.format(horas);
    	  formateador.format(minutos);
    	  formateador.format(segundos);
    	  
    	  cad=formateador.format(horas)+":"+formateador.format(minutos)+":"+formateador.format(segundos);
    	  
    	  return cad;
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
    
    public void setVolume(double d){
    	mediaPlayer.setVolume(d);
    }
    
    public double getVolume(){
    	return mediaPlayer.getVolume();
    }
}
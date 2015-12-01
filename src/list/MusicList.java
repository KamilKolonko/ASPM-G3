package list;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.Music;


final public class MusicList {
   
    private static ArrayList<Music> list;
	static{
		
		
		if (list==null) {
			System.out.println("create list");
			list=new ArrayList<Music>();
		}
	}
	
	public static void add(Music music){
		
		list.add(music);
	}
	
	public static ArrayList<Music> getList() {
		return list;
	}

	public static Music get(int id){
		return list.get(id);
	}
}

package list;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import model.Music;

final public class MusicList {

    private static ArrayList<Music> list;
    private static Music heart;
    static {
	if (list == null) {
	    list = new ArrayList<Music>();
	}
    }

    public static void add(Music music) {
	list.add(music);
    }

    public static ArrayList<Music> getList() {
	return list;
    }

    public static Music get(int id) {
    	System.out.println(id);
	return list.get(id);
    }
    
    public static Music get(String artist, String title, String album, String year){
	for(Music music : list){
	    if((music.getArtist().equals(artist) &&
		    music.getTitle().equals(title) &&
		    music.getAlbum().equals(album) &&
		    music.getYear().equals(year)) || music.getName().equals(title)){
		return music;
	    }
	}
	return null;
    }
    
   
    public static int getSize(){
	return list.size();
    }
}
 

 

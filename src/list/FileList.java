package list;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import model.Music;

public class FileList {

    private static FileOutputStream fileOutputStream;

    public static void writeFile(String filePathAndName, String fileContent) {
	try {
	    File f = new File(filePathAndName);
	    if (!f.exists()) {
		f.createNewFile();
	    }
	    OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8");
	    BufferedWriter writer = new BufferedWriter(write);
	    writer.write(fileContent);
	    writer.close();
	} catch (Exception e) {
	    System.out.println("wrong");
	    e.printStackTrace();
	}
    }

    // clear
    public static void clear(String path) {
	try {
	    fileOutputStream = new FileOutputStream(path);
	    fileOutputStream.write(new String("").getBytes());
	} catch (Exception e) {
	    System.out.println("clear" + e.getMessage());
	}

    }

    public static void readFileByLines(String filePath) {
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));

	    String line = null;
	    ArrayList<Music> list = MusicList.getList();
	    while ((line = reader.readLine()) != null) {
		String[] s = line.split(",");
		Music music = new Music();
		music.setId(s[0]);// id
		music.setName(s[1]);// name
		music.setPath(s[2]);// path
		music.setArtist(s[3]);// artist
		music.setTitle(s[4]);// title
		music.setAlbum(s[5]);// album
		music.setYear(s[6]);// year
		music.setFavorite(Boolean.parseBoolean(s[7]));// favorite
		list.add(music);
	    }
	    reader.close();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e1) {
		    System.out.println(e1.getMessage());
		}
	    }
	}
    }
}
package model;

import javax.swing.ImageIcon;

import dialog.MainWindow;

public class Music {

    private String id;
    private String name;
    private String author;
    private String path;
    private String length;
    private int favorite;
    
    public Music(){};

    public Music(String name, String path) {
	this.name = name;
	this.path = path;
    }

    public String getId() {
	return id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }
//like=1 dislike=0 
	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

//	public ImageIcon getHeart() {
//		return new ImageIcon(MainWindow.class.getResource("/icons/darkheart.png"));
//	}
//
//	public void setHeart(ImageIcon heart) {
//		this.heart = heart;
//	}
    
    
 
}
 
 
 

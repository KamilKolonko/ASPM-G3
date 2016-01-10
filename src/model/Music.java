package model;

public class Music {

    private String id;
    private String name;
    private String artist;
    private String album;
    private String title;
    private String year;
    private String path;
    private boolean favorite;

    public Music() {
    };

    public Music(String name, String artist, String album, String title, String year, String path) {
	this.name = name;
	this.artist = artist;
	this.album = album;
	this.title = title;
	this.year = year;
	this.path = path;
	this.favorite = false;
    }

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

    // like=1 dislike=0
    public boolean getFavorite() {
	return favorite;
    }

    public void setFavorite(boolean favorite) {
	this.favorite = favorite;
    }

    public String getArtist() {
	return artist;
    }

    public void setArtist(String artist) {
	this.artist = artist;
    }

    public String getAlbum() {
	return album;
    }

    public void setAlbum(String album) {
	this.album = album;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getYear() {
	return year;
    }

    public void setYear(String year) {
	this.year = year;
    }

    public String toCommaSeparatedString() {
	return getId() + "," + getName() + "," + getPath() + "," + getArtist() + "," + getTitle() + "," + getAlbum()
		+ "," + getYear() + "," + getFavorite();
    }
   
    public String toString() {
    	return getArtist() + getTitle()+ getAlbum()+ getYear();
        }

    // public ImageIcon getHeart() {
    // return new
    // ImageIcon(MainWindow.class.getResource("/icons/darkheart.png"));
    // }
    //
    // public void setHeart(ImageIcon heart) {
    // this.heart = heart;
    // }

}

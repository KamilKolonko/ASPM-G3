package list;

import javax.swing.JPanel;

import model.Music;


public class MyJPanel extends JPanel{
	private Music music;

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public MyJPanel(Music music) {
		super();
		this.music = music;
	}
	

}

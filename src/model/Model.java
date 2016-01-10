package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import list.MusicList;

public class Model extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private String[] columnNames = { "Title", "Artist", "Album", "Year" };
    private ArrayList<ArrayList<String>> data;

    public Model() {
	refresh();
    }
    
    public Model(String searchedText){
    	data = new ArrayList<>();
    	for (int i = 0; i < MusicList.getList().size(); i++) {
    		Music music = MusicList.get(i);
    		if(music.toString().toLowerCase().contains(searchedText.toLowerCase())){
    			ArrayList<String> row = new ArrayList<>();
        	    if (music.getTitle() != null && !music.getTitle().isEmpty())
        		row.add(music.getTitle());
        	    else
        		row.add(music.getName());
        	    row.add(music.getArtist());
        	    row.add(music.getAlbum());
        	    row.add(String.valueOf(music.getYear()));
        	    data.add(row);
			}
    	}
    }

    public Model(String columnName, String value) {
	data = new ArrayList<>();

	if (MusicPropertiesEnum.ARTIST.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if (music.getArtist().equals(value)) {
		    ArrayList<String> row = new ArrayList<>();
		    if (music.getTitle() != null && !music.getTitle().isEmpty())
			row.add(music.getTitle());
		    else
			row.add(music.getName());
		    row.add(music.getArtist());
		    row.add(music.getAlbum());
		    row.add(String.valueOf(music.getYear()));
		    data.add(row);
		}
	    }
	}
	if (MusicPropertiesEnum.ALBUM.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if (music.getAlbum().equals(value)) {
		    ArrayList<String> row = new ArrayList<>();
		    if (music.getTitle() != null && !music.getTitle().isEmpty())
			row.add(music.getTitle());
		    else
			row.add(music.getName());
		    row.add(music.getArtist());
		    row.add(music.getAlbum());
		    row.add(String.valueOf(music.getYear()));
		    data.add(row);
		}
	    }
	}
	if (MusicPropertiesEnum.YEAR.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if (music.getYear().equals(value)) {
		    ArrayList<String> row = new ArrayList<>();
		    if (music.getTitle() != null && !music.getTitle().isEmpty())
			row.add(music.getTitle());
		    else
			row.add(music.getName());
		    row.add(music.getArtist());
		    row.add(music.getAlbum());
		    row.add(String.valueOf(music.getYear()));
		    data.add(row);
		}
	    }
	}
    }

    public int getColumnCount() {
	return columnNames.length;
    }

    public int getRowCount() {
	return data.size();
    }

    public String getColumnName(int col) {
	return columnNames[col];
    }

    public String getValueAt(int row, int col) {
	return data.get(row).get(col);
    }

    public void refresh() {
	data = new ArrayList<>();
	for (int i = 0; i < MusicList.getList().size(); i++) {
	    Music music = MusicList.get(i);
	    ArrayList<String> row = new ArrayList<>();
	    if (music.getTitle() != null && !music.getTitle().isEmpty())
		row.add(music.getTitle());
	    else
		row.add(music.getName());
	    row.add(music.getArtist());
	    row.add(music.getAlbum());
	    row.add(String.valueOf(music.getYear()));
	    data.add(row);
	}
    }

}

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
	    if(music.getTitle()!=null && !music.getTitle().isEmpty())
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

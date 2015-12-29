package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import list.MusicList;

public class WidgetTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private String[] columnNames;
    private ArrayList<String> data;
    private final String UNKNOWN = "Unknown";

    public WidgetTableModel(String columnName) {
	this.data = new ArrayList<>();
	this.columnNames = new String[] { columnName };
	if (MusicPropertiesEnum.ARTIST.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if(music.getArtist() != null){
		    if (music.getArtist().isEmpty()){
			if(!data.contains(UNKNOWN))
			    data.add(UNKNOWN);
		    }
		    else{
			if(!data.contains(music.getArtist()))
			    data.add(music.getArtist());
		    }
		}
	    }
	} else if (MusicPropertiesEnum.ALBUM.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if(music.getAlbum() != null){
		    if (music.getAlbum().isEmpty()){
			if(!data.contains(UNKNOWN))
			    data.add(UNKNOWN);
		    }
		    else{
			if(!data.contains(music.getAlbum()))
			    data.add(music.getAlbum());
		    }
		}
	    }
	} else if (MusicPropertiesEnum.YEAR.toString().equals(columnName)) {
	    for (int i = 0; i < MusicList.getList().size(); i++) {
		Music music = MusicList.get(i);
		if(music.getYear() != null){
		    if (music.getYear().isEmpty()){
			if(!data.contains(UNKNOWN))
			    data.add(UNKNOWN);
		    }
		    else{
			if(!data.contains(music.getYear()))
			    data.add(music.getYear());
		    }
		}
	    }
	}
    }

    @Override
    public int getColumnCount() {
	return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
	return columnNames[col];
    }

    @Override
    public int getRowCount() {
	return data.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
	return data.get(row);
    }

}

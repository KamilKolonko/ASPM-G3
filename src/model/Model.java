package model;

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import list.MusicList;

public class Model extends AbstractTableModel {

    Vector rowData, columnNames;
    JTable jt = null;

    JScrollPane jsp = null;

    private MusicList list;

    public Model() {
	columnNames = new Vector();
	columnNames.add("music list");

	rowData = new Vector();

	for (int i = 0; i < list.getList().size(); i++) {
	    Vector hang = new Vector();
	    String num = i < 10 ? "0" + (i + 1) : (i + 1) + "";

	    hang.add(num + "  " + list.getList().get(i).getName());
	    rowData.add(hang);
	}
    }

    public int getColumnCount() {
	// TODO Auto-generated method stub
	return this.columnNames.size();
    }

    public int getRowCount() {
	// TODO Auto-generated method stub
	return this.rowData.size();
    }

    public Object getValueAt(int rowIndex, int column) {
	// TODO Auto-generated method stub
	return ((Vector) this.rowData.get(rowIndex)).get(column);
    }

    public String getColumnName(int arg0) {
	// TODO Auto-generated method stub
	return (String) this.columnNames.get(arg0);
    }

}

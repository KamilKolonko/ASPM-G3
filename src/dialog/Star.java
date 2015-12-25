package dialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class Star extends Component {

	static private int num = 0;;
	private Image img;
	private Shape shape;
	
	private int width;
	private int height;
	
	public Star(Dimension Size){
		super();

        setPreferredSize(Size);
 

		img = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/superstar.png"));
		
		addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				solveTheArea(e);
				repaint();
			}
		});
	}
	
	@Override
	public void paint(Graphics g){
		
		g.drawImage(img, 0, 0, width*5, height, 0, 0, 150, 30, this);
		if (num>0)
			g.drawImage(img, 0, 0, width*num, height, 0, 30, 30*num, 60, this);
		
		super.paint(g);
	}
	
	public void solveTheArea(MouseEvent e){
		int x = 0;
		int y = 0;
		int mX = e.getX();
		int mY = e.getY();
		int rX = mX-x;
		int rY = mY-y;
		int pX;
		if (rX<width)
			pX=width;
		else if (rX<width*2)
			pX=width*2;
		else if (rX<width*3)
			pX=width*3;
		else if (rX<width*4)
			pX=width*4;
		else pX=width*5;
		
		if (rX>pX-width+width/10 && rX<pX-width/10 && rY>height/10 && rY<height-height/10)
		{
			if (num == pX/width)
				num = 0;
			else num = pX/width;
		}
	}
	
	@Override
	public void setPreferredSize(Dimension Size){
		super.setPreferredSize(Size);
		width = Size.width/5;
		height = Size.height;
		repaint();
	}
	
	public int getLevel(){
		return num;
	}
}



class StarCellEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = 1L;

	protected Star starButton;

	public StarCellEditor() {
		starButton = new Star(new Dimension(100,20));
		
	}

	@Override public Object getCellEditorValue() {
		return Boolean.valueOf(starButton.isEnabled());
	}

	public Component getTableCellEditorComponent(
			JTable  table,
			Object  value,
			boolean isSelected,
			int     row,
			int     column) {
		

		return starButton;
	}
}

class CWStarRenderer extends JCheckBox implements TableCellRenderer {

	private static final long serialVersionUID = 1L;

	Border border = new EmptyBorder(1, 2, 1, 2);

	public CWStarRenderer() {
		super();
		setOpaque(true);
		setHorizontalAlignment(SwingConstants.CENTER);
	}

	@Override public Component getTableCellRendererComponent(
			JTable  table,
			Object  value,
			boolean isSelected,
			boolean hasFocus,
			int     row,
			int     column) {
		if (value instanceof Boolean) {
		
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}

		return this;
	}
}






//tableMusicList.add(starbutton);
//TableColumnModel a = tableMusicList.getColumnModel().getColumn(0).setCellEditor(new StarCellEditor());
//tableMusicList.getColumnModel().getColumn(0).setCellRenderer(new CWStarRenderer());
//TableColumnModel  model1; //new DefaultTableModel(3,3);

//JTable table = new JTable(model);
//table.getColumnModel().getColumn(1);

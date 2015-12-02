package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuKeyListener;
import javax.swing.table.TableCellRenderer;

import javafx.embed.swing.JFXPanel;
import list.FileList;
import list.MusicList;
import list.MyJPanel;
import model.Music;
import model.Model;
import root.Player;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuKeyEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import java.awt.GridBagLayout;

public class MainWindow extends JFrame implements MouseListener, WindowListener {

    private JPanel contentPane;
    final JFileChooser fc = new JFileChooser();
    private File currentFile;
    private Player player;
    private MusicList list;
    private JPanel[] showMusicList;
    private JScrollPane jsp;
	private JTable jt;
	private Model model;

    public MainWindow() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 538, 377);
	new JFXPanel();

	JMenuBar menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu mnFile = new JMenu("File");
	menuBar.add(mnFile);

	JMenuItem mntmOpen = new JMenuItem("Open");
	mnFile.add(mntmOpen);

	JMenu mnHelp = new JMenu("Help");
	menuBar.add(mnHelp);

	JMenuItem mntmAbout = new JMenuItem("About");
	mnHelp.add(mntmAbout);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));

	JPanel panelPlayer = new JPanel();
	contentPane.add(panelPlayer, BorderLayout.SOUTH);
	panelPlayer.setLayout(new BoxLayout(panelPlayer, BoxLayout.Y_AXIS));

	JPanel panelSlider = new JPanel();
	panelPlayer.add(panelSlider);
	panelSlider.setLayout(new GridLayout(0, 1, 0, 0));

	JSlider slider = new JSlider();
	slider.setValue(0);
	panelSlider.add(slider);

	JPanel panelPlayButtons = new JPanel();
	panelPlayer.add(panelPlayButtons);

	Component horizontalGlue = Box.createHorizontalGlue();
	panelPlayButtons.add(horizontalGlue);

	JButton btnNewButton = new JButton("");
	btnNewButton.setBackground(Color.WHITE);
	btnNewButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/backward.png")));
	panelPlayButtons.add(btnNewButton);

	final JToggleButton toggleButton = new JToggleButton("");
	toggleButton.setBackground(Color.WHITE);
	toggleButton.setSelectedIcon(new ImageIcon(MainWindow.class.getResource("/icons/pause.png")));
	toggleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/play46.png")));
	panelPlayButtons.add(toggleButton);

	JButton btnNewButton_2 = new JButton("");
	btnNewButton_2.setBackground(Color.WHITE);
	btnNewButton_2.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/forward9.png")));
	panelPlayButtons.add(btnNewButton_2);

	JButton btnNewButton_3 = new JButton("");
	btnNewButton_3.setBackground(Color.WHITE);
	btnNewButton_3.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/volume33.png")));
	panelPlayButtons.add(btnNewButton_3);

	final JSlider volumeSlider = new JSlider();
	volumeSlider.setPreferredSize(new Dimension(150, 22));
	// volumeSlider.setOpaque(false);
	volumeSlider.setMaximum(150);
	volumeSlider.setMinimum(0);
	volumeSlider.setValue(50);
	volumeSlider.setToolTipText("change volume");
	// volumeSlider.setUI(new VolumeSliderUI());
	volumeSlider.setPaintTicks(true);
	panelPlayButtons.add(volumeSlider);
	volumeSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		if (player != null) {
		    player.setVolume((double) volumeSlider.getValue() / volumeSlider.getMaximum());
		}
	    }

	});

	Component horizontalGlue_1 = Box.createHorizontalGlue();
	panelPlayButtons.add(horizontalGlue_1);

	JPanel panelMainLeft = new JPanel();
	contentPane.add(panelMainLeft, BorderLayout.WEST);

	JPanel panelMainCenter = new JPanel();
	contentPane.add(panelMainCenter, BorderLayout.CENTER);
	panelMainCenter.setLayout(new GridLayout(1, 0, 0, 0));
	
	
    showMusicList = new JPanel[list.getList().size()];
    for (int i = 0; i < list.getList().size(); i++) {
		
		Music music=list.getList().get(i);
		
		JPanel jPanel=new MyJPanel(music);
		
		
		JLabel jLabel=new JLabel(music.getName(),SwingConstants.CENTER);
		jLabel.setSize(300, 10);
		jLabel.setHorizontalTextPosition(JLabel.CENTER);
		jPanel.setBackground(Color.WHITE);
		showMusicList[i]=jPanel;
		jPanel.addMouseListener(this);		
		jPanel.add(jLabel);
		panelMainCenter.add(jPanel);		
	 }
    model = new Model();
    jt = new JTable(model){
    	  public Component prepareRenderer(TableCellRenderer renderer,
				     int row, int column) {
				    Component c = super.prepareRenderer(renderer, row, column);
				    if (c instanceof JComponent) {
				     ((JComponent) c).setOpaque(false);
				    }
				    return c;
				   }
    };
    jt.setOpaque(false);
	
	jt.setRowHeight(30);
	jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
	jt.setShowHorizontalLines(false);
	jt.setSelectionBackground(new Color(226,41,34));
	jt.addMouseListener(this);
	jsp = new JScrollPane(jt);
	jsp.setOpaque(false);
	jsp.getViewport().setOpaque(false);
	this.add(jsp,BorderLayout.CENTER);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.addWindowListener(this);
	
    Image image=this.getToolkit().getImage("/icons/connected10.png");
    
    this.setIconImage(image);
	
	this.setTitle("music player");
	


	mntmOpen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fc.showOpenDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    currentFile = fc.getSelectedFile();
		    //lblFileName.setText(currentFile.getName());
		    if (player != null && player.isPlaying()) {
			player.stop();
			toggleButton.setSelected(false);
		    }
		    player = new Player(currentFile.getAbsolutePath());
		}
	    }
	});

	toggleButton.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    if (player != null) {
			player.resume();
		    } else {
			if (currentFile != null) {
			    player = new Player(currentFile.getAbsolutePath());
			    player.play();
			}
		    }
		} else {
		    if (player != null) {
			player.pause();
		    }
		}
	    }
	});
    }

	public JTable getJt() {
		return jt;
	}

	public void setJt(JTable jt) {
		this.jt = jt;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int i=1;
		MusicList ls = new MusicList();
		player = new Player(ls.getList().get(jt.getSelectedRow()).getPath());
		if(i/2 != 0){
			player.play();
			i++;
		}else{
			player.stop();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("open");
		
		File file=new File("file/musiclist.txt");
		
		if (file.exists()==false) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {

			FileList.readFileByLines("file/musiclist.txt");
			jt.setModel(new Model());
		}
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("close");
		
		if (MusicList.getList().size()!=0) {
			System.out.println("write");
            //clean
			FileList.clear("file/musiclist.txt");
			ArrayList<Music> list=MusicList.getList();
			for (int i = 0; i < list.size(); i++) {
				FileList.writeFile("file/musiclist.txt",list.get(i).getId()+","+list.get(i).getName()+","
						+list.get(i).getPath()+"\n");
			}
			
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.embed.swing.JFXPanel;
import list.FileList;
import list.MusicList;
import model.Model;
import model.Music;
import model.PlayModeEnum;
import root.Player;
import sun.security.krb5.internal.SeqNumber;
import utillities.FormatUtils;

public class MainWindow extends JFrame implements WindowListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    final JFileChooser fc = new JFileChooser();
    private File[] selectedFiles;
    private Player player;
    private JScrollPane jsp;
    private JTable tableMusicList;
    private Model model;
    final JSlider volumeSlider;
    final JSlider sliderSongProgress;
    private JButton btnBackwards, btnForwards;
    private JTextField lyricsTextField;
    public PlayModeEnum playMode;
    final JLabel labelCurrentTime;
    final JLabel labelTotalTime;
    final PlayThread sliderActive;
    final JToggleButton btnPlayPause;

    public MainWindow() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 616, 377);
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

	JMenuItem mntmLoadLyrics = new JMenuItem("Load lyrics");

	JPanel panelPlayer = new JPanel();
	contentPane.add(panelPlayer, BorderLayout.SOUTH);
	panelPlayer.setLayout(new BoxLayout(panelPlayer, BoxLayout.Y_AXIS));

	JPanel panelSlider = new JPanel();
	panelPlayer.add(panelSlider);
	panelSlider.setLayout(new GridLayout(0, 1, 0, 0));

	sliderSongProgress = new JSlider();
	sliderSongProgress.setMaximum(2000);
	sliderSongProgress.setValue(0);
	panelSlider.add(sliderSongProgress);

	playMode = PlayModeEnum.SEQUENTIAL;

	JPanel panelPlayButtons = new JPanel();
	panelPlayer.add(panelPlayButtons);

	Component horizontalGlue = Box.createHorizontalGlue();
	panelPlayButtons.add(horizontalGlue);
	
	//Adding buttons for different playmodes
	JButton seqButton = new JButton("");
	seqButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/sequentialPlayON.jpg")));
	seqButton.setSelected(true);
	seqButton.setPreferredSize(new Dimension(18,18));
	JButton singleButton = new JButton("");
	singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySong.png")));
	singleButton.setSelected(false);
	singleButton.setPreferredSize(new Dimension(18,18));
	JButton loopButton = new JButton("");
	loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
	loopButton.setSelected(false);
	loopButton.setPreferredSize(new Dimension(18,18));
	
	//provides functionality for sequential playmode
	seqButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(seqButton.isSelected() == false){
				if(loopButton.isSelected() == true){
					loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
					loopButton.setSelected(false);
				}
				if(singleButton.isSelected() == true){
					singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySong.png")));
					singleButton.setSelected(false);
				}
				
				seqButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/sequentialPlayON.jpg")));
				playMode = PlayModeEnum.SEQUENTIAL;
				seqButton.setSelected(true);
			
			}
		}
	});
	panelPlayButtons.add(seqButton);
	
	//provides functionality for single playmode	
	singleButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(singleButton.isSelected() == false){
				if(loopButton.isSelected() == true){
					loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
					loopButton.setSelected(false);
				}
				if(seqButton.isSelected() == true){
					seqButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/sequentialPlay.jpg")));
					seqButton.setSelected(false);
				}
				singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySongON.png")));
				playMode = PlayModeEnum.SINGLE;
				singleButton.setSelected(true);
			
			}
		}
	});
	panelPlayButtons.add(singleButton);
	//provides functionality for looped playmode	
	loopButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(loopButton.isSelected() == false){
				if(singleButton.isSelected() == true){
					singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySong.png")));
					singleButton.setSelected(false);
				}
				if(seqButton.isSelected() == true){
					seqButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/sequentialPlay.jpg")));
					seqButton.setSelected(false);
				}
				loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAllON.png")));
				playMode = PlayModeEnum.LOOP;
				loopButton.setSelected(true);
			
			}
		}
	});
	panelPlayButtons.add(loopButton);

	labelCurrentTime = new JLabel("00:00:00");
	panelPlayButtons.add(labelCurrentTime);

	JLabel labelSlash = new JLabel("/");
	panelPlayButtons.add(labelSlash);

	labelTotalTime = new JLabel("00:00:00");
	panelPlayButtons.add(labelTotalTime);

	btnBackwards = new JButton("");
	btnBackwards.setBackground(Color.WHITE);
	btnBackwards.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/backward9.png")));
	panelPlayButtons.add(btnBackwards);

	btnPlayPause = new JToggleButton("");
	btnPlayPause.setBackground(Color.WHITE);
	btnPlayPause.setSelectedIcon(new ImageIcon(MainWindow.class.getResource("/icons/pause.png")));
	btnPlayPause.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/play46.png")));
	panelPlayButtons.add(btnPlayPause);

	btnForwards = new JButton("");
	btnForwards.setBackground(Color.WHITE);
	btnForwards.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/forward9.png")));
	panelPlayButtons.add(btnForwards);

	JLabel lblVolume = new JLabel("");
	lblVolume.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/volume33.png")));
	panelPlayButtons.add(lblVolume);

	JPanel panelVolumeSlider = new JPanel();
	panelPlayButtons.add(panelVolumeSlider);
	panelVolumeSlider.setLayout(new BorderLayout(0, 0));

	volumeSlider = new JSlider();
	volumeSlider.setPreferredSize(new Dimension(150, 22));
	// volumeSlider.setOpaque(false);
	volumeSlider.setMaximum(150);
	volumeSlider.setMinimum(0);
	volumeSlider.setValue(150);
	volumeSlider.setToolTipText("change volume");
	// volumeSlider.setUI(new VolumeSliderUI());
	volumeSlider.setPaintTicks(true);
	panelVolumeSlider.add(volumeSlider);

	Component verticalStrut = Box.createVerticalStrut(10);
	panelVolumeSlider.add(verticalStrut, BorderLayout.NORTH);

	Component horizontalGlue_1 = Box.createHorizontalGlue();
	panelPlayButtons.add(horizontalGlue_1);

	JPanel panelMainLeft = new JPanel();
	contentPane.add(panelMainLeft, BorderLayout.WEST);

	JPanel panelMainCenter = new JPanel();
	contentPane.add(panelMainCenter, BorderLayout.CENTER);
	tableMusicList = new JTable(model);
	tableMusicList.setOpaque(false);

	tableMusicList.setRowHeight(30);
	tableMusicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableMusicList.setShowHorizontalLines(false);
	tableMusicList.setSelectionBackground(Color.LIGHT_GRAY);
	panelMainCenter.setLayout(new BorderLayout(0, 0));
	jsp = new JScrollPane(tableMusicList);
	panelMainCenter.add(jsp);
	jsp.setOpaque(false);

	JPanel panel = new JPanel();
	panelMainCenter.add(panel, BorderLayout.SOUTH);

	lyricsTextField = new JTextField();
	lyricsTextField.setEditable(false);
	panel.add(lyricsTextField);
	lyricsTextField.setColumns(40);
	jsp.getViewport().setOpaque(false);

	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.addWindowListener(this);
	this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/icons/connected10.png")));
	this.setTitle("Music player");

	sliderActive = new PlayThread();

	volumeSlider.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		if (player != null) {
		    player.setVolume((double) volumeSlider.getValue() / volumeSlider.getMaximum());
		}
	    }
	});

	mntmOpen.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		fc.setMultiSelectionEnabled(true);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".mp3, .wav, .m4a", "mp3", "wav", "m4a");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    selectedFiles = fc.getSelectedFiles();
		    ArrayList<Music> list = MusicList.getList();
		    list.addAll(FormatUtils.toMusicList(selectedFiles));
		    Model model = (Model) tableMusicList.getModel();
		    model.refresh();
		    model.fireTableDataChanged();
		    tableMusicList.repaint();
		}
	    }
	});

	sliderSongProgress.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		sliderActive.threadStop();
	    }
	});

	sliderSongProgress.addMouseListener(new MouseAdapter() {
	    public void mouseReleased(MouseEvent e) {
		// sliderActive.ThreadDelete();
		player.setCurrentTime(
			sliderSongProgress.getValue() * player.getTotalTime() / sliderSongProgress.getMaximum());
		sliderActive.threadStart();
	    }
	});

	mntmLoadLyrics.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fc.showOpenDialog(contentPane);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    lyricsTextField.setText(fc.getSelectedFile().toString());
		}
	    }
	});
	mnFile.add(mntmLoadLyrics);

	btnPlayPause.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    if (player != null) {
			player.resume();
			sliderActive.threadStart();
		    } else {
			if (tableMusicList.getSelectedRowCount() > 0) {
			    player = new Player(MusicList.get(tableMusicList.getSelectedRow()).getPath());
			    player.play();
			    sliderActive.threadStart();
			} else {
			    if (MusicList.getSize() > 0) {
				player = new Player(MusicList.get(0).getPath());
				tableMusicList.setRowSelectionInterval(0, 0);
				player.play();
				sliderActive.threadStart();
			    }
			}
		    }
		} else {
		    if (player != null) {
			player.pause();
			sliderActive.threadStop();
		    }
		}
	    }
	});

	btnBackwards.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int musicNumber = tableMusicList.getSelectedRow();
		if (musicNumber == 0) {
		    player.play();
		} else {
		    if (musicNumber == 1) {
			tableMusicList.setRowSelectionInterval(0, 0);
		    } else {
			tableMusicList.setRowSelectionInterval(musicNumber - 2, musicNumber - 1);
		    }

		    if (player != null && player.isPlaying()) {
			player.pause();
			player = new Player(MusicList.getList().get(musicNumber - 1).getPath());
			player.play();
		    } else {
			player = new Player(MusicList.getList().get(musicNumber - 1).getPath());
			player.play();
		    }
		}
	    }
	});

	btnForwards.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		int musicNumber = tableMusicList.getSelectedRow();
		if (musicNumber == (tableMusicList.getRowCount() - 1)) {
		    player.play();
		} else {

		    tableMusicList.setRowSelectionInterval(musicNumber, musicNumber + 1);

		    if (player != null && player.isPlaying()) {
			player.pause();
			player = new Player(MusicList.getList().get(musicNumber + 1).getPath());
			player.play();
		    } else {
			player = new Player(MusicList.getList().get(musicNumber + 1).getPath());
			player.play();
		    }
		}
	    }
	});

	tableMusicList.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent me) {
		if (me.getClickCount() == 2) {
		    if (player != null) {
			player.stop();
			sliderActive.threadStop();
		    }
		    player = new Player(MusicList.get(tableMusicList.getSelectedRow()).getPath());
		    if (btnPlayPause.isSelected() == true)
			btnPlayPause.setSelected(false);
		    btnPlayPause.setSelected(true);
		}
	    }
	});
    }

    class PlayThread extends Thread {
	public static final int RUNNING = 1;
	public static final int STOPIT = 2;
	public int threadState = STOPIT;

	PlayThread playthread;

	public void setState(int threadState) {
	    this.threadState = threadState;
	}

	public void run() {
	    while (threadState == RUNNING) {
		if (threadState == STOPIT) {
		    break;
		}
		try {
		    sleep(100);
		} catch (InterruptedException e) {
		    throw new RuntimeException(e);
		}

		double totalTime = player.getTotalTime();
		double currentTime = player.getCurrentTime();
		if (totalTime == currentTime) {
		    player.stop();
		    nextSong(this);
		    break;
		}
		// update time slider
		if ((totalTime - currentTime) < 0.01 && (totalTime - currentTime) > -0.01)
		    break;
		sliderSongProgress.setValue((int) (currentTime / totalTime * sliderSongProgress.getMaximum()));
		// update time labels
		labelCurrentTime.setText(FormatUtils.millisecondsToTime(currentTime));
		labelTotalTime.setText(FormatUtils.millisecondsToTime(totalTime));
	    }
	}

	public void threadStart() {
	    playthread = new PlayThread();
	    playthread.setState(RUNNING);
	    playthread.start();
	}

	public void threadStop() {
	    playthread.setState(STOPIT);
	}
    }

    private void nextSong(PlayThread play) {
	sliderSongProgress.setValue(sliderSongProgress.getMinimum());
	int currentSongListId = 0;
	for (int i = 0; i < MusicList.getList().size(); i++) {
	    if (MusicList.getList().get(i).getName().equals(player.getCurrentFile())) {
		currentSongListId = i;
	    }
	}

	switch (playMode) {
	case SEQUENTIAL:
	    player.stop();
	    if (currentSongListId + 1 < MusicList.getSize()) {
		player = new Player(MusicList.getList().get(currentSongListId + 1).getPath());
		player.play();
		sliderActive.threadStart();
	    } else {
		btnPlayPause.setSelected(false);
	    }
	    break;
	case LOOP:
	    player.stop();
	    if (currentSongListId + 1 < MusicList.getSize()) {
		currentSongListId++;
	    } else {
		currentSongListId = 0;
	    }
	    player = new Player(MusicList.getList().get(currentSongListId + 1).getPath());
	    player.play();
	    sliderActive.threadStart();
	    break;
	case SINGLE:
	    player.reset();
	    player.play();
	    sliderActive.threadStart();
	    break;
	}
    }

    @Override
    public void windowOpened(WindowEvent e) {
	File file = new File("file/musiclist.txt");

	if (file.exists() == false) {
	    try {
		file.getParentFile().mkdirs();
		file.createNewFile();
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	}
	FileList.readFileByLines(file.getPath());
	tableMusicList.setModel(new Model());
    }

    @Override
    public void windowClosing(WindowEvent e) {
	if (MusicList.getList().size() != 0) {
	    FileList.clear("file/musiclist.txt");
	    ArrayList<Music> list = MusicList.getList();
	    for (int i = 0; i < list.size(); i++) {
		FileList.writeFile("file/musiclist.txt",
			list.get(i).getId() + "," + list.get(i).getName() + "," + list.get(i).getPath() + "\n");
	    }
	}
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent arg0) {
	// TODO Auto-generated method stub

    }
}

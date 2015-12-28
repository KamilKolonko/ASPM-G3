package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import javax.swing.JPopupMenu;
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
import utillities.CustomTableConstraints;
import utillities.FormatUtils;

public class MainWindow extends JFrame implements WindowListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane, panelLeft, panelList, panelMainCenter;
    final JFileChooser fc = new JFileChooser();
    private File[] selectedFiles;
    private Player player;
    private JScrollPane playlistPanel, jsp1, artistPanel, genrePanel, albumPanel, yearPanel;
    private JTable tableMusicList, listTable, tableMusicList1;
    private Model model;
    final JSlider volumeSlider, sliderSongProgress;
    private JButton btnYears, btnBackwards, btnForwards, btnArtists, btnList, btnCreate, newListname, btnAlbums;
    private JTextField lyricsTextField, listName;
    public PlayModeEnum playMode;
    final JLabel labelCurrentTime, labelTotalTime;
    JLabel createList, cc;
    final PlayThread sliderActive;
    final JToggleButton btnPlayPause;
    private GridBagConstraints createList1, createList2;
    private int y = 0;
    private String name;
    private JPopupMenu pum, pop;
    private JMenuItem item, item1;
    private JMenu del;
    private JMenuBar menuBar;

    public MainWindow() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 900, 450);
	new JFXPanel();

	menuBar = new JMenuBar();
	setJMenuBar(menuBar);

	JMenu mnFile = new JMenu("File");
	menuBar.add(mnFile);

	JMenuItem mntmOpen = new JMenuItem("Open");
	mnFile.add(mntmOpen);

	JMenu mnHelp = new JMenu("Help");
	menuBar.add(mnHelp);

	del = new JMenu("Delete");
	menuBar.add(del);

	JToggleButton favorite2 = new JToggleButton("");
	favorite2.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/darkheart.png")));
	favorite2.setSelectedIcon(new ImageIcon(MainWindow.class.getResource("/icons/redheart.png")));
	menuBar.add(favorite2);

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

	// Adding buttons for different playmodes
	JButton seqButton = new JButton("");
	seqButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
	seqButton.setSelected(true);
	seqButton.setPreferredSize(new Dimension(18, 18));
	JButton singleButton = new JButton("");
	singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySong.png")));
	singleButton.setSelected(false);
	singleButton.setPreferredSize(new Dimension(18, 18));
	JButton loopButton = new JButton("");
	loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/sequentialPlayON.jpg")));
	loopButton.setSelected(false);
	loopButton.setPreferredSize(new Dimension(18, 18));

	// provides functionality for sequential playmode
	seqButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (seqButton.isSelected() == false) {
		    if (loopButton.isSelected() == true) {
			loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
			loopButton.setSelected(false);
		    }
		    if (singleButton.isSelected() == true) {
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

	// provides functionality for single playmode
	singleButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (singleButton.isSelected() == false) {
		    if (loopButton.isSelected() == true) {
			loopButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replayAll.png")));
			loopButton.setSelected(false);
		    }
		    if (seqButton.isSelected() == true) {
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
	// provides functionality for looped playmode
	loopButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		if (loopButton.isSelected() == false) {
		    if (singleButton.isSelected() == true) {
			singleButton.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/replaySong.png")));
			singleButton.setSelected(false);
		    }
		    if (seqButton.isSelected() == true) {
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

	// show list
	panelLeft = new JPanel();
	panelLeft.setPreferredSize(new Dimension(200, 400));
	// frame.getContentPane().add(panelLeft, BorderLayout.WEST);
	GridBagLayout gbl_leftpanel = new GridBagLayout();
	gbl_leftpanel.columnWidths = new int[] { 10, 180 };
	gbl_leftpanel.rowHeights = new int[] { 40, 25, 25, 25, 25, 25, 25, 25, 25 };
	gbl_leftpanel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
	gbl_leftpanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
	panelLeft.setLayout(gbl_leftpanel);
	JLabel label = new JLabel("Music");
	label.setForeground(Color.BLACK);
	label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
	GridBagConstraints gbc_label = new GridBagConstraints();
	gbc_label.fill = GridBagConstraints.BOTH;
	gbc_label.insets = new Insets(0, 0, 5, 0);
	gbc_label.gridx = 0;
	gbc_label.gridy = 0;
	panelLeft.add(label, gbc_label);
	// FlowLayout flowLayout = (FlowLayout) panelLeft.getLayout();
	contentPane.add(panelLeft, BorderLayout.WEST);

	// artists
	JLabel iconArtists = new JLabel("");
	iconArtists.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/girl13.png")));
	panelLeft.add(iconArtists, new CustomTableConstraints(0, 1, GridBagConstraints.CENTER));
	btnArtists = new JButton("Artists");
	btnArtists.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
	btnArtists.setForeground(Color.BLACK);
	btnArtists.setBorder(null);
	btnArtists.setContentAreaFilled(false);
	panelLeft.add(btnArtists, new CustomTableConstraints(1, 1, GridBagConstraints.WEST));

	// albums
	JLabel iconAlbums = new JLabel("");
	iconAlbums.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/music213.png")));
	panelLeft.add(iconAlbums, new CustomTableConstraints(0, 2, GridBagConstraints.CENTER));
	btnAlbums = new JButton("Albums");
	btnAlbums.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
	btnAlbums.setForeground(Color.BLACK);
	btnAlbums.setBorder(null);
	btnAlbums.setContentAreaFilled(false);
	panelLeft.add(btnAlbums, new CustomTableConstraints(1, 2, GridBagConstraints.WEST));

	// years
	JLabel iconYears = new JLabel("");
	iconYears.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/years.png")));
	panelLeft.add(iconYears, new CustomTableConstraints(0, 3, GridBagConstraints.CENTER));
	btnYears = new JButton("Years");
	btnYears.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
	btnYears.setForeground(Color.BLACK);
	btnYears.setBorder(null);
	btnYears.setContentAreaFilled(false);
	panelLeft.add(btnYears, new CustomTableConstraints(1, 3, GridBagConstraints.WEST));

	// create
	JLabel iconCreateList = new JLabel("");
	iconCreateList.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/list.png")));
	panelLeft.add(iconCreateList, new CustomTableConstraints(0, 4, GridBagConstraints.EAST));
	btnCreate = new JButton("Create music list");
	btnCreate.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
	btnCreate.setForeground(Color.BLACK);
	btnCreate.setBorder(null);
	btnCreate.setContentAreaFilled(false);
	// btnCreate.setIcon(new
	// ImageIcon(MainWindow.class.getResource("/icons/list.png")));
	btnCreate.addMouseListener(this);
	panelLeft.add(btnCreate, new CustomTableConstraints(1, 4, GridBagConstraints.WEST));

	// recent
	JLabel bb = new JLabel("");
	bb.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/list.png")));
	panelLeft.add(bb, new CustomTableConstraints(0, 5, GridBagConstraints.EAST));
	btnList = new JButton("Recent music list");
	btnList.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
	btnList.setForeground(Color.BLACK);
	btnList.addMouseListener(this);
	btnList.setBorder(null);
	btnList.setContentAreaFilled(false);
	panelLeft.add(btnList, new CustomTableConstraints(1, 5, GridBagConstraints.WEST));

	// create new list
	cc = new JLabel("");
	cc.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/list.png")));
	panelLeft.add(cc, new CustomTableConstraints(0, 6, GridBagConstraints.EAST));
	listName = new JTextField();
	panelLeft.add(listName, new CustomTableConstraints(1, 6, GridBagConstraints.WEST, GridBagConstraints.BOTH));
	cc.setVisible(false);
	listName.setVisible(false);

	listName.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		name = listName.getText();
		newListname = new JButton(name);
		newListname.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
		newListname.setForeground(Color.BLACK);
		newListname.setBorder(null);
		newListname.setContentAreaFilled(false);
		System.out.println(name);
		newListname.setText(name);
		panelLeft.remove(listName);
		// listName.setVisible(false);
		createList2 = new GridBagConstraints();
		createList2.fill = GridBagConstraints.NONE;
		createList2.anchor = GridBagConstraints.WEST;
		createList2.insets = new Insets(0, 0, 5, 0);
		createList2.gridx = 1;
		createList2.gridy = 3;
		panelLeft.add(newListname, createList2);
		panelLeft.updateUI();
		panelLeft.repaint();
		newListname.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newList();
		    }

		});
	    }

	});

	// delete pop window
	pop = new JPopupMenu();
	item = new JMenuItem("delete list");
	pop.add(item);
	btnList.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		if (e.getSource() == btnList && e.getButton() == MouseEvent.BUTTON3) {
		    System.out.println("pop");
		    pop.show(panelLeft, 100, 100);
		}
	    }
	});
	item.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		deletelist();
		tableMusicList.repaint();

	    }

	});

	JLabel Grade = new JLabel("Grade:");
	panelPlayButtons.add(Grade);

	// star
	Star starbutton = new Star(new Dimension(100, 20));
	starbutton.setEnabled(true);
	starbutton.setPreferredSize(new Dimension(100, 20));
	panelPlayButtons.add(starbutton);

	JButton submitButton = new JButton("submit");
	panelPlayButtons.add(submitButton);
	submitButton.setBackground(Color.lightGray);
	;

	panelMainCenter = new JPanel();
	contentPane.add(panelMainCenter, BorderLayout.CENTER);
	panelMainCenter.setLayout(null);
	tableMusicList = new JTable(model);
	tableMusicList.setOpaque(false);

	tableMusicList.setRowHeight(30);
	tableMusicList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableMusicList.setShowHorizontalLines(false);
	tableMusicList.setSelectionBackground(Color.LIGHT_GRAY);
	playlistPanel = new JScrollPane(tableMusicList);
	playlistPanel.setBounds(0, 0, 674, 269);
	panelMainCenter.add(playlistPanel);
	playlistPanel.setVisible(true);
	playlistPanel.setOpaque(false);

	artistPanel = new JScrollPane();
	artistPanel.setBounds(0, 0, 674, 269);
	artistPanel.setVisible(false);
	panelMainCenter.add(artistPanel);
	artistPanel.setOpaque(false);

	genrePanel = new JScrollPane();
	genrePanel.setBounds(0, 0, 674, 269);
	genrePanel.setVisible(false);
	panelMainCenter.add(genrePanel);
	genrePanel.setOpaque(false);

	albumPanel = new JScrollPane();
	albumPanel.setBounds(0, 0, 674, 269);
	albumPanel.setVisible(false);
	panelMainCenter.add(albumPanel);
	albumPanel.setOpaque(false);

	yearPanel = new JScrollPane();
	yearPanel.setBounds(0, 0, 674, 269);
	yearPanel.setVisible(false);
	panelMainCenter.add(yearPanel);
	yearPanel.setOpaque(false);

	JPanel panel = new JPanel();
	panel.setBounds(0, 269, 674, 30);
	panelMainCenter.add(panel);

	// add music
	pum = new JPopupMenu();
	item1 = new JMenuItem("add music");
	pum.add(item1);
	tableMusicList.addMouseListener(this);
	item1.addActionListener(new ActionListener() {
	    @Override
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

	// delete music
	del.addMouseListener(this);

	lyricsTextField = new JTextField();
	lyricsTextField.setEditable(false);
	panel.add(lyricsTextField);
	lyricsTextField.setColumns(40);
	playlistPanel.getViewport().setOpaque(false);

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

	btnArtists.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		artistPanel.setVisible(true);
		playlistPanel.setVisible(false);
		artistPanel.setVisible(false);
		albumPanel.setVisible(false);
	    }
	});
	
	btnAlbums.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		albumPanel.setVisible(true);
		playlistPanel.setVisible(false);
		artistPanel.setVisible(false);
		albumPanel.setVisible(false);
	    }
	});
	
	btnYears.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		yearPanel.setVisible(true);
		playlistPanel.setVisible(false);
		artistPanel.setVisible(false);
		albumPanel.setVisible(false);
	    }
	});

	tableMusicList.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent me) {
		if (me.getClickCount() == 2) {
		    if (player != null) {
			player.stop();
			sliderActive.threadStop();
		    }

		    if (btnPlayPause.isSelected() == true)
			btnPlayPause.setSelected(false);
		    btnPlayPause.setSelected(true);
		}
	    }

	    public void mouseClicked(MouseEvent me) {
		boolean isFavorite = MusicList.get(tableMusicList.getSelectedRow()).getFavorite();
		Music m = new Music();
		// System.out.println(isFavorite);
		if (me.getSource() == tableMusicList) {
		    favorite2.setSelected(true);
		    favorite2.setSelected(false);
		    favorite2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			    // TODO Auto-generated method stub
			    if (e.getStateChange() == ItemEvent.SELECTED) {
				MusicList.get(tableMusicList.getSelectedRow()).setFavorite(true);
			    } else
				MusicList.get(tableMusicList.getSelectedRow()).setFavorite(false);

			}
		    });
		    if (isFavorite == true) {
			favorite2.setSelected(false);
			favorite2.setSelected(true);
		    } else {
			favorite2.setSelected(true);
			favorite2.setSelected(false);
		    }

		}
		// System.out.println(isFavorite);
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

    // music list (create list)
    private void addMusicList(int x) {
	for (int i = 0; i < x; i++) {
	    int j = 0;
	    cc = new JLabel("");
	    cc.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/list.png")));
	    GridBagConstraints constraints3 = new GridBagConstraints();
	    constraints3.insets = new Insets(0, 0, 5, 0);
	    constraints3.fill = GridBagConstraints.NONE;
	    constraints3.anchor = GridBagConstraints.EAST;
	    constraints3.gridx = j;
	    constraints3.gridy = (i + 4);
	    System.out.println("yi" + j + (i + 4));
	    panelLeft.add(cc, constraints3);
	    listName = new JTextField();
	    createList1 = new GridBagConstraints();
	    createList1.fill = GridBagConstraints.BOTH;
	    createList1.insets = new Insets(0, 0, 5, 0);
	    createList1.gridx = j + 1;
	    createList1.gridy = i + 4;
	    System.out.println("er" + (j + 1) + (i + 4));
	    panelLeft.add(listName, createList1);
	    cc.setVisible(false);
	    listName.setVisible(false);
	    createList2 = new GridBagConstraints();
	    createList2.fill = GridBagConstraints.NONE;
	    createList2.anchor = GridBagConstraints.WEST;
	    createList2.insets = new Insets(0, 0, 5, 0);
	    createList2.gridx = j + 1;
	    createList2.gridy = i + 4;
	    changeListName();

	}
    }

    // music list (modify list name)
    private void changeListName() {
	listName.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		name = listName.getText();
		newListname = new JButton(name);
		newListname.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 10));
		newListname.setForeground(Color.BLACK);
		newListname.setBorder(null);
		newListname.setContentAreaFilled(false);
		System.out.println(name);
		newListname.setText(name);
		panelLeft.remove(listName);
		// listName.setVisible(false);
		panelLeft.add(newListname, createList2);
		panelLeft.updateUI();
		panelLeft.repaint();
		newListname.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			newList();
		    }

		});

	    }

	});

    }

    private void newList() {
	tableMusicList.setVisible(false);
	tableMusicList1 = new JTable(model);
	tableMusicList1.setOpaque(false);

	tableMusicList1.setRowHeight(30);
	tableMusicList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableMusicList1.setShowHorizontalLines(false);
	tableMusicList1.setSelectionBackground(Color.LIGHT_GRAY);
	panelMainCenter.setLayout(new BorderLayout(0, 0));
	jsp1 = new JScrollPane(tableMusicList1);
	panelMainCenter.add(jsp1);
	jsp1.setOpaque(false);
	panelMainCenter.remove(tableMusicList);
	panelMainCenter.updateUI();
	panelMainCenter.repaint();
    }

    private void deleteOneSong() {
	ArrayList<Music> list = MusicList.getList();
	File file = new File("file/musiclist.txt");
	int songIndex = tableMusicList.getSelectedRow();

	// play,not end
	if (player.isPlaying()) {

	    player.stop();
	    player = new Player(MusicList.get(songIndex + 1).getPath());

	    player.play();
	    list.remove(songIndex);
	} else
	    list.remove(songIndex);

	FileList.readFileByLines(file.getPath());
	tableMusicList.setModel(new Model());
	tableMusicList.repaint();
    }

    // delete list
    private void deletelist() {
	ArrayList<Music> list = MusicList.getList();
	File file = new File("file/musiclist.txt");
	System.out.println(list);
	if (list.isEmpty() == false) {
	    for (int i = list.size() - 1; i >= 0; i--) {
		System.out.println(i);
		list.remove(i);
	    }
	}
	System.out.println(list);
	FileList.readFileByLines(file.getPath());
	tableMusicList.setModel(new Model());
	tableMusicList.repaint();

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
	tableMusicList.setVisible(false);
	Model aa = new Model();

    }

    @Override
    public void windowClosing(WindowEvent e) {
	if (MusicList.getList().size() != 0) {
	    FileList.clear("file/musiclist.txt");
	    ArrayList<Music> list = MusicList.getList();
	    Music heart = new Music();
	    for (int i = 0; i < list.size(); i++) {
		FileList.writeFile("file/musiclist.txt", list.get(i).toCommaSeparatedString() + "\n");
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

    @Override
    public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	if (e.getSource() == btnList) {
	    playlistPanel.setVisible(true);
	    tableMusicList.setVisible(true);
	    // tableMusicList1.setVisible(false);
	}
	if (e.getSource() == btnCreate) {
	    System.out.println("create list");
	    addMusicList(y);
	    cc.setVisible(true);
	    listName.setVisible(true);
	    y++;
	}
	if (e.getSource() == del) {

	    deleteOneSong();
	}

    }

    @Override
    public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	if (e.getSource() == tableMusicList && e.getButton() == MouseEvent.BUTTON3) {
	    pum.show(panelMainCenter, 300, 100);
	}
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
}

package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.io.BufferedReader;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
import javax.swing.table.TableColumn;

import javafx.embed.swing.JFXPanel;
import list.FileList;
import list.MusicList;
import model.Model;
import model.Music;
import model.MusicPropertiesEnum;
import model.PlayModeEnum;
import model.WidgetTableModel;
import root.Player;
import utillities.CustomTableConstraints;
import utillities.FormatUtils;

public class MainWindow extends JFrame implements WindowListener, MouseListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane, panelLeft, panelList, panelMainCenter;
    final JFileChooser fc = new JFileChooser();
    private File[] selectedFiles;
    private Player player;
    private JScrollPane playlistPanel, jsp1, artistPanel, genrePanel, albumPanel, yearPanel, gradePanel;
    private JTable tableAlbums, tableMusicList, tableArtists, tableYears, tableGrade, listTable, tableMusicList1;
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

	ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/likeOFF.png"));
	ImageIcon icon2 = new ImageIcon(MainWindow.class.getResource("/icons/like.png"));

	Image img = icon.getImage();
	Image img3 = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon1 = new ImageIcon(img3);

	Image img2 = icon2.getImage();
	Image img4 = img2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon3 = new ImageIcon(img4);

	JToggleButton favorite2 = new JToggleButton("");
	favorite2.setIcon(icon1);
	favorite2.setToolTipText("Like");
	favorite2.setSelectedIcon(icon3);

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

	ImageIcon icon6 = new ImageIcon(MainWindow.class.getResource("/icons/randomON.png"));
	JToggleButton seqButton = new JToggleButton("");
	seqButton.setBackground(Color.WHITE);
	seqButton.setIcon(new ImageIcon(icon6.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
	seqButton.setToolTipText("Sequential mode");
	seqButton.setSelected(true);
	panelPlayButtons.add(seqButton);

	ImageIcon icon8 = new ImageIcon(MainWindow.class.getResource("/icons/repeatSong1.png"));
	JToggleButton singleButton = new JToggleButton("");
	singleButton.setBackground(Color.WHITE);
	singleButton.setIcon(new ImageIcon(icon8.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
	singleButton.setToolTipText("Loop one song mode");
	singleButton.setSelected(false);
	panelPlayButtons.add(singleButton);

	ImageIcon icon10 = new ImageIcon(MainWindow.class.getResource("/icons/repeat.png"));
	JToggleButton loopButton = new JToggleButton("");
	loopButton.setBackground(Color.WHITE);
	loopButton.setIcon(new ImageIcon(icon10.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
	loopButton.setToolTipText("Loop all songs mode");
	loopButton.setSelected(false);
	panelPlayButtons.add(loopButton);

	labelCurrentTime = new JLabel("00:00:00");
	panelPlayButtons.add(labelCurrentTime);

	JLabel labelSlash = new JLabel("/");
	panelPlayButtons.add(labelSlash);

	labelTotalTime = new JLabel("00:00:00");
	panelPlayButtons.add(labelTotalTime);

	ImageIcon icon12 = new ImageIcon(MainWindow.class.getResource("/icons/backwards.png"));

	Image img12 = icon12.getImage();
	Image img13 = img12.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon13 = new ImageIcon(img13);

	btnBackwards = new JButton("");
	btnBackwards.setBackground(Color.WHITE);
	btnBackwards.setToolTipText("Rewind");
	btnBackwards.setIcon(icon13);
	panelPlayButtons.add(btnBackwards);

	ImageIcon icon14 = new ImageIcon(MainWindow.class.getResource("/icons/pause2.png"));

	Image img14 = icon14.getImage();
	Image img15 = img14.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon15 = new ImageIcon(img15);

	ImageIcon icon16 = new ImageIcon(MainWindow.class.getResource("/icons/play.png"));

	Image img16 = icon16.getImage();
	Image img17 = img16.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon17 = new ImageIcon(img17);

	btnPlayPause = new JToggleButton("");
	btnPlayPause.setBackground(Color.WHITE);
	btnPlayPause.setSelectedIcon(icon15);
	btnPlayPause.setToolTipText("Pause");
	btnPlayPause.setIcon(icon17);
	btnPlayPause.setToolTipText("Play");
	panelPlayButtons.add(btnPlayPause);

	ImageIcon icon18 = new ImageIcon(MainWindow.class.getResource("/icons/forwards.png"));

	Image img18 = icon18.getImage();
	Image img19 = img18.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon19 = new ImageIcon(img19);

	btnForwards = new JButton("");
	btnForwards.setBackground(Color.WHITE);
	btnForwards.setIcon(icon19);
	btnForwards.setToolTipText("Forward");
	panelPlayButtons.add(btnForwards);

	ImageIcon icon20 = new ImageIcon(MainWindow.class.getResource("/icons/speaker.png"));

	Image img20 = icon20.getImage();
	Image img21 = img20.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
	ImageIcon icon21 = new ImageIcon(img21);

	JLabel lblVolume = new JLabel("");
	lblVolume.setIcon(icon21);
	lblVolume.setToolTipText("Speakers");
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

	starbutton.addMouseListener(new MouseAdapter() {
	    public void mouseClicked(MouseEvent e) {

		Map<String, String> evaluationMap = new HashMap<String, String>();
		String filename = new String("file\\evaluation.txt");
		File file = new File(filename);
		BufferedReader reader = null;
		try {
		    reader = new BufferedReader(new FileReader(file));
		    String tempString = null;
		    while ((tempString = reader.readLine()) != null) {
			String[] strarray = tempString.split(" ");
			int i;
			for (i = strarray.length - 1; i >= 0; i--) {
			    if (strarray[i].equals("1") || strarray[i].equals("2") || strarray[i].equals("3")
				    || strarray[i].equals("4") || strarray[i].equals("5"))
				break;
			}
			if (i < 0)
			    continue;
			String value = new String(strarray[i]);
			int j;
			String path = new String();
			for (j = 0; j < i; j++) {
			    path += strarray[j];
			    if (j + 1 != i)
				path += " ";
			}
			evaluationMap.put(path, value);
		    }
		    reader.close();
		} catch (IOException e1) {
		    e1.printStackTrace();
		} finally {
		    if (reader != null) {
			try {
			    reader.close();
			} catch (IOException e1) {
			}
		    }
		}
		String path = new String();
		path = MusicList.get(tableMusicList.getSelectedRow()).getPath();

		if (path == null)
		    return;

		int level = ((Star) e.getSource()).getLevel();
		if (level == 0) {
		    Iterator iterator = evaluationMap.keySet().iterator();
		    while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (path.equals(key)) {
			    iterator.remove();
			    evaluationMap.remove(key);
			    break;
			}
		    }
		} else {
		    Integer lev = new Integer(level);
		    evaluationMap.put(path, lev.toString());
		}

		Iterator<Map.Entry<String, String>> entries = evaluationMap.entrySet().iterator();

		try {
		    FileOutputStream fs = new FileOutputStream(new File(filename));
		} catch (FileNotFoundException e2) {
		    e2.printStackTrace();
		}
		while (entries.hasNext()) {

		    Map.Entry<String, String> entry = entries.next();

		    try {

			FileWriter writer = new FileWriter(filename, true);
			writer.write(entry.getKey() + " " + entry.getValue() + "\r\n");
			writer.close();
		    } catch (IOException e1) {
			e1.printStackTrace();
		    }
		}
	    }
	});

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

	tableArtists = new JTable(new WidgetTableModel(MusicPropertiesEnum.ARTIST.toString()));
	tableArtists.setOpaque(false);
	tableArtists.setRowHeight(30);
	tableArtists.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableArtists.setShowHorizontalLines(false);
	tableArtists.setSelectionBackground(Color.LIGHT_GRAY);
	artistPanel = new JScrollPane(tableArtists);
	artistPanel.setBounds(0, 0, 674, 269);
	artistPanel.setVisible(false);
	panelMainCenter.add(artistPanel);
	artistPanel.setOpaque(false);

	genrePanel = new JScrollPane();
	genrePanel.setBounds(0, 0, 674, 269);
	genrePanel.setVisible(false);
	panelMainCenter.add(genrePanel);
	genrePanel.setOpaque(false);

	tableAlbums = new JTable(new WidgetTableModel(MusicPropertiesEnum.ALBUM.toString()));
	tableAlbums.setOpaque(false);
	tableAlbums.setRowHeight(30);
	tableAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableAlbums.setShowHorizontalLines(false);
	tableAlbums.setSelectionBackground(Color.LIGHT_GRAY);
	albumPanel = new JScrollPane(tableAlbums);
	albumPanel.setBounds(0, 0, 674, 269);
	albumPanel.setVisible(false);
	panelMainCenter.add(albumPanel);
	albumPanel.setOpaque(false);

	tableYears = new JTable(new WidgetTableModel(MusicPropertiesEnum.YEAR.toString()));
	tableYears.setOpaque(false);
	tableYears.setRowHeight(30);
	tableYears.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	tableYears.setShowHorizontalLines(false);
	tableYears.setSelectionBackground(Color.LIGHT_GRAY);
	yearPanel = new JScrollPane(tableYears);
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
		if (MusicList.getSize() > 0 || tableArtists.getModel() instanceof Model)
		    tableArtists.setModel(new WidgetTableModel(MusicPropertiesEnum.ARTIST.toString()));
		artistPanel.setVisible(true);
		playlistPanel.setVisible(false);
		yearPanel.setVisible(false);
		albumPanel.setVisible(false);
	    }
	});

	btnAlbums.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (MusicList.getSize() > 0 || tableAlbums.getModel() instanceof Model)
		    tableAlbums.setModel(new WidgetTableModel(MusicPropertiesEnum.ALBUM.toString()));
		albumPanel.setVisible(true);
		playlistPanel.setVisible(false);
		artistPanel.setVisible(false);
		yearPanel.setVisible(false);
	    }
	});

	btnYears.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		if (MusicList.getSize() > 0 || tableYears.getModel() instanceof Model)
		    tableYears.setModel(new WidgetTableModel(MusicPropertiesEnum.YEAR.toString()));
		yearPanel.setVisible(true);
		playlistPanel.setVisible(false);
		artistPanel.setVisible(false);
		albumPanel.setVisible(false);
	    }
	});

	tableArtists.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent me) {
		if (me.getClickCount() == 2) {
		    if (!(tableArtists.getModel() instanceof Model)) {
			String artist = (String) tableArtists.getModel().getValueAt(tableArtists.getSelectedRow(), 0);
			if(artist.equals(WidgetTableModel.UNKNOWN)){
			    artist = "";
			}
			tableArtists.setModel(new Model(MusicPropertiesEnum.ARTIST.toString(), artist));
		    } else {
			if (player != null) {
			    player.stop();
			    sliderActive.threadStop();
			}
			String title = (String)tableArtists.getModel().getValueAt(tableArtists.getSelectedRow(), 0);
			String artist = (String)tableArtists.getModel().getValueAt(tableArtists.getSelectedRow(), 1);
			String album = (String)tableArtists.getModel().getValueAt(tableArtists.getSelectedRow(), 2);
			String year = (String)tableArtists.getModel().getValueAt(tableArtists.getSelectedRow(), 3);
			player = new Player(MusicList.get(artist,title,album,year).getPath());
			if (btnPlayPause.isSelected() == true)
			    btnPlayPause.setSelected(false);
			btnPlayPause.setSelected(true);
		    }
		}
	    }
	});
	
	tableAlbums.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent me) {
		if (me.getClickCount() == 2) {
		    if (!(tableAlbums.getModel() instanceof Model)) {
			String album = (String) tableAlbums.getModel().getValueAt(tableAlbums.getSelectedRow(), 0);
			if(album.equals(WidgetTableModel.UNKNOWN)){
			    album = "";
			}
			tableAlbums.setModel(new Model(MusicPropertiesEnum.ALBUM.toString(), album));
		    } else {
			if (player != null) {
			    player.stop();
			    sliderActive.threadStop();
			}
			String title = (String)tableAlbums.getModel().getValueAt(tableAlbums.getSelectedRow(), 0);
			String artist = (String)tableAlbums.getModel().getValueAt(tableAlbums.getSelectedRow(), 1);
			String album = (String)tableAlbums.getModel().getValueAt(tableAlbums.getSelectedRow(), 2);
			String year = (String)tableAlbums.getModel().getValueAt(tableAlbums.getSelectedRow(), 3);
			player = new Player(MusicList.get(artist,title,album,year).getPath());
			if (btnPlayPause.isSelected() == true)
			    btnPlayPause.setSelected(false);
			btnPlayPause.setSelected(true);
		    }
		}
	    }
	});
	
	tableYears.addMouseListener(new MouseAdapter() {
	    public void mousePressed(MouseEvent me) {
		if (me.getClickCount() == 2) {
		    if (!(tableYears.getModel() instanceof Model)) {
			String year = (String) tableYears.getModel().getValueAt(tableYears.getSelectedRow(), 0);
			if(year.equals(WidgetTableModel.UNKNOWN)){
			    year = "";
			}
			tableYears.setModel(new Model(MusicPropertiesEnum.YEAR.toString(), year));
		    } else {
			if (player != null) {
			    player.stop();
			    sliderActive.threadStop();
			}
			String title = (String)tableYears.getModel().getValueAt(tableYears.getSelectedRow(), 0);
			String artist = (String)tableYears.getModel().getValueAt(tableYears.getSelectedRow(), 1);
			String album = (String)tableYears.getModel().getValueAt(tableYears.getSelectedRow(), 2);
			String year = (String)tableYears.getModel().getValueAt(tableYears.getSelectedRow(), 3);
			player = new Player(MusicList.get(artist,title,album,year).getPath());
			if (btnPlayPause.isSelected() == true)
			    btnPlayPause.setSelected(false);
			btnPlayPause.setSelected(true);
		    }
		}
	    }
	});

	loopButton.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/repeatO.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    loopButton.setIcon(scaledIcon);

		    playMode = PlayModeEnum.LOOP;

		    seqButton.setSelected(false);
		    singleButton.setSelected(false);
		} else {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/repeat.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    loopButton.setIcon(scaledIcon);
		}
	    }
	});

	singleButton.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/repeatSongON1.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    singleButton.setIcon(scaledIcon);

		    playMode = PlayModeEnum.SINGLE;

		    seqButton.setSelected(false);
		    loopButton.setSelected(false);
		} else {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/repeatSong1.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    singleButton.setIcon(scaledIcon);
		}
	    }
	});

	seqButton.addItemListener(new ItemListener() {
	    public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/randomON.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    seqButton.setIcon(scaledIcon);

		    playMode = PlayModeEnum.SEQUENTIAL;

		    singleButton.setSelected(false);
		    loopButton.setSelected(false);
		} else {
		    ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/icons/random.png"));
		    ImageIcon scaledIcon = new ImageIcon(
			    icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH));
		    seqButton.setIcon(scaledIcon);
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
		

		
		  Map<String, String> evaluationMap = new HashMap<String,
		  String>(); String filename = new
		  String("file\\evaluation.txt"); File file = new
		  File(filename); BufferedReader reader = null; try { reader =
		  new BufferedReader(new FileReader(file)); String tempString =
		  null; while ((tempString = reader.readLine()) != null) {
		  String[] strarray = tempString.split(" "); int i; for (i =
		  strarray.length - 1; i >= 0; i--) { if
		  (strarray[i].equals("1") || strarray[i].equals("2") ||
		  strarray[i].equals("3") || strarray[i].equals("4") ||
		  strarray[i].equals("5")) break; } if (i < 0) continue; String
		  value = new String(strarray[i]); int j; String path = new
		  String(); for (j = 0; j < i; j++) { path += strarray[j]; if
		  (j + 1 != i) path += " "; } evaluationMap.put(path, value); }
		  reader.close(); } catch (IOException e1) {
		  e1.printStackTrace(); } finally { if (reader != null) { try {
		  reader.close(); } catch (IOException e1) { } } } String path
		  = new String(); path =
		  MusicList.get(tableMusicList.getSelectedRow()).getPath();
		  
		  if (path == null) return;
		  
		  String level = new String(); level = evaluationMap.get(path);
		  if (level != null) { Integer levelInt = new Integer(level);
		  starbutton.setLevel(levelInt); }
		 

	

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

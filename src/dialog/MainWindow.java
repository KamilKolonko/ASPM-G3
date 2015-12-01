package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuKeyListener;

import javafx.embed.swing.JFXPanel;
import root.Player;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuKeyEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JList;
import java.awt.GridBagLayout;

public class MainWindow extends JFrame {

    private JPanel contentPane;
    final JFileChooser fc = new JFileChooser();
    private File currentFile;
    private Player player;

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
	slider.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (source == slider) {
				
				player.setCrrenttime(slider.getValue()*player.getTotaltime()/100.0);	 
			}
			
		}
	});

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
	
	JList list = new JList();
	panelMainCenter.add(list);

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
}

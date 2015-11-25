package root;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame {

    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public MainWindow() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 538, 377);

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

	JButton btnNewButton_1 = new JButton("");
	btnNewButton_1.setBackground(Color.WHITE);
	btnNewButton_1.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/play46.png")));
	panelPlayButtons.add(btnNewButton_1);

	JButton btnNewButton_2 = new JButton("");
	btnNewButton_2.setBackground(Color.WHITE);
	btnNewButton_2.setIcon(new ImageIcon(MainWindow.class.getResource("/icons/forward9.png")));
	panelPlayButtons.add(btnNewButton_2);

	Component horizontalGlue_1 = Box.createHorizontalGlue();
	panelPlayButtons.add(horizontalGlue_1);

	JPanel panelMainLeft = new JPanel();
	contentPane.add(panelMainLeft, BorderLayout.WEST);

	JPanel panelMainCenter = new JPanel();
	contentPane.add(panelMainCenter, BorderLayout.CENTER);

	JSeparator separator = new JSeparator();
	separator.setOrientation(SwingConstants.VERTICAL);
	panelMainCenter.add(separator);
    }

}

package ihm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageGarde extends JFrame {

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;

	private JLabel titreLabel;
	private JButton commencerBtn;
	private JLabel backgroundLabel;

	public PageGarde() {
		setTitle("Ô'Tomates");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);  

		ImageIcon bgIcon = new ImageIcon(getClass().getResource("/images/backgroundtomate2.jpg"));
		backgroundLabel = new JLabel(bgIcon);
		backgroundLabel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
		backgroundLabel.setLayout(null); // Still needed for absolute positioning
		getContentPane().add(backgroundLabel);

		ImageIcon titleIcon = new ImageIcon(getClass().getResource("/images/TOMATESICON.png"));
		Image scaledTitle = titleIcon.getImage().getScaledInstance(600, 200, Image.SCALE_SMOOTH);
		titreLabel = new JLabel(new ImageIcon(scaledTitle));
		titreLabel.setBounds(100, 80, 600, 200);
		backgroundLabel.add(titreLabel);

		ImageIcon btnIcon = new ImageIcon(getClass().getResource("/images/commencerbutton.png"));
		Image scaledBtn = btnIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
		commencerBtn = new JButton(new ImageIcon(scaledBtn));
		commencerBtn.setBounds(300, 400, 200, 60);
		commencerBtn.setBorderPainted(false);
		commencerBtn.setContentAreaFilled(false);
		commencerBtn.setFocusPainted(false);
		commencerBtn.setOpaque(false);

		commencerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListeTomates();
				dispose();
			}
		});
		backgroundLabel.add(commencerBtn);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PageGarde();
			}
		});
	}
}

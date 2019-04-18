package local.rab.windows;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class InfoFront extends JFrame {

	private JPanel contentPane;
	private JLabel InfoBox;
	
	/**
	 * Create the frame.
	 */
	public InfoFront() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		InfoBox = new JLabel("");
		InfoBox.setForeground(Color.RED);
		InfoBox.setFont(new Font("Tahoma", Font.PLAIN, 50));
		InfoBox.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(InfoBox);
		setUndecorated(true);
	}

	public JLabel getInfoBox() {
		return InfoBox;
	}
}

package rab.local;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class Frontchooseorps extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frontchooseorps frame = new Frontchooseorps();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frontchooseorps() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 551);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("RAB project \"ebe\"");
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(10, 11, 152, 31);
		contentPane.add(label);
		
		JButton XYZcoordinates = new JButton("XYZ-Coordinates");
		XYZcoordinates.setBounds(185, 247, 120, 23);
		contentPane.add(XYZcoordinates);
		
		JButton btnNewButton_1 = new JButton("PS-Controller");
		btnNewButton_1.setBounds(410, 247, 120, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("think");
		lblNewLabel.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(328, 72, 48, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("idea");
		lblNewLabel_1.setForeground(Color.ORANGE);
		lblNewLabel_1.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblNewLabel_1.setBounds(524, 95, 48, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblTry = new JLabel("try");
		lblTry.setForeground(Color.ORANGE);
		lblTry.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblTry.setBounds(650, 190, 48, 14);
		contentPane.add(lblTry);
		
		JLabel lblDo = new JLabel("do");
		lblDo.setForeground(Color.ORANGE);
		lblDo.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblDo.setBounds(616, 369, 48, 14);
		contentPane.add(lblDo);
		
		JLabel lblDoAgain = new JLabel("do again!");
		lblDoAgain.setForeground(Color.ORANGE);
		lblDoAgain.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblDoAgain.setBounds(396, 431, 84, 14);
		contentPane.add(lblDoAgain);
		
		JLabel lblAndAgain = new JLabel("and again");
		lblAndAgain.setForeground(Color.ORANGE);
		lblAndAgain.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblAndAgain.setBounds(110, 370, 84, 14);
		contentPane.add(lblAndAgain);
		
		JLabel lblKeepDoing = new JLabel("keep doing");
		lblKeepDoing.setForeground(Color.ORANGE);
		lblKeepDoing.setFont(new Font("Mongolian Baiti", Font.ITALIC, 16));
		lblKeepDoing.setBounds(68, 191, 96, 14);
		contentPane.add(lblKeepDoing);
		
		JLabel lblSuccess = new JLabel("success!!");
		lblSuccess.setForeground(Color.ORANGE);
		lblSuccess.setFont(new Font("Mongolian Baiti", Font.ITALIC, 18));
		lblSuccess.setBounds(257, 154, 105, 23);
		contentPane.add(lblSuccess);
	}
}

package rab.local;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class front extends JFrame {

	private JPanel contentPane;
	private JTextField Y;
	private JTextField Z;
	private JTextField X;
	private JTextField Motorspeed;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel Baserotation;
	private JLabel Angel1;
	private JLabel Angel2;
	private JLabel Angel3;
	private JLabel Effector;
	private JTextField Akkulevel1;
	private JTextField Akkulevel2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					front frame = new front();
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
	public front() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 551);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("RAB project \"ebe\"");
		label.setBounds(10, 11, 152, 31);
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 14));
		contentPane.add(label);
		
		Y = new JTextField();
		Y.setBounds(70, 163, 106, 20);
		contentPane.add(Y);
		Y.setColumns(10);
		
		Z = new JTextField();
		Z.setBounds(70, 356, 106, 20);
		contentPane.add(Z);
		Z.setColumns(10);
		
		X = new JTextField();
		X.setBounds(411, 356, 106, 20);
		contentPane.add(X);
		X.setColumns(10);
		
		JLabel Ycoordinate = new JLabel("Y-Coordinate");
		Ycoordinate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Ycoordinate.setForeground(Color.GREEN);
		Ycoordinate.setBounds(70, 132, 106, 20);
		contentPane.add(Ycoordinate);
		
		JLabel Zcoordinates = new JLabel("Z-Coordinate");
		Zcoordinates.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Zcoordinates.setForeground(Color.GREEN);
		Zcoordinates.setBounds(70, 331, 106, 14);
		contentPane.add(Zcoordinates);
		
		JLabel Xcoordinates = new JLabel("X-Coordinate");
		Xcoordinates.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Xcoordinates.setForeground(Color.GREEN);
		Xcoordinates.setBounds(411, 331, 106, 14);
		contentPane.add(Xcoordinates);
		
		JButton btnNewButton = new JButton("Run");
		btnNewButton.setBounds(651, 460, 91, 23);
		contentPane.add(btnNewButton);
		
		JLabel choose = new JLabel("choose the position");
		choose.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 16));
		choose.setForeground(Color.CYAN);
		choose.setBounds(356, 14, 161, 23);
		contentPane.add(choose);
		
		JLabel Motorcontrol = new JLabel("Motorcontrol Speed");
		Motorcontrol.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Motorcontrol.setForeground(Color.RED);
		Motorcontrol.setBounds(411, 135, 106, 14);
		contentPane.add(Motorcontrol);
		
		Motorspeed = new JTextField();
		Motorspeed.setEditable(false);
		Motorspeed.setBounds(411, 163, 106, 20);
		contentPane.add(Motorspeed);
		Motorspeed.setColumns(10);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(411, 194, 106, 20);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(411, 221, 106, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(411, 247, 106, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(411, 276, 106, 20);
		contentPane.add(textField_3);
		
		Baserotation = new JLabel("Baserotation:");
		Baserotation.setForeground(Color.ORANGE);
		Baserotation.setBounds(322, 166, 79, 14);
		contentPane.add(Baserotation);
		
		Angel1 = new JLabel("Angel 1:");
		Angel1.setForeground(Color.ORANGE);
		Angel1.setBounds(322, 197, 79, 14);
		contentPane.add(Angel1);
		
		Angel2 = new JLabel("Angel 2:");
		Angel2.setForeground(Color.ORANGE);
		Angel2.setBounds(322, 224, 48, 14);
		contentPane.add(Angel2);
		
		Angel3 = new JLabel("Angel 3:");
		Angel3.setForeground(Color.ORANGE);
		Angel3.setBounds(322, 250, 48, 14);
		contentPane.add(Angel3);
		
		Effector = new JLabel("Effector:");
		Effector.setForeground(Color.ORANGE);
		Effector.setBounds(322, 279, 48, 14);
		contentPane.add(Effector);
		
		Akkulevel1 = new JTextField();
		Akkulevel1.setEditable(false);
		Akkulevel1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akkulevel1.setForeground(Color.ORANGE);
		Akkulevel1.setBackground(Color.BLACK);
		Akkulevel1.setBounds(651, 163, 91, 20);
		contentPane.add(Akkulevel1);
		Akkulevel1.setColumns(10);
		
		JLabel Akku1 = new JLabel("Akku 1");
		Akku1.setForeground(Color.RED);
		Akku1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akku1.setBounds(651, 136, 91, 14);
		contentPane.add(Akku1);
		
		Akkulevel2 = new JTextField();
		Akkulevel2.setEditable(false);
		Akkulevel2.setForeground(Color.ORANGE);
		Akkulevel2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akkulevel2.setColumns(10);
		Akkulevel2.setBackground(Color.BLACK);
		Akkulevel2.setBounds(651, 224, 91, 20);
		contentPane.add(Akkulevel2);
		
		JLabel Akku = new JLabel("Akku 2");
		Akku.setForeground(Color.RED);
		Akku.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akku.setBounds(651, 197, 91, 14);
		contentPane.add(Akku);
	}
}

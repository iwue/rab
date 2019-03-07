package windows.rab.local;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class Frontpscontroller extends JFrame {

	private JPanel contentPane;
	private JTextField YcoordinatePS;
	private JTextField ZcoordinatePS;
	private JTextField XcoordinatesPS;
	private JTextField Motorspeed;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField Akkulevel1;
	private JTextField Akkulevel2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frontpscontroller frame = new Frontpscontroller();
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
	public Frontpscontroller() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 551);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("RAB project \"ebe\"");
		label.setForeground(Color.CYAN);
		label.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 14));
		label.setBounds(23, 11, 152, 31);
		contentPane.add(label);
		
		JLabel Y = new JLabel("Y-Coordinate");
		Y.setForeground(Color.GREEN);
		Y.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Y.setBounds(54, 108, 87, 16);
		contentPane.add(Y);
		
		YcoordinatePS = new JTextField();
		YcoordinatePS.setEditable(false);
		YcoordinatePS.setColumns(10);
		YcoordinatePS.setBounds(54, 135, 106, 20);
		contentPane.add(YcoordinatePS);
		
		JLabel Z = new JLabel("Z-Coordinate");
		Z.setForeground(Color.GREEN);
		Z.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Z.setBounds(54, 352, 106, 14);
		contentPane.add(Z);
		
		ZcoordinatePS = new JTextField();
		ZcoordinatePS.setEditable(false);
		ZcoordinatePS.setColumns(10);
		ZcoordinatePS.setBounds(54, 377, 106, 20);
		contentPane.add(ZcoordinatePS);
		
		JLabel X = new JLabel("X-Coordinate");
		X.setForeground(Color.GREEN);
		X.setFont(new Font("Tahoma", Font.PLAIN, 12));
		X.setBounds(421, 352, 106, 14);
		contentPane.add(X);
		
		XcoordinatesPS = new JTextField();
		XcoordinatesPS.setEditable(false);
		XcoordinatesPS.setColumns(10);
		XcoordinatesPS.setBounds(421, 377, 106, 20);
		contentPane.add(XcoordinatesPS);
		
		JLabel PScontroll = new JLabel("PS-Controll");
		PScontroll.setForeground(Color.CYAN);
		PScontroll.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 16));
		PScontroll.setBounds(267, 14, 161, 23);
		contentPane.add(PScontroll);
		
		Motorspeed = new JTextField();
		Motorspeed.setEditable(false);
		Motorspeed.setBounds(421, 135, 106, 20);
		contentPane.add(Motorspeed);
		Motorspeed.setColumns(10);
		
		JLabel Motorcontrol = new JLabel("Motorcontrol Speed");
		Motorcontrol.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Motorcontrol.setForeground(Color.RED);
		Motorcontrol.setBounds(421, 109, 106, 14);
		contentPane.add(Motorcontrol);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(421, 166, 106, 20);
		contentPane.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(421, 197, 106, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(421, 228, 106, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(421, 259, 106, 20);
		contentPane.add(textField_3);
		
		JLabel Baserotation = new JLabel("Baserotation:");
		Baserotation.setForeground(Color.ORANGE);
		Baserotation.setBounds(324, 138, 87, 14);
		contentPane.add(Baserotation);
		
		JLabel lblAngel = new JLabel("Angel 1:");
		lblAngel.setForeground(Color.ORANGE);
		lblAngel.setBounds(324, 169, 87, 14);
		contentPane.add(lblAngel);
		
		JLabel lblAngel_1 = new JLabel("Angel 2:");
		lblAngel_1.setForeground(Color.ORANGE);
		lblAngel_1.setBounds(324, 200, 87, 14);
		contentPane.add(lblAngel_1);
		
		JLabel lblAngel_2 = new JLabel("Angel 3:");
		lblAngel_2.setForeground(Color.ORANGE);
		lblAngel_2.setBounds(324, 231, 87, 14);
		contentPane.add(lblAngel_2);
		
		JLabel lblEffector = new JLabel("Effector:");
		lblEffector.setForeground(Color.ORANGE);
		lblEffector.setBounds(324, 262, 87, 14);
		contentPane.add(lblEffector);
		
		JLabel Akku1 = new JLabel("Akku 1");
		Akku1.setForeground(Color.RED);
		Akku1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akku1.setBounds(636, 110, 91, 14);
		contentPane.add(Akku1);
		
		JLabel Akku2 = new JLabel("Akku 2");
		Akku2.setForeground(Color.RED);
		Akku2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akku2.setBounds(636, 169, 91, 14);
		contentPane.add(Akku2);
		
		Akkulevel1 = new JTextField();
		Akkulevel1.setForeground(Color.ORANGE);
		Akkulevel1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akkulevel1.setBackground(Color.BLACK);
		Akkulevel1.setEditable(false);
		Akkulevel1.setColumns(10);
		Akkulevel1.setBounds(636, 135, 106, 20);
		contentPane.add(Akkulevel1);
		
		Akkulevel2 = new JTextField();
		Akkulevel2.setBackground(new Color(0, 0, 0));
		Akkulevel2.setForeground(Color.ORANGE);
		Akkulevel2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Akkulevel2.setEditable(false);
		Akkulevel2.setColumns(10);
		Akkulevel2.setBounds(636, 197, 106, 20);
		contentPane.add(Akkulevel2);
	}

}

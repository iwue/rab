package rab.local;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;

import controller.rab.local.MainController;
import controller.rab.local.MoveController;

import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;

public class InformationWindows {

	private JFrame frame;
	private MoveController moveController;
	private MainController controller = new MainController();
	
	// Startposition des TCP
	private double currentX = 100;
	private double currentY = 0;
	private double currentZ = 535;
	
	// Stopbereich von Joystick
	private double joystickStopRange = 0.1;
	
	// Maximale Zunahme der Geschwindigkeit auf dem Koordinatensystem
	private double coordinateMaxSpeed = 25;
	
	
	// Interval für die Geschwindidkeit
	private double interval = 6; // in s
	private JPasswordField passwordField;
	private JTextField Benutzer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformationWindows window = new InformationWindows();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public InformationWindows() {
		// Controller für die Steuerung der Bewegung
		moveController = new MoveController(currentX, currentY, currentZ, interval);
		
		try {
			moveController.setSpeedForAllAngles(200, 200, 300);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(100, 100, 200);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(200, 100, 250);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(100, 0, 535);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(-100, -150, 150);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(-150, -200, 300);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.setSpeedForAllAngles(100, 0, 535);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * interval));
			moveController.stopAllAngels();
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*boolean run = true;
		while(run) {
			move();
			
			if(MainController.getDualshockSimple().isPressedActionCross()) {
				break;
			}
		}*/

		MainController.closeHings();
		System.exit(0);
	}
	
	/**
	 * Umrechnung von Koordinatensystem zu Roboterachsen und Ausführen der Bewegung.
	 */
	public void move() {		
		// Dualstock Stickwerte 
		double joystickCurrentX = MainController.getDualshockSimple().getLeftStickX();
		double joystickCurrentY = MainController.getDualshockSimple().getLeftStickY();
		double joystickCurrentZ = MainController.getDualshockSimple().getRightStickY();
		
		
		System.out.println("X: " + moveController.getCurrentX()
						+ ", Y: " + moveController.getCurrentY()
						+ ", Z: " + moveController.getCurrentZ());
		
		// Prüfen ob die Sticks sich im Stopbereich befinden
		if (joystickCurrentX > (joystickStopRange * -1)
		&& joystickCurrentX < joystickStopRange
		&& joystickCurrentY > (joystickStopRange * -1)
		&& joystickCurrentY < joystickStopRange
		&& joystickCurrentZ > (joystickStopRange * -1)
		&& joystickCurrentZ < joystickStopRange) {
			
			moveController.stopAllAngels();
		
		} else {
			double moveX = coordinateMaxSpeed * joystickCurrentX;
			double moveY = coordinateMaxSpeed * joystickCurrentY;
			double moveZ = coordinateMaxSpeed * joystickCurrentZ;
			
			double newX = moveController.getCurrentX() + moveX;
			double newY = moveController.getCurrentY() + moveY;
			double newZ = moveController.getCurrentZ() + moveZ;
			
			System.out.println("X: " + newX 
							+ ", Y: " + newY
							+ ", Z: " + newZ);
			
			moveController.setSpeedForAllAngles(newX, newY, newZ);
			moveController.goAllAngels();
			
			try {
				Thread.sleep((int) (1000 * interval));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.getContentPane().setForeground(Color.GREEN);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.GRAY);
		passwordField.setBounds(142, 161, 138, 20);
		frame.getContentPane().add(passwordField);
		
		Benutzer = new JTextField();
		Benutzer.setBackground(Color.GRAY);
		Benutzer.setBounds(142, 130, 138, 20);
		frame.getContentPane().add(Benutzer);
		Benutzer.setColumns(10);
		
		JLabel Benutzername = new JLabel("Benutzername:");
		Benutzername.setForeground(Color.GREEN);
		Benutzername.setBackground(Color.BLACK);
		Benutzername.setBounds(56, 133, 76, 14);
		frame.getContentPane().add(Benutzername);
		
		JLabel Passwort = new JLabel("Passwort:");
		Passwort.setForeground(Color.GREEN);
		Passwort.setBounds(56, 164, 48, 14);
		frame.getContentPane().add(Passwort);
		
		JButton Weiter = new JButton("Weiter");
		Weiter.setBounds(314, 218, 91, 23);
		frame.getContentPane().add(Weiter);
		
		JLabel lblNewLabel = new JLabel("RAB project \"ebe\"");
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setBounds(10, 11, 152, 31);
		frame.getContentPane().add(lblNewLabel);
	}
}

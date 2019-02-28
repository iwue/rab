package rab.local;

import java.awt.EventQueue;
import javax.swing.JFrame;
import controller.rab.local.MainController;
import controller.rab.local.MoveController;

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
	private double currentX = 304;
	private double currentY = 0;
	private double currentZ = 428;
	
	// Stopbereich von Joystick
	private double joystickStopRange = 1;
	
	// Maximale Zunahme der Geschwindigkeit auf dem Koordinatensystem
	private double coordinateMaxSpeed = 25;
	
	
	private JPasswordField passwordField;
	private JTextField Benutzer;

	// Interval für die Geschwindidkeit
	private double intervall = 2; // in s
	
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
		moveController = new MoveController(currentX, currentY, currentZ, intervall);
		
		try {			
			moveController.setSpeedForAllAngles(0, 304, 428);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();

			moveController.setSpeedForAllAngles(350, 0, 300);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();
			
			moveController.setSpeedForAllAngles(0, 350, 300);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();
			
			moveController.setSpeedForAllAngles(100, 0, 535);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();
			
			moveController.setSpeedForAllAngles(-100, -150, 150);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();

			moveController.setSpeedForAllAngles(-150, -200, 300);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
			moveController.stopAllAngels();
			
			moveController.setSpeedForAllAngles(100, 0, 535);
			moveController.goAllAngels();
			Thread.sleep((int)(1000 * intervall));
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
		// Auslesen der X-Richtung auf dem Joystick
		double joystickCurrentX = MainController.getDualshockSimple().getLeftStickX();
		// Auslesen der Y-Richtung auf dem Joystick
		double joystickCurrentY = MainController.getDualshockSimple().getLeftStickY();
		// Auslesen der Z-Richtung auf dem Joystick
		double joystickCurrentZ = MainController.getDualshockSimple().getRightStickY();
		
		// Ausgabe auf CLI
		System.out.println("X: " + moveController.getCurrentX()
						+ ", Y: " + moveController.getCurrentY()
						+ ", Z: " + moveController.getCurrentZ());
		
		
		// Prüfen ob die Joystickdaten von X, Y und Z sich im Stoppbereich befinden,
		// weil sich diese Signale nicht genau am 0 Punkt sind
		if (joystickCurrentX > (joystickStopRange * -1)
				&& joystickCurrentX < joystickStopRange
				&& joystickCurrentY > (joystickStopRange * -1)
				&& joystickCurrentY < joystickStopRange
				&& joystickCurrentZ > (joystickStopRange * -1)
				&& joystickCurrentZ < joystickStopRange) {
			
			// Falls sich alle im Stoppbereich befinden,
			// werden alle Motoren gestoppt
			moveController.stopAllAngels();
		
		} else {
			// Berechnung der Bewegung in X-Richtung
			double moveX = coordinateMaxSpeed * joystickCurrentX;
			// Berechnung der Bewegung in Y-Richtung
			double moveY = coordinateMaxSpeed * joystickCurrentY;
			// Berechnung der Bewegung in Z-Richtung
			double moveZ = coordinateMaxSpeed * joystickCurrentZ;
			
			// Addieren der Bewegung zur Akuellen Position
			// Es ensteht die neue Position
			double newX = moveController.getCurrentX() + moveX;
			double newY = moveController.getCurrentY() + moveY;
			double newZ = moveController.getCurrentZ() + moveZ;
			
			// Ausgabe in CLI
			System.out.println("X: " + newX 
							+ ", Y: " + newY
							+ ", Z: " + newZ);
			
			// Festlegen der Geschwindigkeit für die Achsen
			moveController.setSpeedForAllAngles(newX, newY, newZ);
			// Alle Motoren starten
			moveController.goAllAngels();
			
			try {
				// Programm pausieren für Bewegung
				Thread.sleep((int) (1000 * intervall));
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
		frame.setBounds(100, 100, 800, 551);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(Color.GRAY);
		passwordField.setBounds(311, 279, 138, 20);
		frame.getContentPane().add(passwordField);
		
		Benutzer = new JTextField();
		Benutzer.setBackground(Color.GRAY);
		Benutzer.setBounds(311, 215, 138, 20);
		frame.getContentPane().add(Benutzer);
		Benutzer.setColumns(10);
		
		JLabel Benutzername = new JLabel("Benutzername:");
		Benutzername.setForeground(Color.GREEN);
		Benutzername.setBackground(Color.BLACK);
		Benutzername.setBounds(210, 218, 76, 14);
		frame.getContentPane().add(Benutzername);
		
		JLabel Passwort = new JLabel("Passwort:");
		Passwort.setForeground(Color.GREEN);
		Passwort.setBounds(210, 282, 48, 14);
		frame.getContentPane().add(Passwort);
		
		JButton Weiter = new JButton("Weiter");
		Weiter.setBounds(640, 461, 91, 23);
		frame.getContentPane().add(Weiter);
		
		JLabel lblNewLabel = new JLabel("RAB project \"ebe\"");
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setForeground(Color.CYAN);
		lblNewLabel.setBounds(10, 11, 152, 31);
		frame.getContentPane().add(lblNewLabel);
	}
}

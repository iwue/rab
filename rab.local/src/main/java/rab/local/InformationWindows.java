package rab.local;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;

import controller.rab.local.MainController;
import controller.rab.local.MoveController;

import java.awt.BorderLayout;

public class InformationWindows {

	private JFrame frame;
	private JTable table;
	private MoveController moveController;
	private MainController controller = new MainController();
	
	// Startposition des Effektors
	private double currentX = 0;
	private double currentY = 150;
	private double currentZ = 235;
	
	// Stopbereich von Joystick
	private double joystickStopRange = 0.1;
	
	// Maximale Zunahme der Geschwindigkeit auf dem Koordinatensystem
	private double coordinateMaxSpeed = 25;
	
	// Interval für die Geschwindidkeit
	private double interval = 0.25; // in s
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
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
		
		
		boolean run = true;
		while(run) {
			move();
			
			if(MainController.getDualshockSimple().isPressedActionCross()) {
				break;
			}
		}

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
		&& joystickCurrentZ > (joystickStopRange * -1)) {
			
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
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table = new JTable();
		frame.getContentPane().add(table, BorderLayout.CENTER);
	}

}

package rab.local;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;

import controller.rab.local.MoveController;

import java.awt.BorderLayout;

public class InformationWindows {

	private JFrame frame;
	private JTable table;
	private MoveController moveController;
	private Controller controller = new Controller();
	
	private double currentX = 0;
	private double currentY = 150;
	private double currentZ = 235;
	
	private double joystickStopRange = 0.1;
	private double coordinateMaxSpeed = 50;
	private double interval = 1; // in s
	
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
		moveController = new MoveController(currentX, currentY, currentZ, interval);
		
		int i = 1;
		while(i == 1) {
			try {
				moveController.setAll(150, 0, 235);
				moveController.goAll();
				Thread.sleep((int) (1000 * interval));
				moveController.setAll(0, 150, 235);
				moveController.goAll();
				Thread.sleep((int) (1000 * interval));
				moveController.stopAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		boolean run = true;
		while(run) {
			try {
				move();
				Thread.sleep((int) (1000 * interval));
				
				if(Controller.getDualshockSimple().isPressedActionCross()) {
					Controller.closeHings();
					break;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		Controller.closeHings();
		System.exit(0);
	}
	
	public void move() {		
		double joystickCurrentX = Controller.getDualshockSimple().getLeftStickX();
		double joystickCurrentY = Controller.getDualshockSimple().getLeftStickY();
		double joystickCurrentZ = Controller.getDualshockSimple().getRightStickY();
		
		if (joystickCurrentX > (joystickStopRange * -1)
				&& joystickCurrentX < joystickStopRange
				&& joystickCurrentY > (joystickStopRange * -1)
				&& joystickCurrentY < joystickStopRange
				&& joystickCurrentZ > (joystickStopRange * -1)) {
			moveController.stopAll();
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
			
			moveController.setAll(newX, newY, newZ);
			moveController.goAll();
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

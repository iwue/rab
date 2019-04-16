package local.rab.windows;

import java.awt.EventQueue;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import local.rab.config.Statics;
import local.rab.controller.MoveSimpleController;
import local.rab.controller.RabMainController;
import local.rab.controller.threads.ThreadEffector;
import local.rab.controller.threads.ThreadTheta4;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.brick.BrickMoveController;
import local.rab.devices.dualshock.DualshockController;
import local.rab.devices.dualshock.DualshockSimple;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;

public class InformationWindows {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField Benutzer;
	private RabMainController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickComponentHandler brickController;
	private BrickMoveController moveBrickController;
	private MoveSimpleController moveSimple;
	private Thread tAutoTheta4;
	private Thread tEffector;
	
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
		try {
			Statics.initProperties();
			dualshockController = new DualshockController(0);
			dualshockSimple = new DualshockSimple(dualshockController.getController());
			brickController = new BrickComponentHandler();
			moveBrickController = new BrickMoveController(Statics.getStartX(), Statics.getStartY(), Statics.getStartZ(), brickController);
			rabController = new RabMainController(dualshockSimple, moveBrickController);
			moveSimple = new MoveSimpleController(dualshockSimple, brickController);
			
			if (Statics.isTheta4Automatic()) {
				tAutoTheta4 = new Thread(new ThreadTheta4(moveBrickController.getBrickController()));
				tAutoTheta4.setName("Effector Correction");
				tAutoTheta4.start();
			}
			
			tEffector = new Thread(new ThreadEffector(dualshockSimple, brickController));
			tEffector.setName("Effector control");
			tEffector.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//moveBrickController.goTo(304, 0, 428, 5);
		//moveBrickController.goTo(304, 0, 300, 5);
		//moveBrickController.goTo(304, 0, 428, 5);
		
		if (Statics.getMode() == 1) {
			while(true) {
				rabController.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
			}
		} else if (Statics.getMode() == 2){
			while(true) {
				moveSimple.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
			}
		}

		rabController.getMoveBrickController().getBrickController().closeHings();;
		System.exit(0);
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

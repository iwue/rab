package windows.rab.local;

import java.awt.EventQueue;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import brick.rab.local.BrickController;
import brick.rab.local.MoveBrickController;
import controller.rab.local.MoveSimple;
import controller.rab.local.RabController;
import dualshock.rab.local.DualshockController;
import dualshock.rab.local.DualshockSimple;
import rab.local.RabStatics;
import threads.rab.local.ThreadEffector;
import threads.rab.local.ThreadGyros;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.io.File;

public class InformationWindows {

	private JFrame frame;
	private JPasswordField passwordField;
	private JTextField Benutzer;
	private RabController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickController brickController;
	private MoveBrickController moveBrickController;
	private MoveSimple moveSimple;
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
			RabStatics.initProperties();
			dualshockController = new DualshockController(0);
			dualshockSimple = new DualshockSimple(dualshockController.getController());
			brickController = new BrickController();
			moveBrickController = new MoveBrickController(RabStatics.getStartX(), RabStatics.getStartY(), RabStatics.getStartZ(), brickController);
			rabController = new RabController(dualshockSimple, moveBrickController);
			moveSimple = new MoveSimple(dualshockSimple, brickController, moveBrickController);
			
			if (RabStatics.isTheta4Automatic()) {
				tAutoTheta4 = new Thread(new ThreadGyros(moveBrickController.getBrickController()));
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
		
		if (RabStatics.getMode() == 1) {
			while(true) {
				rabController.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
			}
		} else if (RabStatics.getMode() == 2){
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

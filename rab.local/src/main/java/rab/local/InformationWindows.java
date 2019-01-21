package rab.local;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.BorderLayout;

public class InformationWindows {

	private JFrame frame;
	private JTable table;

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
		initialize();
			
		
		while(true) {
			try {
				Controller controller = new Controller();
				Controller.getHingA1().setSpeed(900);
				Controller.getHingA1().forward();
				Thread.sleep(5000);
				Controller.getHingA1().stop(true);
				Controller.closeHings();
			} catch (Exception e) {
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

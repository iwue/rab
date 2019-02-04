package controller.rab.local;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.remote.ev3.RMIRegulatedMotor;

public class MainController {
	private static Logger logger = LogManager.getLogger(MainController.class);
	
	private DualshockController dualshock;
	private static DualshockSimple dualshockSimple;
	
	private BrickController brickLeft;
	private BrickController brickRight;
	
	// Achsenmotoren
	private static RMIRegulatedMotor hingA1;
	private static RMIRegulatedMotor hingA11;
	private static RMIRegulatedMotor hingA2;
	private static RMIRegulatedMotor hingA3;
	private static RMIRegulatedMotor hingRotation;
	private static RMIRegulatedMotor effector;
	
	private static int maxAcceleration		= 6000;
	
	private String iPBrickLeft 			= "192.168.0.20";
	private String iPBrickRight 		= "192.168.0.10";
	private int dualshockID				= 0;
	
	/**
	 * Instanzieren des Objekts
	 */
	public MainController() {
		initialize();
		setupHings();
	}
	
	/**
	 * Vebinden mit dem Joystick und Bricks
	 */
	private void initialize() {
		try {
			// Verbinung zum Dualshock
			dualshock 			= new DualshockController(dualshockID);
			dualshockSimple		= new DualshockSimple(dualshock.getController());
		} catch(Exception e) {
			logger.error(e);
		}
		
		try {
			// Verbindung zu linkem Brick
			brickLeft 			= new BrickController(iPBrickLeft);
			brickLeft.connect();			
		} catch (Exception e) {
			logger.error(e);
		}
		
		try {
			// Verbindung zu rechem Brick
			brickRight 			= new BrickController(iPBrickRight);
			brickRight.connect();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Datenstream öffnen und konfigurieren der Motoren
	 */
	private void setupHings(){
		try {
			// Erster Achsenmotor mit Port A konfigurieren
			hingA1 			= brickRight.getBrick().createRegulatedMotor("A", 'L');
			hingA11 		= brickRight.getBrick().createRegulatedMotor("B", 'L');
			// Zweite Achsenmotor mit Port B konfigurieren
			hingA2 			= brickLeft.getBrick().createRegulatedMotor("C", 'L');
			// Dritte Achsenmotor mit Port C konfigurieren
			hingA3 			= brickLeft.getBrick().createRegulatedMotor("B", 'M');
			// Rotationsmotor mit Port D konfigurieren
			hingRotation 	= brickRight.getBrick().createRegulatedMotor("C", 'L');
			// Effektor
			effector 		= brickLeft.getBrick().createRegulatedMotor("A", 'M');
			
			// Setzen der Beschleunigung für alle Motoren
			hingA1.setAcceleration(maxAcceleration);
			hingA11.setAcceleration(maxAcceleration);
			hingA2.setAcceleration(maxAcceleration);
			hingA3.setAcceleration(maxAcceleration);
			hingRotation.setAcceleration(maxAcceleration);
			effector.setAcceleration(maxAcceleration);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Verbindung zu allen Motoren schliessen
	 */
	public static void closeHings() {
		try {
			hingA1.close();
			hingA11.close();
			hingA2.close();
			hingA3.close();
			hingRotation.close();
			effector.close();
		} catch(RemoteException e) {
			logger.error(e);
		}
	}

	public static DualshockSimple getDualshockSimple() {
		return dualshockSimple;
	}

	public static RMIRegulatedMotor getHingA1() {
		return hingA1;
	}
	
	public static RMIRegulatedMotor getHingA11() {
		return hingA11;
	}

	public static RMIRegulatedMotor getHingA2() {
		return hingA2;
	}

	public static RMIRegulatedMotor getHingA3() {
		return hingA3;
	}

	public static RMIRegulatedMotor getHingRotation() {
		return hingRotation;
	}

	public static RMIRegulatedMotor getEffector() {
		return effector;
	}
}

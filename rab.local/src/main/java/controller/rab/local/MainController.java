package controller.rab.local;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.hardware.sensor.EV3GyroSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import lejos.robotics.Gyroscope;
import sun.rmi.runtime.NewThreadAction;
import threads.rab.local.threadCoordinateInformations;
import threads.rab.local.threadMotorInformation;
import threads.rab.local.threadPower;

public class MainController {
	private static Logger logger = LogManager.getLogger(MainController.class);
	
	private DualshockController dualshock;
	private static DualshockSimple dualshockSimple;
	
	private static BrickController brickLeft;
	private static BrickController brickRight;
	
	private static RMIRegulatedMotor hingTheta1;

	// Achsenmotoren
	private static RMIRegulatedMotor hingTheta20;
	private static RMIRegulatedMotor hingTheta21;
	private static RMIRegulatedMotor hingTheta3;
	private static RMIRegulatedMotor hingTheta4;
	private static RMIRegulatedMotor effector;
	
	// Sensoren
	private static RMISampleProvider sampleGyros;
	
	private static int maxAcceleration		= 6000;
	
	private String iPBrickLeft 			= "192.168.0.10";
	private String iPBrickRight 		= "192.168.0.20";
	private int dualshockID				= 0;
	
	// Threads
	private Thread tPower;
	private Thread tBrickInfo;
	private Thread tMotorInfo;
	
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
		
		
		// Setup für die Ausgabe
		tBrickInfo = new Thread(new threadCoordinateInformations());
		tBrickInfo.setName("Koordinaten");
		tBrickInfo.start();
		
		tPower = new Thread(new threadPower());
		tPower.setName("Brick Power");
		//tPower.start();
		
		tMotorInfo = new Thread(new threadMotorInformation());
		tMotorInfo.setName("Motor Information");
		//tMotorInfo.start();
	}
	
	/**
	 * Datenstream öffnen und konfigurieren der Motoren
	 */
	private void setupHings(){
		try {
			// Erster Achsenmotor mit Port A und B konfigurieren
			hingTheta20 		= brickRight.getBrick().createRegulatedMotor("A", 'L');
			hingTheta21 		= brickRight.getBrick().createRegulatedMotor("B", 'L');
			// Zweite Achsenmotor mit Port C konfigurieren
			hingTheta3 			= brickLeft.getBrick().createRegulatedMotor("C", 'L');
			// Dritte Achsenmotor mit Port C konfigurieren
			hingTheta4 			= brickLeft.getBrick().createRegulatedMotor("B", 'M');
			// Rotationsmotor mit Port D konfigurieren
			hingTheta1 			= brickRight.getBrick().createRegulatedMotor("C", 'L');
			// Effektor
			effector 			= brickLeft.getBrick().createRegulatedMotor("A", 'M');
			
			// Setzen der Beschleunigung für alle Motoren
			hingTheta20.setAcceleration(maxAcceleration);
			hingTheta21.setAcceleration(maxAcceleration);
			hingTheta3.setAcceleration(maxAcceleration);
			hingTheta4.setAcceleration(maxAcceleration);
			hingTheta1.setAcceleration(maxAcceleration);
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
			hingTheta20.close();
			hingTheta21.close();
			hingTheta3.close();
			hingTheta4.close();
			hingTheta1.close();
			effector.close();
		} catch(RemoteException e) {
			logger.error(e);
		}
	}
	
	public void setupsSensors() {
		sampleGyros = brickLeft.getBrick().createSampleProvider("1", "EV3GyroSensor", "Rate and Angle"); 
	}

	public static DualshockSimple getDualshockSimple() {
		return dualshockSimple;
	}

	public static RMIRegulatedMotor getHingTheta1() {
		return hingTheta1;
	}

	public static RMIRegulatedMotor getHingTheta20() {
		return hingTheta20;
	}
	
	public static RMIRegulatedMotor getHingTheta21() {
		return hingTheta21;
	}

	public static RMIRegulatedMotor getHingTheta3() {
		return hingTheta3;
	}

	public static RMIRegulatedMotor getHingTheta4() {
		return hingTheta4;
	}

	public static RMIRegulatedMotor getEffector() {
		return effector;
	}

	public static RMISampleProvider getSampleGyros() {
		return sampleGyros;
	}
	
	public static BrickController getBrickLeft() {
		return brickLeft;
	}

	public static BrickController getBrickRight() {
		return brickRight;
	}
}

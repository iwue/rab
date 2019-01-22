package rab.local;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.rab.local.BrickController;
import controller.rab.local.DualshockController;
import controller.rab.local.DualshockSimple;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Controller {
	private static Logger logger = LogManager.getLogger(Controller.class);
	
	private DualshockController dualshock;
	private static DualshockSimple dualshockSimple;
	
	private BrickController brickLeft;
	private BrickController brickRight;
	
	private static RMIRegulatedMotor hingA1;
	private static RMIRegulatedMotor hingA2;
	private static RMIRegulatedMotor hingA3;
	private static RMIRegulatedMotor hingRotation;
	
	private static int acceleration		= 2500;
	
	private String iPBrickLeft 			= "192.168.0.10";
	private String iPBrickRight 		= "192.168.0.20";
	private int dualshockID				= 0;
	
	public Controller() {
		init();
		setupHings();
	}
	
	private void init() {
		try {
			// Verbinung zum Dualshock
			dualshock 			= new DualshockController(dualshockID);
			dualshockSimple		= new DualshockSimple(dualshock.getController());
		} catch(Exception e) {
			logger.error(e);
		}
		
		try {
			// Verbindung zu linken Brick
			brickLeft 			= new BrickController(iPBrickLeft);
			brickLeft.connect();			
		} catch (Exception e) {
			logger.error(e);
		}
		
		try {
			// Verbindung zu rechen Brick
			//brickRight 			= new BrickController(iPBrickRight);
			//brickRight.connect();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private void setupHings(){
		try {
			// Erste Achse
			hingA1 			= brickLeft.getBrick().createRegulatedMotor("A", 'L');
			hingA2 			= brickLeft.getBrick().createRegulatedMotor("B", 'L');
			hingA3 			= brickLeft.getBrick().createRegulatedMotor("C", 'L');
			hingRotation 	= brickLeft.getBrick().createRegulatedMotor("D", 'L');
			
			// Setzen der Beschleunigung für alle Achsen/Motoren
			hingA1.setAcceleration(acceleration);
			hingA2.setAcceleration(acceleration);
			hingA3.setAcceleration(acceleration);
			hingRotation.setAcceleration(acceleration);
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public static void closeHings() {
		try {
			hingA1.close();
			hingA2.close();
			hingA3.close();
			hingRotation.close();
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

	public static RMIRegulatedMotor getHingA2() {
		return hingA2;
	}

	public static RMIRegulatedMotor getHingA3() {
		return hingA3;
	}

	public static RMIRegulatedMotor getHingRotation() {
		return hingRotation;
	}
}

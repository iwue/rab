package controller.rab.local;

import java.rmi.RemoteException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class BrickController {

	private RemoteEV3 brick;
	private String ip;
	private static Logger logger 		= LogManager.getLogger(BrickController.class);
	private static double stopFactor 	= 0.1;
	
	public BrickController(String ip) throws Exception{
		this.ip = ip;
		
		this.connect();
	}
	
	/**
	 * Verbinden mit dem EV3
	 *
	 * @throws Exception 
	 */
	public void connect() throws Exception{
		try {
			brick = new RemoteEV3(this.ip);
			brick.isLocal();
		} catch (Exception e) {
			logger.error("Fehler beim verbinden.");
			e.printStackTrace();
			throw e;
		}
	}

	public static void hingDirectionController(RMIRegulatedMotor hing, double speed){		
		
		double speedStopFactorNegative = speed * stopFactor * -1;
		double speedStopFactorPositive = speed * stopFactor;
		
		try {
			if (speed >  speedStopFactorNegative && speed < speedStopFactorPositive) {
				logger.info(hing.toString() + "\tstop:\t" + speed);
				hing.stop(true);
			} else if (speed > speedStopFactorNegative) {
					logger.info(hing.toString() + "\tbackwards:\t" + speed);
					hing.forward();
					hing.setSpeed((int) speed);
			} else {
					logger.info(hing.toString() + "\tforward:\t" + speed);
					hing.backward();
					hing.setSpeed((int) speed);
			}
		} catch (RemoteException e) {
			logger.error(e);
		}
	}

	public RemoteEV3 getBrick() {
		return brick;
	}

	public static double getStopFactor() {
		return stopFactor;
	}

	public static void setStopFactor(double stopFactor) {
		BrickController.stopFactor = stopFactor;
	}
}

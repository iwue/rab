package controller.rab.local;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.ev3.*;
import lejos.remote.ev3.RemoteEV3;

public class EvController {

	private RemoteEV3 ev3;	
	private static Logger logger = LogManager.getLogger(EvController.class);

	
	/**
	 * Verbinden mit dem EV3
	 *
	 * @throws Exception 
	 */
	public void connectToHardware() throws Exception{
		try {
			ev3 = new RemoteEV3("10.0.1.1");
			ev3.isLocal();
		} catch (Exception e) {
			logger.error("Fehler beim verbinden.");
			logger.error(e);
			throw e;
		}
	}


	public RemoteEV3 getEv3() {
		return ev3;
	}


	public void setEv3(RemoteEV3 ev3) {
		this.ev3 = ev3;
	}
}

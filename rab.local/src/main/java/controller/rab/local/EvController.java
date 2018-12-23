package controller.rab.local;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;

public class EvController {

	private EV3 ev3;	
	private static Logger logger = LogManager.getLogger(EvController.class);

	
	/**
	 * Verbinden mit dem EV3
	 *
	 * @throws Exception 
	 */
	public void connectToHardware() throws Exception{
		
		try {
			ev3 = (EV3) BrickFinder.getLocal();
		} catch (Exception e) {
			logger.error("Fehler beim verbinden.");
			logger.error(e);
			throw e;
		}
	}


	public EV3 getEv3() {
		return ev3;
	}


	public void setEv3(EV3 ev3) {
		this.ev3 = ev3;
	}
}

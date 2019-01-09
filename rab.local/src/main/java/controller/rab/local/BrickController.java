package controller.rab.local;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.ev3.*;
import lejos.remote.ev3.RemoteEV3;

public class BrickController {

	private RemoteEV3 brick;
	private String ip;
	private static Logger logger = LogManager.getLogger(BrickController.class);
	
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


	public RemoteEV3 getBrick() {
		return brick;
	}
}

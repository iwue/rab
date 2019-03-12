package brick.rab.local;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lejos.remote.ev3.RemoteEV3;

public class BrickConnectionHandler {

	private RemoteEV3 brick;
	private String ip;
	private static Logger logger = LogManager.getLogger(BrickConnectionHandler.class);
	
	/**
	 * Erstellen die Verindung zum EV3 Brick
	 * 
	 * @param IP-Adresse des Bricks
	 * @throws Exception
	 */
	public BrickConnectionHandler(String ip) throws Exception{
		this.ip = ip;
		
		this.connect();
	}
	
	/**
	 * Verbinden mit Brick
	 *
	 * @throws Exception 
	 */
	private void connect() throws Exception{
		try {
			brick = new RemoteEV3(this.ip);
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

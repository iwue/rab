package brick.rab.local;

import java.rmi.RemoteException;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RMISampleProvider;
import rab.local.Statics;

public class BrickComponentHandler {
	private BrickConnectionHandler brickLeft;
	private BrickConnectionHandler brickRight;

	// Achsenmotoren
	private RMIRegulatedMotor hingTheta1;
	private RMIRegulatedMotor hingTheta20;
	private RMIRegulatedMotor hingTheta21;
	private RMIRegulatedMotor hingTheta3;
	private RMIRegulatedMotor hingTheta4;
	private RMIRegulatedMotor effector;

	// Sensoren
	private static RMISampleProvider sampleGyros;

	/**
	 * Instanzieren des Objekts
	 */
	public BrickComponentHandler() {
		initialize();
		setupHings();
		setupsSensors();
	}

	/**
	 * Vebinden mit dem Joystick und Bricks.
	 */
	private void initialize() {
		try {
			// Verbindung zu linkem Brick
			brickLeft = new BrickConnectionHandler(Statics.getiPBrickLeft());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// Verbindung zu rechem Brick
			brickRight = new BrickConnectionHandler(Statics.getiPBrickRight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Datenstream öffnen und konfigurieren der Motoren
	 */
	private void setupHings() {
		try {
			// Erster Achsenmotor mit Port A und B konfigurieren
			hingTheta20 = brickRight.getBrick().createRegulatedMotor("A", 'L');
			hingTheta21 = brickRight.getBrick().createRegulatedMotor("B", 'L');
			// Zweite Achsenmotor mit Port C konfigurieren
			hingTheta3 = brickLeft.getBrick().createRegulatedMotor("C", 'L');
			// Dritte Achsenmotor mit Port C konfigurieren
			hingTheta4 = brickLeft.getBrick().createRegulatedMotor("B", 'M');
			// Rotationsmotor mit Port D konfigurieren
			hingTheta1 = brickRight.getBrick().createRegulatedMotor("C", 'L');
			// Effektor
			effector = brickLeft.getBrick().createRegulatedMotor("A", 'M');

			// Setzen der Beschleunigung für alle Motoren
			hingTheta20.setAcceleration(Statics.getMaxAcceleration());
			hingTheta21.setAcceleration(Statics.getMaxAcceleration());
			hingTheta3.setAcceleration(Statics.getMaxAcceleration());
			hingTheta4.setAcceleration(Statics.getMaxAcceleration());
			hingTheta1.setAcceleration(Statics.getMaxAcceleration());
			effector.setAcceleration(Statics.getMaxAcceleration());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verbindung zu allen Motoren schliessen
	 */
	public void closeHings() {
		try {
			hingTheta20.close();
			hingTheta21.close();
			hingTheta3.close();
			hingTheta4.close();
			hingTheta1.close();
			effector.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Konfigurieren der Motoren
	 */
	public void setupsSensors() {
		sampleGyros = brickLeft.getBrick().createSampleProvider("S1", "lejos.hardware.sensor.EV3GyroSensor", "Angle");
	}

	public RMIRegulatedMotor getHingTheta1() {
		return hingTheta1;
	}

	public RMIRegulatedMotor getHingTheta20() {
		return hingTheta20;
	}

	public RMIRegulatedMotor getHingTheta21() {
		return hingTheta21;
	}

	public RMIRegulatedMotor getHingTheta3() {
		return hingTheta3;
	}

	public RMIRegulatedMotor getHingTheta4() {
		return hingTheta4;
	}

	public RMIRegulatedMotor getEffector() {
		return effector;
	}

	public RMISampleProvider getSampleGyros() {
		return sampleGyros;
	}

	public BrickConnectionHandler getBrickLeft() {
		return brickLeft;
	}

	public BrickConnectionHandler getBrickRight() {
		return brickRight;
	}
}

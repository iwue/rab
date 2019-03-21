package threads.rab.local;

import java.rmi.RemoteException;

import brick.rab.local.BrickComponentHandler;
import calculation.rab.local.CalculationAngels;
import rab.local.Statics;

public class ThreadTheta1 implements Runnable {
	private BrickComponentHandler  brickComponentHandler = null;
	private double speedTheta1WithoutTransmission = 0.0;
	
	public ThreadTheta1(BrickComponentHandler brickComponentHandler) {
		this.brickComponentHandler = brickComponentHandler;
	}
	
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 5;
		
		while(true) {
			try {
					angleMotor = (double) brickComponentHandler.getHingTheta1().getTachoCount() * Statics.getTransmissionTheta1();

					angleCalc = CalculationAngels.calcTheta1(Statics.getCurrentPosition());
					
					if(Math.abs(angleCalc - angleMotor) < toleranz) {
						speed = calcSpeed(angleMotor, angleCalc);
						brickComponentHandler.getHingTheta1().setSpeed((int) Math.abs(speed));
						
						if (speed > 0) {// Wenn die Geschwindigkeit negativ ist
							brickComponentHandler.getHingTheta1().forward();
						} else {
							// Wenn die Geschwindigkeit positiv ist
							brickComponentHandler.getHingTheta1().backward();
						}
					} else {
						brickComponentHandler.getHingTheta1().stop(true);
					}
					
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Setzen der Geschwindigkeit für Theta 1
	 */
	private double calcSpeed(double oldAngle, double newAngle) {
		if (oldAngle < 0) {
			oldAngle = oldAngle + 360;
		}
		
		double diffAngle = oldAngle - newAngle;
		
		// Berechnugn des kürzesten Wegs für Rotation mit Grösse der Bewegung
		if (diffAngle == 0) {
			// keine Rotation
			speedTheta1WithoutTransmission = 0;
		} else if (diffAngle > 0) {
			// Neuer Winkel ist kleiner als alter Winkel
			if (Math.abs(diffAngle - 360) < Math.abs(diffAngle)) {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedTheta1WithoutTransmission = diffAngle - 360;
			} else {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedTheta1WithoutTransmission = diffAngle;
			}
		} else {
			// Neuer Winkel ist grösser als alter Winkel
			if (Math.abs(diffAngle + 360) < Math.abs(diffAngle)) {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedTheta1WithoutTransmission = diffAngle + 360;
			} else {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedTheta1WithoutTransmission = diffAngle;
			}
		}
		
		return (speedTheta1WithoutTransmission / Statics.getTransmissionTheta1()) / Statics.getInterval();
	}
}

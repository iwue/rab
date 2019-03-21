package threads.rab.local;

import java.rmi.RemoteException;

import brick.rab.local.BrickComponentHandler;
import calculation.rab.local.CalculationAngels;
import rab.local.Statics;

public class ThreadTheta2 implements Runnable {
	private BrickComponentHandler  brickComponentHandler = null;
	
	public ThreadTheta2(BrickComponentHandler brickComponentHandler) {
		this.brickComponentHandler = brickComponentHandler;
	}
	
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 5;
		
		while(true) {
			try {
					angleMotor = (double) (brickComponentHandler.getHingTheta20().getTachoCount() * Statics.getTransmissionTheta2()* -1) + 90;

					angleCalc = CalculationAngels.calcTheta2(Statics.getCurrentPosition());
					
					if(Math.abs(angleCalc - angleMotor) < toleranz) {
						speed = calcSpeed(angleMotor, angleCalc);
						brickComponentHandler.getHingTheta20().setSpeed((int) Math.abs(speed));
						brickComponentHandler.getHingTheta21().setSpeed((int) Math.abs(speed));
						
						if (speed > 0) {// Wenn die Geschwindigkeit negativ ist
							brickComponentHandler.getHingTheta20().forward();
							brickComponentHandler.getHingTheta21().forward();
						} else {
							// Wenn die Geschwindigkeit positiv ist
							brickComponentHandler.getHingTheta20().backward();
							brickComponentHandler.getHingTheta21().backward();
						}
					} else {
						brickComponentHandler.getHingTheta20().stop(true);
						brickComponentHandler.getHingTheta21().stop(true);
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
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 2
		return diff / Statics.getTransmissionTheta2() / Statics.getInterval();

	}
}

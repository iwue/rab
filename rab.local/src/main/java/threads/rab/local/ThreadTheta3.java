package threads.rab.local;

import java.rmi.RemoteException;

import brick.rab.local.BrickComponentHandler;
import calculation.rab.local.CalculationAngels;
import rab.local.Statics;

public class ThreadTheta3 implements Runnable {
	private BrickComponentHandler  brickComponentHandler = null;
	
	public ThreadTheta3(BrickComponentHandler brickComponentHandler) {
		this.brickComponentHandler = brickComponentHandler;
	}
	
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 5;
		
		while(true) {
			try {
					angleMotor = (double) (brickComponentHandler.getHingTheta3().getTachoCount() * Statics.getTransmissionTheta3()* -1) - 90;

					angleCalc = CalculationAngels.calcTheta3(Statics.getCurrentPosition());
					
					if(Math.abs(angleCalc - angleMotor) < toleranz) {
						speed = calcSpeed(angleMotor, angleCalc);
						brickComponentHandler.getHingTheta3().setSpeed((int) Math.abs(speed));
						
						if (speed > 0) {// Wenn die Geschwindigkeit negativ ist
							brickComponentHandler.getHingTheta3().forward();
						} else {
							// Wenn die Geschwindigkeit positiv ist
							brickComponentHandler.getHingTheta3().backward();
						}
					} else {
						brickComponentHandler.getHingTheta3().stop(true);
					}
					
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private double calcSpeed(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 3
		return diff / Statics.getTransmissionTheta3() / Statics.getInterval();

	}
}

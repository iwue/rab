package local.rab.controller.threads;

import java.rmi.RemoteException;

import local.rab.config.Statics;
import local.rab.controller.calculation.CalculationAngels;
import local.rab.devices.brick.BrickComponentHandler;

public class ThreadTheta3 implements Runnable {
	private BrickComponentHandler  brickComponentHandler = null;
	
	public ThreadTheta3(BrickComponentHandler brickComponentHandler) {
		this.brickComponentHandler = brickComponentHandler;
	}
	
	@Override
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;
		double minMotor = 80;
		
		while(!Thread.interrupted()) {
			try {
					angleMotor = brickComponentHandler.getHingTheta3().getTachoCount() * Statics.getTransmissionTheta3()* -1 - 90;

					angleCalc = CalculationAngels.calcTheta3(Statics.getNewPosition());
					
					if(Math.abs(calcDiff(angleMotor, angleCalc)) > toleranz) {
						speed = (calcDiff(angleMotor, angleCalc)  / Statics.getTransmissionTheta3()) / Statics.getInterval();
						
						if (speed < minMotor 
								&& speed > 0) {
							
							// Positiv, Minimum
							speed = minMotor;
							
						} else if (speed > -minMotor 
								&& speed < 0) {
							
							// negativ, Minimum
							speed = -minMotor;
						
						} else if (speed > Statics.getMaxSpeedMotor()
								&& speed > 0 ) {
						
							// positiv, Maximum
							speed = Statics.getMaxSpeedMotor();
						
						}  else if (speed < -Statics.getMaxSpeedMotor()
								&& speed < 0 ) {
						
							// negativ, Maximum
							speed = -Statics.getMaxSpeedMotor();
						}
						
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
	
	private double calcDiff(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 3
		return diff;

	}
}

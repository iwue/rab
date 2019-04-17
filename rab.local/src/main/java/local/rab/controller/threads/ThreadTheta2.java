package local.rab.controller.threads;

import java.rmi.RemoteException;

import local.rab.config.Statics;
import local.rab.controller.calculation.CalculationAngels;
import local.rab.devices.brick.BrickComponentHandler;

public class ThreadTheta2 implements Runnable {
	private BrickComponentHandler  brickComponentHandler = null;
	
	public ThreadTheta2(BrickComponentHandler brickComponentHandler) {
		this.brickComponentHandler = brickComponentHandler;
	}
	
	@Override
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;
		
		while(!Thread.interrupted()) {
			try {
					angleMotor = brickComponentHandler.getHingTheta20().getTachoCount() * Statics.getTransmissionTheta2() * -1 + 90;

					angleCalc = CalculationAngels.calcTheta2(Statics.getNewPosition());
					
					if(Math.abs(calcDiff(angleMotor,angleCalc)) > toleranz) {
						
						speed = (calcDiff(angleMotor, angleCalc) / Statics.getTransmissionTheta2()) / Statics.getInterval();
						
						if (speed < Statics.getMinSpeedMotor() 
								&& speed > 0) {
							
							// Positiv, Minimum
							speed = Statics.getMinSpeedMotor();
							
						} else if (speed > -Statics.getMinSpeedMotor() 
								&& speed < 0) {
							
							// negativ, Minimum
							speed = -Statics.getMinSpeedMotor();
						
						} else if (speed > Statics.getMaxSpeedMotor()
								&& speed > 0 ) {
						
							// positiv, Maximum
							speed = Statics.getMaxSpeedMotor();
						
						}  else if (speed < -Statics.getMaxSpeedMotor()
								&& speed < 0 ) {
						
							// negativ, Maximum
							speed = -Statics.getMaxSpeedMotor();
						}
						
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
	private double calcDiff(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 2
		return diff;

	}
}

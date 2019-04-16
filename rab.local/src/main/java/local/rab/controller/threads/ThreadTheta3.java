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
	
	public void run() {
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;
		
		while(true) {
			try {
					angleMotor = (double) (brickComponentHandler.getHingTheta3().getTachoCount() * Statics.getTransmissionTheta3()* -1) - 90;

					angleCalc = CalculationAngels.calcTheta3(Statics.getNewPosition());
					
					System.out.println("Angle Motor:  " + angleMotor + ", Angle Calc: "  + angleCalc );
					
					if((Math.abs(angleCalc) - Math.abs(angleMotor)) > toleranz) {
						speed = calcSpeed(angleMotor, angleCalc);
						
						if (speed < Statics.getMinSpeedMotor() ) {
							speed = Statics.getMinSpeedMotor();
						} else if (speed > Statics.getMaxSpeedOnCoordinateSystem()) {
							speed = Statics.getMaxSpeedOnCoordinateSystem();
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
	
	private double calcSpeed(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 3
		return diff / Statics.getTransmissionTheta3() / Statics.getInterval();

	}
}

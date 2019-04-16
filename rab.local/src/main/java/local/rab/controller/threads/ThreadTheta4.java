package local.rab.controller.threads;

import local.rab.config.Statics;
import local.rab.controller.calculation.CalculationAngels;
import local.rab.devices.brick.BrickComponentHandler;

public class ThreadTheta4 implements Runnable{
	private float toleranz = 4; // in Grad 
	private BrickComponentHandler brickController;
	
	public ThreadTheta4(BrickComponentHandler brickController) {
		super();
		
		this.brickController = brickController;
	}
	
	public void run() {
		
		double angleMotor = 0;
		
		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;
		
		try {
			while (true) {
				angleMotor = (double) brickController.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4() * -1;

				if (true) {
					angleCalc = (double) brickController.getSampleGyros().fetchSample()[0];
				} else {
					angleCalc = CalculationAngels.calcTheta4(Statics.getNewPosition());
				}
				
				System.out.println("Angle Motor:  " + angleMotor + ", Angle Calc: "  + angleCalc );
				
				speed = calcSpeed(angleMotor, angleCalc);
				
				if (speed < Statics.getMinSpeedMotor() ) {
					speed = Statics.getMinSpeedMotor();
				} else if (speed > Statics.getMaxSpeedOnCoordinateSystem()) {
					speed = Statics.getMaxSpeedOnCoordinateSystem();
				}
				
				
				if(Math.abs(Math.abs(angleCalc) - Math.abs(angleMotor)) > toleranz) {
					brickController.getHingTheta4().setSpeed((int) Math.abs(speed));
					if (speed < 0) {
						brickController.getHingTheta4().forward();
					} else {
						brickController.getHingTheta4().backward();
					}
				} else {
					brickController.getHingTheta4().stop(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	private double calcSpeed(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 3
		return diff / Statics.getTransmissionTheta3() / Statics.getInterval();

	}
}

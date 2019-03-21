package threads.rab.local;

import brick.rab.local.BrickComponentHandler;
import calculation.rab.local.CalculationAngels;
import rab.local.Statics;

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
		double toleranz = 5;
		
		try {
			while (true) {
				angleMotor = (double) brickController.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4() * -1;

				if (Statics.isTheta4Automatic()) {
					angleCalc = (double) brickController.getSampleGyros().fetchSample()[0];
				} else {
					angleCalc = CalculationAngels.calcTheta4(Statics.getCurrentPosition());
				}
				
				speed = calcSpeed(angleMotor, angleCalc);
				
				if(Math.abs(angleMotor- angleCalc) > toleranz) {
					brickController.getHingTheta4().setSpeed((int) Math.abs(speed));
					if (speed > 0) {
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

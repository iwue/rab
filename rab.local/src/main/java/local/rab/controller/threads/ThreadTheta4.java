package local.rab.controller.threads;

import local.rab.config.Statics;
import local.rab.controller.calculation.CalculateAngelsToCoordinate;
import local.rab.controller.calculation.CalculationAngels;
import local.rab.devices.brick.BrickComponentHandler;

public class ThreadTheta4 implements Runnable {
	private float toleranz = 4; // in Grad
	private BrickComponentHandler brickController;
	private CalculateAngelsToCoordinate angelsToCoordinate;

	public ThreadTheta4(BrickComponentHandler brickController) {
		super();

		this.brickController = brickController;
		angelsToCoordinate = new CalculateAngelsToCoordinate();	
	}

	@Override
	public void run() {

		double angleMotor = 0;

		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;
		double minSpeed = 20;

		try {
			while (!Thread.interrupted()) {
				angleMotor = brickController.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4()* -1;

				if (Statics.isTheta4Automatic()) {
					angleCalc = CalculationAngels.calcTheta4(angelsToCoordinate.calc(brickController));
				} else {
					angleCalc = CalculationAngels.calcTheta4(Statics.getNewPosition());
				}

				if (Math.abs(calcDiff(angleMotor, angleCalc)) > toleranz) {
					speed = (calcDiff(angleMotor, angleCalc) / Statics.getTransmissionTheta3()) / Statics.getInterval();

					if (speed < minSpeed && speed > 0) {

						// Positiv, Minimum
						speed = minSpeed;

					} else if (speed > -minSpeed && speed < 0) {

						// negativ, Minimum
						speed = -minSpeed;

					} else if (speed > Statics.getMaxSpeedMotor() && speed > 0) {

						// positiv, Maximum
						speed = Statics.getMaxSpeedMotor();

					} else if (speed < -Statics.getMaxSpeedMotor() && speed < 0) {

						// negativ, Maximum
						speed = -Statics.getMaxSpeedMotor();
					}

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

	private double calcDiff(double oldAngle, double newAngle) {
		// Differenz der Winkel für die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 3
		return diff;
	}
}

package local.rab.controller.threads;

import local.rab.config.Statics;
import local.rab.controller.calculation.CalculationAngels;
import local.rab.devices.brick.BrickComponentHandler;

public class ThreadTheta4 implements Runnable {
	private float toleranz = 4; // in Grad
	private BrickComponentHandler brickController;

	public ThreadTheta4(BrickComponentHandler brickController) {
		super();

		this.brickController = brickController;
	}

	@Override
	public void run() {

		double angleMotor = 0;

		double angleCalc = 0;

		double speed = 0;
		double toleranz = 2;

		try {
			while (true) {
				angleMotor = brickController.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4()* -1;

				if (false) {
					angleCalc = brickController.getSampleGyros().fetchSample()[0];
				} else {
					angleCalc = CalculationAngels.calcTheta4(Statics.getNewPosition());
				}

				if (Math.abs(calcDiff(angleMotor, angleCalc)) > toleranz) {
					speed = (calcDiff(angleMotor, angleCalc) / Statics.getTransmissionTheta3()) / Statics.getInterval();

					if (speed < Statics.getMinSpeedMotor() && speed > 0) {

						// Positiv, Minimum
						speed = Statics.getMinSpeedMotor();

					} else if (speed > -Statics.getMinSpeedMotor() && speed < 0) {

						// negativ, Minimum
						speed = -Statics.getMinSpeedMotor();

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
		// Differenz der Winkel f�r die Winkelgeschwindikgkeit
		double diff = oldAngle - newAngle;

		// Geschwindigkeit berechnen f�r Theta 3
		return diff;
	}
}
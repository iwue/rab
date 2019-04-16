package local.rab.controller;

import local.rab.config.Statics;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.dualshock.DualshockSimple;

public class MoveSimpleController {

	private BrickComponentHandler brickController;
	private DualshockSimple dualshockSimple;

	public MoveSimpleController(DualshockSimple dualshockSimple, BrickComponentHandler brickController) {
		try {
			this.dualshockSimple = dualshockSimple;
			this.brickController = brickController;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void move() {
		// Auslesen der X-Richtung auf dem Joystick
		double joystickValueTheta1 = dualshockSimple.getLeftStickX();
		// Auslesen der Y-Richtung auf dem Joystick
		double joystickValueTheta2 = dualshockSimple.getLeftStickY();
		// Auslesen der Z-Richtung auf dem Joystick
		double joystickValueTheta3 = dualshockSimple.getRightStickY();

		// Theta 1
		if (joystickValueTheta1 > (Statics.getDualshockStopRange() * -1)
				&& joystickValueTheta1 < Statics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta1().stop(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = Statics.getMaxSpeedMotor() * joystickValueTheta1;
				brickController.getHingTheta1().setSpeed((int) Math.abs(speed));

				// Bestimmer der Drehrichtung
				if (speed < 0) {
					brickController.getHingTheta1().forward();
				} else {
					brickController.getHingTheta1().backward();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Theta 2
		if (joystickValueTheta2 > (Statics.getDualshockStopRange() * -1)
				&& joystickValueTheta2 < Statics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta20().stop(true);
				brickController.getHingTheta21().stop(true);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = Statics.getMaxSpeedMotor() * joystickValueTheta2;
				brickController.getHingTheta20().setSpeed((int) Math.abs(speed));
				brickController.getHingTheta21().setSpeed((int) Math.abs(speed));

				// Bestimmer der Drehrichtung
				if (speed > 0) {
					brickController.getHingTheta20().forward();
					brickController.getHingTheta21().forward();
				} else {
					brickController.getHingTheta20().backward();
					brickController.getHingTheta21().backward();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Theta 3
		if (joystickValueTheta3 > (Statics.getDualshockStopRange() * -1)
				&& joystickValueTheta3 < Statics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta3().stop(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = Statics.getMaxSpeedMotor() * joystickValueTheta3;
				brickController.getHingTheta3().setSpeed((int) Math.abs(speed));

				// Bestimmer der Drehrichtung
				if (speed > 0) {
					brickController.getHingTheta3().forward();
				} else {
					brickController.getHingTheta3().backward();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

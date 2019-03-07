package controller.rab.local;

import brick.rab.local.BrickController;
import brick.rab.local.MoveBrickController;
import dualshock.rab.local.DualshockSimple;
import rab.local.RabStatics;
import threads.rab.local.ThreadGyros;

public class MoveSimple {

	private BrickController brickController;
	MoveBrickController moveBrickController;
	private DualshockSimple dualshockSimple;

	public MoveSimple(DualshockSimple dualshockSimple, BrickController brickController,
			MoveBrickController moveBrickController) {
		try {
			this.dualshockSimple = dualshockSimple;
			this.brickController = brickController;
			this.moveBrickController = moveBrickController;
			
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
		if (joystickValueTheta1 > (RabStatics.getDualshockStopRange() * -1)
				&& joystickValueTheta1 < RabStatics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta1().stop(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = RabStatics.getMaxSpeedMotor() * joystickValueTheta1;
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
		if (joystickValueTheta2 > (RabStatics.getDualshockStopRange() * -1)
				&& joystickValueTheta2 < RabStatics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta20().stop(true);
				brickController.getHingTheta21().stop(true);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = RabStatics.getMaxSpeedMotor() * joystickValueTheta2;
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
		if (joystickValueTheta3 > (RabStatics.getDualshockStopRange() * -1)
				&& joystickValueTheta3 < RabStatics.getDualshockStopRange()) {
			try {
				brickController.getHingTheta3().stop(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				// Setzen der Geschwindigkeit
				double speed = RabStatics.getMaxSpeedMotor() * joystickValueTheta3;
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

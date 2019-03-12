package controller.rab.local;

import java.util.LinkedList;

import brick.rab.local.BrickMoveController;
import dualshock.rab.local.DualshockSimple;
import javafx.geometry.Point3D;
import rab.local.Statics;

public class RabMainController {
	private BrickMoveController moveBrickController;
	private DualshockSimple dualshockSimple;

	public RabMainController(DualshockSimple dualshockSimple,
			BrickMoveController moveBrickController) {
		try {
			this.dualshockSimple = dualshockSimple;

			this.moveBrickController = moveBrickController;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void choreography(LinkedList<Point3D>  point3ds, double timeForMove) {
		while (!point3ds.isEmpty()) {
			moveBrickController.goTo(point3ds.removeFirst(), timeForMove);
		}
	}

	/**
	 * Umrechnung von Koordinatensystem zu Roboterachsen und Ausführen der Bewegung.
	 */
	public void move() {
		// Auslesen der X-Richtung auf dem Joystick
		double joystickCurrentX = dualshockSimple.getLeftStickX();
		// Auslesen der Y-Richtung auf dem Joystick
		double joystickCurrentY = dualshockSimple.getLeftStickY() * -1;
		// Auslesen der Z-Richtung auf dem Joystick
		double joystickCurrentZ = dualshockSimple.getRightStickY() * -1;

		// Prüfen ob die Joystickdaten von X, Y und Z sich im Stoppbereich befinden,
		// weil sich diese Signale nicht genau am 0 Punkt sind
		if (joystickCurrentX > (Statics.getDualshockStopRange() * -1)
				&& joystickCurrentX < Statics.getDualshockStopRange()
				&& joystickCurrentY > (Statics.getDualshockStopRange() * -1)
				&& joystickCurrentY < Statics.getDualshockStopRange()
				&& joystickCurrentZ > (Statics.getDualshockStopRange() * -1)
				&& joystickCurrentZ < Statics.getDualshockStopRange()) {

			// Falls sich alle im Stoppbereich befinden,
			// werden alle Motoren gestoppt
			moveBrickController.stopAllAngels();

		} else {
			// Berechnung der Bewegung in X-Richtung
			double moveX = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentX;
			// Berechnung der Bewegung in Y-Richtung
			double moveY = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentY;
			// Berechnung der Bewegung in Z-Richtung
			double moveZ = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentZ;

			// Addieren der Bewegung zur Akuellen Position
			// Es ensteht die neue Position
			double newX = Statics.getCurrentX() + moveX;
			double newY = Statics.getCurrentY() + moveY;
			double newZ = Statics.getCurrentZ() + moveZ;

			// Festlegen der Geschwindigkeit für die Achsen
			if (moveBrickController.setSpeedForAllAngles(newX, newY, newZ)) {
				// Alle Motoren starten
				moveBrickController.goAllAngels();
			}

			try {
				// Programm pausieren für Bewegung
				Thread.sleep((int) (1000 * Statics.getInterval()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public BrickMoveController getMoveBrickController() {
		return moveBrickController;
	}

	public DualshockSimple getDualshockSimple() {
		return dualshockSimple;
	}

	public void setDualshockSimple(DualshockSimple dualshockSimple) {
		this.dualshockSimple = dualshockSimple;
	}
}

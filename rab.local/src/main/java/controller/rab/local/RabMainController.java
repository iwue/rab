package controller.rab.local;

import java.util.LinkedList;

import dualshock.rab.local.DualshockSimple;
import javafx.geometry.Point3D;
import rab.local.Statics;

public class RabMainController {
	private DualshockSimple dualshockSimple;
	private CheckCoordinatesHandler checkCoordinatesHandler;

	public RabMainController(DualshockSimple dualshockSimple) {
		try {
			this.dualshockSimple = dualshockSimple;
			this.checkCoordinatesHandler = new CheckCoordinatesHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void choreography(LinkedList<Point3D> point3ds, double timeForMove) {
		double oldTime = Statics.getInterval();
		Statics.setInterval(timeForMove);
		
		Point3D point = null;
		while (!point3ds.isEmpty()) {
			
			point = point3ds.removeFirst();
			
			if (checkCoordinatesHandler.isCoordinateValid(point)) {
				Statics.setCurrentPosition(point);
				try {
					Thread.sleep((long) (1000 * timeForMove));
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		Statics.setInterval(oldTime);
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
		if (Math.abs(joystickCurrentX) < Statics.getDualshockStopRange()
				&& Math.abs(joystickCurrentY) < Statics.getDualshockStopRange()
				&& Math.abs(joystickCurrentZ) < Statics.getDualshockStopRange()) {

		} else {
			// Berechnung der Bewegung in X-Richtung
			double moveX = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentX;
			// Berechnung der Bewegung in Y-Richtung
			double moveY = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentY;
			// Berechnung der Bewegung in Z-Richtung
			double moveZ = Statics.getMaxSpeedOnCoordinateSystem() * joystickCurrentZ;

			// Addieren der Bewegung zur Akuellen Position
			// Es ensteht die neue Position
			Point3D newPosition = new Point3D(Statics.getCurrentPosition().getX() + moveX,
					Statics.getCurrentPosition().getY() + moveY, Statics.getCurrentPosition().getZ() + moveZ);
			if (checkCoordinatesHandler.isCoordinateValid(newPosition)) {
				Statics.setCurrentPosition(newPosition);
			}
			try {
				Thread.sleep(250);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public DualshockSimple getDualshockSimple() {
		return dualshockSimple;
	}

	public void setDualshockSimple(DualshockSimple dualshockSimple) {
		this.dualshockSimple = dualshockSimple;
	}
}

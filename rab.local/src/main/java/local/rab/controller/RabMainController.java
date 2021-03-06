package local.rab.controller;

import java.util.LinkedList;

import org.j3d.maths.vector.Point3d;

import local.rab.config.Statics;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.dualshock.DualshockSimple;

public class RabMainController {
	private DualshockSimple dualshockSimple;
	private CheckCoordinatesHandler checkCoordinatesHandler;
	private BrickComponentHandler componentHandler;
	
	public RabMainController(DualshockSimple dualshockSimple, BrickComponentHandler componentHandler) {
		try {
			this.componentHandler = componentHandler;
			this.dualshockSimple = dualshockSimple;
			this.checkCoordinatesHandler = new CheckCoordinatesHandler();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void choreography(LinkedList<Point3d> point3ds, double timeForMove) {
		double oldTime = Statics.getInterval();
		Statics.setInterval(timeForMove);
		
		Point3d point = null;
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
	 * Umrechnung von Koordinatensystem zu Roboterachsen und Ausf�hren der Bewegung.
	 */
	public void move() {
		// Auslesen der X-Richtung auf dem Joystick
		double joystickCurrentX = dualshockSimple.getLeftStickX();
		// Auslesen der Y-Richtung auf dem Joystick
		double joystickCurrentY = dualshockSimple.getLeftStickY() * -1;
		// Auslesen der Z-Richtung auf dem Joystick
		double joystickCurrentZ = dualshockSimple.getRightStickY() * -1;

		// Pr�fen ob die Joystickdaten von X, Y und Z sich im Stoppbereich befinden,
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
			Point3d newPosition = new Point3d();
			newPosition.set(Statics.getNewPosition().x + moveX,
					Statics.getNewPosition().y + moveY, 
					Statics.getNewPosition().z + moveZ);
			
			if (checkCoordinatesHandler.isCoordinateValid(newPosition)) {
				Statics.setCurrentPosition(newPosition);
			} else {
				componentHandler.getBrickLeft().getBrick().getAudio().playTone(5000, 2000);
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

package controller.rab.local;

import brick.rab.local.MoveBrickController;
import dualshock.rab.local.DualshockSimple;
import rab.local.RabStatics;
import threads.rab.local.ThreadGyros;

public class RabController {
	private MoveBrickController moveBrickController;
	private DualshockSimple dualshockSimple;

	public RabController(DualshockSimple dualshockSimple,
			MoveBrickController moveBrickController) {
		try {
			this.dualshockSimple = dualshockSimple;

			this.moveBrickController = moveBrickController;
			
		} catch (Exception e) {
			e.printStackTrace();
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
		if (joystickCurrentX > (RabStatics.getDualshockStopRange() * -1)
				&& joystickCurrentX < RabStatics.getDualshockStopRange()
				&& joystickCurrentY > (RabStatics.getDualshockStopRange() * -1)
				&& joystickCurrentY < RabStatics.getDualshockStopRange()
				&& joystickCurrentZ > (RabStatics.getDualshockStopRange() * -1)
				&& joystickCurrentZ < RabStatics.getDualshockStopRange()) {

			// Falls sich alle im Stoppbereich befinden,
			// werden alle Motoren gestoppt
			moveBrickController.stopAllAngels();

		} else {
			// Berechnung der Bewegung in X-Richtung
			double moveX = RabStatics.getMaxSpeedOnCoordinateSystem() * joystickCurrentX;
			// Berechnung der Bewegung in Y-Richtung
			double moveY = RabStatics.getMaxSpeedOnCoordinateSystem() * joystickCurrentY;
			// Berechnung der Bewegung in Z-Richtung
			double moveZ = RabStatics.getMaxSpeedOnCoordinateSystem() * joystickCurrentZ;

			// Addieren der Bewegung zur Akuellen Position
			// Es ensteht die neue Position
			double newX = RabStatics.getCurrentX() + moveX;
			double newY = RabStatics.getCurrentY() + moveY;
			double newZ = RabStatics.getCurrentZ() + moveZ;

			// Festlegen der Geschwindigkeit für die Achsen
			if (moveBrickController.setSpeedForAllAngles(newX, newY, newZ)) {
				// Alle Motoren starten
				moveBrickController.goAllAngels();
			}

			try {
				// Programm pausieren für Bewegung
				Thread.sleep((int) (1000 * RabStatics.getInterval()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void testMove() {
		/*
		 * // Rotation Links if (moveController.setSpeedForAllAngles(0, 304, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Rotation Rechts if (moveController.setSpeedForAllAngles(0, -304, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Gerade Links if (moveController.setSpeedForAllAngles(304, 250, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * 
		 * // Gerade Rechts if (moveController.setSpeedForAllAngles(304, -250, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // OOR HEHE if (moveController.setSpeedForAllAngles(0, 0, 0)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * 
		 * // Runter if(moveController.setSpeedForAllAngles(304, 0, 250)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if(moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Oben if (moveController.setSpeedForAllAngles(304, 0, 500)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)) {
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Vorne if(moveController.setSpeedForAllAngles(400, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Hinten if (moveController.setSpeedForAllAngles(200, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 * 
		 * // Ausgangposition if (moveController.setSpeedForAllAngles(304, 0, 428)){
		 * moveController.goAllAngels(); }; Thread.sleep((int)(1000 * interval));
		 * moveController.stopAllAngels();
		 */
	}

	public MoveBrickController getMoveBrickController() {
		return moveBrickController;
	}

	public DualshockSimple getDualshockSimple() {
		return dualshockSimple;
	}

	public void setDualshockSimple(DualshockSimple dualshockSimple) {
		this.dualshockSimple = dualshockSimple;
	}
}

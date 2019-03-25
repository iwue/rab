/*
 * 
 */
package brick.rab.local;

import calculation.rab.local.CalculationAngels;
import controller.rab.local.CheckCoordinatesHandler;
import javafx.geometry.Point3D;
import rab.local.Statics;

/**
 * @author IsaacWürth
 *
 */
public class BrickMoveController {

	private double speedTheta1WithoutTransmission = 0.0;
	private double speedTheta2WithoutTransmission = 0.0;
	private double speedTheta3WithoutTransmission = 0.0;
	private double speedTheta4WithoutTransmission = 0.0;

	private double speedTheta1WithTransmission = 0.0;
	private double speedTheta2WithTransmission = 0.0;
	private double speedTheta3WithTransmission = 0.0;
	private double speedTheta4WithTransmission = 0.0;

	private BrickComponentHandler brickController;

	private CheckCoordinatesHandler checkCoordinates;

	public BrickMoveController(double currentX, double currentY, double currentZ, BrickComponentHandler brickController) {
		checkCoordinates = new CheckCoordinatesHandler();
		this.brickController = brickController;
		Statics.setCurrentX(currentX);
		Statics.setCurrentY(currentY);
		Statics.setCurrentZ(currentZ);
	}

	/**
	 * Fahre zu einer bestimmten Punk.
	 *
	 * @param newX        Neue Position:Punkt X im Koordinatensystem
	 * @param newY        Neue Position: Punk Y im Koordinatensystem
	 * @param newZ        Neue Position: Punk Z im Koordinatensystem
	 * @param timeForMove Zeit für die Bewegung
	 */
	public void goTo	(double newX, double newY, double newZ, double timeForMove) {
		
		double oldIntervall = Statics.getInterval();
		Statics.setInterval(timeForMove);
		try {
			if (setSpeedForAllAngles(newX, newY, newZ)) {
				goAllAngels();
			}
			
			Thread.sleep((long) timeForMove);
			stopAllAngels();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Statics.setInterval(oldIntervall);
	}
	
	public void goTo(Point3D point3d, double timeForMove) {
		goTo(point3d.getX(), point3d.getY(), point3d.getZ(), timeForMove);
	}

	/**
	 * Sets the speed for all angles.
	 *
	 * @param newX X im Koordinatensystem, welcher erreicht werden soll
	 * @param newY Y im Koordinatensystem, welcher erreicht werden soll
	 * @param newZ Z im Koordinatensystem, welcher erreicht werden soll
	 * @return true, wenn die Bewegung sich innerhalb des Erreichbaren befindet
	 */
	public boolean setSpeedForAllAngles(double newX, double newY, double newZ) {
		// Prüfen der Koordinaten
		if (checkCoordinates.isCoordinateValid(newX, newY, 	newZ)) {
			// Geschwindigkeit der 1. Achse berechnen
			setSpeedTheta1(newX, newY);
			// Geschwindigkeit der 2. Achse berechnen
			setSpeedTheta2(newX, newY, newZ);
			// Geschwindigkeit der 3. Achse berechnen
			setSpeedTheta3(newX, newY, newZ);
			// Geschwindigkeit der 4.Achse berechnen
			if (!Statics.isTheta4Automatic()) {
				setSpeedTheta4(newX, newY, newZ);
			}
	
			// Neue Position als aktuelle Position setzen
			Statics.setCurrentX(newX);
			Statics.setCurrentY(newY);
			Statics.setCurrentZ(newZ);
			return true;
		} else {
			// Falls sich die Koordinaten ausserhalbt der Bewegung befinden
			System.out.println("Out of range");
			brickController.getBrickLeft().
				getBrick().getAudio().playTone(10000, 1500);
			return false;
		}
	}

	/**
	 * Fahren aller Motoren, danach muss {@link #stopAllAngels()} ausgeführt werden.
	 */
	public void goAllAngels() {
		goTheta1();
		goTheta2();
		goTheta3();
		if (!Statics.isTheta4Automatic()) {
			goTheta4();
		}
	}

	/**
	 * Stoppen alles Bewegungen/Motoren
	 */
	public void stopAllAngels() {
		try {
			brickController.getHingTheta1().stop(true);
			brickController.getHingTheta20().stop(true);
			brickController.getHingTheta21().stop(true);
			brickController.getHingTheta3().stop(true);

			if (!Statics.isTheta4Automatic()) {
				brickController.getHingTheta4().stop(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setzen der Geschwindigkeit für Theta 1
	 *
	 * @param newX X im Koordinatensystem, welcher erreicht werden soll
	 * @param newY Y im Koordinatensystem, welcher erreicht werden soll
	 */
	private void setSpeedTheta1(double newX, double newY) {
		try {
			double oldAngle = brickController.getHingTheta1().getTachoCount() * Statics.getTransmissionTheta1();
			double newAngle = CalculationAngels.calcTheta1(newX, newY);

			if (oldAngle < 0) {
				oldAngle = oldAngle + 360;
			}

			double diffAngle = oldAngle - newAngle;

			// Berechnugn des kürzesten Wegs für Rotation mit Grösse der Bewegung
			if (diffAngle == 0) {
				// keine Rotation
				speedTheta1WithoutTransmission = 0;
			} else if (diffAngle > 0) {
				// Neuer Winkel ist kleiner als alter Winkel
				if (Math.abs(diffAngle - 360) < Math.abs(diffAngle)) {
					// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
					speedTheta1WithoutTransmission = diffAngle - 360;
				} else {
					// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
					speedTheta1WithoutTransmission = diffAngle;
				}
			} else {
				// Neuer Winkel ist grösser als alter Winkel
				if (Math.abs(diffAngle + 360) < Math.abs(diffAngle)) {
					// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
					speedTheta1WithoutTransmission = diffAngle + 360;
				} else {
					// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
					speedTheta1WithoutTransmission = diffAngle;
				}
			}

			speedTheta1WithTransmission = Math.abs(
					(speedTheta1WithoutTransmission / Statics.getTransmissionTheta1()) / Statics.getInterval());

			brickController.getHingTheta1().setSpeed((int) speedTheta1WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setzen der Geschwindigkeit für Theta 2
	 *
	 * @param newX X im Koordinatensystem, welcher erreicht werden soll
	 * @param newY Y im Koordinatensystem, welcher erreicht werden soll
	 * @param newZ Z im Koordinatensystem, welcher erreicht werden soll
	 */
	private void setSpeedTheta2(double newX, double newY, double newZ) {
		try {
			// Auslesen des aktuellen Winkels bei Theta 2 vom Motor
			double oldAngle = ((brickController.getHingTheta20().getTachoCount() * Statics.getTransmissionTheta2())
					* -1) + 90;
			// Berechnen des neuen Winekel für Theta 2
			double newAngle = CalculationAngels.calcTheta2(newX, newY, newZ);

			// Differenz der Winkel für die Winkelgeschwindikgkeit
			speedTheta2WithoutTransmission = oldAngle - newAngle;

			// Geschwindigkeit berechnen für Theta 2
			speedTheta2WithTransmission = Math.abs(
					speedTheta2WithoutTransmission / Statics.getTransmissionTheta2() / Statics.getInterval());

			// Setzen der Geschwindigkeit für Theta 2 am Motor 1
			brickController.getHingTheta20().setSpeed((int) speedTheta2WithTransmission);

			// Setzen der Geschwindigkeit für Theta 2 am Motor 2
			brickController.getHingTheta21().setSpeed((int) speedTheta2WithTransmission);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setzen der Geschwindigkeit für Theta 3
	 *
	 * @param newX X im Koordinatensystem, welcher erreicht werden soll
	 * @param newY Y im Koordinatensystem, welcher erreicht werden soll
	 * @param newZ Z im Koordinatensystem, welcher erreicht werden soll
	 */
	private void setSpeedTheta3(double newX, double newY, double newZ) {
		try {
			// Auslesen des aktuellen Winkels bei Theta 3 vom Motor
			double oldAngle = ((brickController.getHingTheta3().getTachoCount() * Statics.getTransmissionTheta3())
					* -1) - 90;
			// Berechnen des neuen Winekel für Theta 3
			double newAngle = CalculationAngels.calcTheta3(newX, newY, newZ);

			// Differenz der Winkel für die Winkelgeschwindikgkeit
			speedTheta3WithoutTransmission = oldAngle - newAngle;

			// Geschwindigkeit berechnen für Theta 3
			speedTheta3WithTransmission = Math.abs(
					speedTheta3WithoutTransmission / Statics.getTransmissionTheta3() / Statics.getInterval());

			// Setzen der Geschwindigkeit für Theta 3
			brickController.getHingTheta3().setSpeed((int) speedTheta3WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Setzen der Geschwindigkeit für Theta 4
	 *
	 * @param newX X im Koordinatensystem, welcher erreicht werden soll
	 * @param newY Y im Koordinatensystem, welcher erreicht werden soll
	 * @param newZ Z im Koordinatensystem, welcher erreicht werden soll
	 */
private void setSpeedTheta4(double newX, double newY, double newZ) {
	try {
		// Auslesen des aktuellen Winkels bei Theta 4 vom Motor
		double oldAngle = brickController.getHingTheta4().getTachoCount() * Statics.getTransmissionTheta4() * -1;
		// Berechnen des neuen Winekel für Theta 4
		double newAngle = CalculationAngels.calcTheta4(newX, newY, newZ);

		// Differenz der Winkel für die Winkelgeschwindikgkeit
		speedTheta4WithoutTransmission = oldAngle - newAngle;

		// Geschwindigkeit berechnen für Theta 4
		speedTheta4WithTransmission = Math.abs(
				speedTheta4WithoutTransmission / Statics.getTransmissionTheta4() / Statics.getInterval());

		// Setzen der Geschwindigkeit für Theta 4
		brickController.getHingTheta4().setSpeed((int) speedTheta4WithTransmission);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

	/**
	 * Fahren der Bewegung für Theta 1 Vorher muss der Befehl
	 * {@Link #setSpeedForAllAngles(double, double, double)} ausgeführt werden damit
	 * gefahren werden kann. Dannach muss {@link #stopAllAngels()}} damit die
	 * Bewegung gestop wird
	 */
	private void goTheta1() {
		try {
			if (speedTheta1WithoutTransmission > 0) {
				// Wenn die Geschwindigkeit negativ ist
				brickController.getHingTheta1().backward();
			} else {
				// Wenn die Geschwindigkeit positiv ist
				brickController.getHingTheta1().forward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fahren der Bewegung für Theta 2 Vorher muss der Befehl
	 * {@Link #setSpeedForAllAngles(double, double, double)} ausgeführt werden damit
	 * gefahren werden kann. Dannach muss {@link #stopAllAngels()}} damit die
	 * Bewegung gestop wird
	 */
	private void goTheta2() {
		try {
			if (speedTheta2WithoutTransmission > 0) {
				brickController.getHingTheta21().forward();
				brickController.getHingTheta20().forward();
			} else {
				brickController.getHingTheta21().backward();
				brickController.getHingTheta20().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fahren der Bewegung für Theta 3 Vorher muss der Befehl
	 * {@Link #setSpeedForAllAngles(double, double, double)} ausgeführt werden damit
	 * gefahren werden kann. Dannach muss {@link #stopAllAngels()}} damit die
	 * Bewegung gestop wird
	 */
	private void goTheta3() {
		try {
			if (speedTheta3WithoutTransmission > 0) {
				brickController.getHingTheta3().forward();
			} else {
				brickController.getHingTheta3().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fahren der Bewegung für Theta 4 Vorher muss der Befehl
	 * {@Link #setSpeedForAllAngles(double, double, double)} ausgeführt werden damit
	 * gefahren werden kann. Dannach muss {@link #stopAllAngels()}} damit die
	 * Bewegung gestop wird
	 */
	private void goTheta4() {
		try {
			if (speedTheta4WithoutTransmission > 0) {
				brickController.getHingTheta4().forward();
			} else {
				brickController.getHingTheta4().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BrickComponentHandler getBrickController() {
		return brickController;
	}
}

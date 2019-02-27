package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	private double translationAngle1 = 0.1;
	private double translationAngle2 = 0.1;
	private double translationAngle3 = 0.33333333;
	private double translationRotation = 0.2;

	private double speedRotationWithoutTranslation = 0.0;
	private double speedAngle1WithoutTranmission = 0.0;
	private double speedAngle2WithoutTransmission = 0.0;
	private double speedAngle3WithoutTransmission = 0.0;
	
	private double speedRotationWithTransmission = 0.0;
	private double speedAngle1WithTransmission = 0.0;
	private double speedAngle2WithTransmission = 0.0;
	private double speedAngle3WithTransmission = 0.0;
	
	
	private double currentX = 0.0;
	private double currentY = 0.0;
	private double currentZ = 0.0;

	private double interval = 1.0;

	public MoveController(double currentX, double currentY, double currentZ, double interval) {
		super();
		this.currentX = currentX;
		this.currentY = currentY;
		this.currentZ = currentZ;
		this.interval = interval;
	}

	public void setSpeedForAllAngles(double newX, double newY, double newZ) {
		// Prüfen der Koordinaten
		if (CheckCoordinates.isCoordinateValid(newX, newY, newZ)) {
			// Geschwindigkeit der 1. Achse berechnen
			setSpeedTheta1(currentX, currentY, newX, newY);
			// Geschwindigkeit der 2. Achse berechnen
			setSpeedTheta2(currentX, currentY, currentZ, newX, newY, newZ);
			// Geschwindigkeit der 3. Achse berechnen
			setSpeedTheta3(currentX, currentY, currentZ, newX, newY, newZ);
			// Geschwindigkeit der 4.Achse berechnen
			setSpeedTheta4(currentX, currentY, currentZ, newX, newY, newZ);

			// Ausgabe auf CLI
			System.out.println("Theta 1: " + speedRotationWithoutTranslation
					+ ", Theta 2: " + speedAngle1WithoutTranmission
					+ ", Theta 3: " + speedAngle2WithoutTransmission
					+ ", Theta 4: " + speedAngle3WithoutTransmission);
			// Neue Position als aktuelle Position setzen
			currentX = newX;
			currentY = newY;
			currentZ = newZ;
		} else {
			// Falls sich die Koordinaten ausserhalbt der Bewegung befinden
			System.out.println("Out of range");
		}
	}

	public void goAllAngels() {
		goAngle1();
		goAngle2();
		goAngle3();
		goRotation();
		System.out.println("All Go");
	}

	public void stopAllAngels() {
		try {
			MainController.getHingTheta20().stop(true);
			MainController.getHingTheta21().stop(true);
			MainController.getHingTheta3().stop(true);
			MainController.getHingTheta4().stop(true);
			MainController.getHingTheta1().stop(true);
			System.out.println("All stop");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcTheta2(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcTheta2(newX, newY, newZ);

		speedAngle1WithoutTranmission = oldAngle - newAngle;

		try {
			speedAngle1WithTransmission = Math.abs(speedAngle1WithoutTranmission / translationAngle1 / interval);
			
			MainController.getHingTheta20().setSpeed((int) speedAngle1WithTransmission);
			MainController.getHingTheta21().setSpeed((int) speedAngle1WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcTheta3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcTheta3(newX, newY, newZ);

		speedAngle2WithoutTransmission = oldAngle - newAngle;

		try {
			speedAngle2WithTransmission = Math.abs(speedAngle2WithoutTransmission / translationAngle2 / interval);
			MainController.getHingTheta3().setSpeed((int) speedAngle2WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta4(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcTheta4(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcTheta4(newX, newY, newZ);

		speedAngle3WithoutTransmission = oldAngle - newAngle;

		try {
			speedAngle3WithTransmission = Math.abs(speedAngle3WithoutTransmission / translationAngle3 / interval);
			MainController.getHingTheta4().setSpeed((int) speedAngle3WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta1(double oldX, double oldY, double newX, double newY) {
		double oldAngleRotation = CalculationAngels.calcTheta1(oldX, oldY);
		double newAngleRotation = CalculationAngels.calcTheta1(newX, newY);
		double diffNewOldRotation = oldAngleRotation - newAngleRotation;
		
		// Berechnugn des kürzesten Wegs für Rotation mit Grösse der Bewegung
		if (diffNewOldRotation == 0) {
			// keine Rotation
			speedRotationWithoutTranslation = 0;
		} else if (diffNewOldRotation > 0) {
			// Neuer Winkel ist kleiner als alter Winkel
			if (Math.abs(diffNewOldRotation - 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedRotationWithoutTranslation = diffNewOldRotation - 360;
			} else {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedRotationWithoutTranslation = diffNewOldRotation;
			}
		} else {
			// Neuer Winkel ist grösser als alter Winkel
			if (Math.abs(diffNewOldRotation + 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedRotationWithoutTranslation = diffNewOldRotation + 360;
			} else {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedRotationWithoutTranslation = diffNewOldRotation;
			}
		}
		
		try {
			speedRotationWithTransmission = Math.abs((speedRotationWithoutTranslation / translationRotation) / interval);
			MainController.getHingTheta1().setSpeed((int) speedRotationWithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle1() {
		try {
			if (speedAngle1WithoutTranmission > 0) {
				MainController.getHingTheta21().forward();
				MainController.getHingTheta20().forward();
			} else {
				MainController.getHingTheta21().backward();
				MainController.getHingTheta20().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle2() {
		try {
			if (speedAngle2WithoutTransmission > 0) {
				MainController.getHingTheta3().forward();
			} else {
				MainController.getHingTheta3().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle3() {
		try {
			if (speedAngle3WithoutTransmission > 0) {
				MainController.getHingTheta4().forward();
			} else {
				MainController.getHingTheta4().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goRotation() {
		try {
			// MainController.getHingRotation().rotateTo((int)(calculatedSpeedRotation /
			// translationAngleRotation), true);
			if (speedRotationWithoutTranslation > 0) {
				MainController.getHingTheta1().forward();
			} else {
				MainController.getHingTheta1().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getCurrentX() {
		return currentX;
	}

	public double getCurrentY() {
		return currentY;
	}

	public double getCurrentZ() {
		return currentZ;
	}
}

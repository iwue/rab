package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	private double transmissionTheta1 = 0.2;
	private double transmissionTheta2 = 0.1;
	private double transmissionTheta3 = 0.1;
	private double transmissionTheta4 = 0.33333333;
	private double speedTheta1WithoutTransmission = 0.0;
	private double speedTheta2WithoutTransmission = 0.0;
	private double speedTheta3WithoutTransmission = 0.0;
	private double speedTheta4WithoutTransmission = 0.0;
	
	private double speedTheta1WithTransmission = 0.0;
	private double speedTheta2WithTransmission = 0.0;
	private double speedTheta3WithTransmission = 0.0;
	private double speedTheta4WithTransmission = 0.0;
	
	
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
			System.out.println("Theta 1: " + speedTheta1WithoutTransmission
					+ ", Theta 2: " + speedTheta2WithoutTransmission
					+ ", Theta 3: " + speedTheta3WithoutTransmission
					+ ", Theta 4: " + speedTheta4WithoutTransmission);
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
		goTheta1();
		goTheta2();
		goTheta3();
		goTheta4();
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

		speedTheta2WithoutTransmission = oldAngle - newAngle;

		try {
			speedTheta2WithTransmission = Math.abs(speedTheta2WithoutTransmission / transmissionTheta2 / interval);
			
			MainController.getHingTheta20().setSpeed((int) speedTheta2WithTransmission);
			MainController.getHingTheta21().setSpeed((int) speedTheta2WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcTheta3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcTheta3(newX, newY, newZ);

		speedTheta3WithoutTransmission = oldAngle - newAngle;

		try {
			speedTheta3WithTransmission = Math.abs(speedTheta3WithoutTransmission / transmissionTheta3 / interval);
			MainController.getHingTheta3().setSpeed((int) speedTheta3WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta4(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcTheta4(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcTheta4(newX, newY, newZ);

		speedTheta4WithoutTransmission = oldAngle - newAngle;

		try {
			speedTheta4WithTransmission = Math.abs(speedTheta4WithoutTransmission / transmissionTheta4 / interval);
			MainController.getHingTheta4().setSpeed((int) speedTheta4WithTransmission);
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
			speedTheta1WithoutTransmission = 0;
		} else if (diffNewOldRotation > 0) {
			// Neuer Winkel ist kleiner als alter Winkel
			if (Math.abs(diffNewOldRotation - 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedTheta1WithoutTransmission = diffNewOldRotation - 360;
			} else {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedTheta1WithoutTransmission = diffNewOldRotation;
			}
		} else {
			// Neuer Winkel ist grösser als alter Winkel
			if (Math.abs(diffNewOldRotation + 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				speedTheta1WithoutTransmission = diffNewOldRotation + 360;
			} else {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				speedTheta1WithoutTransmission = diffNewOldRotation;
			}
		}
		
		try {
			speedTheta1WithTransmission = Math.abs((speedTheta1WithoutTransmission / transmissionTheta1) / interval);
			MainController.getHingTheta1().setSpeed((int) speedTheta1WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goTheta2() {
		try {
			if (speedTheta2WithoutTransmission > 0) {
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

	private void goTheta3() {
		try {
			if (speedTheta3WithoutTransmission > 0) {
				MainController.getHingTheta3().forward();
			} else {
				MainController.getHingTheta3().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goTheta4() {
		try {
			if (speedTheta4WithoutTransmission > 0) {
				MainController.getHingTheta4().forward();
			} else {
				MainController.getHingTheta4().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goTheta1() {
		try {
			// MainController.getHingRotation().rotateTo((int)(calculatedSpeedRotation /
			// translationAngleRotation), true);
			if (speedTheta1WithoutTransmission > 0) {
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

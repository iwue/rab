package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	//
	private double translationAngle1 = 0.1;
	private double translationAngle2 = 0.1;
	private double translationAngle3 = 0.1;
	private double translationAngleRotation = 0.4;

	private double calculatedSpeedRotation = 0.0;
	private double calculatedSpeedAngle1 = 0.0;
	private double calculatedSpeedAngle2 = 0.0;
	private double calculatedSpeedAngle3 = 0.0;

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
		if (!Double.isNaN(CalculationAngels.calcAngle1(newX, newY, newZ))) {
			setSpeedAngle1(currentX, currentY, currentZ, newX, newY, newZ);
			setSpeedAngle2(currentX, currentY, currentZ, newX, newY, newZ);
			setSpeedAngle3(currentX, currentY, currentZ, newX, newY, newZ);
			setSpeedRotation(currentX, currentY, newX, newY);

			System.out.println("Anglespeed 1: " + calculatedSpeedAngle1 + ", Anglespeed 2: " + calculatedSpeedAngle2
					+ ", Anglespeed 3: " + calculatedSpeedAngle3 + ", Rotationspeed: " + calculatedSpeedRotation);

			currentX = newX;
			currentY = newY;
			currentZ = newZ;
		} else {
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
			MainController.getHingA1().stop(true);
			MainController.getHingA2().stop(true);
			MainController.getHingA3().stop(true);
			MainController.getHingRotation().stop(true);
			System.out.println("All stop");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle1(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle1(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle1(newX, newY, newZ);

		calculatedSpeedAngle1 = oldAngle - newAngle;

		try {
			double speed = Math.abs(calculatedSpeedAngle1 / translationAngle1 / interval);
			MainController.getHingA1().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle2(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle2(newX, newY, newZ);

		calculatedSpeedAngle2 = oldAngle - newAngle;

		try {
			double speed = Math.abs(calculatedSpeedAngle2 / translationAngle2 / interval);
			MainController.getHingA2().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle3(newX, newY, newZ);

		calculatedSpeedAngle3 = oldAngle - newAngle;

		try {
			double speed = Math.abs(calculatedSpeedAngle3 / translationAngle3 / interval);
			MainController.getHingA3().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedRotation(double oldX, double oldY, double newX, double newY) {
		double oldAngleRotation = CalculationAngels.calcAngleRotation(oldX, oldY);
		double newAngleRotation = CalculationAngels.calcAngleRotation(newX, newY);
		
		// Berechnugn des k�rzesten Wegs f�r Rotation mit Gr�sse der Bewegung
		if (oldAngleRotation - newAngleRotation == 0) {
			// keine Rotation
			calculatedSpeedRotation = 0;
		} else if (oldAngleRotation - newAngleRotation > 0) {
			// Neuer Winkel ist kleiner als alter Winkel
			if (Math.abs(oldAngleRotation - newAngleRotation - 360) < Math.abs(oldAngleRotation - newAngleRotation)) {
				// Wenn Bewegung in Uhrzeigersinn (negativ) k�rzer ist
				calculatedSpeedRotation = oldAngleRotation - newAngleRotation - 360;
			} else {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) k�rzer ist
				calculatedSpeedRotation = oldAngleRotation - newAngleRotation;
			}
		} else {
			// Neuer Winkel ist gr�sser als alter Winkel
			if (Math.abs(oldAngleRotation - newAngleRotation + 360) < Math.abs(oldAngleRotation - newAngleRotation)) {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) k�rzer ist
				calculatedSpeedRotation = oldAngleRotation - newAngleRotation + 360;
			} else {
				// Wenn Bewegung in Uhrzeigersinn (negativ) k�rzer ist
				calculatedSpeedRotation = oldAngleRotation - newAngleRotation;
			}
		}
		
		try {
			double speed = Math.abs((calculatedSpeedRotation / translationAngleRotation) / interval);
			MainController.getHingRotation().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle1() {
		try {
			if (calculatedSpeedRotation < 0) {
				MainController.getHingA1().forward();
			} else {
				MainController.getHingA1().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle2() {
		try {
			if (calculatedSpeedRotation < 0) {
				MainController.getHingA2().forward();
			} else {
				MainController.getHingA2().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle3() {
		try {
			if (calculatedSpeedRotation < 0) {
				MainController.getHingA3().forward();
			} else {
				MainController.getHingA3().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goRotation() {
		try {
			// MainController.getHingRotation().rotateTo((int)(calculatedSpeedRotation /
			// translationAngleRotation), true);
			if (calculatedSpeedRotation < 0) {
				MainController.getHingRotation().forward();
			} else {
				MainController.getHingRotation().backward();
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

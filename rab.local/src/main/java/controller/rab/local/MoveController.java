package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	//
	private double translationAngle1 = 0.1;
	private double translationAngle2 = 0.1;
	private double translationAngle3 = 0.33333333;
	private double translationRotation = 0.2;

	private double speedRotationWithoutTranslation = 0.0;
	private double speedAngle1WithoutTranslation = 0.0;
	private double speedAngle2WithoutTranslation = 0.0;
	private double speedAngle3WithoutTranslation = 0.0;
	
	private double speedRotationWithTranslation = 0.0;
	private double speedAngle1WithTranslation = 0.0;
	private double speedAngle2WithTranslation = 0.0;
	private double speedAngle3WithTranslation = 0.0;
	
	
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
			
			System.out.println("Motor 1: " + speedAngle1WithoutTranslation
					+ ", Motor 2: " + speedAngle2WithoutTranslation
					+ ", Motor 3: " + speedAngle3WithoutTranslation
					+ ", RotationMotor: " + speedRotationWithoutTranslation);
			
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
			MainController.getHingA11().stop(true);
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

		speedAngle1WithoutTranslation = oldAngle - newAngle;

		try {
			speedAngle1WithTranslation = Math.abs(speedAngle1WithoutTranslation / translationAngle1 / interval);
			
			MainController.getHingA1().setSpeed((int) speedAngle1WithTranslation);
			MainController.getHingA11().setSpeed((int) speedAngle1WithTranslation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle2(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle2(newX, newY, newZ);

		speedAngle2WithoutTranslation = oldAngle - newAngle;

		try {
			speedAngle2WithTranslation = Math.abs(speedAngle2WithoutTranslation / translationAngle2 / interval);
			MainController.getHingA2().setSpeed((int) speedAngle2WithTranslation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle3(newX, newY, newZ);

		speedAngle3WithoutTranslation = oldAngle - newAngle;

		try {
			speedAngle3WithTranslation = Math.abs(speedAngle3WithoutTranslation / translationAngle3 / interval);
			MainController.getHingA3().setSpeed((int) speedAngle3WithTranslation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedRotation(double oldX, double oldY, double newX, double newY) {
		double oldAngleRotation = CalculationAngels.calcAngleRotation(oldX, oldY);
		double newAngleRotation = CalculationAngels.calcAngleRotation(newX, newY);
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
			speedRotationWithTranslation = Math.abs((speedRotationWithoutTranslation / translationRotation) / interval);
			MainController.getHingRotation().setSpeed((int) speedRotationWithTranslation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle1() {
		try {
			if (speedAngle1WithoutTranslation > 0) {
				MainController.getHingA11().forward();
				MainController.getHingA1().forward();
			} else {
				MainController.getHingA11().backward();
				MainController.getHingA1().backward();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle2() {
		try {
			if (speedAngle2WithoutTranslation > 0) {
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
			if (speedAngle3WithoutTranslation > 0) {
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
			if (speedRotationWithoutTranslation > 0) {
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

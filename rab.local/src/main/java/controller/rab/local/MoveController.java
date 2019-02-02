package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	//
	private double translationAngle1 = 0.1;
	private double translationAngle2 = 0.1;
	private double translationAngle3 = 0.33333333;
	private double translationAngleRotation = 0.2;

	private double calculatedSpeedRotationWithoutTranslation = 0.0;
	private double calculatedSpeedAngle1WithoutTranslation = 0.0;
	private double calculatedSpeedAngle2WithoutTranslation = 0.0;
	private double calculatedSpeedAngle3WithoutTranslation = 0.0;
	
	private double calculatedSpeedRotationMotor = 0.0;
	private double calculatedSpeedAngle1Motor = 0.0;
	private double calculatedSpeedAngle2Motor = 0.0;
	private double calculatedSpeedAngle3Motor = 0.0;
	
	
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

			System.out.println("Motor 1: " + calculatedSpeedAngle1WithoutTranslation
					+ ", Motor 2: " + calculatedSpeedAngle2WithoutTranslation
					+ ", Motor 3: " + calculatedSpeedAngle3WithoutTranslation
					+ ", RotationMotor: " + calculatedSpeedRotationWithoutTranslation);
			
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

		calculatedSpeedAngle1WithoutTranslation = oldAngle - newAngle;

		try {
			calculatedSpeedAngle1Motor = Math.abs(calculatedSpeedAngle1WithoutTranslation / translationAngle1 / interval);
			
			MainController.getHingA1().setSpeed((int) calculatedSpeedAngle1Motor);
			MainController.getHingA11().setSpeed((int) calculatedSpeedAngle1Motor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle2(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle2(newX, newY, newZ);

		calculatedSpeedAngle2WithoutTranslation = oldAngle - newAngle;

		try {
			calculatedSpeedAngle2Motor = Math.abs(calculatedSpeedAngle2WithoutTranslation / translationAngle2 / interval);
			MainController.getHingA2().setSpeed((int) calculatedSpeedAngle2Motor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle3(newX, newY, newZ);

		calculatedSpeedAngle3WithoutTranslation = oldAngle - newAngle;

		try {
			calculatedSpeedAngle3Motor = Math.abs(calculatedSpeedAngle3WithoutTranslation / translationAngle3 / interval);
			MainController.getHingA3().setSpeed((int) calculatedSpeedAngle3Motor);
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
			calculatedSpeedRotationWithoutTranslation = 0;
		} else if (diffNewOldRotation > 0) {
			// Neuer Winkel ist kleiner als alter Winkel
			if (Math.abs(diffNewOldRotation - 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				calculatedSpeedRotationWithoutTranslation = diffNewOldRotation - 360;
			} else {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				calculatedSpeedRotationWithoutTranslation = diffNewOldRotation;
			}
		} else {
			// Neuer Winkel ist grösser als alter Winkel
			if (Math.abs(diffNewOldRotation + 360) < Math.abs(diffNewOldRotation)) {
				// Wenn Bewegung in Gegenuhrzeigersinn (positiv) kürzer ist
				calculatedSpeedRotationWithoutTranslation = diffNewOldRotation + 360;
			} else {
				// Wenn Bewegung in Uhrzeigersinn (negativ) kürzer ist
				calculatedSpeedRotationWithoutTranslation = diffNewOldRotation;
			}
		}
		
		try {
			calculatedSpeedRotationMotor = Math.abs((calculatedSpeedRotationWithoutTranslation / translationAngleRotation) / interval);
			MainController.getHingRotation().setSpeed((int) calculatedSpeedRotationMotor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goAngle1() {
		try {
			if (calculatedSpeedAngle1WithoutTranslation > 0) {
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
			if (calculatedSpeedAngle2WithoutTranslation > 0) {
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
			if (calculatedSpeedAngle3WithoutTranslation > 0) {
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
			if (calculatedSpeedRotationWithoutTranslation > 0) {
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

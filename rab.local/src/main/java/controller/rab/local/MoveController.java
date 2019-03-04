package controller.rab.local;

import org.bytedeco.javacpp.opencv_stitching.SiftFeaturesFinder;

import calculation.rab.local.CalculationAngels;

public class MoveController {

	private double transmissionTheta1 = 0.1;
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
	
	private static double angleTheta1;
	private static double angleTheta2;
	private static double angleTheta3;
	private static double angleTheta4;
	
	private static double currentX = 0.0;
	private static double currentY = 0.0;
	private static double currentZ = 0.0;

	private double interval = 1.0;

	public MoveController(double currentX, double currentY, double currentZ, double interval) {
		super();
		MoveController.currentX = currentX;
		MoveController.currentY = currentY;
		MoveController.currentZ = currentZ;
		
		angleTheta1 = CalculationAngels.calcTheta1(currentX, currentY);
		angleTheta2 = CalculationAngels.calcTheta2(currentX, currentY, currentZ);
		angleTheta3 = CalculationAngels.calcTheta3(currentX, currentY, currentZ);
		angleTheta4 = CalculationAngels.calcTheta4(currentX, currentY, currentZ);
		
		this.interval = interval;
	}

	public boolean setSpeedForAllAngles(double newX, double newY, double newZ) {
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

			// Neue Position als aktuelle Position setzen
			currentX = newX;
			currentY = newY;
			currentZ = newZ;
			
			return true;
		} else {
			// Falls sich die Koordinaten ausserhalbt der Bewegung befinden
			System.out.println("Out of range");
			MainController.getBrickLeft().getBrick().getAudio().playTone(10000, 1500);
			return false;
		}
	}

	public void goAllAngels() {
		goTheta1();
		goTheta2();
		goTheta3();
		goTheta4();
	}

	public void stopAllAngels() {
		try {
			MainController.getHingTheta20().stop(true);
			MainController.getHingTheta21().stop(true);
			MainController.getHingTheta3().stop(true);
			MainController.getHingTheta4().stop(true);
			MainController.getHingTheta1().stop(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta1(double oldX, double oldY, double newX, double newY) {
		try {
			double oldAngle = MainController.getHingTheta1().getTachoCount() * transmissionTheta1;
			double newAngle = CalculationAngels.calcTheta1(newX, newY);
			
			if (oldAngle < 0) {
				oldAngle = oldAngle + 360;
			}
			
			double diffAngle = oldAngle - newAngle;
			
			angleTheta1 = newAngle;
		
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
			
		
			speedTheta1WithTransmission = Math.abs((speedTheta1WithoutTransmission / transmissionTheta1) / interval);
					
			MainController.getHingTheta1().setSpeed((int) speedTheta1WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		try {
			double oldAngle = ((MainController.getHingTheta20().getTachoCount() * transmissionTheta2) * -1) + 90;
			double newAngle = CalculationAngels.calcTheta2(newX, newY, newZ);

			speedTheta2WithoutTransmission = oldAngle - newAngle;
			angleTheta2 = newAngle;

			speedTheta2WithTransmission = Math.abs(speedTheta2WithoutTransmission / transmissionTheta2 / interval);
			
			MainController.getHingTheta20().setSpeed((int) speedTheta2WithTransmission);
			MainController.getHingTheta21().setSpeed((int) speedTheta2WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		try {
			double oldAngle = ((MainController.getHingTheta3().getTachoCount() * transmissionTheta3) * -1) - 90;
			double newAngle = CalculationAngels.calcTheta3(newX, newY, newZ);

			
			speedTheta3WithoutTransmission = oldAngle - newAngle;
			angleTheta3 = newAngle;
			
			speedTheta3WithTransmission = Math.abs(speedTheta3WithoutTransmission / transmissionTheta3 / interval);
			
			MainController.getHingTheta3().setSpeed((int) speedTheta3WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedTheta4(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		try {
			double oldAngle = MainController.getHingTheta4().getTachoCount() * transmissionTheta4 * -1;
			double newAngle = CalculationAngels.calcTheta4(newX, newY, newZ);

			speedTheta4WithoutTransmission = oldAngle - newAngle;
			angleTheta4 = newAngle;
			
			speedTheta4WithTransmission = Math.abs(speedTheta4WithoutTransmission / transmissionTheta4 / interval);
			
			System.out.println("Old Angle Theta 4 : " + oldAngle);
			System.out.println("New Angle Theta 4 : " + newAngle);
			System.out.println("Speed Set Motor Theta 4 with transmission: " + speedTheta4WithTransmission);
			System.out.println("Speed Set Motor Theta 4 without transmission: " + speedTheta4WithoutTransmission);
			System.out.print("---------------------------------------------------------");
			
			MainController.getHingTheta4().setSpeed((int) speedTheta4WithTransmission);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goTheta1() {
		try {
			// MainController.getHingRotation().rotateTo((int)(calculatedSpeedRotation /
			// translationAngleRotation), true);
			if (speedTheta1WithoutTransmission > 0) {
				MainController.getHingTheta1().backward();
			} else {
				MainController.getHingTheta1().forward();
			}
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

	public static double getCurrentX() {
		return currentX;
	}

	public static double getCurrentY() {
		return currentY;
	}

	public static double getCurrentZ() {
		return currentZ;
	}

	public static void setCurrentX(double currentX) {
		MoveController.currentX = currentX;
	}

	public static void setCurrentY(double currentY) {
		MoveController.currentY = currentY;
	}

	public static void setCurrentZ(double currentZ) {
		MoveController.currentZ = currentZ;
	}

	public static double getAngleTheta4() {
		return angleTheta4;
	}

	public static void setAngleTheta4(double angleTheta4) {
		MoveController.angleTheta4 = angleTheta4;
	}

	public static double getAngleTheta1() {
		return angleTheta1;
	}

	public static double getAngleTheta2() {
		return angleTheta2;
	}

	public static double getAngleTheta3() {
		return angleTheta3;
	}
}

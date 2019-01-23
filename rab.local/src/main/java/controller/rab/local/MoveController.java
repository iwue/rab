package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class MoveController {
	
	// 
	private double translationAngle1 = 1;
	private double translationAngle2 = 2;
	private double translationAngle3 = 3;
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
		setSpeedAngle1(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedAngle2(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedAngle3(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedRotation(currentX, currentY, newX, newY);
		
		System.out.println("Anglespeed 1: " + calculatedSpeedAngle1 
						+ ", Anglespeed 2: " + calculatedSpeedAngle2
						+ ", Anglespeed 3: " + calculatedSpeedAngle3
						+ ", Rotationspeed: " + calculatedSpeedRotation);
		
		currentX = newX;
		currentY = newY;
		currentZ = newZ;
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
		
		calculatedSpeedRotation = oldAngleRotation - newAngleRotation;
		
		try {
			double speed = Math.abs(calculatedSpeedRotation / translationAngleRotation / interval);
			MainController.getHingRotation().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle1() {
		try {
			if(calculatedSpeedRotation < 0) {
				MainController.getHingRotation().forward();
			} else {
				MainController.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle2() {
		try {
			if(calculatedSpeedRotation < 0) {
				MainController.getHingRotation().forward();
			} else {
				MainController.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle3() {
		try {
			if(calculatedSpeedRotation < 0) {
				MainController.getHingRotation().forward();
			} else {
				MainController.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goRotation() {
		try {
			//MainController.getHingRotation().rotateTo((int)(calculatedSpeedRotation / translationAngleRotation), true);
			if(calculatedSpeedRotation < 0) {
				MainController.getHingRotation().forward();
			} else {
				MainController.getHingRotation().backward();
			}
		} catch(Exception e) {
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

package controller.rab.local;

import rab.local.CalculationAngels;
import rab.local.Controller;

public class MoveController {
	private double translationAngle1 = 1;
	private double translationAngle2 = 2;
	private double translationAngle3 = 3;
	private double translationAngleRotation = 0.4;
	
	private double speedRotation = 0.0;
	private double speedAngle1 = 0.0;
	private double speedAngle2 = 0.0;
	private double speedAngle3 = 0.0;
	
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
	
	public void setAll(double newX, double newY, double newZ) {
		setSpeedAngle1(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedAngle2(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedAngle3(currentX, currentY, currentZ, newX, newY, newZ);
		setSpeedRotation(currentX, currentY, newX, newY);
		
		System.out.println("Anglespeed 1: " + speedAngle1 
						+ ", Anglespeed 2: " + speedAngle2
						+ ", Anglespeed 3: " + speedAngle3
						+ ", Rotationspeed: " + speedRotation);
		
		currentX = newX;
		currentY = newY;
		currentZ = newZ;
	}
	
	public void goAll() {
		goAngle1();
		goAngle2();
		goAngle3();
		goRotation();
		System.out.println("All stop");
	}
	
	public void stopAll() {
		try {
			Controller.getHingA1().stop(true);
			Controller.getHingA2().stop(true);
			Controller.getHingA3().stop(true);
			Controller.getHingRotation().stop(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSpeedAngle1(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle1(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle1(newX, newY, newZ);
		
		speedAngle1 = oldAngle - newAngle;
		
		try {
			double speed = Math.abs(speedAngle1 / translationAngle1 / interval);
			Controller.getHingA1().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle2(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle2(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle2(newX, newY, newZ);
		
		speedAngle2 = oldAngle - newAngle;
		
		try {
			double speed = Math.abs(speedAngle2 / translationAngle2 / interval);
			Controller.getHingA2().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setSpeedAngle3(double oldX, double oldY, double oldZ, double newX, double newY, double newZ) {
		double oldAngle = CalculationAngels.calcAngle3(oldX, oldY, oldZ);
		double newAngle = CalculationAngels.calcAngle3(newX, newY, newZ);
		
		speedAngle3 = oldAngle - newAngle;
		
		try {
			double speed = Math.abs(speedAngle3 / translationAngle3 / interval);
			Controller.getHingA3().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setSpeedRotation(double oldX, double oldY, double newX, double newY) {
		double oldAngleRotation = CalculationAngels.calcAngleRotation(oldX, oldY);
		double newAngleRotation = CalculationAngels.calcAngleRotation(newX, newY);
		
		speedRotation = oldAngleRotation - newAngleRotation;
		
		try {
			double speed = Math.abs(speedRotation / translationAngleRotation / interval);
			Controller.getHingRotation().setSpeed((int) speed);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void goRotation() {
		try {
			if(speedRotation < 0) {
				Controller.getHingRotation().forward();
			} else {
				Controller.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle1() {
		try {
			if(speedRotation < 0) {
				Controller.getHingRotation().forward();
			} else {
				Controller.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle2() {
		try {
			if(speedRotation < 0) {
				Controller.getHingRotation().forward();
			} else {
				Controller.getHingRotation().backward();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void goAngle3() {
		try {
			if(speedRotation < 0) {
				Controller.getHingRotation().forward();
			} else {
				Controller.getHingRotation().backward();
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

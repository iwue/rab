package rab.local;

import controller.rab.local.BrickController;

public class Calculation {
	private double currentX;	
	private double currentY;
	private double currentZ;
	
	public void calculate() {
		calculateSpeedForHingA1(x, y, z);
		calculateSpeedForHingA2(x, y, z);
		calculateSpeedForHingA3(x, y, z);
		calculateSpeedForHingRotation(x, y, z);
	}
	
	public void goto(double x, double y, double z) {
		
	}
	
	public double calculateSpeedForHingA1(double x, double y, double z) {
	}

	public double calculateSpeedForHingA2(double x, double y, double z) {
	}

	public double calculateSpeedForHingA3(double x, double y, double z) {
	}

	public double calculateSpeedForHingRotation(double x, double y, double z) {
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

package rab.local;

import java.text.Normalizer.Form;

public class CalculationAngels {
	
	// Länge der Achsen in mm
	private static double a1 = 100;
	private static double a2 = 100;
	private static double a3 = 100;
	private static double a5 = 100;
	private static double a6 = 50;
	
	
	public static double calcAngle1(double x, double y, double z) {
		double finalAngle = 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;
		
		double r1;
		double r2; 
		double r3;
		
		// Teilwinkel
		double fi1;
		double fi2;
		
		// In z Richutng
		r1 = z4 - a1;
		
		// in x-y Richtung, je nach Drehung
		r2 = Formel.Pythagoras(x4, y4) - a6;		
		
		// 2. Achse zu Achse 4
		r3 = Formel.Pythagoras(r1, r2);
		
		fi1 = Formel.GetAngleAnyTriangle(r3, a2, a5);
		fi2 = Math.atan(r1 / r2);
		
		finalAngle = fi1 + fi2;
		
		return finalAngle;
	}
	
	public static double calcAngle2(double x, double y, double z) {
		double finalAngle = 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;
		
		double r1;
		double r2; 
		double r3;
		
		// Teilwinkel
		double fi1;
		double fi2;
		double fi3;
		
		// In z Richutng
		r1 = z4 - a1;
		
		// in x-y Richtung, je nach Drehung
		r2 = Formel.Pythagoras(x4, y4) - a6;		
		
		// 2. Achse zu Achse 4
		r3 = Formel.Pythagoras(r1, r2);
		
		fi3 = Formel.GetAngleAnyTriangle(a5, a2, r3);
		
		finalAngle = fi3 - 180;
		
		return finalAngle;
	}
	
	public static double calcAngle3(double x, double y, double z) {
		double finalAngle = 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;
		
		double r1;
		double r2; 
		double r3;
		
		// Teilwinkel
		double fi1;
		double fi2;
		double fi3;
		
		double ta1;
		double ta2;
		
		// In z Richutng
		r1 = z4 - a1;
		
		// in x-y Richtung, je nach Drehung
		r2 = Formel.Pythagoras(x4, y4) - a6;		
		
		// 2. Achse zu Achse 4
		r3 = Formel.Pythagoras(r1, r2);
		
		fi1 = Formel.GetAngleAnyTriangle(r3, a2, a5);
		fi2 = Math.atan(r1 / r2);
		fi3 = Formel.GetAngleAnyTriangle(a5, a2, r3);
		
		ta1 = fi1 + fi2;
		ta2 = fi3 - 180;
		
		finalAngle = ta1 - ta2;
		
		return finalAngle;
	}
	
	public static double calcAngleRotation(double x, double y) {
		double finalAngle = 0.0;
		
		finalAngle = Math.atan(x / y);
		
		return finalAngle;
	}
}

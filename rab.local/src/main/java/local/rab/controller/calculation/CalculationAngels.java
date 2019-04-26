package local.rab.controller.calculation;

import org.j3d.maths.vector.Point3d;
import org.opencv.core.Point3;

import local.rab.config.Statics;

public class CalculationAngels {
	
	private static double a1 = Statics.getA1();
	private static double a2 = Statics.getA2();
	private static double a5 = Statics.getA5();
	private static double a6 = Statics.getA6();
	
	
	public static double calcTheta1(Point3d point) {
		return calcTheta1(point.x, point.y);
	}
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem in Winkel für die dritte Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @return Winkel der Rotation
	 */
	public static double calcTheta1(double x, double y) {
		double finalAngle = 0.0;
		double x4 = x;
		double y4 = y;
	
		if (x4 != 0 && y4 != 0) {
			// X oder Y sind ungleich 0
	
			if (x4 > 0) {
				if (y4 > 0) {
					// I Quadrant (x>0 & y>0)
					finalAngle = Math.toDegrees(Math.atan(y4 / x4));
				} else {
					// IV Quadrant (x>0 & y<0)
					finalAngle = 360 + Math.toDegrees(Math.atan(y4 / x4));
				}
			} else {
				if (y4 > 0) {
					// II Quadrant (x<0 & y>0)
					finalAngle = 180 + Math.toDegrees(Math.atan(y4 / x4));
				} else {
					// III Quadrant (x<0 & y<0)
					finalAngle = 180 + Math.toDegrees(Math.atan(y4 / x4));
				}
			}
		} else {
			// X oder Y gleich 0
			if (x4 == 0) {
				// x ist gleich 0
				if (y4 == 0) {
					// x = 0 und y = 0
					finalAngle = 0;
				} else if (y4 > 0) {
					// x = 0 und y > 0
					finalAngle = 90;
				} else {
					// x = 0 und y < 0
					finalAngle = 270;
				}
			} else {
				// Y ist gleich 0
				if (x4 > 0) {
					// x > 0 und y = 0
					finalAngle = 0;
				} else {
					// x < 0 und y = 0
					finalAngle = 180;
				}
			}
		}
	
		return finalAngle;
	}

	
	public static double calcTheta2(Point3d point) {
		return calcTheta2(point.x, point.y, point.z);
	}
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem in Winkel für die erste Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der ersten Achse
	 */
	public static double calcTheta2(double x, double y, double z) {
		double finalAngle = 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;

		double b1;
		double b2;
		double b3;

		// Teilwinkel
		double phi1;
		double phi2;

		// In z Richutng
		b1 = z4 - a1;

		// in x-y Richtung, je nach Drehung
		b2 = Formel.pythagoras(x4, y4) - a6;

		// 2. Achse zu Achse 4
		b3 = Formel.pythagoras(b1, b2);

		phi2 = Formel.getAngleAnyTriangle(a5, b3, a2);
		phi1 = Math.toDegrees(Math.atan(b1 / b2));
		// r2 kann negativ sein, deshalb kann phi1 negativ sein

		if (b2 > 0) {
			// wenn r2 grösser als 0 ist
			finalAngle = phi1 + phi2;
		} else if (b2 == 0) {
			// wenn r2 genau gleich 0 ist
			finalAngle = 90 + phi2;
		} else {
			// wenn r2 kleiner als 0 ist
			finalAngle = 180 + phi1 + phi2;
		}
		return finalAngle;
	}

	public static double calcTheta3(Point3d point) {
		return calcTheta3(point.x, point.y, point.z);
	}
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem in Winkel für die zweite Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der zweiten Achse
	 */
	public static double calcTheta3(double x, double y, double z) {
		double finalAngle = 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;

		double b1;
		double b2;
		double b3;

		// Teilwinkel
		double phi3;

		// In z Richutng
		b1 = z4 - a1;

		// in x-y Richtung, je nach Drehung
		b2 = Formel.pythagoras(x4, y4) - a6;

		// 2. Achse zu Achse 4
		b3 = Formel.pythagoras(b1, b2);

		// Phi
		phi3 = Formel.getAngleAnyTriangle(b3, a5, a2);

		// finalAngle = Teta 3
		finalAngle = phi3 - 180;

		return finalAngle;
	}

	
	public static double calcTheta4(Point3d point) {
		return calcTheta4(point.x, point.y, point.z);
	}
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem in Winkel für die dritte Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der dritte Achse
	 */
	public static double calcTheta4(double x, double y, double z) {
		double finalAngle = 0.0;
		double theta2 = calcTheta2(x, y, z);
		double theta3 = calcTheta3(x, y, z);

		finalAngle = (theta2 + theta3) * -1;

		return finalAngle;
	}
}

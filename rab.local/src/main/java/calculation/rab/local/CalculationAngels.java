package calculation.rab.local;

public class CalculationAngels {
	
	// Länge der Achsen in mm
	private static double a1 = 235;
	private static double a2 = 150;
	private static double a5 = 150;
	private static double a6 = 100;
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem
	 * in Winkel für die erste Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der ersten Achse
	 */
	public static double calcAngle1(double x, double y, double z) {
		double finalAngle 	= 0.0;

		// Endposition Effektor
		double x4 = x;
		double y4 = y;
		double z4 = z;
		
		double r1;
		double r2; 
		double r3;
		
		// Teilwinkel
		double phi1;
		double phi2;
		
		// In z Richutng
		r1 = z4 - a1;
		
		// in x-y Richtung, je nach Drehung
		r2 = Formel.pythagoras(x4, y4) - a6;		
		
		// 2. Achse zu Achse 4
		r3 = Formel.pythagoras(r1, r2);
		
		phi2 = Formel.getAngleAnyTriangle(a5, r3, a2);
		phi1 = Math.toDegrees(Math.atan(r1 / r2));
		
		finalAngle = phi1 + phi2;
		
		return finalAngle;
	}
	
	/**
	 * Umrechnung vom kartesischen Koordinatensystem
	 * in Winkel für die zweite Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der zweiten Achse
	 */
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
		double phi3;
		
		// In z Richutng
		r1 = z4 - a1;
		
		// in x-y Richtung, je nach Drehung
		r2 = Formel.pythagoras(x4, y4) - a6;	
		
		// 2. Achse zu Achse 4
		r3 = Formel.pythagoras(r1, r2);
		
		// Phi
		phi3 = Formel.getAngleAnyTriangle(r3, a5, a2);
		
		// finalAngle = Teta 3
		finalAngle = phi3 - 180;
		
		return finalAngle;
	}
	/**
	 * Umrechnung vom kartesischen Koordinatensystem
	 * in Winkel für die dritte Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @param z Z Koordinate TCP
	 * @return Winkel der dritte Achse
	 */
	public static double calcAngle3(double x, double y, double z) {
		double finalAngle = 0.0;
		double theta2 = calcAngle1(x, y, z);				
		double theta3 = calcAngle2(x, y, z);
		
		finalAngle = (theta2 + theta3) * -1;
		
		return finalAngle;
	}
	/**
	 * Umrechnung vom kartesischen Koordinatensystem
	 * in Winkel für die dritte Achse.
	 * 
	 * @param x X-Koordinate TCP
	 * @param y Y-Koordinate TCP
	 * @return Winkel der Rotation
	 */
	public static double calcAngleRotation(double x, double y) {
		double finalAngle = 0.0;
		double x4 = x;
		double y4 = y;
		
		if (x4 != 0 
		&& y4 != 0) {
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
 }

package calculation.rab.local;

public class Formel {

	/**
	 * Pythagoras kann nur in einem Koordinaten
	 * @param x X-Koordinate als Kathete
	 * @param y Y-Koordinate als Kathete
	 * @return Hypothenuse 
	 */
	public static double pythagoras(double x, double y) {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	/**
	 * 
	 * @param a gegenüberliegende Seite
	 * @param b anliegende Seite
	 * @param c anliegende Seite
	 * @return Winkel gegenüber a
	 */
	public static double getAngleAnyTriangle(double a, double b, double c) {
		double finalAngle = 0.0;

		finalAngle = Math.acos((Math.pow(b, 2) + Math.pow(c, 2) - Math.pow(a, 2)) / (2 * b * c));

		return Math.toDegrees(finalAngle);
	}
}

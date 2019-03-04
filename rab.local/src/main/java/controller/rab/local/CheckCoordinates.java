package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class CheckCoordinates {
	private static double a1 = CalculationAngels.getA1();
	private static double a2 = CalculationAngels.getA2();
	private static double a5 = CalculationAngels.getA5();
	private static double a6 = CalculationAngels.getA6();

	// TODO Ausmessen von dem Radius r2 aus Roboter
	private static double r1 = a2; // Radius 1 ist gleich Achsenlaenge 2
	private static double r2 = 60 + 10; // Radius 2 gemessen aus Roboter
	private static double r3 = a2 + a5; // Radius 3 ist gleich Summe Achsenlaenge 2 und 3
	private static double r4 = a5;

	// TODO Ausmessen von den Variablen aus Roboter
	private static double d1 = 163; // Verschiebung d1 gemessen aus Roboter
	private static double d2 = a6 + 30; // Verschiebung d2 gemessen aus Roboter
	private static double d3 = a6; // Verschiebung d3 ist gleich Achsenlaenge a6
	private static double d4 = a1 + 15; // oberhalb Basis tiefstes Niveau gemessen aus Roboter
	private static double d5 = 15 + 10; // oberhalb Boden tiefstes Niveau gemessen aus Roboter
	private static double d6 = a6 - a2;
	private static double d7 = a1;
	

	public static boolean isCoordinateValid(double x4, double y4, double z4) {
		boolean result = false;

		// b4 ist die "l�nge" des Arms aus der Vogelperspektive (Pythagoras aus x4 und
		// y4)
		double b4 = Math.sqrt(Math.pow(x4, 2) + Math.pow(y4, 2));
		
		// verschiebung des Kreiszentrum a1 und d3 (z4-a1)^2 + (r4-d3)^2
		double centreA1D3 = Math.pow((z4 - a1), 2) + Math.pow((b4 - d3), 2);
		
		// TODO Pr�fen ob die Werte richtig ausgewertet werden
		if(!Double.isNaN(CalculationAngels.calcTheta2(x4, y4, z4))) {
			if (centreA1D3 < Math.pow(r3, 2) // kleiner als Radius 3
				&& centreA1D3 > Math.pow(r2, 2)) { // gr�sser als Radius 2
				if (b4 >= a6 // rechter Deffinitionsbereich		
					&& Math.pow(z4 - (a1 - d1), 2) + Math.pow(b4 - d2, 2) > Math.pow(r1, 2) // gr�sser als Radius
					&& z4 > d5 ) { // oberhalb von d5 (�ber Boden)
					
					result = true;
				} else if (b4 < a6 // linker Deffinitionsbereich
					&& z4 > d4 // oberhalb von d4 (�ber Basis)
					&& Math.pow(b4 - d6, 2) + Math.pow(z4 - d7, 2) > Math.pow(r4, 2)) {
					
					result = true;
				} 
			}
		}
		
		return result;
	}
}

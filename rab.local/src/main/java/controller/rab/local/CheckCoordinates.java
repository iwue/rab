package controller.rab.local;

import calculation.rab.local.CalculationAngels;

public class CheckCoordinates {
	public boolean isCoordinateValid(float x4, float y4, float z4) {
		// init
		boolean result = false;
		
		double a1 = CalculationAngels.getA1();
		double a2 = CalculationAngels.getA2();
		double a5 = CalculationAngels.getA5();
		double a6 = CalculationAngels.getA6();
		
		// TODO Ausmessen von dem Radius r2 aus Roboter
		double r1 = a2;			// Radius 1 ist gleich Achsenlaenge 2
		double r2 = 0;			// Radius 2 gemessen aus Roboter
		double r3 = a2 + a5; 	// Radius 3 ist gleich Summe Achsenlaenge 2 und 3
		
		double b4 = Math.sqrt(Math.pow(x4, 2) + Math.pow(y4, 2));
		//b4 ist die "länge" des Arms aus der Vogelperspektive (Pythagoras aus x4 und y4)
		
		// TODO Ausmessen von den Variablen aus Roboter
		double d1 =	0;	// Verschiebung d1 gemessen aus Roboter
		double d2 =	0;	// Verschiebung d2 gemessen aus Roboter	
		double d3 =	a6; // Verschiebung d3 ist gleich Achsenlaenge a6	
		double d4 =	0;	// überhalb Basis tiefstes Niveau gemessen aus Roboter 	
		double d5 =	0;	// überhalb Boden tiefstes Niveau gemessen aus Roboter 	

				// system
		double centreA1D3 = Math.pow((z4 - a1), 2) + Math.pow((b4 - d3), 2);
			//verschiebung des Kreiszentrum a1 und d3 (z4-a1)^2 + (r4-d3)^2
		
		if (centreA1D3 < Math.pow((r3), 2)) {
			// innerhalb des Grossen Radius 3
			
			if (b4 >= a6
						// rechter Deffinitionsbereich
				&& Math.pow(z4 - (a1 - d1), 2) 
						+ Math.pow(b4 - d2, 2) 
						> Math.pow(r1, 2)
						// grösser als Radius 1
				&& centreA1D3 > Math.pow(r2, 2)
						//grösser als Radius 2
				&& z4 > d5) {
						// oberhalb von d5 (über Boden)
				
				result = true;
				
			} else if (b4 < a6
						// linker Deffinitionsbereich
				&& centreA1D3 > Math.pow(r2, 2)
						// grösser als Radius 2
				&& z4 > d4) {
						//oberhalb von d4 (über Basis)
				
				result = true;
				
			} else {
			
				result = false; }
		} else {
				result = false;
			}
		
		return result;
	}
	
	public void test() {
		if (isCoordinateValid(10, 20, 30)) {
			
		} else {
			
		}
	}
}

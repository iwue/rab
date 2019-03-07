package threads.rab.local;

import rab.local.RabStatics;

public class ThreadCoordinateInformations implements Runnable{

	public void run() {
		while(true) {
			// Ausgabe auf CLI
			System.out.println("X: " + RabStatics.getCurrentX()
							+ ", Y: " +  RabStatics.getCurrentY()
							+ ", Z: " +  RabStatics.getCurrentZ());
			
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

package threads.rab.local;

import rab.local.Statics;

public class ThreadCoordinateInformations implements Runnable{

	public void run() {
		while(true) {
			// Ausgabe auf CLI
			System.out.println("X: " + Statics.getCurrentX()
							+ ", Y: " +  Statics.getCurrentY()
							+ ", Z: " +  Statics.getCurrentZ());
			
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

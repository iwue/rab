package local.rab.controller.threads;

import local.rab.config.Statics;

public class ThreadCoordinateInformations implements Runnable{

	public void run() {
		while(true) {
			// Ausgabe auf CLI
			System.out.println("X: " + Statics.getNewPosition().getX()
							+ ", Y: " +  Statics.getNewPosition().getY()
							+ ", Z: " +  Statics.getNewPosition().getZ());
			
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

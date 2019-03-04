package threads.rab.local;

import controller.rab.local.MoveController;

public class threadCoordinateInformations implements Runnable{

	public void run() {
		while(true) {
			// Ausgabe auf CLI
			System.out.println("X: " + MoveController.getCurrentX()
							+ ", Y: " + MoveController.getCurrentY()
							+ ", Z: " + MoveController.getCurrentZ());
			
			try {
				Thread.sleep(500);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

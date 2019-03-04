package threads.rab.local;

import controller.rab.local.MainController;

public class threadPower implements Runnable{

	public void run() {
		while (true) {
			System.out.println("Brick Left ----------------");
			System.out.println("Battery Current : " 
					+ MainController.getBrickLeft().getBrick().getPower().getBatteryCurrent());
			System.out.println("Voltage : " 
					+ MainController.getBrickLeft().getBrick().getPower().getVoltage());
			System.out.println("Motor current : " 
					+ MainController.getBrickLeft().getBrick().getPower().getMotorCurrent());
	
			System.out.println("Brick Right ----------------");
			System.out.println("Battery current : " 
					+ MainController.getBrickRight().getBrick().getPower().getBatteryCurrent());
			System.out.println("Voltage : " 
					+ MainController.getBrickRight().getBrick().getPower().getVoltage());
			System.out.println("Motor current : " 
					+ MainController.getBrickRight().getBrick().getPower().getMotorCurrent());
			}
	}
}

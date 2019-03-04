package threads.rab.local;

import controller.rab.local.MainController;
import controller.rab.local.MoveController;

public class threadMotorInformation implements Runnable{

	public void run() {		
		while(true) {
			try {
				double theta1Tacho = MainController.getHingTheta1().getTachoCount() * 0.1;
				
				if (theta1Tacho < 0) {
					theta1Tacho = theta1Tacho + 360;
				}
				
				System.out.println("Theta 1 :\t"
						+ (theta1Tacho)
						+ "\t"
						+ MoveController.getAngleTheta1()
						+ "\t"
						+ (MoveController.getAngleTheta1() - theta1Tacho));
				
				System.out.println("Theta 2 :\t"
						+ ((MainController.getHingTheta20().getTachoCount() * -0.1) + 90)
						+ "\t"
						+ MoveController.getAngleTheta2()
						+ "\t"
						+ (MoveController.getAngleTheta2() - ((MainController.getHingTheta20().getTachoCount() * -0.1) + 90)));
				
				System.out.println("Theta 3 :\t"
						+ ((MainController.getHingTheta3().getTachoCount() * -0.1) - 90)
						+ "\t"
						+ MoveController.getAngleTheta3()
						+ "\t"
						+ (MoveController.getAngleTheta3() - ((MainController.getHingTheta3().getTachoCount() * -0.1) - 90)));
		
				System.out.println("Theta 4 :\t"
						+ ((MainController.getHingTheta4().getTachoCount() * 0.333333))
						+ "\t"
						+ MoveController.getAngleTheta4()
						+ "\t"
						+ (MoveController.getAngleTheta4() - (MainController.getHingTheta4().getTachoCount() * 0.333333)));
		
				Thread.sleep(250);
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

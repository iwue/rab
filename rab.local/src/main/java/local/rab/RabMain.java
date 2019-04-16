package local.rab;

import local.rab.config.Statics;
import local.rab.controller.MoveSimpleController;
import local.rab.controller.RabMainController;
import local.rab.controller.threads.ThreadCoordinateInformations;
import local.rab.controller.threads.ThreadEffector;
import local.rab.controller.threads.ThreadTheta1;
import local.rab.controller.threads.ThreadTheta2;
import local.rab.controller.threads.ThreadTheta3;
import local.rab.controller.threads.ThreadTheta4;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.dualshock.DualshockController;
import local.rab.devices.dualshock.DualshockSimple;

public class RabMain {
	private RabMainController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickComponentHandler brickController;
	private MoveSimpleController moveSimple;

	private Thread tTheta1;
	private Thread tTheta2;
	private Thread tTheta3;
	private Thread tTheta4;
	private Thread tThetaEffector;
	private Thread tInformation;

	private MoveTest moveTest;

	public static void main(String[] args) {
		RabMain main = new RabMain();
		main.rab();
	}

	public void rab() {
		try {
			Statics.initProperties();
			dualshockController = new DualshockController(0);
			dualshockSimple = new DualshockSimple(dualshockController.getController());
			brickController = new BrickComponentHandler();
			rabController = new RabMainController(dualshockSimple);
			moveSimple = new MoveSimpleController(dualshockSimple, brickController);
			moveTest = new MoveTest();
			moveTest.generalTestList();

			if(Statics.getMode() == 1) {
			
				tTheta1 = new Thread(new ThreadTheta1(brickController));
				tTheta1.setName("Theta 1");
				tTheta1.start();
	
				tTheta2 = new Thread(new ThreadTheta2(brickController));
				tTheta2.setName("Theta 2");
				tTheta2.start();
	
				tTheta3 = new Thread(new ThreadTheta3(brickController));
				tTheta3.setName("Theta 3");
				tTheta3.start();
			}
			
			tTheta4 = new Thread(new ThreadTheta4(brickController));
			tTheta4.setName("Theta 4");
			tTheta4.start();
			
			tThetaEffector = new Thread(new ThreadEffector(dualshockSimple, brickController));
			tThetaEffector.setName("Effector");
			tThetaEffector.start();	
			
			tInformation = new Thread(new ThreadCoordinateInformations());
			tInformation.setName("Information");
			tInformation.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		if (Statics.getMode() == 1) {
			while (true) {
				rabController.move();

				if (dualshockSimple.isPressedActionCross()) {
					break;
				}

				if (dualshockSimple.isPressedActionSquare()) {
					rabController.choreography(moveTest.getPoint3ds(), 5);
				}
			}
		} else if (Statics.getMode() == 2) {
			while (true) {

				
				moveSimple.move();

				if (dualshockSimple.isPressedActionCross()) {
					break;
				}

				if (dualshockSimple.isPressedActionSquare()) {
					rabController.choreography(moveTest.getPoint3ds(), 5);
				}
			}
		}

		System.exit(0);

	}
}

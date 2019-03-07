package rab.local;

import brick.rab.local.BrickController;
import brick.rab.local.MoveBrickController;
import controller.rab.local.MoveSimple;
import controller.rab.local.RabController;
import dualshock.rab.local.DualshockController;
import dualshock.rab.local.DualshockSimple;
import threads.rab.local.ThreadEffector;
import threads.rab.local.ThreadGyros;

public class RabMain {
	private RabController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickController brickController;
	private MoveBrickController moveBrickController;
	private MoveSimple moveSimple;
	private Thread tAutoTheta4;
	private Thread tEffector;
	
	public static void main(String[] args) {
		RabMain main = new RabMain();
		main.rab();
	}
	
	public void rab() {
		try {
			RabStatics.initProperties();
			dualshockController = new DualshockController(0);
			dualshockSimple = new DualshockSimple(dualshockController.getController());
			brickController = new BrickController();
			moveBrickController = new MoveBrickController(RabStatics.getStartX(), RabStatics.getStartY(), RabStatics.getStartZ(), brickController);
			rabController = new RabController(dualshockSimple, moveBrickController);
			moveSimple = new MoveSimple(dualshockSimple, brickController, moveBrickController);
			
			if (RabStatics.isTheta4Automatic()) {
				tAutoTheta4 = new Thread(new ThreadGyros(moveBrickController.getBrickController()));
				tAutoTheta4.setName("Effector Correction");
				tAutoTheta4.start();
			}
			
			tEffector = new Thread(new ThreadEffector(dualshockSimple, brickController));
			tEffector.setName("Effector control");
			tEffector.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//moveBrickController.goTo(304, 0, 428, 5);
		//moveBrickController.goTo(304, 0, 300, 5);
		//moveBrickController.goTo(304, 0, 428, 5);
		
		if (RabStatics.getMode() == 1) {
			while(true) {
				rabController.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
			}
		} else if (RabStatics.getMode() == 2){
			while(true) {
				moveSimple.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
			}
		}

		rabController.getMoveBrickController().getBrickController().closeHings();;
		System.exit(0);
	
	}
}

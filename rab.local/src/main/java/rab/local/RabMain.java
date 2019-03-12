package rab.local;

import brick.rab.local.BrickComponentHandler;
import brick.rab.local.BrickMoveController;
import controller.rab.local.MoveSimpleController;
import controller.rab.local.RabMainController;
import dualshock.rab.local.DualshockController;
import dualshock.rab.local.DualshockSimple;
import threads.rab.local.ThreadEffector;
import threads.rab.local.ThreadGyros;

public class RabMain {
	private RabMainController rabController;
	private DualshockController dualshockController;
	private DualshockSimple dualshockSimple;
	private BrickComponentHandler brickController;
	private BrickMoveController moveBrickController;
	private MoveSimpleController moveSimple;
	private Thread tAutoTheta4;
	private Thread tEffector;
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
			moveBrickController = new BrickMoveController(Statics.getStartX(), Statics.getStartY(), Statics.getStartZ(), brickController);
			rabController = new RabMainController(dualshockSimple, moveBrickController);
			moveSimple = new MoveSimpleController(dualshockSimple, brickController);
			moveTest = new MoveTest();
			moveTest.generalTestList();
			
			if (Statics.isTheta4Automatic()) {
				tAutoTheta4 = new Thread(new ThreadGyros(moveBrickController.getBrickController()));
				tAutoTheta4.setName("Effector correction");
				tAutoTheta4.start();
			}
			
			tEffector = new Thread(new ThreadEffector(dualshockSimple, brickController));
			tEffector.setName("Effector control");
			tEffector.start();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		if (Statics.getMode() == 1) {
			while(true) {
				rabController.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
				
				if (dualshockSimple.isPressedActionSquare()) {
					rabController.choreography(moveTest.getPoint3ds(), 5000);
				}
			}
		} else if (Statics.getMode() == 2){
			while(true) {
				moveSimple.move();
				
				if (dualshockSimple.isPressedActionCross()) {
					break;
				}
				
				if (dualshockSimple.isPressedActionSquare()) {
					rabController.choreography(moveTest.getPoint3ds(), 5000);
				}
			}
		}

		rabController.getMoveBrickController().getBrickController().closeHings();;
		System.exit(0);
	
	}
}

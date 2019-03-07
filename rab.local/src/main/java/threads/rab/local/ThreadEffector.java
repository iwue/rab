package threads.rab.local;

import brick.rab.local.BrickController;
import dualshock.rab.local.DualshockSimple;
import rab.local.RabStatics;

public class ThreadEffector implements Runnable{
	private BrickController brickController;
	private DualshockSimple dualshockSimple;
	
	public ThreadEffector(DualshockSimple dualshockSimple, BrickController brickController){
		this.brickController = brickController;
		this.dualshockSimple = dualshockSimple;
	}
	
	public void run() {
		double joystickL2;
		double joystickR2;
		double speed = 0;
		//double moveAdoption = new Double(RabConfigs.getProperty("effectorMoveAdoption"));
		
		while(true) {
			joystickL2 = (dualshockSimple.getL2() + 1)/2;
			joystickR2 = (dualshockSimple.getR2() + 1)/2;
			try {
				if (!((joystickL2 > RabStatics.getDualshockStopRange() || joystickR2 > RabStatics.getDualshockStopRange())
					&& !(joystickL2 > RabStatics.getDualshockStopRange() && joystickR2 > RabStatics.getDualshockStopRange()))) {
					 
					brickController.getEffector().stop(true);
				} else {
					if  (joystickL2 > RabStatics.getDualshockStopRange()) {
						speed = RabStatics.getMaxSpeedMotor() * -joystickL2;
						brickController.getEffector().setSpeed((int) Math.abs(speed));
					}
					
					if (joystickR2 > RabStatics.getDualshockStopRange()){
						speed = RabStatics.getMaxSpeedMotor() * joystickR2;
						brickController.getEffector().setSpeed((int) Math.abs(speed));
					}
					
					if (speed > 0 ) {
						brickController.getEffector().forward();
					} else {
						brickController.getEffector().backward();
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

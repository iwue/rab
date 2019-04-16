package local.rab.controller.threads;

import local.rab.config.Statics;
import local.rab.devices.brick.BrickComponentHandler;
import local.rab.devices.dualshock.DualshockSimple;

public class ThreadEffector implements Runnable{
	private BrickComponentHandler brickController;
	private DualshockSimple dualshockSimple;
	
	public ThreadEffector(DualshockSimple dualshockSimple, BrickComponentHandler brickController){
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
				if (!((joystickL2 > Statics.getDualshockStopRange() || joystickR2 > Statics.getDualshockStopRange())
					&& !(joystickL2 > Statics.getDualshockStopRange() && joystickR2 > Statics.getDualshockStopRange()))) {
					 
					brickController.getEffector().stop(true);
				} else {
					if  (joystickL2 > Statics.getDualshockStopRange()) {
						speed = Statics.getMaxSpeedMotor() * -joystickL2;
						brickController.getEffector().setSpeed((int) Math.abs(speed));
					}
					
					if (joystickR2 > Statics.getDualshockStopRange()){
						speed = Statics.getMaxSpeedMotor() * joystickR2;
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

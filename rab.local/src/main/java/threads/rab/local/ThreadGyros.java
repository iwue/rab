package threads.rab.local;

import brick.rab.local.BrickComponentHandler;

public class ThreadGyros implements Runnable{
	private float toleranz = 4; // in Grad 
	private BrickComponentHandler brickController;
	
	public ThreadGyros(BrickComponentHandler brickController) {
		super();
		
		this.brickController = brickController;
	}
	
	public void run() {
		try {
			brickController.getHingTheta4().setAcceleration(60000);;
			double gyrosCorrection = 0;
			while (true) {
				float[] gyrosSample = brickController.getSampleGyros().fetchSample();
				
				float gyrosAngle = gyrosSample[0];
				
				System.out.println("Gyros:  " + gyrosAngle + " degree");
				
				if(Math.abs(gyrosAngle- gyrosCorrection) > toleranz) {
					
					if (Math.abs(gyrosAngle) < 10){
						brickController.getHingTheta4().setSpeed(50);
					} else {
						brickController.getHingTheta4().setSpeed(250);
					}
					
					if (gyrosAngle > 0) {
						brickController.getHingTheta4().forward();
					} else {
						brickController.getHingTheta4().backward();
					}
				} else {
					gyrosCorrection = gyrosAngle;
					brickController.getHingTheta4().stop(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

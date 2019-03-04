package controller.rab.local;

public class EffectorCorrectionController {
	public void correction() {
		float[] sample;
		boolean isInRightPosition = false;
		try {
			 sample = MainController.getSampleGyros().fetchSample();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(!isInRightPosition) {
			if(sample[1] > 0) {
				
			} else {
				
			}
		}
	}
}

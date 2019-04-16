package local.rab.windows.panes.motors.speed;

import java.util.ArrayList;
import java.util.List;
import lejos.remote.ev3.RMIRegulatedMotor;

public class DefaultMotordataModelSpeed implements MotordataModel {

	private List<Double>[] speedData;
	
	private RMIRegulatedMotor motor;
    private int sampleCount = 100;
   private  int data = 0; 
	
	public DefaultMotordataModelSpeed(RMIRegulatedMotor motor) {
		// data
		List<Double> dataX = new ArrayList<Double>(sampleCount);
		for(int i=0; i < sampleCount; i++) {
			dataX.add((double) i );
		}
		
		// Speed
		List<Double> dataY = new ArrayList<Double>(sampleCount);
		for(int i=0; i < sampleCount; i++) {
			dataY.add((double) 0 );
		}
		
		List[] lists = new List[]{dataX, dataY};
		speedData = lists;
		
		this.motor  = motor;
	}
	
	@Override
	public List<Double>[] getSpeed() {
		try {
			//Remove First
			speedData[0].remove(0);
			speedData[1].remove(0);
			
			//Get Data from Motor
			if(motor.isMoving()) {
				data = motor.getSpeed();
			} else {
				data = 0;
			}
			
			//Add
			speedData[0].add(speedData[0].size(), speedData[0].get(speedData[0].size() -1) + 1);
			speedData[1].add(speedData[1].size(), (double) (data));
			
			return speedData;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return speedData;
	}
}
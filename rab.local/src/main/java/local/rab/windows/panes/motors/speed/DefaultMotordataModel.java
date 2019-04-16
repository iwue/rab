package local.rab.windows.panes.motors.speed;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lejos.remote.ev3.RMIRegulatedMotor;
import local.rab.RandomGenerator;

public class DefaultMotordataModel implements MotordataModel {

	private List<Double>[] speedData;
	
	private RandomGenerator motor;
    private int sampleCount = 100;
	
	public DefaultMotordataModel(RandomGenerator motor) {
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
			int data = motor.getRandom().nextInt(200);
			
			//Add
			speedData[0].add(speedData[0].size(), (double) speedData[0].get(speedData[0].size() -1) + 1);
			speedData[1].add(speedData[1].size(), (double) (data));
			
			return speedData;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return speedData;
	}
}
package local.rab.windows.panes.coordinates;

import java.util.ArrayList;
import java.util.List;
import local.rab.config.Statics;

public class DefaultCoordinateModelZ implements CoordinateModel {

	private List<Double>[][] speedData;
	
	
	public DefaultCoordinateModelZ() {
		// data
		List<Double> dataZX = new ArrayList<Double>(2);
		dataZX.add(-1000.0);
		dataZX.add(1000.0);
		
		List<Double> dataZY = new ArrayList<Double>(2);
		dataZY.add(0.0);
		dataZY.add(0.0);
		
		
		List[][] lists = new List[][]{{dataZX, dataZY}};
		speedData = lists;
		speedData = getCoordinateXY();
		
	}
	
	@Override
	public List<Double>[][] getCoordinateXY() {
		try {
			speedData[0][1].set(0, Statics.getNewPosition().z);
			speedData[0][1].set(1, Statics.getNewPosition().z);
			return speedData;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return speedData;
	}
}
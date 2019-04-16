package local.rab.windows.panes.coordinates;

import java.util.ArrayList;
import java.util.List;
import local.rab.config.Statics;

public class DefaultCoordinateModelXY implements CoordinateModel {

	private List<Double>[][] speedData;
	
	
	public DefaultCoordinateModelXY() {
		// data
		List<Double> dataXX = new ArrayList<Double>(2);
		dataXX.add(0.0);
		dataXX.add(0.0);
		
		List<Double> dataXY = new ArrayList<Double>(2);
		dataXY.add(1000.0);
		dataXY.add(-1000.0);
		
		List<Double> dataYX = new ArrayList<Double>(2);
		dataYX.add(-1000.0);
		dataYX.add(1000.0);
		
		List<Double> dataYY = new ArrayList<Double>(2);
		dataYY.add(0.0);
		dataYY.add(0.0);
		
		
		List[][] lists = new List[][]{{dataXX, dataXY}, {dataYX, dataYY}};
		speedData = lists;
		speedData = getCoordinateXY();
		
	}
	
	@Override
	public List<Double>[][] getCoordinateXY() {
		try {
			speedData[0][0].set(0, Statics.getNewPosition().getX());
			speedData[0][0].set(1, Statics.getNewPosition().getX());
			
			speedData[1][1].set(0, Statics.getNewPosition().getY());
			speedData[1][1].set(1, Statics.getNewPosition().getY());
			
			return speedData;		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return speedData;
	}
}
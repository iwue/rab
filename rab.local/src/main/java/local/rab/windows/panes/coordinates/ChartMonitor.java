package local.rab.windows.panes.coordinates;

import java.util.List;

public interface ChartMonitor {
	public CoordinateModel getModel();
	public void updateData(List<Double>[][] data);
}

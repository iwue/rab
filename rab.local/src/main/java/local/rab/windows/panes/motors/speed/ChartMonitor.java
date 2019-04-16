package local.rab.windows.panes.motors.speed;

import java.util.List;

public interface ChartMonitor {
	public MotordataModel getModel();
	public void updateData(List<Double>[] data);
}

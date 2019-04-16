package local.rab.windows.panes.motors.speed;

import java.util.List;

import javax.swing.JPanel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

public class SeriesChartPane extends JPanel implements ChartMonitor{
	private MotordataModel model;
	private XYChart chart;

	public SeriesChartPane(MotordataModel model, String title) {
		this.model = model;
		
		chart = new XYChartBuilder()
				.height(300)
				.width(1500)
				.title(title)
				.build();
		
		List<Double>[] speedData = model.getSpeed();
		chart.addSeries("Speed", speedData[0], speedData[1]);
		
		XChartPanel<XYChart> chartPane = new XChartPanel<>(chart);
        add(chartPane);
		
		UpdateWorker worker = new UpdateWorker(this);
		worker.execute();
	}
	
	@Override
	public MotordataModel getModel() {
		return this.model;
	}

	@Override
	public void updateData(List<Double>[] data) {
		chart.updateXYSeries("Speed", data[0], data[1], null);
		repaint();
	}
}

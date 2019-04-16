package local.rab.windows.panes.motors.speed;

import java.util.List;

import javax.swing.JPanel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

public class SeriesChartPane extends JPanel implements ChartMonitor{
	private MotordataModel model;
	private XYChart chart;

	public SeriesChartPane(MotordataModel model, String title, double min, double max) {
		this.model = model;
		
		chart = new XYChartBuilder()
				.title(title)
				.width(1500)
				.height(300)
				.theme(ChartTheme.GGPlot2)
				.build();
		chart.getStyler().setPlotMargin(0);
		chart.getStyler().setPlotContentSize(.95);

		List<Double>[] speedData = model.getSpeed();
		chart.addSeries("Speed", speedData[0], speedData[1]);
		chart.getStyler().setYAxisMin(min);
		chart.getStyler().setYAxisMax(max);
		chart.getStyler().setHasAnnotations(false);
		
		XChartPanel<XYChart> chartPane = new XChartPanel<>(chart);
        add(chartPane);
        
        chartPane.setAutoscrolls(true);
		
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

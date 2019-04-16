package local.rab.windows.panes.coordinates;

import java.util.List;

import javax.swing.JPanel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

public class SeriesChartPaneXYZ extends JPanel implements ChartMonitor{
	private CoordinateModel model;
	private XYChart chart;

	public SeriesChartPaneXYZ(CoordinateModel model, String title, double minX, double maxX, double minY, double maxY) {
		this.model = model;
		
		chart = new XYChartBuilder()
				.title(title)
				.width(420)
				.height(350)
				.theme(ChartTheme.GGPlot2)
				.build();
		
		chart.getStyler().setPlotMargin(0);
		chart.getStyler().setPlotContentSize(.95);

		List<Double>[][] coodiantedata = model.getCoordinateXY();
		
		if (coodiantedata.length == 2) {
			chart.addSeries("X", coodiantedata[0][0], coodiantedata[0][1]);
			chart.addSeries("Y", coodiantedata[1][0], coodiantedata[1][1]);
		} else if (coodiantedata.length == 1) {
			chart.addSeries("Z", coodiantedata[0][0], coodiantedata[0][1]);
		}

		chart.getStyler().setXAxisMin(minX);
		chart.getStyler().setXAxisMax(maxX);
		chart.getStyler().setYAxisMin(minY);
		chart.getStyler().setYAxisMax(maxY);
		
		chart.getStyler().setHasAnnotations(false);
		
		XChartPanel<XYChart> chartPane = new XChartPanel<>(chart);
        add(chartPane);
        
        chartPane.setAutoscrolls(true);
		
		UpdateWorker worker = new UpdateWorker(this);
		worker.execute();
	}
	
	@Override
	public CoordinateModel getModel() {
		return this.model;
	}

	@Override
	public void updateData(List<Double>[][] data) {
		if (data.length == 2) {
			chart.updateXYSeries("X", data[0][0], data[0][1], null);
			chart.updateXYSeries("Y", data[1][0], data[1][1], null);
		} else if (data.length == 1) {
			chart.updateXYSeries("Z", data[0][0], data[0][1], null);
		}
		repaint();
	}
}

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This class is used to design a Line Series viewer by extending and 
 * implementing a concrete design of the abstract viewer class.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see AbstractViewer
 */
public class ViewerLine extends AbstractViewer{
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenModelRef A reference to the model storing the data.
	 * @param givenAnalysisType The type of analysis being performed.
	 */	
	public ViewerLine(Model givenModelRef, int givenAnalysisType) { 
		modelRef = givenModelRef;
		this.analysisType = givenAnalysisType;
	}
	
	@Override
	/**
	 * This function is used to display the viewer onto the main UI.
	 *
	 * @param givenPanel The window panel on which to display the viewer.
	 */
	public void display(JPanel givenPanel) {
		createLineChart(givenPanel);
	}
	
	@Override
	/**
	 * This function is used to update the user parameters according to 
	 * the current options selected by the user.
	 *
	 * @param givenParam The user parameter on the main UI.
	 */
	public void update(UserParameters givenParam) {
		this.viewerState = modelRef.getState();
		this.analysisType = givenParam.getAnalysisType();
		this.startYear = givenParam.getStartYear();
		this.endYear = givenParam.getEndYear();	
	}
	
	@Override
	/**
	 * This function is used to set the viewer type to a valid option.
	 *
	 * @param givenGraphType The new type of graph.
	 */
	public void setGraphType(String givenGraphType) {
		this.graphType = givenGraphType;
	}
	
	@Override
	/**
	 * This function is used to get the current viewer type.
	 *
	 * @param givenGraphType The required type of graph.
	 * @return The name of the viewer type.
	 */
	public String getGraphType() {
		return this.graphType;
	}

	/**
	 * This function is used to plot the Line Chart viewer based on the
	 * analysis type.
	 *
	 * @param givenPanel The panel on which to display the viewer.
	 */
	private void createLineChart(JPanel givenPanel) {
		JFreeChart finaLineChart = null;
		
		// Determine Analysis Type.
		if (this.analysisType == 1) {
			finaLineChart = plotAnalysisOne();	
		}
		
		else if (this.analysisType == 2) {
			finaLineChart = plotAnalysisTwo();	
		}
		
		else if (this.analysisType == 3) {
			finaLineChart = plotAnalysisThree();
		}
		
		else if (this.analysisType == 4) {
			finaLineChart = plotAnalysisFour();
		}
		
		else if (this.analysisType == 5) {
			finaLineChart = plotAnalysisFive();
		}
		
		else if (this.analysisType == 6) {
			finaLineChart = plotAnalysisSix();	
		}
		
		else if (this.analysisType == 7) {
			finaLineChart = plotAnalysisSeven();
		}
		
		else if (this.analysisType == 8) {
			finaLineChart = plotAnalysisEight();
		}
		
		else {
			System.err.println("Error createLine could not determine analysis"
					+ " type!");
		}
		
		ChartPanel chartPanel = new ChartPanel(finaLineChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		givenPanel.add(chartPanel);
	}

	/**
	 * This function is used to plot the Line Chart for the analysis of CO2 
	 * Emissions vs Energy Use vs PM2.5 Air Pollution.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisOne() {
		XYSeries cO2Series = createXYSeries(viewerState.get(0), 
				"CO2 Emissions (metric tons/capita)");
		XYSeries energySeries = createXYSeries(viewerState.get(1),
				"Energy Use (kg of oil/capita)");
		XYSeries pm25Series = createXYSeries(viewerState.get(2),
				"PM2.5 (micrograms/m^3)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		
		dataset.addSeries(cO2Series);
		dataset.addSeries(pm25Series);
		
		dataset2.addSeries(energySeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("CO2 Emissions vs"
				+ " Energy Use vs PM2.5 Air Pollution", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		renderer2.setSeriesPaint(0, Color.BLUE);
		renderer2.setSeriesStroke(0, new BasicStroke(2.0f));
		
		XYPlot plot= lineChart.getXYPlot();
		plot.setRenderer(0,renderer);
		plot.setRenderer(1,renderer2);
		plot.setDataset(0,dataset);
		plot.setDataset(1,dataset2);
		plot.setRangeAxis(0,new NumberAxis(""));
		plot.setRangeAxis(1,new NumberAxis("kg of oil"));
		
		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
		
		String chartTitle = "CO2 Emissions vs Energy Use vs PM2.5 Air Pollution";
		
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of PM2.5 
	 * Air Pollution vs Forest Area.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisTwo(){
		XYSeries pm25Series = createXYSeries(viewerState.get(0),
				"PM2.5 (micrograms/m^3)");
		XYSeries forestAreaSeries = createXYSeries(viewerState.get(1),
				"Forest Area (% of Land)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		
		dataset.addSeries(pm25Series);
		dataset2.addSeries(forestAreaSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("PM2.5 Air "
				+ "Pollution vs Forest Area", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		renderer2.setSeriesPaint(0, Color.BLUE);
		renderer2.setSeriesStroke(0, new BasicStroke(2.0f));

		XYPlot plot = lineChart.getXYPlot();
		plot.setRenderer(0,renderer);
		plot.setRenderer(1,renderer2);
		plot.setDataset(0,dataset);
		plot.setDataset(1,dataset2);
		plot.setRangeAxis(1,new NumberAxis("%"));
		
		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
		
		String chartTitle = "PM2.5 Air Pollution vs Forest Area";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Ratio of
	 * CO2 Emissions and GDP per capita.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisThree() {
		XYSeries ratioCO2GDP = createXYSeries(viewerState.get(0),
				"CO2 / GDP (metric tons/US$)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		dataset.addSeries(ratioCO2GDP);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Ratio of CO2 "
				+ "Emissions and GDP per Capita", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = lineChart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setDataset(dataset);

		String chartTitle = "Ratio of CO2 Emissions and GDP per Capita";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Average 
	 * Forest Area for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFour() {
		XYSeries avgForestAreaSeries = createXYSeries(viewerState.get(0),
				"Forest Area (% of Land)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(avgForestAreaSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Average Forest"
				+ " Area", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = lineChart.getXYPlot();
		
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setDataset(dataset);
		
		String chartTitle = "Average Forest Area";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Average 
	 * of Government expenditure on education for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFive() {
		XYSeries avgGovExpSeries = createXYSeries(viewerState.get(0),
				"Government Expenditure (% of GDP)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(avgGovExpSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Average Gov."
				+ " Expenditure on Education", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = lineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setDataset(dataset);
		
		String chartTitle = "Average Gov. Expenditure on Education";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Hospital
	 * Beds per 1000 people vs Current Health Expenditure per 1000 people.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisSix() {
		XYSeries hospBedsSeries = createXYSeries(viewerState.get(0),
				"Hospital Beds Per 1000");
		XYSeries healthExpSeries = createXYSeries(viewerState.get(1), 
				"Health Expenditure Per 1000 (US$)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		dataset.addSeries(hospBedsSeries);
		dataset.addSeries(healthExpSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Ratio Of Hospital Beds"
				+ " and Current Health Expenditure per 1000", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = lineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setDataset(dataset);
		
		String chartTitle = "Ratio Of Hospital Beds and Current Health Expenditure"
				+ " per 1000";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Current 
	 * Health Expenditure per capita vs Mortality Rate.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisSeven() {
		XYSeries currHealthExpSeries = createXYSeries(viewerState.get(0),
				"Current Health Expenditure Per Capita (US$)");
		XYSeries mortRateSeries = createXYSeries(viewerState.get(1), 
				"Mortality Rate Per 1000 Births");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeriesCollection dataset2 = new XYSeriesCollection();
		
		dataset.addSeries(currHealthExpSeries);
		dataset2.addSeries(mortRateSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Current Health"
				+ " Expenditure per Capita vs Mortality Rate", "Year", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
		
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		renderer2.setSeriesPaint(0, Color.BLUE);
		renderer2.setSeriesStroke(0, new BasicStroke(2.0f));
		
		XYPlot plot = lineChart.getXYPlot();
		plot.setRenderer(0,renderer);
		plot.setRenderer(1,renderer2);
		plot.setDataset(0,dataset);
		plot.setDataset(1,dataset2);
		plot.setRangeAxis(1,new NumberAxis("US$"));
		
		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
		
		String chartTitle = "Current Health Expenditure per Capita vs Mortality"
				+ " Rate";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to plot the Line Chart for the analysis of Ratio of
	 * Government Expenditure on Education and Current Health Expenditure.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisEight() {
		XYSeries ratioGovExpHealthSeries = createXYSeries(viewerState.get(0),
				"Gov. Expenditure (% of GDP)/Health Expenditure (% of GDP)");
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		dataset.addSeries(ratioGovExpHealthSeries);
		
		JFreeChart lineChart = ChartFactory.createXYLineChart("Ratio of Gov. "
				+ "Expenditure on Education and Current Health Expenditure", 
				"Year", "", dataset, PlotOrientation.VERTICAL, true, true, false);

		XYPlot plot = lineChart.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		
		plot.setRenderer(renderer);
		plot.setDataset(dataset);
		
		String chartTitle = "Ratio of Gov. Expenditure on Education vs Current"
				+ " Health Expenditure";
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);

		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		lineChart.getLegend().setFrame(BlockBorder.NONE);

		lineChart.setTitle(
				new TextTitle(chartTitle, new Font("Serif", java.awt.Font.BOLD, 18)));
		
		return lineChart;
	}
	
	/**
	 * This function is used to produce the XY Series data points based on 
	 * the acquired data.
	 *
	 * @param dataList The data series acquired from the World-Bank.
	 * @param seriesTitle The name of the data series being plotted.
	 * @return A XYSeries object depicting the data.
	 */
	private XYSeries createXYSeries(ArrayList<DataContainer> dataList,
			String seriesTitle) {
		XYSeries tempSeries = new XYSeries(seriesTitle);
		
		for(int i = 0; i < dataList.size(); i++) {
			tempSeries.add(dataList.get(i).getYear(),dataList.get(i).getValue());
		}
		
		return tempSeries;
	}
	
	private
	/**
	 * Reference to the model being used to store the processed data.
	 */
	static Model modelRef;
	
	/**
	 * Reference to the concatenated data received from the World-Bank.
	 */
	ArrayList <ArrayList<DataContainer>> viewerState;
	
	/**
	 * Reference to the name of the viewer.
	 */
	String graphType;
	
	/**
	 * Reference to the type of analysis being executed.
	 */
	int analysisType;
	
	/**
	 * Reference to the start year of the analysis being executed.
	 */
	int startYear;
	
	/**
	 * Reference to the end year of the analysis being executed.
	 */
	int endYear;
}

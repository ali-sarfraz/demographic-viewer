import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

/**
 * This class is used to design a Time Series viewer by extending and 
 * implementing a concrete design of the abstract viewer class.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see AbstractViewer
 */
public class ViewerTime extends AbstractViewer{
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenModelRef A reference to the model storing the data.
	 * @param givenAnalysisType The type of analysis being performed.
	 */	
	public ViewerTime(Model givenModelRef, int givenAnalysisType) { 
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
		createTimeSeriesPlot(givenPanel);
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
	 * This function is used to plot the Time Series viewer based on the
	 * analysis type.
	 *
	 * @param givenPanel The panel on which to display the viewer.
	 */
	private void createTimeSeriesPlot(JPanel givenPanel) {
		JFreeChart timeSeriesChart = null;
				
		// Determine Analysis Type
		if(this.analysisType == 1) {
			timeSeriesChart = plotAnalysisOne();
		}
		
		else if (this.analysisType == 2) {
			timeSeriesChart = plotAnalysisTwo();
		}
		
		else if (this.analysisType == 3) {
			timeSeriesChart = plotAnalysisThree();
		}
		
		else if (this.analysisType == 4) {
			timeSeriesChart = plotAnalysisFour();
		}
		
		else if(this.analysisType == 5) {
			timeSeriesChart = plotAnalysisFive();
		}
		
		else if (this.analysisType == 6) {
			timeSeriesChart = plotAnalysisSix();
		}
		
		else if (this.analysisType == 7) {
			timeSeriesChart = plotAnalysisSeven();
		}
		
		else if (this.analysisType == 8) {
			timeSeriesChart = plotAnalysisEight();
		}
		
		else {
			System.err.println("Error createTimeSeriesPlot could not determine"
					+ " analysis type!");
		}
		
		ChartPanel chartPanel = new ChartPanel(timeSeriesChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		givenPanel.add(chartPanel);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of CO2 
	 * Emissions vs Energy Use vs PM2.5 Air Pollution.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisOne() {
		TimeSeries cO2Series = createTimeSeries(viewerState.get(0),
				"CO2 Emissions (metric tons/capita");
		TimeSeries energyUseSeries = createTimeSeries(viewerState.get(1),
				"Energy Use (kg of oil/capita)");
		TimeSeries pm25Series = createTimeSeries(viewerState.get(2),
				"PM2.5 (micrograms/m^3)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		
		dataset.addSeries(cO2Series);
		dataset2.addSeries(energyUseSeries);
		dataset.addSeries(pm25Series);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);
		splinerenderer1.setSeriesShapesVisible(1,false);
		splinerenderer2.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1,new NumberAxis("kg of Oil"));

		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
	
		return new JFreeChart("CO2 Emissions vs Energy Use vs PM2.5 Air "
				+ "Pollution", new Font("Serif", java.awt.Font.BOLD, 18),
				plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of PM2.5 
	 * Air Pollution vs Forest Area.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisTwo() {
		TimeSeries pm25Series = createTimeSeries(viewerState.get(0),
				"PM2.5 (micrograms/m^3)");
		TimeSeries forestAreaSeries = createTimeSeries(viewerState.get(1),
				"Forest Area (% of Land)");

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		
		dataset.addSeries(pm25Series);
		dataset2.addSeries(forestAreaSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);
		splinerenderer2.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1,new NumberAxis("%"));

		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
		
		return new JFreeChart("PM2.5 Air Pollution vs Forest Area",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Ratio of
	 * CO2 Emissions and GDP per capita.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisThree() {
		TimeSeries ratioCO2GDP = createTimeSeries(viewerState.get(0),
				"CO2 / GDP (metric tons/US$)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(ratioCO2GDP);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);
		
		return new JFreeChart("Ratio of CO2 Emissions and GDP per Capita",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Average 
	 * Forest Area for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFour() {
		TimeSeries avgForestAreaSeries = createTimeSeries(viewerState.get(0),
				"Forest Area (% of Land)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(avgForestAreaSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);

		return new JFreeChart("Average Forest Area",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Average 
	 * of Government expenditure on education for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFive() {
		TimeSeries avgGovExpSeries = createTimeSeries(viewerState.get(0),
				"Government Expenditure (% of GDP)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(avgGovExpSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);
		
		return new JFreeChart("Average Gov. Expenditure on Education",
				new Font("Serif", java.awt.Font.BOLD, 18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Hospital
	 * Beds per 1000 people vs Current Health Expenditure per 1000 people.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisSix() {
		TimeSeries hospBedsSeries = createTimeSeries(viewerState.get(0),
				"Hospital Beds Per 1000");
		TimeSeries healthExpSeries = createTimeSeries(viewerState.get(1),
				"Health Expenditure Per 1000 (US$)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(hospBedsSeries);
		dataset.addSeries(healthExpSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);
		splinerenderer1.setSeriesShapesVisible(1,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);
		
		return new JFreeChart("Ratio Of Hospital Beds and Current Health"
				+ " Expenditure per 1000",new Font("Serif", java.awt.Font.BOLD,
						18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Current 
	 * Health Expenditure per capita vs Mortality Rate.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisSeven() {
		TimeSeries currHealthExpSeries = createTimeSeries(viewerState.get(0),
				"Current Health Expenditure Per Capita (US$)");
		TimeSeries mortRateSeries = createTimeSeries(viewerState.get(1), 
				"Mortality Rate Per 1000");

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeriesCollection dataset2 = new TimeSeriesCollection();
		dataset2.addSeries(currHealthExpSeries);
		dataset.addSeries(mortRateSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		XYSplineRenderer splinerenderer2 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);
		splinerenderer2.setSeriesShapesVisible(0,false);

		plot.setDataset(0, dataset);
		plot.setRenderer(0, splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));
		
		plot.setDataset(1, dataset2);
		plot.setRenderer(1, splinerenderer2);
		plot.setRangeAxis(1,new NumberAxis("US$"));

		plot.mapDatasetToRangeAxis(0, 0);
		plot.mapDatasetToRangeAxis(1, 1);
		

		return new JFreeChart("Current Health Expenditure per Capita vs "
				+ "Mortality Rate", new Font("Serif", java.awt.Font.BOLD,
						18), plot, true);
	}
	
	/**
	 * This function is used to plot the Time Series for the analysis of Ratio of
	 * Government Expenditure on Education and Current Health Expenditure.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisEight() {
		TimeSeries govExpHealthExpSeries = createTimeSeries(viewerState.get(0),
				"Gov. Expenditure (% of GDP)/Health Expenditure (% of GDP)");
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(govExpHealthExpSeries);
		
		XYPlot plot = new XYPlot();
		XYSplineRenderer splinerenderer1 = new XYSplineRenderer();
		splinerenderer1.setSeriesShapesVisible(0,false);

		plot.setDataset(dataset);
		plot.setRenderer(splinerenderer1);
		DateAxis domainAxis = new DateAxis("Year");
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(new NumberAxis(""));

		plot.mapDatasetToRangeAxis(0, 0);
		
		return new JFreeChart("Ratio of Gov. Expenditure on Education vs Current"
				+ " Health Expenditure", new Font("Serif", java.awt.Font.BOLD, 
						18), plot, true);
	}
	
	/**
	 * This function is used to produce the Time Series data points based on 
	 * the acquired data.
	 *
	 * @param dataList The data series acquired from the World-Bank.
	 * @param seriesTitle The name of the data series being plotted.
	 * @return A TimeSeries object depicting the data.
	 */
	private TimeSeries createTimeSeries(ArrayList<DataContainer> dataList,
			String seriesTitle) {
		TimeSeries series = new TimeSeries(seriesTitle);
		for (int i = 0; i < dataList.size(); i++) {
			series.add(new Year(dataList.get(i).getYear()), 
					dataList.get(i).getValue());
		}
		
		return series;
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

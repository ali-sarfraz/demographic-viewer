import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * This class is used to design a Pie Chart viewer by extending and 
 * implementing a concrete design of the abstract viewer class.
 *
 * @author Mohammad Iqbal.
 * @version	1.0.0
 * @see AbstractViewer
 */
public class ViewerPie extends AbstractViewer {
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenModelRef A reference to the model storing the data.
	 * @param givenAnalysisType The type of analysis being performed.
	 */	
	public ViewerPie(Model givenModelRef, int givenAnalysisType) { 
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
		createPieChart(givenPanel);
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
	 * This function is used to plot the Pie Chart viewer based on the
	 * valid analysis type.
	 *
	 * @param givenPanel The panel on which to display the viewer.
	 */
	private void createPieChart(JPanel givenPanel) {
		JFreeChart pieChart = null;

		// Determine Analysis Type.
		if (this.analysisType == 1) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else if (this.analysisType == 2) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else if (this.analysisType == 3) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else if (this.analysisType == 4) {
			pieChart = plotAnalysisFour();	
		}
		
		else if(this.analysisType == 5) {
			pieChart = plotAnalysisFive();	
		}
		
		else if (this.analysisType == 6) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else if (this.analysisType == 7) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else if (this.analysisType == 8) {
			System.err.println("Error Pie Chart incompatible with this "
					+ "analysis type!");
		}
		
		else {
			System.err.println("Error createTimeSeriesPlot could not "
					+ "determine analysis type!");
		}
		
		ChartPanel chartPanel = new ChartPanel(pieChart);
		chartPanel.setPreferredSize(new Dimension(400, 300));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		givenPanel.add(chartPanel);
	}
	
	/**
	 * This function is used to plot the Pie Chart for the analysis of Average 
	 * Forest Area for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFour() {
		DefaultCategoryDataset avgForestAreaDataset = 
				new DefaultCategoryDataset();
		
		// Add values to these datasets.
		addDatasetPie(avgForestAreaDataset,viewerState.get(0),
				"Forest Area (% of Land)");			
	
		JFreeChart pieChart = ChartFactory.createMultiplePieChart("Average Forest Area",
				avgForestAreaDataset, TableOrder.BY_COLUMN, true, true, false);
		
		return pieChart;
	}
	
	/**
	 * This function is used to plot the Pie Chart for the analysis of Average 
	 * of Government expenditure on education for the selected years.
	 *
	 * @return A JFreeChart plot containing the data series for the analysis.
	 */
	private JFreeChart plotAnalysisFive() {
		DefaultCategoryDataset avgGovExpDataset = new DefaultCategoryDataset();
		
		// Add values to these datasets.
		addDatasetPie(avgGovExpDataset,viewerState.get(0),
				"Government Expenditure (% of GDP)");			
	
		JFreeChart pieChart = ChartFactory.createMultiplePieChart("Average Gov."
				+ " Expenditure on Education", avgGovExpDataset,
				TableOrder.BY_COLUMN, true, true, false);
		
		return pieChart;
	}

	
	/**
	 * This function is used to populate the Dataset data points based on 
	 * the acquired data.
	 *
	 *@param givenDataset The data set to be populated
	 * @param dataList The data series acquired from the World-Bank.
	 * @param seriesTitle The name of the data series being plotted.
	 * @return A TimeSeries object depicting the data.
	 */
	private void addDatasetPie(DefaultCategoryDataset givenDataset, 
			ArrayList<DataContainer> dataList, String seriesTitle) {
		for(int i = 0; i < dataList.size(); i++) {
			givenDataset.addValue(dataList.get(i).getValue(),
					Integer.toString(dataList.get(i).getYear()),seriesTitle);
		}
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

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class is used to design a Report viewer by extending and 
 * implementing a concrete design of the abstract viewer class.
 *
 * @author Sanjayan Kulendran.
 * @version	1.0.0
 * @see AbstractViewer
 */
public class ViewerReport extends AbstractViewer {
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenModelRef A reference to the model storing the data.
	 * @param givenAnalysisType The type of analysis being performed.
	 */	
	public ViewerReport(Model givenModelRef, int givenAnalysisType) { 
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
		createReport(givenPanel);
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
	 * This function is used to plot the Report viewer based on the
	 * analysis type.
	 *
	 * @param givenPanel The panel on which to display the viewer.
	 */
	private void createReport(JPanel givenPanel) {
		JTextArea report = new JTextArea();
		report.setEditable(false);
		report.setPreferredSize(null);
		report.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		report.setBackground(Color.white);
		String reportMessage = makeReportMessage(viewerState);	
		report.setText(reportMessage);
		JScrollPane outputScrollPane = new JScrollPane(report);
		outputScrollPane.setPreferredSize(null);
		givenPanel.add(outputScrollPane);
	}
	
	/**
	 * This function is used to create the Report message based on the current
	 * analysis type.
	 *
	 * @param mainDataList All the necessary dataseries obtained by WorldBank.
	 * @return A String containing the entire Report message.
	 */
	private String makeReportMessage(ArrayList <ArrayList<DataContainer>> mainDataList) {
		ArrayList<String> tempSeriesNames = getReportSeriesNames();
		
		// String array that contains respective title for each analysis type.
		String[] titleArray = {"CO2 Emissions vs Energy Use vs PM2.5 Air Pollution",
				"PM2.5 Air Pollution vs Forest Area",
				"Ratio of CO2 Emissions & GDP per Capita",
				"Average Forest Area",
				"Average Gov. Expenditure on Education",
				"Ratio Of Hospital Beds and Current Health Expenditure per 1000",
				"Current Health Expenditure per Capita vs Mortality Rate",
				"Ratio of Gov. Expenditure on Education & Current Health Expenditure"};
		
		// Constructing final message that Report viewer will plot.
		String finalMsg = titleArray[this.analysisType -1] + "\n" + 
				"========================================="+ "\n";
		
		for (int count = this.endYear; count >= this.startYear; count--) {
			finalMsg = finalMsg + "Year " + Integer.toString(count) + ":\n";
			
			// Traverse each array list in dataList and see if it contains data
			// for current year (count).
			for (int i = 0; i < mainDataList.size(); i++) {
				for (int j = 0; j < mainDataList.get(i).size(); j++) {
					if (mainDataList.get(i).get(j).getYear() == count) {
						finalMsg = finalMsg + "\t" + 
						tempSeriesNames.get(i) + " => " + 
						Float.toString(mainDataList.get(i).get(j).getValue()) +
						"\n";
					}
				}
			}
			
			// Newline after each year.
			finalMsg = finalMsg + "\n";
		}
		
		tempSeriesNames = null;
		return finalMsg;
	}

	/**
	 * This function is used to create the series titles of a Report message
	 * based on the current analysis type.
	 *
	 * @return An ArrayList of Strings containing respective series titles.
	 */
	private ArrayList<String> getReportSeriesNames() {
		ArrayList<String> seriesNames = new ArrayList<String>();
		
		// Determine Analysis Type.
		if (this.analysisType == 1) {
			seriesNames.add("CO2 Emissions (metric tons/capita)");
			seriesNames.add("Energy Use (kg of oil/capita)");
			seriesNames.add("PM2.5 (micrograms/m^3)");
		}
		
		else if (this.analysisType == 2) {
			seriesNames.add("PM2.5 (micrograms/m^3)");
			seriesNames.add("Forest Area (% of Land)");
		}
		
		else if (this.analysisType == 3) {
			seriesNames.add("CO2 / GDP (metric tons/US$)");
		}
		
		else if (this.analysisType == 4) {
			seriesNames.add("Forest Area (% of Land)");
		}
		
		else if (this.analysisType == 5) {
			seriesNames.add("Government Expenditure (% of GDP)");
		}
		
		else if (this.analysisType == 6) {
			seriesNames.add("Hospital Beds/1000");
			seriesNames.add("Health Expenditure/1000 (US$)");
		}
		
		else if(this.analysisType == 7) {
			seriesNames.add("Current Health Expenditure/Capita (US$)");
			seriesNames.add("Mortality Rate/1000 Births");
		}
		
		else if(this.analysisType == 8) {
			seriesNames.add("Gov. Expenditure/Health Expenditure");
		}
		
		else {
			seriesNames.add("Unknown");
		}
		
		return seriesNames;
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

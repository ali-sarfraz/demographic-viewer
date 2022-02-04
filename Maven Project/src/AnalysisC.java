import java.util.ArrayList;

/**
 * This class is used to define an implementation of the strategy for computing
 * the analysis. This particular class implements the analysis type of Ratio of
 * CO2 Emissions and GDP per capita.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see Strategy
 */
public class AnalysisC implements Strategy {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public AnalysisC() {		
		ratioCo2GdpData = new ArrayList<DataContainer>();
		processedDataList = new ArrayList <ArrayList<DataContainer>>();
	}
		
	@Override
	/**
	 * This function is overridden to implement the specific strategy used 
	 * for this type of analysis.
	 * 
	 * @param country The user chosen country for analysis.
	 * @param givenStartyear The user chosen start year for analysis.
	 * @param givenEndYear The user chosen end year for analysis.
	 * @param modelRef A reference to the model used to store data.
	 * @return True if the analysis was completed successfully, false otherwise.
	 */
	public boolean doAnalysis(String country, int givenStartyear,
			int givenEndYear, Model modelRef) {
		// Create a concatenated array of strings for the analysis types.
		String[] analysisTypes = {co2Table, gdpTable};
		
		// Connect to the World-Bank and get the desired data.
		WorldBankInterface worldBank = WorldBankInterface.getInstance();
		var concatenatedDataList = worldBank.getData(country, givenStartyear,
				givenEndYear, analysisTypes);
	
		// Ensure empty lists were not received.
		if (isDataEmpty(concatenatedDataList)) { return false; }
		
		// Compute ratios for nonzero values from each data list.
		computeRatios(concatenatedDataList);
			
		// Check to see there is no empty processed data lists.
		if (ratioCo2GdpData.size() == 0) { return false; }
		
			
		// All computations done so just save the new data.
		processedDataList.clear();
		processedDataList.add(ratioCo2GdpData);
		modelRef.storeData(processedDataList);
		
		return true;
	}
	
	/**
	 * This function is used to check if any data series returned has a size
	 * of 0.
	 * 
	 * @param data The concatenated array of data values from the server.
	 * @return True if the data list is empty, false otherwise.
	 */
	private boolean isDataEmpty(ArrayList<ArrayList<DataContainer>> data) {
		int series = data.size();
		
		// Loop through all available series and check sizes.
		for (int i = 0; i < series; i++) {
			if (data.get(i).size() == 0) {return true;}
		}
				
		return false;
	}
	
	/**
	 * This function is used for computing the ratio for all non-zero entires 
	 * acquired form the world bank.
	 * 
	 * @param data The concatenated array of data values from the server.
	 */
	private void computeRatios(ArrayList<ArrayList<DataContainer>> data) {
		int years = data.get(0).size();
		
		// Clear out previous data.
		ratioCo2GdpData.clear();
		
		// Loop through all the yearly attributes.
		for (int i = 0; i < years; i++) {
			var year = data.get(0).get(i).getYear();
			var co2 = data.get(0).get(i).getValue();
			var gdp = data.get(1).get(i).getValue();
			
			// Skip zero value elements.
			if (co2 == 0 || gdp == 0) { continue; }
			
			// Compute ratio and append to the data series.
			var ratio = co2 / gdp;
			ratioCo2GdpData.add(new DataContainer(year,ratio));
		}
	}
	
	private
	/**
	 * A reference to the CO2 Emissions data table in the World-Bank.
	 */
	static final String co2Table = "EN.ATM.CO2E.PC";
	
	/**
	 * A reference to the GDP Per Capita data table in the World-Bank.
	 */
	static final String gdpTable = "NY.GDP.PCAP.CD";
	
	/**
	 * A reference to the processed ratio data.
	 */
	ArrayList<DataContainer> ratioCo2GdpData;
	
	/**
	 * A reference to the compiled data to be used by the application.
	 */
	ArrayList <ArrayList<DataContainer>> processedDataList;
}

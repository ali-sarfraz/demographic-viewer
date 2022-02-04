import java.util.ArrayList;

/**
 * This class is used to define an implementation of the strategy for computing
 * the analysis. This particular class implements the analysis type of CO2 
 * Emissions vs Energy Use vs PM2.5 Air Pollution.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see Strategy
 */
public class AnalysisA implements Strategy {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public AnalysisA() {
		processedCo2Data = new ArrayList<DataContainer>();
		processedEnergyData = new ArrayList<DataContainer>();
		processedPM25Data = new ArrayList<DataContainer>();
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
		String[] analysisTypes = {co2Table, energyUseTable, pm25Table};
		
		// Connect to the World-Bank and get the desired data.
		WorldBankInterface worldBank = WorldBankInterface.getInstance();
		var concatenatedDataList = worldBank.getData(country, givenStartyear,
				givenEndYear, analysisTypes);
	
		// Ensure empty lists were not received.
		if (isDataEmpty(concatenatedDataList)) { return false; }
		
		// Sort out nonzero values from each data list.
		eliminateZeroes(concatenatedDataList);
			
		// Check to see there is no empty processed data lists.
		if (processedCo2Data.size() == 0 || processedEnergyData.size() == 0
				|| processedPM25Data.size() == 0) { return false; }
		
		// No computations needed here so just save the new data.
		processedDataList.clear();
		processedDataList.add(processedCo2Data);
		processedDataList.add(processedEnergyData);
		processedDataList.add(processedPM25Data);
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
			if (data.get(i).size() == 0) { return true; }
		}
				
		return false;
	}
	 
	/**
	 * This function is used for eliminating all zero values from the compiled
	 *  data series before plotting them to only showcase non-zero numerics.
	 * 
	 * @param data The concatenated array of data values from the server.
	 */
	private void eliminateZeroes(ArrayList<ArrayList<DataContainer>> data) {
		int years = data.get(0).size();
		
		// Clear out any previously held data.
		processedCo2Data.clear();
		processedEnergyData.clear();
		processedPM25Data.clear();
		
		// Loop through all the yearly attributes.
		for (int i = 0; i < years; i++) {
			// Check CO2 data.
			if (data.get(0).get(i).getValue() != 0) {
				processedCo2Data.add(data.get(0).get(i));
			}
			
			// Check energy data.
			if (data.get(1).get(i).getValue() != 0) {
				processedEnergyData.add(data.get(1).get(i));
			}
			
			// Check PM25 data.
			if (data.get(2).get(i).getValue() != 0) {
				processedPM25Data.add(data.get(2).get(i));
			}
		}
	}
	
	private
	/**
	 * A reference to the CO2 Emissions data table in the World-Bank.
	 */
	static final String co2Table = "EN.ATM.CO2E.PC";
	
	/**
	 * A reference to the Energy Usage data table in the World-Bank.
	 */
	static final String energyUseTable = "EG.USE.PCAP.KG.OE";
	
	/**
	 * A reference to the PM2.5 Air Pollution data table in the World-Bank.
	 */
	static final String pm25Table = "EN.ATM.PM25.MC.M3";
	
	/**
	 * A reference to the processed CO2 Emissions data table.
	 */
	ArrayList<DataContainer> processedCo2Data;
	
	/**
	 * A reference to the processed Energy Usage data table.
	 */
	ArrayList<DataContainer> processedEnergyData;
	
	/**
	 * A reference to the processed PM2.5 Air Pollution data table.
	 */
	ArrayList<DataContainer> processedPM25Data;
	
	/**
	 * A reference to the compiled data to be used by the application.
	 */
	ArrayList <ArrayList<DataContainer>> processedDataList;
}

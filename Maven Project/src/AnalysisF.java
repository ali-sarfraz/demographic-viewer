import java.util.ArrayList;

/**
 * This class is used to define an implementation of the strategy for computing
 * the analysis. This particular class implements the analysis type of Hospital
 * Beds per 1000 people vs Current Health Expenditure per 1000 people.
 *
 * @author Mohammad Iqbal.
 * @version	1.0.0
 * @see Strategy
 */
public class AnalysisF implements Strategy {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public AnalysisF() {
		processedHospitalBedData = new ArrayList<DataContainer>();
		processedHealthExpPer1kData = new ArrayList<DataContainer>();
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
		String[] analysisTypes = {hospitalBedTable, currHealthExpTable};
		
		// Connect to the World-Bank and get the desired data.
		WorldBankInterface worldBank = WorldBankInterface.getInstance();
		var concatenatedDataList = worldBank.getData(country, givenStartyear,
				givenEndYear, analysisTypes);
	
		// Ensure empty lists were not received.
		if (isDataEmpty(concatenatedDataList)) { return false; }
		
		// Sort out nonzero values from each data list.
		eliminateZeroes(concatenatedDataList);
		
		// Check to see there is no empty processed data lists.
		if (processedHospitalBedData.size() == 0 ||
				processedHealthExpPer1kData.size() == 0) { return false; }
		
		// No computations needed here so just save the new data.
		processedDataList.clear();
		processedDataList.add(processedHospitalBedData);
		processedDataList.add(processedHealthExpPer1kData);
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
		
		// Clear out previous data.
		processedHospitalBedData.clear();
		processedHealthExpPer1kData.clear();
		
		// Loop through all the yearly attributes.
		for (int i = 0; i < years; i++) {		
			// Check hospital bed data.
			if (data.get(0).get(i).getValue() != 0) {
				processedHospitalBedData.add(data.get(0).get(i));
			}
			
			// Check health expenditure data.
			if (data.get(1).get(i).getValue() != 0) {
				var year = data.get(1).get(i).getYear();
				var val = data.get(1).get(i).getValue();
				
				// Divide the value by 1000 for analysis.
				processedHealthExpPer1kData.add(new DataContainer(year,val / 1000));
			}
		}
	}
	
	private 
	/**
	 * A reference to the Hospital Beds data table in the World-Bank.
	 */
	static final String hospitalBedTable = "SH.MED.BEDS.ZS";	
	
	/**
	 * A reference to the Current Health Expenditure data table in the
	 * World-Bank.
	 */
	static final String currHealthExpTable = "SH.XPD.CHEX.PC.CD";
	
	/**
	 * A reference to the processed Hospital Beds data table.
	 */
	ArrayList<DataContainer> processedHospitalBedData;
	
	/**
	 * A reference to the processed Current Health Expenditure data table.
	 */
	ArrayList<DataContainer> processedHealthExpPer1kData;
	
	/**
	 * A reference to the compiled data to be used by the application.
	 */
	ArrayList <ArrayList<DataContainer>> processedDataList;
}

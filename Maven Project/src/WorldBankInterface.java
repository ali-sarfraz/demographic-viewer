import java.util.ArrayList;

/**
 * This class is used to implement a communication interface between the main
 * application and the World-Bank database by employing the use of an adapter.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see WorldBankAdapter
 */
public class WorldBankInterface {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 *
	 * @return A unique instance of the object.
	 */
	public static WorldBankInterface getInstance() {
		if(instance == null) {
			instance = new WorldBankInterface();
		}
		
		return instance;
	}
	
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	private WorldBankInterface() {
		// Create instance for the World-Bank API.
		worldBank = new WorldBankAdapter();
		dataArray = new ArrayList<DataContainer>();
		concatenatedDataList = new ArrayList <ArrayList<DataContainer>>();
	}
		
	/**
	 * This function is used for receiving the requested data from the 
	 * World-Bank.
	 * 
	 * @param country Selected country.
	 * @param givenStartYear Selected start year.
	 * @param givenEndYear  Selected end year.
	 * @param analysisTypes Array containing the appropriate World-Bank
	 * reference titles for accessing the data tables.
	 * @return The concatenated data series for the requested analysis.
	 */
	public ArrayList <ArrayList<DataContainer>> getData(String country, 
			int givenStartYear,int givenEndYear, String[] analysisTypes) {
		// Clear any compiled data from before.
		concatenatedDataList.clear();
		
		// Loop through analysis types and retrieve the data as needed.
		for (int i = 0; i < analysisTypes.length; i++) {
			// Establish server connection.
			var server = worldBank.connectToServer(country, analysisTypes[i],
					givenStartYear, givenEndYear);
			
			// Only proceed if valid internet connection is available.
			if (server != null) {
				var data = worldBank.readDataFromServer(server);
				dataArray = worldBank.translateJsonData(data);			
			} else {
				System.err.println("Illegal request made to the server!");
			}
			
			// Append the data array to the final containers.
			concatenatedDataList.add(dataArray);
		}
		
		return concatenatedDataList;
	}
		
	private
	/**
	 * Reference to the unique instance of the World-Bank interface. 
	 */
	static WorldBankInterface instance;
	
	/**
	 * Reference to the World-Bank adapter for acceptable communication.
	 */
	static WorldBankAdapter worldBank;
	
	/**
	 * Reference to a single data series for one computation metric.
	 */
	static ArrayList<DataContainer> dataArray;
	
	/**
	 * Reference to the concatenated series of data needed for each analysis
	 * type.
	 */
	ArrayList <ArrayList<DataContainer>> concatenatedDataList;
}

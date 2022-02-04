import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * This class is used to implement an adapter model for the World-Bank so that
 * the application is able to interpret the JSON format of data being returned
 * by the World-Bank API through HTTP GET requests.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see WorldBankInterface DataContainer
 */
public class WorldBankAdapter {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public WorldBankAdapter() {}
	
	/**
	 * This function is used for establishing a URL connection to the 
	 * appropriate World-Bank database records based on the user parameters.
	 * 
	 * @param country Selected country.
	 * @param analysisType World-Bank reference title for accessing the data.
	 * @param startYear Selected start year.
	 * @param endYear  Selected end year.
	 * @return URL resource to the appropriate World-Bank data table.
	 */
	public URL connectToServer(String country, String analysisType,
			int startYear, int endYear) {
		// Construct the URL to access the desired data from.
		String urlString = String.format(URL_FORMAT, country,
				analysisType, startYear, endYear);
						
		// Attempt to make connection.
		try {			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();	
			
			// Only return connection URL if it was successful.
			int responsecode = conn.getResponseCode();
			if (responsecode == 200) {
				return url;
			}
		} catch (IOException err) {
				err.printStackTrace();
			}
		
		// Return null otherwise.
		return null;
	}
	
	/**
	 * This function is used for getting the data sent in by the server as a
	 * JSON Array string.
	 * 
	 * @param url URL resource to the appropriate World-Bank data table.	
	 * @return JSON Array as a string containing the data.
	 */
	public String readDataFromServer(URL url) {
		String data = "";
		
		// Read and concatenate the data from the URL.
		try {
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext()) {
				data += sc.nextLine();
			}
			sc.close();
			
		} catch (IOException err) {
			err.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * This function is used for translating the JSON string data into data
	 * that can be used in the application as a data series. 
	 * 
	 * @param jsonData JSON Array as a string containing the data.	
	 * @return Array of data stored in DataContainers.
	 */
	public ArrayList<DataContainer> translateJsonData(String jsonData) {
		// Parse the results as a JSON array.
		JsonArray jsonArray = new JsonParser().parse(jsonData).getAsJsonArray();
		
		// Test if the user parameters sent in were valid.
		if (jsonArray.size() <= 1) {
			System.err.println("Error in request parameters!");
			System.exit(69);
		}
		
		// Determine the size of the acquired results.
		int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
		int year;
		float attributeForYear;
		ArrayList<DataContainer> dataArray = new ArrayList<DataContainer>();
		
		// Parse the year and data values based on their availability. 
		for (int i = 0; i < sizeOfResults; i++) {
			year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject()
					.get("date").getAsInt();
			
			if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject()
					.get("value").isJsonNull()) {
				attributeForYear = 0;				
			}
			else {
				attributeForYear = jsonArray.get(1).getAsJsonArray().get(i)
						.getAsJsonObject().get("value").getAsFloat();				
			}
			
			// Combine the year and attribute into the data container class.
			DataContainer translatedData = new DataContainer(year, attributeForYear); 

			// Append the data into the final array.
			dataArray.add(translatedData);
		}
		
		return dataArray;	
	}
	
	private
	/**
	 * Reference to the URL format used for sending HTTP GET requests.
	 */
	static final String URL_FORMAT = 
			"http://api.worldbank.org/v2/country/%s/indicator/%s?date=%d:%d&format=json"; 
}

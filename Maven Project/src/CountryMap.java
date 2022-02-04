import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

/**
 * This class is used to define and host a hash map that defines each of the
 * country codes to the full country name so that the main UI is able to
 * extract the country code using the full name.
 *
 * @author Mohammad Iqbal.
 * @version	1.0.0
 */
public class CountryMap {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public CountryMap() {
		map = new HashMap<String, String>();
		
		// Connect to the credential database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(countryListFile))) {
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		    	// Add the individual countries from the list.
		    	String[] countriesSplit = line.split(",");
		    	
		    	// Insert the valid countries into the map.
		    	map.put(countriesSplit[0].toLowerCase(), countriesSplit[1]);
		    }
		} catch (Exception err) {
		    System.err.println(err.getMessage());
			}
	}
	
	/**
	 * This function is used to retrieve the country code based on the key
	 * index provided.
	 * 
	 * @param key The key index value to retrieve from the map.
	 * @return The mapping country code assigned to the key.
	 */
	public String getCountryCode(String key) {
		return map.get(key);
	}
	
	private
	/**
	 * A mapping of all country names to a country code for the main
	 * UI to use for invoking the appropriate analysis.
	 */
	static HashMap<String, String> map;
	
	/**
	 * A reference to the country list database file name.
	 */
	static final String countryListFile = "country_list.txt";
}

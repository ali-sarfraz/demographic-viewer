import java.io.BufferedReader;
import java.io.FileReader;

/**
 * This class is used to define a middle man implementation of the abstract
 * database functionalities for purposes of producing the Proxy design pattern.
 *
 * @author Sanjayan Kulendran.
 * @version	1.0.0
 * @see AbstractDatabase
 */
public class RealDatabase extends AbstractDatabase {
	
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public RealDatabase() {}
	
	/**
	 * This function is used to validate the user's login credentials through
	 * the credentials database file.
	 *
	 * @param username The user's application username.
	 * @param password The user's application password.
	 * @return True if the credentials are valid, false otherwise.
	 */
	public boolean authenticateUser(String username, String password) {	
		// Connect to the credential database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(credentialDb))) {
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		    	// Split the stings based on commas to 
		    	// isolate username and password.
		    	String[] credentials = line.split(",");
		    	   	
		    	// Check if the credentials match the input provided.
		    	if (username.equals(credentials[0]) && 
		    			password.equals(credentials[1])) { return true; }		    	
		    }
		} catch (Exception err) {
		    System.err.println(err.getMessage());
			}
		
		return false;
	}
	
	/**
	 * This function is used to validate the user's inputed selection of 
	 * parameters for computing the requested analysis.
	 * 
	 * @param analysisType The user chosen analysis type.
	 * @param country The user chosen country for analysis.
	 * @param startYear The user chosen start year for analysis.
	 * @param endYear The user chosen end year for analysis.
	 * @return 0 if the parameters are valid, 1 if valid country but invalid
	 *  year, 2 if invalid country.
	 */
	public int isValidSetup(int analysisType, String country, int startYear,
			int endYear) {
		if (validCountry(analysisType, country)) {
			if (validYearRange(analysisType, startYear, endYear)) { return 0; }
			else { return 1; }
		}
		return 2;
	}
	
	/**
	 * This function is used to validate if the user's viewer selection can
	 * be produced for the selected analysis type.
	 *
	 * @param analysisType The user chosen analysis type.
	 * @param graphType The user chosen viewer type.
	 * @return True if the viewer type are valid, false otherwise.
	 */
	public boolean isValidViewer(int analysisType, String graphType) {
		// Row to read file till.
		int rowStop = analysisType - 1;
		
		// Connect to the viewer database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(viewerAnalysisDb))) {
		    String line;
		    int count = 0;
		    
		    // Traverse database until correct analysis type row is found
		    while ((line = reader.readLine()) != null) {
		    	if (count == rowStop) {
		    		// Split the stings based on commas to 
			    	// isolate viewer names.
			    	String[] analysis = line.split(",");
			    	
		    		// Check if a valid viewer name matches the input provided.
			    	for (int i = 0; i < analysis.length; i++) {
			    		if (graphType.equals(analysis[i])) {
			    			return true;
			    		}
			    	}		
		    	}
		    	
		    	count++;
		    }
		} catch (Exception err){
		    System.err.println(err.getMessage());
			}
		
		return false;
	}
	
	/**
	 * This function is used to validate if the user's country selection can
	 * be used for the selected analysis type.
	 *
	 * @param analysisType The user chosen analysis type.
	 * @param country The user chosen country.
	 * @return True if the country supports the analysis type, false otherwise.
	 */
	private boolean validCountry(int analysisType, String country) {
		// Row to read file till.
		int rowStop = analysisType - 1;
					
		// Connect to the country database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(countryAnalysisDb))) {
		    String line;
		    int count = 0;
		    
		    while ((line = reader.readLine()) != null) {	
		    	if(count == rowStop) {
		    		// Split the stings based on commas to 
			    	// isolate country names.
			    	String[] countryNames = line.split(",");
			    	
		    		// Check if the a valid country matches the input provided.
			    	for (int i = 0; i < countryNames.length; i++) {
			    		if(country.equals(countryNames[i])) {
			    			return true;
			    		}
			    	}		
		    	}
		    	
		    	count++;
		    }
		} catch (Exception err) {
		    System.err.println(err.getMessage());
			}
		
		return false;
	}
	
	/**
	 * This function is used to validate if the user's year range selection can
	 * be used for the selected analysis type.
	 *
	 * @param analysisType The user chosen analysis type.
	 * @param givenStartYear The user start year.
	 * @param givenEndYear The user chosen end.
	 * @return True if the year range supports the analysis type, false otherwise.
	 */
	private boolean validYearRange(int analysisType, int givenStartYear,
			int givenEndYear) {
		// Row to read file till.
		int rowStop = analysisType - 1;
				
		// Connect to the credential database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(yearAnalysisDb))) {
		    String line;
		    int count = 0;
		    
		    while ((line = reader.readLine()) != null) { 	
		    	if(count == rowStop) {
		    		// Split the stings based on commas to 
			    	// isolate start and end years.
			    	String[] yearRangeSplit = line.split(",");
			    	   	
			    	// Check if the ranges match the input provided.
			    	if (givenStartYear >= Integer.valueOf(yearRangeSplit[0]) &&
			    			givenStartYear <= Integer.valueOf(yearRangeSplit[1]) &&
			    			givenEndYear >= Integer.valueOf(yearRangeSplit[0]) &&
			    			givenEndYear <= Integer.valueOf(yearRangeSplit[1])) {
			    		return true;
			    	}
		    	}
		    	
		    	count++;
		    }
		} catch (Exception err) {
		    System.err.println(err.getMessage());
			}

		return false;
	}
	
	private
	/**
	 * A reference to the credentials database file name.
	 */
	static final String credentialDb = "credential_database.txt";
	
	/**
	 * A reference to the viewer-analysis database file name.
	 */
	static final String viewerAnalysisDb = "viewer_analysis.txt";
	
	/**
	 * A reference to the year-analysis database file name.
	 */
	static final String yearAnalysisDb = "year_analysis.txt";
	
	/**
	 * A reference to the country-analysis database file name.
	 */
	static final String countryAnalysisDb = "country_analysis.txt";
}

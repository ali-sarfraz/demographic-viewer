/**
 * This class is used to hold the user parameters that have been selected
 * through the main UI window and hence store the analysis components needed
 * by the World-Bank API.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see MainUI
 */
public class UserParameters {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 *
	 * @return A unique instance of the object.
	 */
	public static UserParameters getInstance() {
		if(instance == null) {
			instance = new UserParameters();
		}
		
		return instance;
	 }
	
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	private UserParameters() {
		// Set attributes to default values.
		analysisType = 1;
		country = "CAN";
		startYear = 2020;
		endYear = 2021;	
	}
	
	/**
	 * This function is used to set the analysis type.
	 * 
	 * @param givenAnalysis Selected analysis type.
	 */
	public void setAnalysisType(int givenAnalysis) {
		analysisType = givenAnalysis;
	}
	
	/**
	 * This function is used to set the country.
	 * 
	 * @param givenCountry Selected country.
	 */
	public void setCountry(String givenCountry) {
		country = givenCountry;
	}
	
	/**
	 * This function is used to set the start year.
	 * 
	 * @param givenStartYear Selected start year.
	 */
	public void setStartYear(int givenStartYear) {
		startYear = givenStartYear;
	}
	
	/**
	 * This function is used to set the end year.
	 * 
	 * @param givenEndYear Selected end year.
	 */
	public void setEndYear(int givenEndYear) {
		endYear = givenEndYear;
	}
	
	/**
	 * This function is used to get the analysis type.
	 * 
	 * @return An integer mapping the analysis type.
	 */
	public int getAnalysisType() {
		return analysisType;
	}
	
	/**
	 * This function is used to get the country.
	 * 
	 * @return The name of the country selected.
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * This function is used to get the start year.
	 * 
	 * @return The selected start year.
	 */
	public int getStartYear() {
		return startYear;
	}
	
	/**
	 * This function is used to get the end year.
	 * 
	 * @return The selected end year.
	 */
	public int getEndYear() {
		return endYear;
	}
	
	private
	/**
	 * Reference to the unique instance of the user parameters. 
	 */
	static UserParameters instance;
	
	/**
	 * Reference to the selected country.
	 */
	String country;
	
	/**
	 * Reference to the analysis type.
	 */
	int analysisType;
	
	/**
	 * Reference to the start year.
	 */
	int startYear;
	
	/**
	 * Reference to the end year.
	 */
	int endYear;
}

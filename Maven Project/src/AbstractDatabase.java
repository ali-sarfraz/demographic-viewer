/**
 * This class is used to define an abstract implementation of the database
 * functionalities so that the different variations of the databases may
 * define their own specific instances of the local functions.
 *
 * @author Sanjayan Kulendran.
 * @version	1.0.0
 * @see ProxyDatabase RealDatabase
 */
public abstract class AbstractDatabase {
	/**
	 * This function is used to validate the user's login credentials through
	 * the credentials database file.
	 *
	 * @param username The user's application username.
	 * @param password The user's application password.
	 * @return True if the credentials are valid, false otherwise.
	 */
	public abstract boolean authenticateUser(String username,
			String password);
	
	/**
	 * This function is used to validate the user's inputed selection of 
	 * parameters for computing the requested analysis.
	 * 
	 * @param analysisType The user chosen analysis type.
	 * @param country The user chosen country for analysis.
	 * @param startYear The user chosen start year for analysis.
	 * @param endYear The user chosen end year for analysis.
	 * @return 0 if the parameters are valid, 1 if valid country but invalid
	 * year, 2 if invalid country, 3 if bad or missing data, 4 if the time
	 * range is invalid.
	 */
	public abstract int isValidSetup(int analysisType, String country, 
			int startYear, int endYear);
	
	/**
	 * This function is used to validate if the user's viewer selection can
	 * be produced for the selected analysis type.
	 *
	 * @param analysisType The user chosen analysis type.
	 * @param graphType The user chosen viewer type.
	 * @return True if the viewer type are valid, false otherwise.
	 */
	public abstract boolean isValidViewer(int analysisType, 
			String graphType);
}

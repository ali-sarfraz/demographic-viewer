import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This class is used to define a middle man implementation of the abstract
 * database functionalities for purposes of producing the Proxy design pattern.
 *
 * @author Sanjayan Kulendran.
 * @version	1.0.0
 * @see AbstractDatabase
 */
public class ProxyDatabase extends AbstractDatabase{
	
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public ProxyDatabase() {}
	
	/**
	 * This function is used to validate the user's login credentials through
	 * the credentials database file by calling the local database for the
	 * computations.
	 *
	 * @param username The user's application username.
	 * @param password The user's application password.
	 * @return True if the credentials are valid, false otherwise.
	 */
	public boolean authenticateUser(String username, String password) {
		// Error checking for null fields.
		if (username.equals("") || password.equals("")) {
			displayError("Invalid credential format!");
		}
		
		// Lazy initialization of local database.
		lazyInitDB();
		
		return localDB.authenticateUser(username, password);
	}
	
	/**
	 * This function is used to validate the user's inputed selection of 
	 * parameters for computing the requested analysis by calling the local
	 * database for the computations.
	 * 
	 * @param analysisType The user chosen analysis type.
	 * @param country The user chosen country for analysis.
	 * @param startYear The user chosen start year for analysis.
	 * @param endYear The user chosen end year for analysis.
	 * @return 0 if the parameters are valid, 1 if valid country but invalid
	 * year, 2 if invalid country, 3 if bad or missing data, 4 if the time
	 * range is invalid.
	 */
	public int isValidSetup(int analysisType, String country, 
			int startYear, int endYear) {
		if (analysisType < 0 || startYear < 0 || endYear < 0
				|| country.equals("")) { return 3; }
		else if (startYear > endYear) { return 4; }
		
		// Lazy initialization of database.
		lazyInitDB();
		
		return localDB.isValidSetup(analysisType, country, startYear, endYear);
	}
	
	/**
	 * This function is used to validate if the user's viewer selection can
	 * be produced for the selected analysis type by calling the local database
	 * for the computations.
	 *
	 * @param analysisType The user chosen analysis type.
	 * @param graphType The user chosen viewer type.
	 * @return True if the viewer type are valid, false otherwise.
	 */
	public boolean isValidViewer(int analysisType, String graphType) {
		// Error checking given inputs.
		if (graphType.equals("")) {
			System.err.println("No graph type given to Database!");
			return false;
		}
		
		else if(analysisType < 0) {
			System.err.println("No analysis type given to Database!");
			return false;
		}
		
		// Lazy initialization of database.
		lazyInitDB();
		
		return localDB.isValidViewer(analysisType, graphType);
	}
	
	/**
	 * This function is used to print the error responses in a new window.
	 *
	 * @param error A description detailing the cause of the error. 
	 */
	private void displayError(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Error",
		        JOptionPane.ERROR_MESSAGE);
		System.exit(69);
	}
	
	/**
	 * This function is used to implement the lazy initialization of the
	 * local database.
	 */
	private void lazyInitDB() {
		if (localDB == null) {
			localDB = new RealDatabase();
		}
	}
	
	private
	/**
	 * A reference to the local database being mapped by this proxy database.
	 */
	RealDatabase localDB;
}

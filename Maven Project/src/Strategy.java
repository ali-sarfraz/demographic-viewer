/**
 * This interface is used to define an abstraction for the strategy used to 
 * compute the analysis. It has one method that will be overridden by all
 * the classes that implement it.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see AnalysisMap
 */
public interface Strategy {
	/**
	 * This function is overridden to implement the specific strategy used 
	 * for the various types of analysis being offered by the application.
	 * 
	 * @param country The user chosen country for analysis.
	 * @param givenStartyear The user chosen start year for analysis.
	 * @param givenEndYear The user chosen end year for analysis.
	 * @param modelRef A reference to the model used to store data.
	 * @return True if the analysis was completed successfully, false otherwise.
	 */
	public boolean doAnalysis(String country, int givenStartyear,
			int givenEndYear, Model modelRef);
}

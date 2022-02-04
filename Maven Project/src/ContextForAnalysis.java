/**
 * This class is used to create a specific instance of an analysis type based
 * on the strategy design pattern working in conjunction with the analysis map. 
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see Strategy AnalysisMap
 */
public class ContextForAnalysis {
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenParameters The provided user parameters.
	 * @param givenModelRef The provided reference to the model.
	 */
	public ContextForAnalysis(UserParameters givenParameters,
			Model givenModelRef) {
		parameters = givenParameters;
		modelRef = givenModelRef;
		
		// Initialize the hash map to hold strategy as key-value pairs.
		analysisMap = new AnalysisMap();
	}
		
	/**
	 * This function is used to execute the particular context of the analysis.
	 *
	 * @return True if the execution was successful, false otherwise.
	 */
	public boolean executeStrat() {
		// Evaluate the criteria of analysis being applied.
		var analysisType = parameters.getAnalysisType();
		var country = parameters.getCountry();
		var startYear = parameters.getStartYear();
		var endYear = parameters.getEndYear();
		
		// Get appropriate analysis from the hash map.
		strat = analysisMap.getAnalysis(analysisType);
		
		// Apply strategy for computing the appropriate analysis.
		return strat.doAnalysis(country, startYear, endYear, modelRef);
	}
		
	private
	/**
	 * A reference to a strategy for implementing the analysis.
	 */
	static Strategy strat;
	
	/**
	 * A reference to the user parameters needed to run the analysis.
	 */
	static UserParameters parameters;
	
	/**
	 * A reference to the model used to store the computed data.
	 */
	static Model modelRef;
	
	/**
	 * A reference to the analysis map for deciding the analysis type.
	 */
	static AnalysisMap analysisMap;
}

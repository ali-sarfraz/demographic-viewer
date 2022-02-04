import java.util.HashMap;
/**
 * This class is used to define and host a hash map that defines each of the
 * analysis types to an integer value so that the main UI is able to refer
 * to each of these using a value from 1 to 8.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see AnalysisA AnalysisB AnalysisC AnalysisD AnalysisE AnalysisF
 * @see AnalysisG AnalysisH
 */
public class AnalysisMap {
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	public AnalysisMap() {
		map = new HashMap<Integer, Strategy>();
		map.put(1, new AnalysisA());
		map.put(2, new AnalysisB());
		map.put(3, new AnalysisC());
		map.put(4, new AnalysisD());
		map.put(5, new AnalysisE());
		map.put(6, new AnalysisF());
		map.put(7, new AnalysisG());
		map.put(8, new AnalysisH());
	}
	
	/**
	 * This function is used to retrieve an analysis type based on the key
	 * index provided.
	 * 
	 * @param key The key index value to retrieve from the map.
	 * @return The mapping strategy assigned to the key.
	 */
	public Strategy getAnalysis(int key) {
		return map.get(key);
	}
	
	private 
	/**
	 * A mapping of all eight analysis types to an integer value for the main
	 * UI to use for invoking the appropriate analysis.
	 */
	static HashMap<Integer, Strategy> map;
}

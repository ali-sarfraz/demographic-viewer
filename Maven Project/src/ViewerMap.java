import java.util.HashMap;

/**
 * This class is used to define and host a hash map that defines each of the
 * viewer types to a string value so that the main UI is able to refer
 * to each of these using the drop-down menu strings.
 *
 * @author Mohammad Iqbal.
 * @version	1.0.0
 * @see ViewerLine ViewerPie ViewerReport ViewerScatter ViewerTime
 */
public class ViewerMap {
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenModelRef A reference to the internal model.
	 * @param givenAnalysisType The type of analysis being executed.
	 */
	public ViewerMap(Model givenModelRef, int givenAnalysisType) {
		map = new HashMap<String, AbstractViewer>();
		map.put("line", new ViewerLine(givenModelRef,givenAnalysisType));
		map.put("scatter", new ViewerScatter(givenModelRef,givenAnalysisType));
		map.put("time", new ViewerTime(givenModelRef,givenAnalysisType));
		map.put("pie", new ViewerPie(givenModelRef,givenAnalysisType));
		map.put("report", new ViewerReport(givenModelRef,givenAnalysisType));
	}
		
	/**
	 * This function is used to retrieve an abstract viewer type based on the
	 * key index string provided.
	 * 
	 * @param key The key index value to retrieve from the map.
	 * @return The mapping viewer assigned to the key.
	 */
	public static AbstractViewer getViewer(String key) {
			return map.get(key);
	}
		
	private
	/**
	 * A mapping of all possible types of viewers to a name string for the main
	 * UI to use for invoking the appropriate viewers.
	 */
	static HashMap<String, AbstractViewer> map;
}

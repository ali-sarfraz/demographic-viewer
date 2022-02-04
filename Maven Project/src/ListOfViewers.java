import java.util.Vector;
import javax.swing.JPanel;

/**
 * This class is used to create a list of viewers being chosen for the analysis.
 *
 * @author Mohammad Iqbal.
 * @version	1.0.0
 * @see ViewerMap
 */
public class ListOfViewers {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 * 
	 * @param givenModel The model to store the data to.
	 * @param givenParam The user parameters being modified.
	 * @return A unique instance of the object.
	 */
	public static ListOfViewers getInstance(Model givenModel,
			UserParameters givenParam) {
		if (instance == null) {
			instance = new ListOfViewers(givenModel,givenParam);
		}
		
		return instance;
	}
	
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenYear The year value to store in the container.
	 * @param givenValue The value to store in the container.
	 */
	private ListOfViewers(Model givenModel, UserParameters givenParam) {
		parametersRef = givenParam;
		modelRef = givenModel;
		internalList = new Vector<AbstractViewer>();
		viewerMap = new ViewerMap(modelRef,parametersRef.getAnalysisType());
	}
	
	/**
	 * This function is used to check if a viewer already exists before adding
	 * it.
	 * 
	 * @param givenGraphType The viewer to add.
	 * @return True if the viewer does not exist and has been added, false
	 * otherwise.
	 */
	public boolean addViewerToList(String givenGraphType) {				
		// Check to see if viewer has already been added to list.
		if (internalList != null) {
			for (int i = 0; i < internalList.size(); i++) {
				if (internalList.get(i).getGraphType().equals(givenGraphType)) {
					return false;
				}
			}
		}
		
		// Specified viewer does not exist, so add it.
		var newViewer = ViewerMap.getViewer(givenGraphType);
		newViewer.setGraphType(givenGraphType);
		internalList.add(newViewer);
		return true;
	}
	
	/**
	 * This function is used to check if a viewer already exists before
	 * removing it.
	 * 
	 * @param givenGraphType The viewer to remove.
	 * @return True if the viewer exists and has been removed, false
	 * otherwise.
	 */
	public boolean removeViewerFromList(String givenGraphType) {
		// If the list is already empty then return false.
		if (internalList.isEmpty()) { return false;	}
		
		// Check to see if viewer already exists.
		for (int i = 0; i < internalList.size(); i++) {
			if (internalList.get(i).getGraphType().equals(givenGraphType)) {
				// Specified viewer exists, so remove it.
				internalList.remove(i);
				return true;
			}
		}
		
		// Specified viewer does not reside in list.
		return false;
	}
	
	/**
	 * This function is used to empty the internal list of viewers.
	 */
	public void emptyList() {
		// Only clear the list if it isn't already empty.
		if (!internalList.isEmpty()) {
			internalList.clear();
		}
	}
	
	/**
	 * This function is used for calling the internal update method for
	 * updating all viewers in the list accordingly.
	 */
	public void updateAllViewers() {
		for (int i = 0; i < internalList.size(); i++) {
			internalList.get(i).update(parametersRef);
		}
	}

	/**
	 * This function is used for initializing the internal model reference
	 * used for storing data.
	 * 
	 * @param givenModelRef Reference to the model for storing data.
	 */
	public void addModelRef(Model givenModelRef) {
		modelRef = givenModelRef;
	}
	
	/**
	 * This function is used for initializing the user parameters reference.
	 * 
	 * @param givenParameters Reference to the user parameters.
	 */
	public void addUserParamRef(UserParameters givenParameters) {
		parametersRef = givenParameters;
	}

	/**
	 * This function is used for rendering and displaying all the viewers.
	 * 
	 * @param givenPanel The UI panel for the viewers to be displayed on.
	 */
	public void displayViewers(JPanel givenPanel) {
		for (int i = 0; i < internalList.size(); i++) {
			internalList.get(i).display(givenPanel);
		}
	}
	
	private
	/**
	 * Reference to the internal list of viewers.
	 */
	static Vector<AbstractViewer> internalList;
	
	/**
	 * Reference to the internal model used for storing data.
	 */
	static Model modelRef;
	
	/**
	 * Reference to the user parameters received from the UI.
	 */
	static UserParameters parametersRef;
	
	/**
	 * Reference to the unique instance of the list of viewers.
	 */
	static ListOfViewers instance;
	
	/**
	 * Reference to the viewer map for deciding the viewer type.
	 */
	static ViewerMap viewerMap;
}

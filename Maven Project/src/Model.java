import java.util.ArrayList;

/**
 * This class is used to store all the processed data retrieved from the
 * World-Bank server and maintain the list of viewers accordingly.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see ListOfViewers WorldBankInterface
 */
public class Model {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 * 
	 * @return A unique instance of the object.
	 */
	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		
		return instance;
	}
	
	/**
	 * Constructor function for initializing an instance of this object.
	 */
	private Model() {
		modelState = new ArrayList <ArrayList<DataContainer>>();
	}
	
	/**
	 * This function is used to notify all observers about state change.
	 */
	protected void notifyStateChange() {
		listOfViewersRef.updateAllViewers();
	}
	
	/**
	 * This function is used to retrieve the current model state.
	 * 
	 * @return The current model state.
	 */
	public ArrayList <ArrayList<DataContainer>> getState() {
		 return modelState; 
	}
	 
	/**
	 * This function is used to store a dataset to the model.
	 * 
	 * @param givenData Concatenated list of data processed from the 
	 * World-Bank.
	 */
	public void storeData(ArrayList <ArrayList<DataContainer>> givenData) {
		modelState = givenData;
		notifyStateChange();
	}
	
	/**
	 * This function is used to set the viewers reference for the observers.
	 * 
	 * @param givenObserverList List of viewers to subscribe to.
	 */
	public void setObserverList(ListOfViewers givenObserverList) {
		 listOfViewersRef = givenObserverList;
	}
	 
	private
	/**
	 * Reference to concatenated list of data processed from the World-Bank.
	 */
	static ArrayList <ArrayList<DataContainer>> modelState;
	
	/**
	 * Reference to list of viewers to subscribe to.
	 */
	static ListOfViewers listOfViewersRef;
	
	/**
	 * Reference to the unique instance of the model object. 
	 */
	static Model instance;
}

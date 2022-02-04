/**
 * This class defines a container that gets used for storing the data
 * retrieved from the world bank as a year and value pair.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 */
public class DataContainer {
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenYear The year value to store in the container.
	 * @param givenValue The value to store in the container.
	 */
	public DataContainer(int givenYear, float givenValue) {
		year = givenYear;
		value = givenValue;
	}
	
	/**
	 * This function is used to retrieve the year from the data container.
	 * 
	 * @return The year value held in the container.
	 */
	public int getYear() { return year; }
	
	/**
	 * This function is used to retrieve the value from the data container.
	 * 
	 * @return The value held in the container.
	 */
	public float getValue() { return value; }
	
	private
	/**
	 * Reference to the year value being stored in the container.
	 */
	int year;
	
	/**
	 * Reference to the value being stored in the container.
	 */
	float value;
}

import javax.swing.JPanel;
/**
 * This class is used to define an abstract implementation of the viewer
 * functionalities so that the different variations of the viewers may
 * define their own specific instances of the local functions.
 *
 * @author Matthew Bertuzzi.
 * @version	1.0.0
 * @see ViewerMap
 */
public abstract class AbstractViewer {
	/**
	 * This function is used to display the viewer onto the main UI.
	 *
	 * @param givenPanel The window panel on which to display the viewer.
	 */
	public abstract void display(JPanel givenPanel);
	
	/**
	 * This function is used to update the user parameters according to 
	 * the current options selected by the user.
	 *
	 * @param givenParam The user parameter on the main UI.
	 */
	public abstract void update(UserParameters givenParam);
	
	/**
	 * This function is used to set the viewer type to a valid option.
	 *
	 * @param givenGraphType The new type of graph.
	 */
	public abstract void setGraphType(String givenGraphType);
	
	/**
	 * This function is used to get the current viewer type.
	 *
	 * @return The name of the viewer type.
	 */
	public abstract String getGraphType();
}

import javax.swing.JFrame;

/**
 * This class is used as the main entry point to the application. It creates
 * all the required data objects as needed and displays both the Login and main
 * UI windows for the user to interact with.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see Login MainUI
 */
public class Application {
	/**
	 * Main function for the program to start execution.
	 * 
	 * @param args Command line arguments passed to the application.
	 */	
	public static void main(String[] args) {
		// Create the Login window for validating user credentials.
		ProxyDatabase proxyDatabase = new ProxyDatabase();
		Login loginWindow = Login.getInstance(proxyDatabase);
		loginWindow.initWindow();
		
		// Instantiate the objects needed to store and transfer data that has
		// been processed by the World-Bank.
		UserParameters userParameters = UserParameters.getInstance();
		Model model = Model.getInstance();
		
		// Instantiate the list of viewers and their subscriptions as Observers.
		ListOfViewers viewerList = ListOfViewers.getInstance(model,
				userParameters);
		model.setObserverList(viewerList);
				
		// Create the main UI window for launching application. 
		JFrame frame = MainUI.getInstance(viewerList, proxyDatabase,
				userParameters, model);
		frame.setSize(1200, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		return; 
	}
}

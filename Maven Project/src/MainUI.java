import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is used to create and display the main UI for the application for
 * the users to select the parameters needed for the analysis and visualize the
 * data set back by the World-Bank.
 *
 * @author Mohammad Sarfraz.
 * @version	1.0.0
 * @see Application
 */
public class MainUI extends JFrame {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 * 
	 * @param listofViewers The list of viewers to be populated.
	 * @param proxyDatabase The proxy database to verify analysis content.
	 * @param parameters The user parameters being populated.
	 * @param givenModelRef The model which stores the data.
	 * @return A unique instance of the object.
	 */
	public static MainUI getInstance(ListOfViewers listofViewers, 
			ProxyDatabase proxyDatabase, 
			UserParameters parameters,
			Model givenModelRef) {
		if (instance == null) {			
			instance = new MainUI(listofViewers, proxyDatabase,
					parameters, givenModelRef);
		}

		return instance;
	}
	
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param viewersRef The list of viewers to be populated.
	 * @param proxyRef The proxy database to verify analysis content.
	 * @param paramRef The user parameters being populated.
	 * @param givenModelRef The model which stores the data.
	 */
	private MainUI(ListOfViewers viewersRef, 
			ProxyDatabase proxyRef, 
			UserParameters paramRef,
			Model givenModelRef) {
		// Set the main UI's window title and viewer list.
		super("Country Statistics");
		
		// Initialize all the internal parameters.
		listofViewers = viewersRef;
		proxyDB = proxyRef;
		currUserParameters = paramRef;
		mainModelRef = givenModelRef;
		analysisContext = new ContextForAnalysis(currUserParameters,
				mainModelRef);
		countryMap = new CountryMap();

		// Construct the top bar of the UI. 
		buildCountrySelection();
		buildTimeRangeSelection();
		
		// Construct the bottom bar of the UI.
		buildViewsSelection();
		buildAnalysisSelection();
		
		// Assign action listeners for all the buttons.
		initButtonListeners();
			
		// Add all the constructed components to the window.
		addComponents();
	}
	
	/**
	 * This function is used for creating the country selection build of the
	 * UI window.
	 */
	private void buildCountrySelection() {
		chooseCountryLabel = new JLabel("Choose a country: ");
		Vector<String> countriesNames = new Vector<String>();
				
		// Connect to the credential database by opening text file.
		try (BufferedReader reader = new BufferedReader
				(new FileReader(countryListFile))) {
		    String line;
		    
		    while ((line = reader.readLine()) != null) {
		    	// Add the individual countries from the list.
		    	String[] countriesSplit = line.split(",");
		    	countriesNames.add(countriesSplit[0]);
		    }
		} catch (Exception err) {
		    System.err.println(err.getMessage());
			}
		
		// Sort the country names accordingly.
		countriesNames.sort(null);
		countriesList = new JComboBox<String>(countriesNames);
	}
	
	/**
	 * This function is used for setting up the year range selection build of 
	 * the UI window.
	 */
	private void buildTimeRangeSelection() {
		fromTimeLabel = new JLabel("From");
		toTimeLabel = new JLabel("To");
		Vector<String> years = new Vector<String>();
		
		// Add the predefined set of years to the component.
		for (int i = maxYear; i >= minYear; i--) {
			years.add("" + i);
		}
		
		fromTimeList = new JComboBox<String>(years);
		toTimeList = new JComboBox<String>(years);
	}
	
	/**
	 * This function is used for setting up the viewer type selection build of 
	 * the UI window.
	 */
	private void buildViewsSelection() {
		viewsLabel = new JLabel("Available Views: ");
		Vector<String> viewsNames = new Vector<String>();
		
		// Add the five predefined set of viewers.
		viewsNames.add("Pie Chart");
		viewsNames.add("Line Chart");
		viewsNames.add("Time Chart");
		viewsNames.add("Scatter Chart");
		viewsNames.add("Report");
		
		viewsList = new JComboBox<String>(viewsNames);
		addView = new JButton("+");
		removeView = new JButton("-");
	}
	
	/**
	 * This function is used for setting up the analysis type selection build of 
	 * the UI window.
	 */
	private void buildAnalysisSelection() {
		recalculate = new JButton("Recalculate");
		methodLabel = new JLabel("        Choose analysis method: ");
		Vector<String> methodsNames = new Vector<String>();
		
		// Add the eight predefined set of analysis.
		methodsNames.add("CO2 Emissions vs Energy Use vs PM2.5 Air Pollution");
		methodsNames.add("PM2.5 Air Pollution vs Forest Area");
		methodsNames.add("Ratio of CO2 Emissions & GDP Per Capita");
		methodsNames.add("Average Forest Area");
		methodsNames.add("Average Government Expenditure on Education");
		methodsNames.add("Hospital Beds vs Current Health Expenditure");
		methodsNames.add("Current Health Expenditure vs Mortality Rate");
		methodsNames.add("Ratio of Government Expenditure on Education & "
				+ "Current Health Expenditure");

		methodsList = new JComboBox<String>(methodsNames);
	}
	
	/**
	 * This function is used for instantiating action listeners for all the
	 * button clicks so that they may be registered.
	 */
	private void initButtonListeners() {
		// Associate an action listener for each button.
		addView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addViewButtonPressed();
			}});
		
		removeView.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				removeViewButtonPressed();
			}});
		
		recalculate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				recalculateButtonPressed();
			}});
		
		methodsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				methodSelected();
		}});
	}
	
	/**
	 * This function is executed when the Add View button is clicked.
	 */
	private void addViewButtonPressed() {
		// Retrieve the selected viewer from the UI.
		String[] selectedViewerList = 
				viewsList.getSelectedItem().toString().split(" ");
		String selectedViewer = selectedViewerList[0].toLowerCase();
		
		// Validate if the viewer can be used for the analysis, where analysis
		// type it item selected from drop down.
		if (proxyDB.isValidViewer(methodsList.getSelectedIndex() + 1,
				selectedViewer)) {
			if (listofViewers.addViewerToList(selectedViewer)) {
				listofViewers.updateAllViewers();
				displayInfo("Viewer added!");
			} else {
				displayError("Viewer already in the list!");
			}		
		} else {
			displayError("Selected Viewer is incompatible with analysis type!");
		}
	}
	
	/**
	 * This function is executed when the Remove View button is clicked.
	 */
	private void removeViewButtonPressed() {
		// Retrieve the selected viewer from the UI.
		String[] selectedViewerList = 
				viewsList.getSelectedItem().toString().split(" ");
		String selectedViewer = selectedViewerList[0].toLowerCase();
		
		// Validate if the viewer can be removed from list.
		if (listofViewers.removeViewerFromList(selectedViewer)) {
			displayInfo("Viewer removed!");
		} else {
			displayError("Selected viewer is not in the list!");
		}
	}
		
	/**
	 * This function is executed when the Recalculate button is clicked.
	 */
	private void recalculateButtonPressed() {
		// Retrieve selected user parameters from the UI.
		int startYear = Integer.valueOf(fromTimeList.getSelectedItem().toString());
		int endYear = Integer.valueOf(toTimeList.getSelectedItem().toString());
		int analysisType = methodsList.getSelectedIndex() + 1;
		String country = countriesList.getSelectedItem().toString().toLowerCase();
		String countryCode = countryMap.getCountryCode(country);
		
		// Validate user parameters retrieved.
		int response = proxyDB.isValidSetup(analysisType, countryCode,
				startYear, endYear);
		
		// Convert integer value to mapped Response code.
		Response responseEnum = Response.values()[response]; 
		
		// Evaluate the proxy database response code.
		switch(responseEnum) {
		case INVALID_TIME_RANGE:
			displayError("Invalid time range for analysis!");
			break;
		case INVALID_COUNTRY:
			displayError("Invalid Country for analysis!");
			break;
		case UNEXPECTED_FORMAT:
			displayError("Unexpected Format, cannot validate setup!");
			break;
		case INVALID_TIME_FORMAT:
			displayError("Starting year is after the ending year!");
			break;
		case SUCCESS:
			// Set the new analysis user parameters accordingly.
			currUserParameters.setAnalysisType(analysisType);
			currUserParameters.setCountry(countryCode);
			currUserParameters.setStartYear(startYear);
			currUserParameters.setEndYear(endYear);
			
			// Perform analysis and update UI.
			if (analysisContext.executeStrat()) {
				displayPanel.removeAll();
				listofViewers.displayViewers(displayPanel);
				displayPanel.updateUI();
			} else {
				displayError("Insuffucient data for analysis! "
						+ "Try different parameters.");
			} break;
		}		
	}

	/**
	 * This function is executed when an analysis type is selected from the
	 * drop-down menu.
	 */
	private void methodSelected() {
		int selectedAnalysisType = methodsList.getSelectedIndex() + 1;
		
		// Check if the selected analysis type is new.
		if (selectedAnalysisType != currentAnalysis) {
			// Clear the list of viewers.
			listofViewers.emptyList();
			
			// Update the current analysis type.
			currentAnalysis = selectedAnalysisType;
			displayInfo("List of viewers has been cleared!");
		}
	}

	/**
	 * This function is used for adding all the initialized components to the
	 * UI window panel for display. 
	 */
	private void addComponents() {
		// Add all the top level components.
		JPanel northPanel = new JPanel();
		northPanel.add(chooseCountryLabel);
		northPanel.add(countriesList);
		northPanel.add(fromTimeLabel);
		northPanel.add(fromTimeList);
		northPanel.add(toTimeLabel);
		northPanel.add(toTimeList);
		
		// Add all the bottom level components.
		JPanel southPanel = new JPanel();
		southPanel.add(viewsLabel);
		southPanel.add(viewsList);
		southPanel.add(addView);
		southPanel.add(removeView);
		southPanel.add(methodLabel);
		southPanel.add(methodsList);
		southPanel.add(recalculate);
		
		// Add the display panel for rendering the viewers on.
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(2, 0));
				
		// Establish panels across the window.
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(displayPanel, BorderLayout.WEST);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * This function is used for displaying any error in a new window.
	 * 
	 * @param err Message detailing what caused the error.
	 */
	private void displayError(String err) {
		JOptionPane.showMessageDialog(new JFrame(), err, "Error",
		        JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * This function is used for displaying any information in a new window.
	 * 
	 * @param info Message detailing any information relevant to the user.
	 */
	private void displayInfo(String info) {
		JOptionPane.showMessageDialog(new JFrame(), info, "Info",
		        JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private
	/**
	 * Reference to the unique instance of the main UI window. 
	 */
	static MainUI instance;
	
	/**
	 * Reference to the list of viewers.
	 */
	static ListOfViewers listofViewers;
	
	/**
	 * Reference to the proxy database used for validating analysis components.
	 */
	static ProxyDatabase proxyDB;
	
	/**
	 * Reference to the current user parameters as seen in the UI. 
	 */
	static UserParameters currUserParameters;
	
	/**
	 * Reference to the country map for retrieving country codes. 
	 */
	static CountryMap countryMap;
	
	/**
	 * Reference to the model used to store the processed data.
	 */
	static Model mainModelRef;
	
	/**
	 * Reference to the current context for the type of analysis being executed.
	 */
	static ContextForAnalysis analysisContext;
	
	/**
	 * Reference to the current analysis type selected on the UI.
	 */
	static int currentAnalysis = 1;
	
	/**
	 * Reference to the unique serial version UID.
	 */
	static final long serialVersionUID = 1L;
	
	/**
	 *  Reference to the country list database file name.
	 */
	static final String countryListFile = "country_list.txt";
	
	/**
	 *  Reference to the minimum year that the application can fetch data for.
	 */
	static final int minYear = 1990;
	
	/**
	 *  Reference to the maximum year that the application can fetch data for.
	 */
	static final int maxYear = 2020;
		
	/**
	 *  Reference to the country selection label.
	 */
	static JLabel chooseCountryLabel;
	
	/**
	 *  Reference to the start time selection label.
	 */
	static JLabel fromTimeLabel;
	
	/**
	 *  Reference to the end time selection label.
	 */
	static JLabel toTimeLabel;
	
	/**
	 *  Reference to the viewer type selection label.
	 */
	static JLabel viewsLabel;
	
	/**
	 *  Reference to the analysis type selection label.
	 */
	static JLabel methodLabel;
	
	/**
	 *  Reference to the country selection drop-down menu.
	 */
	static JComboBox<String> countriesList;
	
	/**
	 *  Reference to the start year selection drop-down menu.
	 */
	static JComboBox<String> fromTimeList;
	
	/**
	 *  Reference to the end year selection drop-down menu.
	 */
	static JComboBox<String> toTimeList;	
	
	/**
	 *  Reference to the viewer type selection drop-down menu.
	 */
	static JComboBox<String> viewsList;
	
	/**
	 *  Reference to the analysis type selection drop-down menu.
	 */
	static JComboBox<String> methodsList;
	
	/**
	 *  Reference to the add view click button.
	 */
	static JButton addView;
	
	/**
	 *  Reference to the remove view click button.
	 */
	static JButton removeView;
	
	/**
	 *  Reference to the recalculate click button.
	 */
	static JButton recalculate;
	
	/**
	 *  Reference to the panel used for displaying all the viewers.
	 */
	static JPanel displayPanel;
	
	/**
	 *  Enum mapping the different types of proxy database response codes.
	 */
	enum Response {
		SUCCESS,
		INVALID_TIME_RANGE,
		INVALID_COUNTRY,
		UNEXPECTED_FORMAT,
		INVALID_TIME_FORMAT
	}
}

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * This class is used to create a login screen asking for user credentials
 * and verifying them through the credentials database before being able to
 * access the main UI features.
 *
 * @author Sanjayan Kulendran.
 * @version	1.0.0
 * @see AbstractDatabase ProxyDatabase RealDatabase
 */
public class Login {
	/**
	 * This function is used to retrieve the unique instance of this object.
	 * The uniqueness is for the purposes of implementing a singleton.
	 * 
	 * @param givenProx The proxy database for verifying credentials through.
	 * @return A unique instance of the object.
	 */
	public static Login getInstance(ProxyDatabase givenProx) {
		if (instance == null) {
			instance = new Login(givenProx);
		}
		
		return instance;
	}
	
	/**
	 * Constructor function for initializing an instance of this object.
	 * 
	 * @param givenProx The proxy database for verifying credentials through.
	 */
	private Login(ProxyDatabase givenProx) {
		// Initialize window layouts and size.
		frame = new JFrame("System Login");
		panel = new JPanel();
		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		proxyRef = givenProx;
	}
	
		
	/**
	 * This function is used for constructing the login window with all of
	 * its components and panels.
	 * 
	 */
	public void initWindow() {		
		// Add the username field.
		JLabel userLabel = new JLabel("Username");
		userLabel.setBounds(10, 20, 80, 25);
		panel.add(userLabel);
		
		userText = new JTextField(20);
		userText.setBounds(100, 20, 165, 25);
		panel.add(userText);
				
		// Add the password field.
		JLabel passLabel = new JLabel("Password");
		passLabel.setBounds(10, 50, 80, 25);
		panel.add(passLabel);
				
		passText = new JPasswordField(20);
		passText.setBounds(100, 50, 165, 25);
		panel.add(passText);
				
		// Add login click button.
		JButton loginButton = new JButton("Login");
		loginButton.setBounds(10, 80, 80, 25);
				
		// Associate an action with the button.
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginButtonPressed();
			}});
		panel.add(loginButton);
		
		// Visualize the window.
		frame.setVisible(true);
		
		// Make program wait for the user to attempt login.
		while(!isPressed) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}
	
	/**
	 * This function is executed whenever the Login button is pressed.
	 */
	private void loginButtonPressed() {	
		String username = userText.getText();
		String password = passText.getText();
				
		// Display error window if login was unsuccessful.
		if (!proxyRef.authenticateUser(username, password)) {
			displayError("Login Failed!");
		}
		
		// Close login window.
		frame.dispose();
		isPressed = true;
	}
	
	/**
	 * This function is used for displaying any errors in a new window.
	 */
	private void displayError(String error) {
		JOptionPane.showMessageDialog(new JFrame(), error, "Error",
		        JOptionPane.ERROR_MESSAGE);
		System.exit(69);
	}
	
	private
	/**
	 * Reference to the login window frame.
	 */
	static JFrame frame;
	
	/**
	 * Reference to the login window panel.
	 */
	static JPanel panel;
	
	/**
	 * Reference to the username text field.
	 */
	static JTextField userText;
	
	/**
	 * Reference to the password text field.
	 */	
	static JPasswordField passText;
	
	/**
	 * Reference to the proxy database for verifying credentials through.
	 */
	static ProxyDatabase proxyRef;
	
	/**
	 * Reference to the unique instance of the login window.
	 */
	static Login instance;
	
	/**
	 * Reference to whether the login button has been clicked.
	 */
	static boolean isPressed = false;
}

package ca.uwo.garage;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
/**
 * This class represents the authorization view
 * @author Jon
 *
 */
@SuppressWarnings("serial")
public class AuthorizationView
	extends View
{
	private JRadioButton m_buyer;
	private JTextField m_userid;
	private JPasswordField m_password;
	private JButton m_login;

	/**
	 * The constructor for this class
	 * @param controller the controller for this view
	 */
	public AuthorizationView(AuthorizationController controller)
	{
		super(controller);

		setTitle("Log In");

		JPanel outer = new JPanel();
		outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
		add(outer);

		outer.add(Box.createRigidArea(new Dimension(0, 5)));
		outer.add(new JLabel("Welcome to the UWO Garage Sale Listings."));
		outer.add(new JLabel("Please enter your credentials below."));

		outer.add(Box.createRigidArea(new Dimension(0,20)));
		JPanel idPanel = new JPanel(new GridLayout(2,2));
		idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		idPanel.add(new JLabel(" User ID: "));
		m_userid = new JTextField();
		idPanel.add(m_userid);

		idPanel.add(new JLabel(" Password: "));
		m_password = new JPasswordField();
		idPanel.add(m_password);

		reset(); // reset the userid and password fields

		outer.add(idPanel);
		
		//Mode panel
		JPanel modePanel = new JPanel(new GridLayout(2, 1));
		outer.add(Box.createRigidArea(new Dimension(0,10)));
		outer.add(modePanel);
		modePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		m_buyer = new JRadioButton("Buyer Mode");
		m_buyer.setSelected(true);
		JRadioButton seller = new JRadioButton("Seller Mode");

		// Ensure that only one of buyer or seller are picked
		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(m_buyer);
		modeGroup.add(seller);

		modePanel.add(m_buyer);
		modePanel.add(seller);
		
		JPanel buttonPanel = new JPanel();
		outer.add(Box.createRigidArea(new Dimension(0,10)));
		outer.add(buttonPanel);
		buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		m_login = new JButton("Login");
		buttonPanel.add(m_login);
		buttonPanel.add(Box.createRigidArea(new Dimension(20,0)));

		// Display the frame
		pack();
		setResizable(false);
		setVisible(true);

		// Center the box
		setLocationRelativeTo(null);
		setLocation(getLocationOnScreen().x, getLocationOnScreen().y - getHeight()/2);
	}

	/**
	 * A method to reset the components in the window
	 */
	public void reset()
	{
		m_userid.setText("");
		m_userid.requestFocusInWindow();
		m_password.setText("");
	}
	/**
	 * A method to determine whether or not buyer mode is selected
	 * @return true if buyer mode is selected
	 */
	public boolean isBuyerMode()
	{
		return m_buyer.isSelected();
	}
	/**
	 * A method used to return the string in the user ID text field
	 * @return the string in the user ID text field
	 */
	public String getUserId()
	{
		return m_userid.getText();
	}
	/**
	 * A method to return the string in the password text field
	 * @return the string in the password textfield
	 */
	public String getPassword()
	{
		// getPassword returns a char[], convert it to a string
		return new String(m_password.getPassword());
	}
	/**
	 * Adds the action listener for the login button
	 * @param trigger the action listener for the login button
	 */
	public void addLoginAction(ActionListener trigger)
	{
		m_login.addActionListener(trigger);
	}
}

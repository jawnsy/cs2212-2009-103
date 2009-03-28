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

@SuppressWarnings("serial")
public class AuthorizationView
	extends View
{
	private JRadioButton m_buyer;
	private JTextField m_userid;
	private JPasswordField m_password;
	private JButton m_login;

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

	public void reset()
	{
		m_userid.setText("");
		m_userid.requestFocusInWindow();
		m_password.setText("");
	}
	public boolean isBuyerMode()
	{
		return m_buyer.isSelected();
	}
	public String getUserId()
	{
		return m_userid.getText();
	}
	public String getPassword()
	{
		// getPassword returns a char[], convert it to a string
		return new String(m_password.getPassword());
	}

	public void addLoginAction(ActionListener trigger)
	{
		m_login.addActionListener(trigger);
	}
}

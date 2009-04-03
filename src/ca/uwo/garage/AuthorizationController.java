package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;
/**
 * This class performs all the actions for the authorization view
 * @author Jon
 *
 */
public class AuthorizationController
	implements Controller
{
	private AuthorizationView m_view; //The view object
	private boolean m_ready, m_authorized;
	private Storage m_storage; //The storage object
	private User m_user; //The user object
	private int m_counter;//Keeps track of unsuccessful log ons

	/**
	 * The constructor for this class
	 */
	public AuthorizationController()
	{
		m_view = null;
		m_ready = false;
		m_storage = null;
		m_counter = 0;
	}
	/**
	 * A method used to start the controller
	 */
	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("an AuthorizationView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");
	}
	/**
	 * A method to display the view object
	 */
	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof AuthorizationView))
			throw new ViewTypeException("AuthorizationView");

		m_view = (AuthorizationView) view;
		m_view.addLoginAction(new AuthorizationHook());
		m_view.addWindowListener(new CloseTrigger());
	}
	/**
	 * Returns the storage object
	 * @param storage the storage object
	 */
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
	/**
	 * A method used to determine whether or not the controller has been started
	 * @return true if it has been started
	 */
	public boolean isReady()
	{
		return m_ready;
	}
	/**
	 * A method used to determine whether or not the login information entered
	 * by the user was valid or not
	 * @return true if it was valid
	 */
	public boolean isAuthorized()
	{
		return m_authorized;
	}
	/**
	 * A method to determine whether or not the user selected to enter buyer mode
	 * @return true if the user did
	 */
	public boolean isBuyerMode()
	{
		return m_view.isBuyerMode();
	}
	/**
	 * A method to determine whether or not the user selected to enter seller mode
	 * @return true if the user did
	 */	
	public boolean isSellerMode()
	{
		return !m_view.isBuyerMode();
	}
	/**
	 * A method used to get the user object associated with the user
	 * using the system
	 * @return the user object
	 */
	public User getUser()
	{
		return m_user;
	}

	/**
	 * The action listener for this class
	 *
	 */
	private class AuthorizationHook
		extends WindowAdapter
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{

			try {
				m_user = m_storage.findUser(m_view.getUserId());
			} catch (StorageNotFoundException e) {
				// no-op; errors will be caught later
			}

			// Check if the user typed a valid password; if so, then return true
			if (m_user != null && m_user.validPassword(m_view.getPassword()))
			{
				m_authorized = true;
				m_ready = true;
				m_view.dispose();
			}
			else // If we get here, there was an error, so show a message
				error();
		}
		/**
		 * A method used to close the program when a user has had 3 unsuccessful log ons
		 */
		public void error() {
			if (m_counter == 3)
			{
				System.exit(0);
			}
			
			JOptionPane.showMessageDialog(
					null,
					"Could not authenticate your login credentials.\n" +
					"Please try again.", // Text
					"Authentication Error",
					JOptionPane.ERROR_MESSAGE
				);
			m_counter ++;
			m_view.reset();
		}
	}

	/**
	 * Closes the program
	 *
	 */
	private class CloseTrigger
		extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) {
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}
}

package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;

public class AuthorizationController
	implements Controller
{
	private AuthorizationView m_view;
	private boolean m_ready, m_authorized;
	private Storage m_storage;
	private User m_user;
	private int m_counter;

	public AuthorizationController()
	{
		m_view = null;
		m_ready = false;
		m_storage = null;
		m_counter = 0;
	}
	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("an AuthorizationView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");
	}
	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof AuthorizationView))
			throw new ViewTypeException("AuthorizationView");

		m_view = (AuthorizationView) view;
		m_view.addLoginAction(new AuthorizationHook());
		m_view.addWindowListener(new CloseTrigger());
	}
	public void storage(Storage storage)
	{
		m_storage = storage;
	}

	public boolean isReady()
	{
		return m_ready;
	}
	public boolean isAuthorized()
	{
		return m_authorized;
	}
	public boolean isBuyerMode()
	{
		return m_view.isBuyerMode();
	}
	public boolean isSellerMode()
	{
		return !m_view.isBuyerMode();
	}

	public User getUser()
	{
		return m_user;
	}

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

	private class CloseTrigger
		extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) {
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}
}

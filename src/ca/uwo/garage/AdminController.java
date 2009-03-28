package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;
import ca.uwo.garage.storage.UserException;

public class AdminController
	implements Controller
{
	private AdminView m_view;
	private boolean m_ready;
	private Storage m_storage;
	
	public AdminController()
	{
		m_view = null;
		m_ready = false;
		m_storage = null;
	}
	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("an AdminView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");

		try {
			m_view.setUserList(m_storage.listUsers());
		} catch (StorageEmptyException e) {
			System.err.println("No users");
		}
	}
	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof AdminView))
			throw new ViewTypeException("AdminView");

		m_view = (AdminView) view;
		m_view.addWindowListener(new CloseTrigger());
		m_view.addUpdateUserAction(new UserUpdateTrigger());
	}
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
	public boolean isReady() {
		return false;
	}
	public User getUser(String userid)
	{
		User user = null;
		try {
			user = m_storage.findUser(userid);
		} catch (StorageNotFoundException e) {
		}
		return user;
	}
	public void deleteUser(String userid)
	{
		try {
			User user = m_storage.findUser(userid);
			m_storage.delete(user);
			m_view.setUserList(m_storage.listUsers());
		} catch (StorageNotFoundException e) {
			JOptionPane.showMessageDialog(
					m_view,
					"Could not find user: " + userid + "\n" +
					"Please select a user to delete from the listbox.\n",
					"User Deletion Problem",
					JOptionPane.ERROR_MESSAGE
				);
		} catch (StorageEmptyException e) {
		}
	}

	private class UserUpdateTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			User user = null;
			try {
				user = new User(m_view.getUserId());
				user.first_name(m_view.getFirstName());
				user.last_name(m_view.getLastName());
				user.phone(m_view.getPhone());
				if (m_view.passwordChanged())
					user.password(m_view.getPassword());
			} catch (UserException e) {
				JOptionPane.showMessageDialog(
						m_view,
						"Error with user parameters:\n" +
						e.getMessage(),
						"User Parameter Problem",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}

			// All parameters are okay, copy them to the actual user
			try {
				User old = m_storage.findUser(user.id());
				old.first_name(user.first_name());
				old.last_name(user.last_name());
				old.phone(user.phone());
				old.password(m_view.getPassword());

				if (m_view.passwordChanged())
					user.password(m_view.getPassword()); // validated above
			} catch (StorageNotFoundException e) {
			} catch (UserException e) {
				// shouldn't happen since we have validated parameters before copying
			}
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

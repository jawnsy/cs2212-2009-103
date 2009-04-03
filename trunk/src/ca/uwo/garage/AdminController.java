package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.CategoryException;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;
import ca.uwo.garage.storage.StorageFullException;
import ca.uwo.garage.storage.StorageKeyException;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;
import ca.uwo.garage.storage.UserException;

/**
 *This class performs all the actions for admin mode
 * @author Jon
 *
 */
public class AdminController
	implements Controller
{
	private AdminView m_view; //The view object
	private boolean m_ready; //Stores whether or not the controller has been started
	private Storage m_storage; //The storage object
	/**
	 * The constructor for this class
	 */
	public AdminController()
	{
		m_view = null; 
		m_ready = false;
		m_storage = null;
	}
	/**
	 * A method used to start the controller
	 */
	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("an AdminView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");

		try {
			m_view.setUserList(m_storage.listUsers());
			m_view.setCategoryList(m_storage.listCategories());
		} catch (StorageEmptyException e) {
		}
	}
	/**
	 * Method used to display the view object
	 */
	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof AdminView))
			throw new ViewTypeException("AdminView");

		m_view = (AdminView) view;
		m_view.addWindowListener(new CloseTrigger());

		m_view.addUpdateUserAction(new UserUpdateTrigger());
		m_view.addDeleteUserAction(new UserDeleteTrigger());
		m_view.addAddUserAction(new UserAddTrigger());

		m_view.addAddCategoryAction(new CategoryAddTrigger());
		m_view.addUpdateCategoryAction(new CategoryUpdateTrigger());
		m_view.addDeleteCategoryAction(new CategoryDeleteTrigger());
	}
	
	/**
	 * A method used to return the storage object
	 * @param storage the storage object
	 */
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
	/**
	 * Method used to return whether or not the controller jas been started
	 */
	public boolean isReady() {
		return m_ready;
	}
	/**
	 * Method used to get a user object associated with a user ID
	 * @param userid the user ID
	 * @return the user object
	 */
	public User getUser(String userid)
	{
		User user = null;
		try {
			user = m_storage.findUser(userid);
		} catch (StorageNotFoundException e) {
		}
		return user;
	}

	/**
	 * The action listener for the category add button
	 *
	 */
	private class CategoryAddTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			String s = (String)JOptionPane.showInputDialog(
					m_view,
					"Please choose a category name:",
					"Adding New Category",
					JOptionPane.PLAIN_MESSAGE
				);

			// The user hit cancel
			if (s == null || s.isEmpty())
				return;

			int categoryid = m_storage.getCategoryIdByName(s);
			// categoryid == -1 means we couldn't find it
			if (categoryid == -1)
			{
				try {
					m_storage.store(new Category(s));

					// Update the category list display
					m_view.setCategoryList(m_storage.listCategories());
				} catch (StorageFullException e) {
				} catch (StorageKeyException e) {
				} catch (CategoryException e) {
					JOptionPane.showMessageDialog(
							m_view,
							"The specified category name is invalid: " +
							e.getMessage(),
							"Error Adding Category",
							JOptionPane.ERROR_MESSAGE
						);
				} catch (StorageEmptyException e) {
					// won't happen since we just added a category
				}
			}
			else
			{
				JOptionPane.showMessageDialog(
						m_view,
						"Sorry, that category name already exists",
						"Error Adding Category",
						JOptionPane.ERROR_MESSAGE
					);
			}
		}
	}
	
	/**
	 * The action listener for the category delete button
	 *
	 */
	private class CategoryDeleteTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			String category = m_view.getSelectedCategory();
			if (category == null || category.isEmpty()) {
				JOptionPane.showMessageDialog(
						m_view,
						"Please select a category to delete first.",
						"Error Deleting Category",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
	
			int categoryid = m_storage.getCategoryIdByName(category);
			if (categoryid == -1) {
				JOptionPane.showMessageDialog(
						m_view,
						"Could not find category... This shouldn't happen. " +
						"Please file a bug report!",
						"Error Deleting Category",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
			
			// Everything looks good, do the delete now
			try {
				Category cat = m_storage.findCategory(categoryid);
				m_storage.delete(cat);
	
				// Update the category list display
				m_view.setCategoryList(m_storage.listCategories());
			} catch (StorageNotFoundException e) {
				// shouldn't happen since we checked above
			} catch (StorageEmptyException e) {
				// ignore this
			}
		}
	}
	
	/**
	 * The action listener for the category update button
	 *
	 */
	private class CategoryUpdateTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			String category = m_view.getSelectedCategory();
			if (category == null || category.isEmpty()) {
				JOptionPane.showMessageDialog(
						m_view,
						"Please select a category to modify first.",
						"Error Modifying Category",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}

			String s = (String)JOptionPane.showInputDialog(
					m_view,
					"Please enter a new category name:",
					category
				);

			// The user hit cancel
			if (s == null || s.isEmpty())
				return;

			int categoryid = m_storage.getCategoryIdByName(category);
			if (categoryid == -1) {
				JOptionPane.showMessageDialog(
						m_view,
						"Could not find category... This shouldn't happen. " +
						"Please file a bug report!",
						"Error Deleting Category",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}

			// Everything looks good, do the update now
			try {
				Category cat = m_storage.findCategory(categoryid);
				cat.name(s);
	
				// Update the category list display
				m_view.setCategoryList(m_storage.listCategories());
			} catch (StorageNotFoundException e) {
				// shouldn't happen since we checked above
			} catch (StorageEmptyException e) {
				// ignore this
			} catch (CategoryException e) {
				JOptionPane.showMessageDialog(
						m_view,
						"Invalid category name: " + e.getMessage(),
						"Error Updating Category",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}
		}
	}

	/**
	 * The action listener for the user add button
	 *
	 */
	private class UserAddTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			String s = (String)JOptionPane.showInputDialog(
					m_view,
					"Please choose a userid:",
					"Adding New User",
					JOptionPane.PLAIN_MESSAGE
				);

			// The user hit cancel
			if (s == null || s.isEmpty())
				return;

			if (m_storage.existsUser(s))
			{
				JOptionPane.showMessageDialog(
						m_view,
						"Sorry, that userid already exists",
						"Error Adding User",
						JOptionPane.ERROR_MESSAGE
					);
			}
			else {
				try {
					m_storage.store(new User(s));

					// Update the user list display
					m_view.setUserList(m_storage.listUsers());
				} catch (StorageFullException e) {
				} catch (StorageKeyException e) {
				} catch (UserException e) {
					JOptionPane.showMessageDialog(
							m_view,
							"The specified username is invalid: " +
							e.getMessage(),
							"Error Adding User",
							JOptionPane.ERROR_MESSAGE
						);
				} catch (StorageEmptyException e) {
					// not going to get here; we just added something!
				}
			}
		}
	}
	
	/**
	 * The action listener for the user delete button
	 *
	 */
	private class UserDeleteTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			try {
				User user = m_storage.findUser(m_view.getUserId());
				m_storage.delete(user);
				m_view.setUserList(m_storage.listUsers());
			} catch (StorageNotFoundException e) {
				JOptionPane.showMessageDialog(
						m_view,
						"Could not find user: " + m_view.getUserId() + "\n" +
						"Please select a user to delete from the listbox.\n",
						"User Deletion Problem",
						JOptionPane.ERROR_MESSAGE
					);
			} catch (StorageEmptyException e) {
			}
		}
	}

	/**
	 * The action listener for the user update button
	 *
	 */
	private class UserUpdateTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			User user = null;
			try {
				user = new User(m_view.getUserId());

				if (!m_view.getFirstName().isEmpty())
					user.first_name(m_view.getFirstName());

				if (!m_view.getLastName().isEmpty())
					user.last_name(m_view.getLastName());

				if (!m_view.getPhone().isEmpty())
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

	/**
	 * The action listener for the window close button
	 * @author James
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

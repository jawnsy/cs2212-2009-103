package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.User;

@SuppressWarnings("serial")
public class AdminView
	extends View
{
	private UserPanel m_users;
	private CategoryPanel m_categories;
	public AdminView(Controller control) {
		super(control);
		setTitle("Admin View");

	    Container container = getContentPane();

        JTabbedPane tabbedPane = new JTabbedPane();
        m_users = new UserPanel();
        tabbedPane.addTab("Users", m_users);

        m_categories = new CategoryPanel();
        tabbedPane.addTab("Categories", m_categories);

        //Add the tabbed pane to this panel.
        container.add(tabbedPane);

        pack();
	    setVisible(true);
	    setResizable(false);
	}
	public void setUserList(Collection<User> users)
	{
		Iterator<User> iter = users.iterator();

		/* the JList needs an array of objects, so we've got to create a new collection
		 * of Strings (userid's) and then copy that to an array
		 */
		DefaultListModel listModel = new DefaultListModel();
		while (iter.hasNext())
		{
			listModel.addElement(iter.next().id());
		}
		m_users.list.setModel(listModel);

		// If the list model isn't empty, select the first user
		if (!listModel.isEmpty())
			m_users.list.setSelectedIndex(0);
	}
	public void setCategoryList(Collection<Category> categories)
	{
		Iterator<Category> iter = categories.iterator();

		/* the JList needs an array of objects, so we've got to create a new collection
		 * of Strings (category names) and then copy that to an array
		 */
		DefaultListModel listModel = new DefaultListModel();
		while (iter.hasNext())
		{
			listModel.addElement(iter.next().name());
		}
		m_categories.list.setModel(listModel);

		// If the list model isn't empty, select the first category
		if (!listModel.isEmpty())
			m_categories.list.setSelectedIndex(0);
	}

	public void addAddCategoryAction(ActionListener ev)
	{
		m_categories.add.addActionListener(ev);
	}
	public void addDeleteCategoryAction(ActionListener ev)
	{
		m_categories.delete.addActionListener(ev);
	}
	public void addUpdateCategoryAction(ActionListener ev)
	{
		m_categories.modify.addActionListener(ev);
	}
	public String getSelectedCategory()
	{
		return (String)m_categories.list.getSelectedValue();
	}

	public void addAddUserAction(ActionListener ev)
	{
		m_users.add.addActionListener(ev);
	}
	public void addDeleteUserAction(ActionListener ev)
	{
		m_users.delete.addActionListener(ev);
	}
	public void addUpdateUserAction(ActionListener ev)
	{
		m_users.modify.addActionListener(ev);
	}
	public String getUserId()
	{
		return m_users.userid.getText();
	}
	public String getFirstName()
	{
		return m_users.firstName.getText();
	}
	public String getLastName()
	{
		return m_users.lastName.getText();
	}
	public String getPhone()
	{
		return m_users.phone.getText();
	}
	public String getPassword()
	{
		return m_users.password.getText();
	}
	public boolean passwordChanged()
	{
		// If getText is empty, then the password wasn't changed
		return !m_users.password.getText().isEmpty();
	}

	private class CategoryPanel
		extends JPanel
	{
		JList list;
		JButton add, modify, delete;
		public CategoryPanel()
		{
			setLayout(new BorderLayout());
			list = new JList();
			add(list, BorderLayout.CENTER);

			// Bottom toolbox
			JPanel tools = new JPanel(new GridLayout(1,3));
			add = new JButton("Add");
			tools.add(add);

			modify = new JButton("Modify");
			tools.add(modify);
	
			delete = new JButton("Delete");
			tools.add(delete);

			add(tools, BorderLayout.SOUTH);
		}
	}

	private class UserPanel
		extends JPanel
	{
		JButton add, modify, delete;
		JList list;
		JTextField userid, firstName, lastName, zoom, phone, password;

		public UserPanel()
		{
			setLayout(new BorderLayout());

			JPanel tools = new JPanel(new GridLayout(3, 1));
			add = new JButton("Add");
			tools.add(add);

			modify = new JButton("Modify");
			tools.add(modify);

			delete = new JButton("Delete");
			tools.add(delete);

			list = new JList();
			list.setPreferredSize(new Dimension(150, 0));
			list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			list.setLayoutOrientation(JList.VERTICAL);

			add(list, BorderLayout.WEST);
			list.addListSelectionListener(new UserSelectionTrigger());

			JPanel editor = new JPanel(new GridLayout(7,2));
			editor.add(new JLabel("User ID: "));
			userid = new JTextField();
			editor.add(userid);

			editor.add(new JLabel("Password: "));
			password = new JTextField();
			editor.add(password);
		
			editor.add(new JLabel("First Name: "));
			firstName = new JTextField();
			editor.add(firstName);

			editor.add(new JLabel("Last Name: "));
			lastName = new JTextField();
			editor.add(lastName);

			editor.add(new JLabel("Phone Number: "));
			phone = new JTextField();
			editor.add(phone);

			editor.add(new JLabel("Home Position: "));
			editor.add(new JTextField());

			editor.add(new JLabel("Default Zoom: "));
			zoom = new JTextField();
			editor.add(zoom);

			JPanel right = new JPanel(new BorderLayout());
			right.add(editor, BorderLayout.CENTER);
			right.add(tools, BorderLayout.SOUTH);

			add(right, BorderLayout.EAST);
		}

		private class UserSelectionTrigger
			implements ListSelectionListener
		{
			public void valueChanged(ListSelectionEvent ev) {
				AdminController control = (AdminController)m_control;
				if (list.isSelectionEmpty())
					return;

				User user = control.getUser((String)list.getSelectedValue());
				userid.setText(user.id());
				userid.setEnabled(false);
				password.setText(null); // clear the password box

				firstName.setText(user.first_name());
				lastName.setText(user.last_name());
				zoom.setText(Integer.toString(user.zoom().level()));
				phone.setText(user.phone());
			}		
		}
	}
}

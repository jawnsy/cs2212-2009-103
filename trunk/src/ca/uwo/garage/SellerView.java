package ca.uwo.garage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import ca.uwo.garage.storage.GarageSale;
import ca.uwo.garage.storage.User;
/**
 * This class represents the seller view
 *
 */
@SuppressWarnings("serial")
public class SellerView
	extends View
{
	private JButton delete, modify, addNew, bulkLoad, browse;
	private JList garageList;
	private TextField bulkTextField;

	/**
	 * The constructor for this class
	 * @param control the controller
	 */
	public SellerView(Controller control)
	{	
		super(control);

		setTitle("Seller Mode");
		setVisible(true);

		JPanel panel = new JPanel(new BorderLayout());
		add(panel);

		JLabel sellerLabel = new JLabel(" Your Garage Sales:");
		panel.add(sellerLabel, BorderLayout.NORTH);

		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(innerPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		delete = new JButton("Delete");
		modify = new JButton("Modify");
		addNew = new JButton("Add New");

		buttonPanel.add(addNew);
		buttonPanel.add(modify);
		buttonPanel.add(delete);
		innerPanel.add(buttonPanel);

		innerPanel.add(Box.createRigidArea(new Dimension(90,0)));

		garageList = new JList();
		innerPanel.add(garageList);

		innerPanel.add(Box.createRigidArea(new Dimension(20,0)));

		JPanel innerPanel2 = new JPanel();
		innerPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(innerPanel2, BorderLayout.SOUTH);

		innerPanel2.add(Box.createRigidArea(new Dimension(0,40)));

		bulkLoad = new JButton("Bulk Load");
		innerPanel2.add(bulkLoad);

		JLabel bulkLabel = new JLabel(" Path:");
		innerPanel2.add(bulkLabel);

		bulkTextField = new TextField("",20);
		innerPanel2.add(bulkTextField);

		browse = new JButton("Browse");
		innerPanel2.add(browse);
		browse.addActionListener(new BrowseTrigger());

		pack();
		setResizable(false);

		garageList.setSize(garageList.getSize().width + 60, garageList.getSize().height);

		delete.setSize(addNew.getSize());
		modify.setSize(addNew.getSize());

		setLocationRelativeTo(null);
		setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y - this.getHeight()/2);
	}
	/**
	 * A method used to get the string in the bulk load text field
	 * @return
	 */
	public String getPath()
	{
		return bulkTextField.getText();
	}
	/**
	 * A method to add the action listener for the bulk load button
	 * @param ev the action listener for the bulk load button
	 */
	public void addBulkLoadAction(ActionListener ev)
	{
		bulkLoad.addActionListener(ev);
	}
	/**
	 * The action to be performed when the browse button is clicked
	 *
	 */
	private class BrowseTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
		    JFileChooser chooser = new JFileChooser();
		    int returnVal = chooser.showOpenDialog(((JButton)ev.getSource()).getParent());
		    if (returnVal == JFileChooser.APPROVE_OPTION)
		    {
		    	bulkTextField.setText(chooser.getSelectedFile().getPath());
		    }
		}
	}
	/**
	 * A method used to add the action listener for the add button
	 * @param ev the action listener for the add button
	 */
	public void addAddAction(ActionListener ev)
	{
		addNew.addActionListener(ev);
	}
	/**
	 * A method used to add the action listener for the delete button
	 * @param ev the action listener for the delete button
	 */
	public void addDeleteAction(ActionListener ev)
	{
		delete.addActionListener(ev);
	}
	/**
	 * A method used to get the selected garage sale in the list of
	 * garage sales
	 * @return the selected garage sale
	 */
	public GarageSale getListSelect()
	{
		return (GarageSale) garageList.getSelectedValue();
	}

	/**
	 * A method used to update the garage sale list
	 * @param sales the garage sales from the user
	 * @param user the user
	 */
	public void update(Collection<GarageSale> sales, User user)
	{
		DefaultListModel listModel = new DefaultListModel();
		Iterator<GarageSale> iter = sales.iterator();
		while (iter.hasNext())
		{
			GarageSale sale = iter.next();
			if (sale.owner().id().equals(user.id()))
				listModel.addElement(sale);
		}
		garageList.setModel(listModel);
	}
}
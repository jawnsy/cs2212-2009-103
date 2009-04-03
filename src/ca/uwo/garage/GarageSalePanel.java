package ca.uwo.garage;

import java.awt.BorderLayout;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import ca.uwo.garage.storage.GarageSale;
/**
 * This class represents the main panel in the buyer mode
 * @author Jon
 *
 */
@SuppressWarnings("serial")
public class GarageSalePanel
	extends JPanel
{
	private JList m_list;
	private LinkedList<GarageSale> m_sales;

	/**
	 * The constructor for this class
	 */
	public GarageSalePanel()
	{
		setLayout(new BorderLayout());

		add(new JLabel("Quick access:"), BorderLayout.NORTH);
		m_list = new JList();
		m_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		m_list.setLayoutOrientation(JList.VERTICAL);
		add(m_list, BorderLayout.CENTER);
	}
	/**
	 * A method used to update the panel
	 * @param sales the garage sales
	 */
	public void update(Collection<GarageSale> sales)
	{
		Iterator<GarageSale> iter = sales.iterator();
		DefaultListModel listModel = new DefaultListModel();		
		m_sales = new LinkedList<GarageSale>();
		while (iter.hasNext())
		{
			GarageSale sale = iter.next();
			m_sales.add(sale);
			listModel.addElement(sale);
		}

		m_list.setModel(listModel);

		// If the list model isn't empty, select the first sale
		if (!listModel.isEmpty())
			m_list.setSelectedIndex(0);
	}
	/**
	 * A method used to filter garage sales the user has selected to view
	 * @param filter the filter object
	 */
	public void filter(SearchFilter filter)
	{
		DefaultListModel listModel = new DefaultListModel();
		Iterator<GarageSale> iter = m_sales.iterator();
		while (iter.hasNext())
		{
			GarageSale sale = iter.next();
			if (filter.isIncluded(sale))
				listModel.addElement(sale);
		}

		m_list.setModel(listModel);

		// If the list model isn't empty, select the first sale
		if (!listModel.isEmpty())
			m_list.setSelectedIndex(0);
	}
	/**
	 * A method used to get the current garage sale selected
	 * @return the garage sale selected
	 */
	public GarageSale getSelected()
	{
		return (GarageSale)m_list.getSelectedValue();
	}
	/**
	 * A method used to add the action listener for the list of garage sales
	 * @param ev the action listener
	 */
	public void addGarageSaleTrigger(ListSelectionListener ev)
	{
		m_list.addListSelectionListener(ev);
	}
}

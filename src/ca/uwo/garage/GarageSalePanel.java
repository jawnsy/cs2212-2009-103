package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

@SuppressWarnings("serial")
public class GarageSalePanel
	extends JPanel
{
	private JList m_list;
	private LinkedList<GarageSale> m_sales;

	public GarageSalePanel()
	{
		setLayout(new BorderLayout());

		add(new JLabel("Quick access:"), BorderLayout.NORTH);
		m_list = new JList();
		m_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		m_list.setLayoutOrientation(JList.VERTICAL);
		add(m_list, BorderLayout.CENTER);
	}
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
	public GarageSale getSelected()
	{
		return (GarageSale)m_list.getSelectedValue();
	}
	public void addGarageSaleTrigger(ListSelectionListener ev)
	{
		m_list.addListSelectionListener(ev);
	}
}

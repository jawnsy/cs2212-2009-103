package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.uwo.garage.mapviewer.MapPanel;
import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.GarageSale;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;

@SuppressWarnings("serial")
public class BuyerView
	extends View
{
	SearchBar m_search;
	MapPanel m_map;
	StatusBar m_status;
	GarageSalePanel m_sales;

	public BuyerView(Controller control) {
		super(control);

		setTitle("Garage Sale Viewer");

		setLayout(new BorderLayout());

		m_search = new SearchBar();
		add(m_search, BorderLayout.NORTH);
		m_search.addSearchTrigger(new SearchTrigger());
		m_search.addResetTrigger(new ClearTrigger());

		m_map = new MapPanel();
		add(m_map, BorderLayout.CENTER);

		m_sales = new GarageSalePanel();
		m_sales.addGarageSaleTrigger(new SaleSelectedTrigger());

		JPanel tools = new JPanel(new FlowLayout());
		tools.add(new JButton("Test"));
		tools.add(new JButton("Test"));
		tools.add(new JButton("Test"));

		JPanel right = new JPanel(new BorderLayout());
		right.add(m_sales, BorderLayout.CENTER);
		right.add(tools, BorderLayout.SOUTH);		
		add(right, BorderLayout.EAST);

		m_status = new StatusBar();
		add(m_status, BorderLayout.SOUTH);

		// Center the box
		setLocationRelativeTo(null);

		// Display the frame
		pack();
		setResizable(false);
		setVisible(true);
	}
	public void update(Storage storage)
	{
		m_search.update(storage);
		try {
			m_sales.update(storage.listGarageSales()); // show everything
		} catch (StorageEmptyException e) {
			// don't do anything if it's empty
		}

		pack();
	}

	private class ClearTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			m_search.reset();
			m_search.update(((BuyerController)m_control).storage());
		}
	}

	private class SearchTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev)
		{
			m_sales.filter(new SearchQuery());
		}
		private class SearchQuery
			extends SearchFilter
		{
			Set<String> categorySet;
			Collection<GeoConstraint> geoConstraints;
			public SearchQuery()
			{
				geoConstraints = m_search.getGeoConstraints();
				categorySet = m_search.getSelectedCategories();
			}
			public boolean isIncluded(GarageSale sale)
			{
				Iterator<Category> iter = sale.listCategories().iterator();

				while (iter.hasNext())
				{
					if (!categorySet.contains(iter.next().name()))
					{
						return false;
					}
				}

				Iterator<GeoConstraint> geoiter = geoConstraints.iterator();
				while (geoiter.hasNext())
				{
					if (!geoiter.next().isOk(sale.location()))
					{
						return false;
					}
				}
				return true;
			}
		}
	}
	private class SaleSelectedTrigger
		implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			// If they're not adjusting, then this is a no-op
			if (!e.getValueIsAdjusting())
				return;
			System.out.println("Current address: " + m_sales.getSelected().address());
		}
	}
}

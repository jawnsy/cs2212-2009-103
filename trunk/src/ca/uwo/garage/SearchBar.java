package ca.uwo.garage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.uwo.garage.storage.Storage;

@SuppressWarnings("serial")
public class SearchBar
	extends JPanel
{
	private LinkedList<FilterBar> m_filters;
	private static final int FILTERS = 3;
	private JButton m_start;
	
	public SearchBar()
	{
		setLayout(new GridLayout(2 + FILTERS, 1));

		m_filters = new LinkedList<FilterBar>();

		add(new JLabel("To filter the results shown in the Quick Access panel, enter search conditions below..."));
		for (int i = 0; i < FILTERS; i++)
		{
			addFilterRow();
		}

		JPanel bottom = new JPanel(new GridLayout(1, 2));
		m_start = new JButton("Begin Search");
		bottom.add(m_start);

		JButton reset = new JButton("Clear Query");
		reset.addActionListener(new ClearTrigger());
		bottom.add(reset);

		add(bottom);
	}
	public void addSearchTrigger(ActionListener ev)
	{
		m_start.addActionListener(ev);
	}
	public Set<String> getSelectedCategories()
	{
		HashSet<String> selected = new HashSet<String>();
		Iterator<FilterBar> iter = m_filters.iterator();
		while (iter.hasNext())
		{
			// take the intersection of all categories
			selected.retainAll(iter.next().getSelectedCategories());
		}
		return selected;
	}

	public void addFilterRow() {
		FilterBar filter = new FilterBar();
		m_filters.add(filter);
		add(filter);
	}
	public void reset() {
		Iterator<FilterBar> iter = m_filters.iterator();
		while (iter.hasNext())
		{
			iter.next().reset();
		}
	}

	public void update(Storage storage) {
		Iterator<FilterBar> iter = m_filters.iterator();
		while (iter.hasNext())
		{
			iter.next().update(storage);
		}
	}

	private class ClearTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) {
			reset();
		}
	}
	/*
	   1. Within a given radius, in kilometers, of a given longitude and latitude
	   2. By userid. The buyer must be able to see all the garage sales that, for example, user abc1 has posted
	   3. By date: The buyer must be able to see all the garage sales on a particular date, OR all the garage sales with a given date range.
	   4. By category. The buyer must be able to choose one or more categories and then see all the garage sales offering those types of items.
	   5. By ranking for a seller: The buyer must be able to select a ranking and see all garage sales offered by seller who either have the selected ranking OR have a ranking worse that or equal to the selected ranking or have a ranking better than or equal to the selected ranking.
	   6. By ranking for a garage sale: The buyer must be able to select a ranking and see all garage sales offered that have the selected ranking OR have a ranking worse that or equal to the selected ranking or have a ranking better than or equal to the selected ranking.
	   7. Within the above options for viewing, the buyer must be able select just one of the ways to view OR select several ways to view. For example, the buyer might want to see all garages sales offered on Saturday, June 14, 2008 that are selling furniture and have a ranking of 4 or more.
	*/
}

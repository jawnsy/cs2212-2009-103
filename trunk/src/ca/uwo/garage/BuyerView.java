package ca.uwo.garage;

import java.awt.BorderLayout;

import ca.uwo.garage.mapviewer.MapPanel;
import ca.uwo.garage.storage.Storage;

@SuppressWarnings("serial")
public class BuyerView
	extends View
{
	SearchBar m_search;
	MapPanel m_map;
	StatusBar m_status;

	public BuyerView(Controller control) {
		super(control);

		setTitle("Garage Sale Viewer");

		setLayout(new BorderLayout());

		m_search = new SearchBar();
		add(m_search, BorderLayout.NORTH);

		m_map = new MapPanel();
		add(m_map, BorderLayout.CENTER);

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
		pack();
	}
}

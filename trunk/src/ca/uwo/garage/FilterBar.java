package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.GeoPosition;
import ca.uwo.garage.storage.GeoPositionException;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;
import ca.uwo.garage.storage.User;

@SuppressWarnings("serial")
public class FilterBar
	extends JPanel
	implements ItemListener
{
	private JComboBox m_selector;
	private JPanel m_filter;
	private static final String[] searchBy = {
			"--- Select Condition ---",
			"radius within",
			"category",
			"date",
			"seller",
			"garage sale rating",
			"seller rating"
		};

	private RadiusFilter m_radius;
	private CategoryFilter m_category;
	private DateFilter m_date;
	private SellerFilter m_seller;

	public FilterBar()
	{
		setLayout(new BorderLayout());

		m_selector = new JComboBox(searchBy);
		m_selector.addItemListener(this);
		add(m_selector, BorderLayout.WEST);
	}
	public void update(Storage storage)
	{
		m_filter = new FilterPlaceholder(storage);
		add(m_filter, BorderLayout.EAST);
	}
	public void reset()
	{
		m_selector.getModel().setSelectedItem(searchBy[0]);
	}
	public GeoPosition getGeoPosition()
	{
		return m_radius.getGeoPosition();
	}
	public double getGeoDistance()
	{
		double distance = -1;
		try {
			distance = Double.parseDouble(m_radius.distance.getText());
			if (distance <= 0)
			{
				JOptionPane.showMessageDialog(
						null,
						"Distance values must be greater than zero km.",
						"Error in Distance Constraint",
						JOptionPane.ERROR_MESSAGE
					);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(
					null,
					"Error parsing distance value. Please make sure it's a decimal number.",
					"Error Reading Distance Constraint",
					JOptionPane.ERROR_MESSAGE
				);
		}
		return distance;
	}

	public void itemStateChanged(ItemEvent evt)
	{
		CardLayout cl = (CardLayout) (m_filter.getLayout());
		// Change the panel based on the selected ComboBox text
		cl.show(m_filter, (String) evt.getItem());
	}

	public String mode()
	{
		switch (m_selector.getSelectedIndex())
		{
			case 0: return "";
			case 1: return "radius";
			case 2: return "category";
			case 3: return "date";
			case 4: return "seller";
			case 5: return "rank_sales";
			case 6: return "rank_seller";
		}
		return "";
	}
	private class FilterPlaceholder
		extends JPanel
	{
		public FilterPlaceholder(Storage storage)
		{
			setLayout(new CardLayout());

			m_radius = new RadiusFilter(storage);
			m_category = new CategoryFilter(storage);
			m_date = new DateFilter(storage);
			m_seller = new SellerFilter(storage);

			add(new JPanel(),	searchBy[0]); // empty panel
			add(m_radius, 		searchBy[1]);
			add(m_category, 	searchBy[2]);
			add(m_date, 		searchBy[3]);
			add(m_seller, 		searchBy[4]);
		}
	}

	private class RadiusFilter
		extends JPanel
	{
		JTextField distance, latitude, longitude;

		public RadiusFilter(Storage storage)
		{
			setLayout(new FlowLayout());
			distance = new JTextField(3);
			add(distance);

			add(new JLabel(" km of ("));

			latitude = new JTextField(5);
			add(latitude);

			add(new JLabel(", "));

			longitude = new JTextField(5);
			add(longitude);

			add(new JLabel(")"));
		}
		public GeoPosition getGeoPosition()
		{
			double latVal, longVal;
			GeoPosition geo = null;
			try {
				latVal = Double.parseDouble(latitude.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
						null,
						"Error parsing latitude value, does it contain non-numeric characters?",
						"Error Deciphering Latitude Value",
						JOptionPane.ERROR_MESSAGE
					);
				return null;
			}

			try {
				longVal = Double.parseDouble(longitude.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
						null,
						"Error parsing longitude value, does it contain non-numeric characters?",
						"Error Deciphering Longitude Value",
						JOptionPane.ERROR_MESSAGE
					);
				return null;
			}

			try {
				geo = new GeoPosition(latVal, longVal);
			} catch (GeoPositionException e) {
				JOptionPane.showMessageDialog(
						null,
						"Error in latitude/longitude pair:\n" +
						e.getMessage(),
						"Invalid Latitude/Longitude Pair",
						JOptionPane.ERROR_MESSAGE
					);
				return null;
			}
			return geo;
		}
	}

	public Set<String> getSelectedCategories()
	{
		Set<String> set = new HashSet<String>();
		Iterator<JCheckBox> iter = m_category.categories.iterator();

		while (iter.hasNext())
		{
			JCheckBox box = iter.next();
			if (box.isSelected())
				set.add(box.getName());
		}
		return set;
	}
	private class CategoryFilter
		extends JPanel
	{
		LinkedList<JCheckBox> categories;
		public CategoryFilter(Storage storage)
		{
			setLayout(new FlowLayout());
			categories = new LinkedList<JCheckBox>();

			Iterator<Category> iter = null;
			try {
				iter = storage.listCategories().iterator();
				while (iter.hasNext())
				{
					JCheckBox box = new JCheckBox(iter.next().name());
					box.setSelected(true);
					categories.add(box);
					add(box);
				}
			} catch (StorageEmptyException e) {
			}
		}
	}

	private class DateFilter
		extends JPanel
	{
		public DateFilter(Storage storage)
		{
			setLayout(new FlowLayout());
			add(new JLabel(" is "));
			String[] range = {
					"exactly",
					"before",
					"after"
				};
			add(new JComboBox(range));

			/*
			 * Apparently, even though the Java documentation says that the start date only
			 * needs to be equal to or less than the default date set, this is not true.
			 * The model will ONLY work if the Default Date is some time in advance of
			 * the start date. It doesn't throw any errors and the bug is really hard
			 * to catch. Java is stupid.
			 * 
			 * As a result, we have to set the default date to one or more days in advance
			 * of the current date. Since we *have* to have earliest < start (they cannot
			 * even be equal, as the documentation says. the spinner just refuses to work
			 * if they are equal.) So we set the earliest date to yesterday, which is
			 * apparently shown in the spinner as *today*'s date. this is confusing, and
			 * Java is stupid.
			 */
			// Current date
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -1);
			Date earliestDate = cal.getTime();

			// Default date
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date defaultDate = cal.getTime();

			SpinnerDateModel model = new SpinnerDateModel(
					defaultDate, // initial date
					earliestDate, // earliest date to allow
					null, // latest date (null means no limit)
					Calendar.YEAR // ignored for SpinnerDateModel
				);

			JSpinner spinner = new JSpinner(model);
			spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
			spinner.setEnabled(true);
			add(spinner);
		}
	}

	private class SellerFilter
		extends JPanel
	{
		public SellerFilter(Storage storage)
		{
			setLayout(new FlowLayout());
			add(new JLabel(" is user "));

			JComboBox cb = new JComboBox();
			Iterator<User> iter = null;
			try {
				iter = storage.listUsers().iterator();
				while (iter.hasNext()) {
					cb.addItem(iter.next().id());
				}
			} catch (StorageEmptyException e) {
			}
			add(cb);
		}
	}
}

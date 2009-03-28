package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import ca.uwo.garage.storage.Category;
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

	public void itemStateChanged(ItemEvent evt)
	{
		CardLayout cl = (CardLayout) (m_filter.getLayout());
		// Change the panel based on the selected ComboBox text
		cl.show(m_filter, (String) evt.getItem());
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
		JTextField distance;
		JTextField latitude;
		JTextField longitude;

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
	}

	private class CategoryFilter
		extends JPanel
	{
		public CategoryFilter(Storage storage)
		{
			setLayout(new FlowLayout());

			Iterator<Category> iter = null;
			try {
				iter = storage.listCategories().iterator();
				while (iter.hasNext())
				{
					add(new JCheckBox(iter.next().name()));
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
					"after",
					"between" 
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

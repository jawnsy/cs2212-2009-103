package ca.uwo.garage.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * The GarageSaleLoader class is used for bulk loading into UWOGarage 
 * by reading a text file, and making new GarageSale objects that fit
 * the input from the file.
 * 
 * @author Jason
 * @version $Revision$
 */

public class GarageSaleLoader
{
	private String m_filename;
	private Storage m_storage;
	private Collection<GarageSale> m_sales;
	private FileReader m_handle;
	private User m_owner;

	/**
	 * Constructor for the class only taking in a filename
	 * creates a new GarageSaleLoader
	 * by the parameters
	 * @param filename The filename of the file we're reading in
	 * @throws IOException if the filename is invalid
	 * @throws GarageSaleLoaderException 
	 */
	public GarageSaleLoader(String filename)
		throws IOException, GarageSaleLoaderException
	{
		m_filename = filename;
		m_handle = new FileReader(filename);
		m_sales = new LinkedList<GarageSale>();
		load();
	}

	/**
	 * Constructor for the class only taking in a filename
	 * creates a new GarageSaleLoader
	 * by the parameters
	 * @param filename The filename of the file we're reading in
	 * @param storage The storage object we are using
	 * @throws IOException if the filename is invalid
	 * @throws GarageSaleLoaderException 
	 */
	public GarageSaleLoader(String filename, Storage storage, User owner)
		throws IOException, GarageSaleLoaderException
	{
		this(filename);
		m_storage = storage;
		m_owner = owner;
	}

	/**
	 * Mutator method for m_storage
	 * sets or changes which storage object we're using
	 * by the parameters
	 * @param storage The storage object we're using
	 */
	public void storage(Storage storage) 
	{
		m_storage = storage;
	}
	
	/**
	 * Accessor method for m_storage
	 * returns the m_storage object
	 * @return storage The storage object we're using
	 */
	public Storage storage()
	{
		return m_storage;
	}

	/**
	 * Accessor method for m_filename
	 * Returns the m_filename object
	 * @return m_filename The filename that we're importing from
	 */
	public String filename() 
	{
		return m_filename;
	}

	/**
	 * Private method
	 * This method is what does the actual importing
	 * It parses the text file, and gets the information needed for each GarageSale object
	 * 
	 * As it finishes getting the information for each individual GarageSale object it creates one
	 * and stores it in the storage object
	 * 
	 * This method follows the requirements set out by the 
	 * 
	 * @throws IOException in the event that the formatting is off an IOException is thrown
	 */
	// Maintain the parser state; this is a simple finite state machine
	private static enum State {
		AREA,
		STREET,
		CITY,
		PROV,
		DATE,
		TIME
	};
	private void load()
		throws IOException, GarageSaleLoaderException
	{
		BufferedReader reader = new BufferedReader(m_handle);
		String line = null;
		GarageSale currentSale = new GarageSale(m_owner);

		// To Report errors
		int lineNum = 1;

		State state = State.AREA; // Begin expecting an area
		String date = null;

		while (reader.ready())
		{
			line = reader.readLine();
			if (line == null || line.isEmpty())
				parseError(lineNum, "Blank line detected. Please remove it and try again");

			if (state == State.AREA)
			{
				if (line.startsWith("area: "))
				{
					currentSale = new GarageSale(m_owner);

					try {
						String parts[] = line.substring(6).split(" ");
						double latitude = Double.parseDouble(parts[0]);
						double longitude = Double.parseDouble(parts[1]);

						currentSale.location(latitude, longitude);
						state = State.STREET; // we are expecting a street token next
					} catch (NumberFormatException e) {
						parseError(lineNum, "Could not parse coordinate value:\n" + e.getMessage());
					} catch (GeoPositionException e) {
						parseError(lineNum, "Invalid coordinate value: " + e.getMessage());
					}
				}
				else {
					parseError(lineNum, "Expecting 'area:' but found: " + line);
				}
			}
			else if (state == State.STREET)
			{
				if (line.startsWith("stre: "))
				{
					// Remove the "stre: " prefix
					try {
						currentSale.address(line.substring(6));
						state = State.CITY;
					} catch (GarageSaleException e) {
						parseError(lineNum, "Error with street address: " + e.getMessage());
					}
				}
				else {
					parseError(lineNum, "Expecting 'stre:' but found: " + line);
				}
			}
			else if (state == State.CITY)
			{
				if (line.startsWith("city: "))
				{
					try {
						currentSale.municipality(line.substring(6));
						state = State.PROV;
					} catch (GarageSaleException e) {
						parseError(lineNum, "Invalid city name: " + line);
					}
				}
				else {
					parseError(lineNum, "Expecting 'city:' but found: " + line);
				}
			}
			else if (state == State.PROV)
			{
				if (line.startsWith("prov: "))
				{
					String province = line.substring(6);
					try {
						currentSale.province(province);
						state = State.DATE;
					} catch (GarageSaleException e) {
						parseError(lineNum, "Invalid province name: " + province);
					}
				}
				else {
					parseError(lineNum, "Expecting 'prov:' but found: " + line);
				}
			}
			else if (state == State.DATE)
			{
				if (line.startsWith("date: "))
				{
					date = line.substring(6);
					state = State.TIME;
				}
				else {
					parseError(lineNum, "Expecting 'date:' but found: " + line);
				}
			}
			else if (state == State.TIME)
			{
				if (line.startsWith("time: "))
				{
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
					String time = line.substring(6);
					String datetime = date + " " + time;
					try {
						sdf.parse(datetime);
					} catch (ParseException e) {
						parseError(lineNum, "Error parsing date/time: " + datetime +
								" (could be an error with the time or date, on previous line)");
					}
					currentSale.datetime(sdf.getCalendar().getTime());

					// We're done parsing, push the current sale to our list
					m_sales.add(currentSale);
					state = State.AREA;
				}
			}
			lineNum++;
		}
	}
	
	public void parseError(int line, String description)
		throws GarageSaleLoaderException
	{
		JOptionPane.showMessageDialog(
				null,
				"Error parsing input, line " + line + ":\n" +
				description + "\n" +
				"Please correct file errors and try again.",
				"File Parsing Error!",
				JOptionPane.ERROR_MESSAGE
			);
		throw new GarageSaleLoaderException(description);
	}

	/**
	 * Saves the list of garage sales created by the load
	 * function to the storage
	 * @throws IOException
	 */
	public void save()
		throws IOException, GarageSaleLoaderException
	{
		if (m_owner == null || m_storage == null)
			throw new GarageSaleLoaderException("Cannot save garage sales without backing store or default owner");

		Iterator<GarageSale> iter = m_sales.iterator();

		while (iter.hasNext()) {
			try {
				m_storage.store(iter.next());
			} catch (StorageFullException e) {
				System.err.println("Error inserting Garage Sale: " + e.getMessage());
				e.printStackTrace();
			} catch (StorageKeyException e) {
				System.err.println("Key error inserting Garage Sale. This shouldn't happen!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Accessor method for m_sales
	 * Returns the m_sales object
	 * @return m_sales The collection of garage sales we're adding
	 */
	public Collection<GarageSale> listGarageSales()
	{
		return m_sales;
	}
}

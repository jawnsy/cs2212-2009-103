package ca.uwo.garage.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

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
	
	/**
	 * Constructor for the class only taking in a filename
	 * creates a new GarageSaleLoader
	 * by the parameters
	 * @param filename The filename of the file we're reading in
	 * @throws IOException if the filename is invalid
	 */
	public GarageSaleLoader(String filename)
	throws IOException
	{
		m_filename = filename;
		m_handle = new FileReader(filename);
		m_sales = new LinkedList<GarageSale>();
	}
	/**
	/**
	 * Constructor for the class only taking in a filename
	 * creates a new GarageSaleLoader
	 * by the parameters
	 * @param filename The filename of the file we're reading in
	 * @param storage The storage object we are using
	 * @throws IOException if the filename is invalid
	 */
	public GarageSaleLoader(String filename, Storage storage)
	throws IOException
	{
		m_filename = filename;
		m_handle = new FileReader(filename);
		m_storage = storage;
		m_sales = new LinkedList<GarageSale>();
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
	private void load() throws IOException
	{
		BufferedReader reader = new BufferedReader(m_handle);
		String currentLine = null;
		GarageSale currentSale = new GarageSale();

		// To Report errors
		int currentLineNum = 1;

		// A list of valid province codes
		LinkedList<String> provinces = new LinkedList<String>();
		provinces.add("AB");
		provinces.add("BC");
		provinces.add("MB");
		provinces.add("NB");
		provinces.add("NL");
		provinces.add("NS");
		provinces.add("ON");
		provinces.add("PE");
		provinces.add("QC");
		provinces.add("SK");

		try
		{
			////////////////////////Read the "area: " line///////////////////////////////
			currentLine = reader.readLine();
			
			while(currentLine != null)
			{
				if (currentLine.startsWith("area: "))
				{
					double longitude, latitude;

					// Remove the "area: " prefix from the string
					currentLine = currentLine.substring(6);

					int decimalIndex = currentLine.indexOf('.');
					int spaceIndex = currentLine.indexOf(' ');

					// Check to see if a space separates the longitude and latitude
					if (spaceIndex < 0)
					{
						throw new IOException("Format error at line " + currentLineNum);
					}

					// Check to see if the latitude has a decimal point
					if ((spaceIndex - decimalIndex) < 0)
					{
						throw new IOException("Format error at line " + currentLineNum);
					}

					// Test to see if the latitude has 1,2 or 3 digits preceding the decimal place
					if (decimalIndex < 1 || decimalIndex > 3)
					{
						throw new IOException("Format error at line " + currentLineNum + ": The longitutde and " +
						"latitude values must have 1,2 or 3 digits prior to the decimal point.");
					}

					// Test to see if the latitude has 3,4,5 or 6 digits after the decimal place
					if ((spaceIndex - decimalIndex) < 4 || (spaceIndex - decimalIndex) > 7)
					{
						throw new IOException("Format error at line " + currentLineNum + ": The longitutde and " +
						"latitude values must have 3,4,5 or 6 digits after the decimal point.");
					}

					// Get the latitude value
					try
					{
						latitude = Double.parseDouble(currentLine.substring(0, spaceIndex));
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum + ": The longitude contains " +
						"invalid characters");
					}

					currentLine = currentLine.substring(spaceIndex + 1);
					decimalIndex = currentLine.indexOf('.');

					// Check to see if the longitude has a decimal point
					if (decimalIndex < 0)
					{
						throw new IOException("Format error at line " + currentLineNum);
					}

					// Test to see if the longitude has 1,2 or 3 digits preceding the decimal place
					if (decimalIndex < 1 || decimalIndex > 3)
					{
						throw new IOException("Format error at line " + currentLineNum + ": The longitutde and " +
						"latitude values must have 1,2 or 3 digits prior to the decimal point.");
					}

					// Test to see if the longitude has 3,4,5 or 6 digits after the decimal place
					if (currentLine.substring(decimalIndex).length() < 4 || currentLine.substring(decimalIndex).length() > 7)
					{
						throw new IOException("Format error at line " + currentLineNum + ": The longitutde and " +
						"latitude values must have 3,4,5 or 6 digits after the decimal point.");
					}

					// Get the longitude value
					try
					{
						longitude = Double.parseDouble(currentLine);
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum + 
						": The longitude contains invalid characters");
					}

					// Enter the longitude and latitude positions as the location for this garage sale
					try
					{
						currentSale.location(latitude, longitude);
					}
					catch (GeoPositionException e)
					{
						throw new IOException("Format error at line " + currentLineNum + 
						": The longitude and latitude values are invalid");
					}
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
					": This line should start with \"area: \"");
				}
				
				////////////////////////Read the "stre: " line///////////////////////////////
				currentLine = reader.readLine();
				currentLineNum++;

				if (currentLine.startsWith("stre: "))
				{
					// Remove the "stre: " prefix from the string
					currentLine = currentLine.substring(6);

					if (currentLine.length() < 1)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": You must enter a street address");
					}

					// Truncate any address longer than 50 characters long
					if (currentLine.length() > 50)
					{
						currentLine = currentLine.substring(0, 50);
					}

					// Enter the address for the this garage sale
					try
					{
						currentSale.address(currentLine);
					}
					catch (GarageSaleException e)
					{
						// This should never happen
					}
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
					": This line should start with \"stre: \"");
				}
				
				////////////////////////Read the "city: " line///////////////////////////////
				currentLine = reader.readLine();
				currentLineNum++;

				if (currentLine.startsWith("city: "))
				{
					// Remove the "city: " prefix from the string
					currentLine = currentLine.substring(6);

					if (currentLine.length() < 1)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": You must enter a city");
					}

					// Truncate any address longer than 50 characters long
					if (currentLine.length() > 20)
					{
						currentLine = currentLine.substring(0, 20);
					}

					// Enter the address for the this garage sale
					try
					{
						currentSale.municipality(currentLine);
					}
					catch (GarageSaleException e)
					{
						// This should never happen
					}
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
					": This line should start with \"city: \"");
				}
				
				////////////////////////Read the "prov: " line///////////////////////////////
				currentLine = reader.readLine();
				currentLineNum++;

				if (currentLine.startsWith("prov: "))
				{
					// Remove the "prov: " prefix from the string
					currentLine = currentLine.substring(6);

					if (provinces.contains(currentLine) == false)
					{
						throw new IOException("Format error at line " + currentLineNum + 
								": You must enter a province code and it must be one of " + 
						"AB, BC, MB, NB, NL, NS, ON, PE, QC and SK");
					}

					try
					{
						currentSale.province(currentLine);
					}
					catch (GarageSaleException e)
					{
						// This should never happen
					}
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
							": This line should start with \"prov: \"");
				}
				
				////////////////////////Read the "date: " line///////////////////////////////
				currentLine = reader.readLine();
				currentLineNum++;

				if (currentLine.startsWith("date: "))
				{
					int day, month, year;
					
					// Remove the "date: " prefix from the string
					currentLine = currentLine.substring(6);
					
					int slashIndex = currentLine.indexOf('/');
					// Test to see if the date has a slash and if at most 2 characters
					// appear before it
					if (slashIndex < 1 || slashIndex > 2)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": The format of the date must be in the form day/month/year with the year " +
						"being 4 digits long and the day and month being 1 or 2 digits long");
					}
					
					// Get the day
					try
					{
						day = Integer.parseInt(currentLine.substring(0, slashIndex));
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The date contains an invalid character");
					}
					
					currentLine = currentLine.substring(slashIndex + 1);
					
					slashIndex = currentLine.indexOf('/');
					// Test to see if the date has another slash and if at most 2 characters
					// appear before it
					if (slashIndex < 1 || slashIndex > 2)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": The format of the date must be in the form day/month/year with the year " +
						"being 4 digits long and the day and month being 1 or 2 digits long");
					}
					
					// Get the month
					try
					{
						month = Integer.parseInt(currentLine.substring(0, slashIndex));
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The date contains an invalid character");
					}
					
					currentLine = currentLine.substring(slashIndex + 1);
					
					// Check to see that the year is 4 characters long 
					if (currentLine.length() != 4)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": The format of the date must be in the form day/month/year with the year " +
						"being 4 digits long and the day and month being 1 or 2 digits long");
					}
					
					// Get the year
					try
					{
						year = Integer.parseInt(currentLine);
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The date contains an invalid character");
					}
					
					// Check to see if the dates are valid
					if(day < 1 || day > 31)
						throw new IOException("Format error at line " + currentLineNum +
						": This is not a valid day of the month");
					
					if(month > 12 || month < 1)
						throw new IOException("Format error at line " + currentLineNum +
						": This is not a valid month");
					
					if(month == 4 || month == 6 || month == 9 || month == 11)
					{
						if (day > 30)
							throw new IOException("Format error at line " + currentLineNum +
							": This is not a valid day of the month");
					}
					
					// This might be confusing, but trust me, it works
					if (month == 2)
					{
						if(day > 29)
							throw new IOException("Format error at line " + currentLineNum +
							": This is not a valid day of the month");
						if(year % 4 != 0 && month > 28)
						{
							throw new IOException("Format error at line " + currentLineNum +
							": This is not a valid day of the month");
						}
						else
						{
							if(year % 100 == 0 && year % 400 !=0)
								throw new IOException("Format error at line " + currentLineNum +
								": This is not a valid day of the month");
						}
					}
					
					// Enter the date for this garage sale
					Date date = (new GregorianCalendar(year, month, day)).getTime();
					currentSale.datetime(date);
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
							": This line should start with \"date: \"");
				}
				
				//////////////////////// Read the "time: " line///////////////////////////////
				currentLine = reader.readLine();
				currentLineNum++;

				if (currentLine.startsWith("time: "))
				{
					int hours, minutes;
					
					// Remove the "time: " prefix from the string
					currentLine = currentLine.substring(6);
					
					int colonIndex = currentLine.indexOf(':');
					if (colonIndex < 1 || colonIndex > 2)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The format of the time must be in the form hour:minute with the hour" +
								"being 1 or 2 digits long and the minute being 2 digits long");
					}
					
					// Get the hours of the time
					try
					{
						hours = Integer.parseInt(currentLine.substring(0, colonIndex));
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The time contains an invalid character");
					}
					
					currentLine = currentLine.substring(colonIndex + 1);
					// Check to see if the minutes of the time is 2 characters long
					if (currentLine.length() != 2)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The format of the time must be in the form hour:minute with the hour" +
								"being 1 or 2 digits long and the minute being 2 digits long");
					}
					
					// Get the minutes of the time
					try
					{
						minutes = Integer.parseInt(currentLine.substring(0, colonIndex));
					}
					catch (Exception e)
					{
						throw new IOException("Format error at line " + currentLineNum +
								": The time contains an invalid character");
					}
					
					// Check to see if a valid time is given
					
					if (hours > 23 || hours < 0 || minutes < 0 || minutes > 59)
					{
						throw new IOException("Format error at line " + currentLineNum +
						": You must enter a valid time");
					}
					// Enter the hours and minutes of this garage sale
					Date date = currentSale.datetime();
					date.setHours(hours);
					date.setMinutes(minutes);
					currentSale.datetime(date);
					
				}
				else
				{
					throw new IOException("Format error at line " + currentLineNum +
							": This line should start with \"time: \"");
				}
			
				this.m_sales.add(currentSale);
				
				// Read the "area: " line for the next garage sale or exit the loop if the 
				// end of the bulk file has been reached
				currentLine = reader.readLine();
				currentLineNum++;
			}
		}
		catch (IOException e)
		{
			//System.out.println(e.getMessage());
			throw e;
		}
		catch (NullPointerException e)
		{
			//System.out.println("The bulk load file does not contain all the required information");
			throw new NullPointerException("The bulk load file does not contain all the required information");
		}
	}
	
	
	public void save() throws IOException
	{
		Iterator<GarageSale> iter = m_sales.iterator();
		while (iter.hasNext()) 
		{
			try 
			{
				m_storage.store(iter.next());
			} 
			catch (StorageFullException e) 
			{	
				System.err.println;			
			} 
			catch (StorageKeyException e) 
			{	
				System.err.println;
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
	public static void main (String[] args)
	{
		try
		{
			GarageSaleLoader test = new GarageSaleLoader("Test.txt");
			test.load();
		}
		catch (IOException e)
		{
			System.out.print(e.getMessage());
		}
		catch (NullPointerException e)
		{
			
		}
	}
}

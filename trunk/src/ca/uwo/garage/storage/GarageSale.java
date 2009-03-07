package ca.uwo.garage.storage;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The GarageSale class provides a representation of a user's garage
 * sale, including things like its location, address, date/time
 * information, and an optional note. It also tracks which categories
 * this GarageSale belongs to, for easy searching capability.
 * 
 * @author Jonathan Yu
 * @implements Serializable
 * @version $Revision$
 */

public class GarageSale
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private GeoPosition m_location; // The coordinate of our GarageSale
	private String m_address, m_municipality, m_province; // Geocode type stuff
	private Date m_datetime; // date and time of the Garage Sale
	private Set<Category> m_categories; // this GarageSale's categories
	private String m_note; // a note for the GarageSale

	private static final int MAXADDRESSLEN = 50;
	private static final int MAXCITYLEN    = 20;
	private static final int MAXPROVLEN    = 2;
	private static final String[] PROVINCES = {
			"ON",
			"QC",
			"NS",
			"NB",
			"MB",
			"BC",
			"PE",
			"SK",
			"AB",
			"NL"
		};


	/**
	 * This method creates a new GarageSale object with default parameters.
	 * 
	 * @return A new GarageSale object
	 */
	public GarageSale() {
		m_categories = new HashSet<Category>();
		m_note = "";
	}

	/**
	 * This method creates a new GarageSale object at a given waypoint.
	 * 
	 * @param waypoint A GeoPosition containing the coordinates of the garage sale.
	 * @return A new GarageSale object
	 */
	public GarageSale(GeoPosition waypoint)
	{
		this();
		m_location = waypoint;
	}

	/**
	 * This method creates a new GarageSale object at a given latitude/longitude
	 * pair.
	 * 
	 * @param latitude A coordinate latitude (between +/- 90 degrees)
	 * @param longitude A coordinate longitude (between +/- 180 degrees)
	 * @return A new GarageSale object
	 * @throws A GeoPositionException if the GeoPosition is invalid
	 */
	public GarageSale(double latitude, double longitude)
		throws GeoPositionException
	{
		this(new GeoPosition(latitude, longitude));
	}

	/**
	 * This method sets the GarageSale's position parameters.
	 * 
	 * @param position A GeoPosition specifying where this GarageSale is located
	 */
	public void location(GeoPosition position)
	{
		m_location = position;
	}

	/**
	 * This is a convenience method that allows one to modify the location of
	 * a GarageSale.
	 * 
	 * @param latitude A coordinate latitude (between +/- 90 degrees)
	 * @param longitude A coordinate longitude (between +/- 180 degrees)
	 * @throws A GeoPositionException if the coordinates are invalid
	 */
	public void location(double latitude, double longitude)
		throws GeoPositionException
	{
		m_location = new GeoPosition(latitude, longitude);
	}

	/**
	 * @return The position of the GarageSale (the Waypoint)
	 */
	public GeoPosition location()
	{
		return m_location;
	}

	/**
	 * This sets the Address of the Garage Sale. Note that this is not constrained
	 * to the GeoPosition, so one must be careful to ensure that the coordinate and
	 * address match!
	 * 
	 * The best way to do this is to search the Mapping site using some sort of other
	 * interface (JXMapKit does not provide a means to do this).
	 * 
	 * @param address A string containing the street number and address information
	 * @throws GarageSaleException If the address is null, blank or longer than MAXADDRESSLEN
	 */
	public void address(String address)
		throws GarageSaleException
	{
		if (address == null || address.isEmpty())
			throw new GarageSaleException("The address parameter cannot be blank");

		if (address.length() > MAXADDRESSLEN)
			throw new GarageSaleException("Street address may not be longer than " + MAXADDRESSLEN + " characters");

		m_address = address;
	}

	/**
	 * @return The current GarageSale address, or null
	 */
	public String address() {
		return m_address;
	}

	/**
	 * This method sets the municipality to the given name.
	 * 
	 * @param name The new municipality
	 * @throws GarageSaleException if the municipality is null, empty or longer than MAXCITYLEN characters
	 */
	public void municipality(String name)
		throws GarageSaleException
	{
		if (name == null || name.isEmpty())
			throw new GarageSaleException("The city name parameter cannot be blank");

		if (name.length() > MAXCITYLEN)
			throw new GarageSaleException("City name may not be longer than " + MAXCITYLEN + " characters");

		m_municipality = name;
	}

	/**
	 * @return The current municipality, or null if it has not yet been set
	 */
	public String municipality() {
		return m_municipality;
	}

	/**
	 * Sets the GarageSale's province to this new value. Will ensure that the province is one of:
	 * ON, QC, NS, NB, MB, BC, PE, SK, AB, NL
	 * @param province The GarageSale's province
	 * @throws GarageSaleException If the province is empty, null or not a valid province
	 */
	public void province(String province)
		throws GarageSaleException
	{
		if (province == null || province.isEmpty())
			throw new GarageSaleException("The province parameter cannot be blank");
		if (!provinceok(province))
			throw new GarageSaleException("String '" + province + "' is not a valid provincial ISO code.");

		m_province = province;
	}

	// check if the province appears valid
	private boolean provinceok(String province) {
		// If the province is longer than 2, then it's invalid
		if (province.length() > MAXPROVLEN)
			return false;

		// Check if the given province is in our list
		for (int i = 0; i < PROVINCES.length; i++) {
			if (province == PROVINCES[i])
				return true;
		}
		return false;
	}

	/**
	 * @return the current two letter Canadian province code
	 */
	public String province()
	{
		return m_province;
	}

	/**
	 * This method blindly sets the date of the garage sale to what is given.
	 * 
	 * @param datetime A Date object representing the date/time of the garage sale
	 */
	public void datetime(Date datetime)
	{
		m_datetime = datetime;
	}
	/**
	 * @return The Date object representing the date/time of the garage sale
	 */
	public Date datetime()
	{
		return m_datetime;
	}

	/**
	 * Add a given Category. Do nothing if it already exists.
	 * @param category The Category to add to this GarageSale
	 */
	public void addCategory(Category category) {
		// Don't do anything if this Category was already set
		if (m_categories.contains(category))
			return;

		m_categories.add(category);
	}

	/**
	 * Remove a given Category. Do nothing if it does not exist.
	 * @param category The Category to delete from this GarageSale
	 */
	public void deleteCategory(Category category) {
		// Don't do anything unless this category was already set
		if (!m_categories.contains(category))
			return;

		m_categories.remove(category);
	}

	/**
	 * Get a list of all current Categories registered for this GarageSale.
	 */
	public Set<Category> listCategories() {
		return m_categories;
	}

	/**
	 * Set the optional Note field to the given string. If a null parameter is
	 * passed, then the note will be the empty string.
	 * @param note The new note string, null or the empty string.
	 */
	public void note(String note) {
		// If we are trying to make note undefined, then set to the empty string
		if (note == null)
			m_note = "";
		else
			m_note = note;
	}

	/**
	 * This method is always guaranteed to return a non-null value; however, the string
	 * may be empty.
	 * 
	 * @return The currently set note, or the empty string if not set.
	 */
	public String note() {
		return m_note;
	}
}

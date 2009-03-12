/**
* This class represents a geographic coordinate
* 
* @author Jonathan Yu
* @version Version 1.0
*/ 
package ca.uwo.garage.storage;

public class GeoPosition {
	
	//instance variables **********************************
	private double m_latitude; 		// The latitude coordinate. Positive is North
	private double m_longitude; 	// The Longitude coordinate. Positive is East

	/**
	 * Constructor for objects of class GeoPosition creates a new 
	 * GeoPosition with default coordinates (43.011583,-81.257586)
	 */
	public GeoPosition() 
	{
		/* These are the coordinates of the University of Western Ontario
		 * campus in London, ON. 1151 Richmond Street
		 */
		m_latitude = 43.011583;
		m_longitude = -81.257586;
	}

	/**
	 * Constructor for objects of class GeoPosition
	 * creates a new GeoPosition with the coordinates specified
	 * by the parameters
	 * @param m_latitude Latitude value of coordinate
	 * @param m_longitude Longitude value of coordinate
	 */
	public GeoPosition(double latitude, double longitude)
		throws GeoPositionException
	{
		this();
		latlong(latitude, longitude);
	}

	// ACCESSOR METHODS ***********************************
	/**
	 * This method will return the latitude coordinate
	 * @return the latitude coordinate
	 */
	public double latitude() 
	{
		return m_latitude;
	}

	/**
	 * This method will return the longitude coordinate
	 * @return the longitude coordinate
	 */
	public double longitude() 
	{
		return m_longitude;
	}
	
	// MUTATOR METHODS ***********************************
	/**
	 * This Method sets the latitude value of a GeoPosition object
	 * @param latitude The latitude value
	 */
	public void latitude(double coord)
		throws GeoPositionException
	{
		if (Math.abs(coord) > 90)
			throw new GeoPositionException("Latitude values cannot exceed +/- 90 degrees");

		m_latitude = coord;
	}
	
	/**
	 * This Method sets the longitude value of a GeoPosition object
	 * @param longitude The longitude value
	 */
	public void longitude(double coord)
		throws GeoPositionException
	{
		if (Math.abs(coord) > 180)
			throw new GeoPositionException("Longitude values cannot exceed +/- 180 degrees");

		m_longitude = coord;
	}

	/**
	 * This method sets the latitude and longitude values of
	 * a GeoPosition object
	 * @param latitude The latitude value
	 * @param longitude The longitude value
	 */
	public void latlong(double latitude, double longitude)
		throws GeoPositionException
	{
		latitude(latitude);
		longitude(longitude);
	}

	/**
	 * This method calculates the distance between two GeoPosition 
	 * objects in kilometers using the Haversine formula
	 * @param other The second GeoPosition object
	 * @return the distance between the two GeoPosition objects
	 */
	public double distance(GeoPosition other)
	{
		final int RADIUS = 6371; // The radius of the earth in kilometers
		
		// Get the distance between latitudes and longitudes
		double deltaLat = Math.toRadians(this.latitude() - other.latitude());
		double deltaLong = Math.toRadians(this.longitude() - other.longitude());
		
		// Apply the Haversine function
		double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
		        Math.cos(Math.toRadians(other.latitude())) * Math.cos(Math.toRadians(this.latitude())) * 
		        Math.sin(deltaLong/2) * Math.sin(deltaLong/2); 
		return RADIUS * 2 * Math.asin(Math.sqrt(a));
	}

	/**
	 * This method compares two GeoPosition objects to determine if they
	 * are semantically identical
	 * @param other The second GeoPosition object
	 * @return A boolean indicating if they are equal
	 */
	public boolean equals(GeoPosition other)
	{
		return (other.latitude() == m_latitude &&
				other.longitude() == m_longitude);
	}

	/**
	 * This method returns a string displaying the coordinates
	 * of a GeoPosition object
	 * @return the string displaying the coordinates
	 */
	public String toString() 
	{
		StringBuilder str = new StringBuilder();

		if (m_latitude > 0)
			str.append(m_latitude + " N, ");
		else
			str.append(-m_latitude + "S, ");

		if (m_longitude > 0)
			str.append(m_longitude + " E");
		else
			str.append(-m_longitude + "W");

		return str.toString();
	}
	
	public static void main(String[] args)
	{
		try
		{
			GeoPosition test1 = new GeoPosition(-67, 24);
			GeoPosition test2 = new GeoPosition(-67, 34);
			System.out.println(test1.distance(test2));
		}
		
		catch (Exception e)
		{
			
		}
		
	}
}

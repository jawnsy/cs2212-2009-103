package ca.uwo.garage.storage;

public class GeoPosition {
	private double m_latitude; // positive is North
	private double m_longitude; // positive is East

	public GeoPosition() {
		/* These are the coordinates of the University of Western Ontario
		 * campus in London, ON. 1151 Richmond Street
		 */
		m_latitude = 43.011583;
		m_longitude = -81.257586;
	}

	public GeoPosition(double latitude, double longitude)
		throws GeoPositionException
	{
		this();
		latlong(latitude, longitude);
	}

	public double latitude() {
		return m_latitude;
	}
	public void latitude(double coord)
		throws GeoPositionException
	{
		if (Math.abs(coord) > 90)
			throw new GeoPositionException("Latitude values cannot exceed +/- 90 degrees");

		m_latitude = coord;
	}

	public double longitude() {
		return m_longitude;
	}
	public void longitude(double coord)
		throws GeoPositionException
	{
		if (Math.abs(coord) > 180)
			throw new GeoPositionException("Longitude values cannot exceed +/- 180 degrees");

		m_longitude = coord;
	}

	public void latlong(double latitude, double longitude)
		throws GeoPositionException
	{
		latitude(latitude);
		longitude(longitude);
	}

	public double distance(GeoPosition other)
	{
		return 0.0;
	}

	public String toString() {
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
}

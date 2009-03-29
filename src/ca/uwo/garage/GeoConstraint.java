package ca.uwo.garage;

import ca.uwo.garage.storage.GeoPosition;

public class GeoConstraint
{
	private double m_dist;
	private GeoPosition m_geo;

	public GeoConstraint(GeoPosition coord, double distance)
	{
		m_geo = coord;
		m_dist = distance;
	}
	public double distance()
	{
		return m_dist;
	}
	public GeoPosition position()
	{
		return m_geo;
	}
	public boolean isOk(GeoPosition other)
	{
		return (other.distance(m_geo) < m_dist);
	}
}

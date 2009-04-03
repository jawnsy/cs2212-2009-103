package ca.uwo.garage;

import ca.uwo.garage.storage.GeoPosition;
/**
 * A method used to help filter searches by radius
 * @author Jon
 *
 */
public class GeoConstraint
{
	private double m_dist;
	private GeoPosition m_geo;

	/**
	 * The constructor for this class
	 * @param coord a geo position
	 * @param distance the distance of something
	 */
	public GeoConstraint(GeoPosition coord, double distance)
	{
		m_geo = coord;
		m_dist = distance;
	}
	/**
	 * Returns the distance
	 * @return the distance
	 */
	public double distance()
	{
		return m_dist;
	}
	/**
	 * Returns a GeoPosition
	 * @return the GeoPosition
	 */
	public GeoPosition position()
	{
		return m_geo;
	}
	/**
	 * A method to decide whether another GeoPosition is OK or not
	 * @param other the other GeoPosition
	 * @return true if the other GeoPosition is OK
	 */
	public boolean isOk(GeoPosition other)
	{
		return (other.distance(m_geo) < m_dist);
	}
}

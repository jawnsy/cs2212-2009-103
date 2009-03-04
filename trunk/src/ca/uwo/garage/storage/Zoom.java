package ca.uwo.garage.storage;

/**
 * This class is used to zoom the map to different levels. 
 * 
 * @author Jonathan Yu, Jason Lu
 * @version Version 1.1
 */
public class Zoom {
	// instance variables **********************************
	private int m_level; 				//the zoom level
	private final int Z_MIN = 1;		//minimum zoom level required by Google, 1
	private final int Z_MAX = 19;		//maximum zoom level required by Google, 19
	
	/**
	* Constructor for objects of class Zoom,
	* initialize zoom level to 1
	*/
	public Zoom() {
		m_level = 1;
	}
// ACCESSOR METHODS ***********************************
	/**
	* The isMin method will return a boolean value indicating whether
	* the map is at minimum zoom level allowed.
	* @return whether the map is at minimum zoom level.
	*/
	public boolean isMin() {
		return (m_level == Z_MIN);
	}
	
	/**
	* The isMax method will return a boolean value indicating whether
	* the map is at maximum zoom level allowed.
	* @return whether the map is at maximum zoom level.
	*/
	public boolean isMax() {
		return (m_level == Z_MAX);
	}
	
// HELPER METHODS *************************************	
	/**
	* The level method will zoom the map to a specified level.
	* @param zoom the specified zoom level.
	* @exception ZoomInvalidException the specified zoom level is not within the available range
	*/
	public void level(int zoom)
		throws ZoomInvalidException
	{
		if (zoom < Z_MIN || zoom > Z_MAX)
			throw new ZoomInvalidException("Zoom level must be between " + Z_MIN + " and " + Z_MAX + " inclusive");
	}
	
	/**
	* The up method will zoom the map up from the current level by 1 level.
	* nothing will be done if the the map is already at the maximum zoom level 
	* the new zoom level will be updated if action performed.
	*/
	public void up() {
		// only zoom if we're not yet at the maximum zoom level
		if (m_level == Z_MAX)
			return;
		m_level++;
	}
	
	/**
	* The down method will zoom the map down from the current level by 1 level.
	* nothing will be done if the the map is already at the minimum zoom level 
	* the new zoom level will be updated if action performed.
	*/
	public void down() {
		// only zoom if we're not yet at the minimum zoom level
		if (m_level == Z_MIN)
			return;
		m_level--;
	}
}

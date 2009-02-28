package ca.uwo.garage.storage;

public class Zoom {
	private int m_level;

	// Google has a minimum zoom of 1 and maximum zoom of 19
	private final int Z_MIN = 1;
	private final int Z_MAX = 19;

	public Zoom() {
		m_level = 1;
	}
	public boolean isMin() {
		return (m_level == Z_MIN);
	}
	public boolean isMax() {
		return (m_level == Z_MAX);
	}
	public void level(int zoom)
		throws ZoomInvalidException
	{
		if (zoom < Z_MIN || zoom > Z_MAX)
			throw new ZoomInvalidException("Zoom level must be between " + Z_MIN + " and " + Z_MAX + " inclusive");
	}
	public void up() {
		// only zoom if we're not yet at the maximum zoom level
		if (m_level == Z_MAX)
			return;
		m_level++;
	}
	public void down() {
		// only zoom if we're not yet at the minimum zoom level
		if (m_level == Z_MIN)
			return;
		m_level--;
	}
}

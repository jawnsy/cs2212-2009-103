package ca.uwo.garage;

import javax.swing.JFrame;
/**
 * An interface for view objects
 * @author Jon
 *
 */
public class View
	extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected Controller m_control;
	
	/**
	 * The constructor for view classes
	 * @param control
	 */
	public View(Controller control) {
		m_control = control;
	}
	/**
	 * A method to reset the view object
	 */
	public void reset() {
		
	}
	/**
	 * A method to close the view object
	 */
	public void close() {
		
	}

	/**
	 * A method to close the view object
	 */
	// Java's destructor; call our "close" method
	public void finalize() {
		close();
	}
}

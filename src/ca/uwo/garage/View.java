package ca.uwo.garage;

import javax.swing.JFrame;

public class View
	extends JFrame
{
	private static final long serialVersionUID = 1L;
	protected Controller m_control;
	
	public View(Controller control) {
		m_control = control;
	}
	public void reset() {
		
	}
	public void close() {
		
	}

	// Java's destructor; call our "close" method
	public void finalize() {
		close();
	}
}

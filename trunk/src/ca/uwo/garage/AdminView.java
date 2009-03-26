package ca.uwo.garage;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class AdminView
	extends View
{
	public AdminView(Controller control) {
		super(control);
		setTitle("Admin View");

	    setMinimumSize(new Dimension(250, 250));

	    pack();
	    setVisible(true);
	}
}

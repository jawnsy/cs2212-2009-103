package ca.uwo.garage;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class AdminView
	extends View
{
	public AdminView(Controller control) {
		super(control);
		setTitle("Admin View");

	    setMinimumSize(new Dimension(250, 250));
	    Container container = getContentPane();

	    setLayout(new GridLayout(1,1));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Users", new JLabel("test"));
        tabbedPane.addTab("Categories", new JLabel("test"));
        tabbedPane.addTab("Garage Sales", new JLabel("test"));

        //Add the tabbed pane to this panel.
        container.add(tabbedPane);

        pack();
	    setVisible(true);
	}
}

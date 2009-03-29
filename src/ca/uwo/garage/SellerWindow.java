package ca.uwo.garage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class SellerWindow extends JFrame
{
	private JButton delete, modify, addNew, bulkLoad, browse;

	public SellerWindow()
	{		
		this.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			} 
		});
		this.setTitle("Seller Mode");
		this.setVisible(true);
		this.setBackground(new Color(132, 227, 255));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createLineBorder(Color.black));


		JMenu menu = new JMenu("Menu");
		JMenuItem buyerMode = new JMenuItem("Switch to Buyer Mode");	
		JMenuItem exit = new JMenuItem("Exit");

		menuBar.add(menu);
		menu.add(buyerMode);
		menu.addSeparator();
		menu.add(exit);

		this.setJMenuBar(menuBar);

		JPanel panel = new JPanel(new BorderLayout(0, 5));
		panel.setBackground(new Color(132, 227, 255));
		this.add(panel);

		JLabel sellerLabel = new JLabel(" Your Garage Sales:");
		panel.add(sellerLabel, BorderLayout.NORTH);

		JPanel innerPanel = new JPanel();
		innerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		innerPanel.setBackground(new Color(132, 227, 255));
		panel.add(innerPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(132, 227, 255));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		innerPanel.add(buttonPanel);

		innerPanel.add(Box.createRigidArea(new Dimension(90,0)));

		delete = new JButton("Delete");
		modify = new JButton("Modify");
		addNew = new JButton("Add New");

		buttonPanel.add(delete);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,15)));
		buttonPanel.add(modify);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,15)));
		buttonPanel.add(addNew);

		List garageList = new List(8,false);
		innerPanel.add(garageList);
		/*garageList.addItem("Garage Sale 1");
		garageList.addItem("Garage Sale 2");
		garageList.addItem("Garage Sale 3");
		garageList.addItem("Garage Sale 4");
		garageList.addItem("Garage Sale 5");
		garageList.addItem("Garage Sale 6");
		garageList.addItem("Garage Sale 7");
		garageList.addItem("Garage Sale 8");
		garageList.addItem("Garage Sale 9");
		garageList.addItem("Garage Sale 10");*/

		innerPanel.add(Box.createRigidArea(new Dimension(50,0)));

		JPanel innerPanel2 = new JPanel();
		innerPanel2.setBackground(new Color(132, 227, 255));
		innerPanel2.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.add(innerPanel2, BorderLayout.SOUTH);

		innerPanel2.add(Box.createRigidArea(new Dimension(0,40)));

		bulkLoad = new JButton("Bulk Load");
		innerPanel2.add(bulkLoad);

		JLabel bulkLabel = new JLabel(" Path:");
		innerPanel2.add(bulkLabel);

		TextField bulkTextField = new TextField("",20);
		innerPanel2.add(bulkTextField);

		browse = new JButton("Browse");
		innerPanel2.add(browse);


		this.pack();
		this.setResizable(false);

		garageList.setSize(garageList.getSize().width + 60, garageList.getSize().height);

		delete.setSize(addNew.getSize());
		modify.setSize(addNew.getSize());

		this.setLocationRelativeTo(null);
		this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y - this.getHeight()/2);
	}
}
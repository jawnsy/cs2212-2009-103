import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

import javax.swing.*;

import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;

import ca.uwo.garage.HTMLPrinter;
import ca.uwo.garage.storage.GarageGeoPos;
import ca.uwo.garage.storage.GeoPositionException;
import ca.uwo.garage.mapviewer.GoogleMapsProvider;

public class GUI implements ActionListener
{
	private static StartWindow startWindow;
	private static AddNewUserWindow addNewUserWindow;
	private static UpdateUserWindow updateUserWindow;
	private static AdministratorWindow administratorWindow;
	private static SellerWindow sellerWindow;
	private static AddNewGarageWindow addNewGarageWindow;
	private static UpdateGarageWindow updateGarageWindow;
	private static SelectCatWindow selectCatWindow;
	private static BuyerWindow buyerWindow;
	private static GarageInfoWindow garageInfoWindow;
	private static MapHolder mapHolder;
	private static TileFactory tileFactory;
	public static JXMapKit jxMapKit;
	
	private static TileFactory initMapProvider(){
		//initialize map provider
		GoogleMapsProvider map = new GoogleMapsProvider();
        TileFactoryInfo tileProviderInfo = map.getTileProviderInfo();
        return (new DefaultTileFactory (tileProviderInfo));
		
	}	
	
	public GUI()
	{
//		startWindow = new StartWindow();
//				
//		addNewUserWindow = new AddNewUserWindow();
//		
//		updateUserWindow = new UpdateUserWindow();
//		
//		administratorWindow = new AdministratorWindow();
//		administratorWindow.catAddNew.addActionListener(this);
//		
//		sellerWindow = new SellerWindow();
//		
//		addNewGarageWindow = new AddNewGarageWindow();
//		
//		updateGarageWindow = new UpdateGarageWindow();
//		
//		selectCatWindow = new SelectCatWindow();
//		
//		buyerWindow = new BuyerWindow();
//		
//		garageInfoWindow = new GarageInfoWindow();
//		
//		mapHolder = new MapHolder();
		tileFactory=initMapProvider();
		jxMapKit = new JXMapKit();
        jxMapKit.setTileFactory(initMapProvider());
        
	}
	
	public class MapHolder extends JXMapKit {
		
		public MapHolder (){
	
	        
		}
        
		
	}
	
	public class AddNewUserWindow extends JFrame
	{
		private JButton add, cancel;
		public AddNewUserWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Add New User");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,15));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			add = new JButton("Add");
			cancel = new JButton("Cancel");
			
			JLabel label = new JLabel("Enter the new user information.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(6,2,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			JLabel idLabel = new JLabel(" User ID:");
			infoPanel.add(idLabel);
			
			TextField idTextField = new TextField();
			infoPanel.add(idTextField);
			
			JLabel firstNameLabel = new JLabel(" First Name:");
			infoPanel.add(firstNameLabel);
			
			TextField firstNameTextField = new TextField();
			infoPanel.add(firstNameTextField);
			
			JLabel lastNameLabel = new JLabel(" Last Name:");
			infoPanel.add(lastNameLabel);
			
			TextField lastNameTextField = new TextField();
			infoPanel.add(lastNameTextField);
			
			JLabel phoneNumberLabel = new JLabel(" Phone Number:");
			infoPanel.add(phoneNumberLabel);
			
			TextField phoneNumberTextField = new TextField();
			infoPanel.add(phoneNumberTextField);
			
			JLabel defaultLatLongLabel = new JLabel(" Default Lat and Long:");
			infoPanel.add(defaultLatLongLabel);
			
			TextField defaultLatLongTextField = new TextField();
			infoPanel.add(defaultLatLongTextField);
			
			JLabel defaultZoomLabel = new JLabel(" Default Zoom:");
			infoPanel.add(defaultZoomLabel);
			
			TextField defaultZoomTextField = new TextField();
			infoPanel.add(defaultZoomTextField);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(35);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(add);
			buttonPanel.add(cancel);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			add.setSize(cancel.getSize());
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y - this.getHeight()/2);
		}
	}
	
	public class UpdateUserWindow extends JFrame
	{
		private JButton update, cancel;
		public UpdateUserWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Update User");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,15));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			update = new JButton("Update");
			cancel = new JButton("Cancel");
			
			JLabel label = new JLabel("Update user information.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(6,2,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			JLabel firstNameLabel = new JLabel(" First Name:");
			infoPanel.add(firstNameLabel);
			
			TextField firstNameTextField = new TextField();
			infoPanel.add(firstNameTextField);
			
			JLabel lastNameLabel = new JLabel(" Last Name:");
			infoPanel.add(lastNameLabel);
			
			TextField lastNameTextField = new TextField();
			infoPanel.add(lastNameTextField);
			
			JLabel phoneNumberLabel = new JLabel(" Phone Number:");
			infoPanel.add(phoneNumberLabel);
			
			TextField phoneNumberTextField = new TextField();
			infoPanel.add(phoneNumberTextField);
			
			JLabel defaultLatLongLabel = new JLabel(" Default Lat and Long:");
			infoPanel.add(defaultLatLongLabel);
			
			TextField defaultLatLongTextField = new TextField();
			infoPanel.add(defaultLatLongTextField);
			
			JLabel defaultZoomLabel = new JLabel(" Default Zoom:");
			infoPanel.add(defaultZoomLabel);
			
			TextField defaultZoomTextField = new TextField();
			infoPanel.add(defaultZoomTextField);
			
			JLabel resetPasswordLabel = new JLabel(" Reset Password?");
			infoPanel.add(resetPasswordLabel);
			
			JCheckBox resetPasswordCheckBox = new JCheckBox();
			resetPasswordCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(resetPasswordCheckBox);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(35);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(update);
			buttonPanel.add(cancel);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			cancel.setSize(update.getSize());
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y - this.getHeight()/2);
		}
	}
	
	public class AdministratorWindow extends JFrame
	{
		private JButton userDelete, modify, userAddNew, catDelete, catAddNew;
		
		public AdministratorWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Administrator Mode");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			//Main Panel
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			//User Panel
			JPanel userPanel = new JPanel(new BorderLayout(0, 5));
			userPanel.setBackground(new Color(132, 227, 255));
			panel.add(userPanel, BorderLayout.NORTH);
			
			JLabel userLabel = new JLabel(" Users:");
			userPanel.add(userLabel, BorderLayout.NORTH);
			
			JPanel userInnerPanel = new JPanel();;
			userInnerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			userInnerPanel.setBackground(new Color(132, 227, 255));
			userPanel.add(userInnerPanel, BorderLayout.SOUTH);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
			userInnerPanel.add(buttonPanel);
			
			userInnerPanel.add(Box.createRigidArea(new Dimension(40,0)));
			
			userDelete = new JButton("Delete");
			modify = new JButton("Modify");
			userAddNew = new JButton("Add New");
			
			buttonPanel.add(Box.createRigidArea(new Dimension(0,5)));
			buttonPanel.add(userDelete);
			buttonPanel.add(Box.createRigidArea(new Dimension(0,10)));
			buttonPanel.add(modify);
			buttonPanel.add(Box.createRigidArea(new Dimension(0,10)));
			buttonPanel.add(userAddNew);
			buttonPanel.add(Box.createRigidArea(new Dimension(0,5)));
				
			List userList = new List(6, false);
			userInnerPanel.add(userList);
			userList.addItem("aaaaa");
			userList.addItem("bbbbb");
			userList.addItem("ccccc");
			userList.addItem("ddddd");
			userList.addItem("eeeee");
			userList.addItem("fffff");
			userList.addItem("ggggg");
			userList.addItem("hhhhh");
			
			panel.add(Box.createRigidArea(new Dimension(0,5)), BorderLayout.CENTER);
			
			// Categories Panel
			JPanel catPanel = new JPanel(new BorderLayout(0, 5));
			catPanel.setBackground(new Color(132, 227, 255));
			panel.add(catPanel,BorderLayout.SOUTH);
			
			JLabel catLabel = new JLabel(" Categories:");
			catPanel.add(catLabel, BorderLayout.NORTH);
			
			JPanel catInnerPanel = new JPanel();
			catInnerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			catInnerPanel.setBackground(new Color(132, 227, 255));
			catPanel.add(catInnerPanel);
			
			JPanel buttonPanel2 = new JPanel();
			buttonPanel2.setBackground(new Color(132, 227, 255));
			buttonPanel2.setLayout(new BoxLayout(buttonPanel2, BoxLayout.Y_AXIS));
			catInnerPanel.add(buttonPanel2);
			
			catInnerPanel.add(Box.createRigidArea(new Dimension(40,0)));
			
			catDelete = new JButton("Delete");
			catAddNew = new JButton("Add New");
			
			buttonPanel2.add(Box.createRigidArea(new Dimension(0,5)));
			buttonPanel2.add(catDelete);
			buttonPanel2.add(Box.createRigidArea(new Dimension(0,10)));
			buttonPanel2.add(catAddNew);
			buttonPanel2.add(Box.createRigidArea(new Dimension(0,10)));
			
			List catList = new List(5,false);
			catInnerPanel.add(catList);
			catList.addItem("Clothes");
			catList.addItem("Electronics");
			catList.addItem("Accessories");
			catList.addItem("Things");
			catList.addItem("Antiques");
			catList.addItem("Lamps");
			catList.addItem("Furniture");
			catList.addItem("Books");
			
			
			this.pack();
			this.setResizable(false);
			
			userDelete.setSize(userAddNew.getSize());
			modify.setSize(userAddNew.getSize());
			catDelete.setSize(userAddNew.getSize());
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y - this.getHeight()/2);
		}
	}
	
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
			garageList.addItem("Garage Sale 1");
			garageList.addItem("Garage Sale 2");
			garageList.addItem("Garage Sale 3");
			garageList.addItem("Garage Sale 4");
			garageList.addItem("Garage Sale 5");
			garageList.addItem("Garage Sale 6");
			garageList.addItem("Garage Sale 7");
			garageList.addItem("Garage Sale 8");
			garageList.addItem("Garage Sale 9");
			garageList.addItem("Garage Sale 10");
			
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
	
	public class AddNewGarageWindow extends JFrame
	{
		private JButton add, cancel, categories;
		
		public AddNewGarageWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Add New Garage Sale");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,15));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			add = new JButton("Add");
			cancel = new JButton("Cancel");
			
			JLabel label = new JLabel("Enter the new garage sale information.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(8,2,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			JLabel latitudeLabel = new JLabel(" Latitude:");
			infoPanel.add(latitudeLabel);
			
			TextField latitudeTextField = new TextField();
			infoPanel.add(latitudeTextField);
			
			JLabel longitudeLabel = new JLabel(" Longitude:");
			infoPanel.add(longitudeLabel);
			
			TextField longitudeTextField = new TextField();
			infoPanel.add(longitudeTextField);
			
			JLabel addressLabel = new JLabel(" Address:");
			infoPanel.add(addressLabel);
			
			TextField addressTextField = new TextField();
			infoPanel.add(addressTextField);
			
			JLabel cityLabel = new JLabel(" City:");
			infoPanel.add(cityLabel);
			
			TextField cityTextField = new TextField();
			infoPanel.add(cityTextField);
			
			JLabel dateLabel = new JLabel(" Date:");
			infoPanel.add(dateLabel);
			
			TextField dateTextField = new TextField();
			infoPanel.add(dateTextField);
			
			JLabel timeLabel = new JLabel(" Time:");
			infoPanel.add(timeLabel);
			
			TextField timeTextField = new TextField();
			infoPanel.add(timeTextField);
			
			JLabel commentLabel = new JLabel(" Comment (optional):");
			infoPanel.add(commentLabel);
			
			TextField commentTextField = new TextField();
			infoPanel.add(commentTextField);
			
			JLabel catLabel = new JLabel(" Categories:");
			infoPanel.add(catLabel);
			
			categories = new JButton("Click to select categories");
			infoPanel.add(categories);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(35);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(add);
			buttonPanel.add(cancel);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			add.setSize(cancel.getSize());
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
		}
	}
	
	public class UpdateGarageWindow extends JFrame
	{
		private JButton update, cancel, categories;
		
		public UpdateGarageWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Update Garage Sale");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,15));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			update = new JButton("Update");
			cancel = new JButton("Cancel");
			
			JLabel label = new JLabel("Update garage sale information.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(8,2,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			JLabel latitudeLabel = new JLabel(" Latitude:");
			infoPanel.add(latitudeLabel);
			
			TextField latitudeTextField = new TextField();
			infoPanel.add(latitudeTextField);
			
			JLabel longitudeLabel = new JLabel(" Longitude:");
			infoPanel.add(longitudeLabel);
			
			TextField longitudeTextField = new TextField();
			infoPanel.add(longitudeTextField);
			
			JLabel addressLabel = new JLabel(" Address:");
			infoPanel.add(addressLabel);
			
			TextField addressTextField = new TextField();
			infoPanel.add(addressTextField);
			
			JLabel cityLabel = new JLabel(" City:");
			infoPanel.add(cityLabel);
			
			TextField cityTextField = new TextField();
			infoPanel.add(cityTextField);
			
			JLabel dateLabel = new JLabel(" Date:");
			infoPanel.add(dateLabel);
			
			TextField dateTextField = new TextField();
			infoPanel.add(dateTextField);
			
			JLabel timeLabel = new JLabel(" Time:");
			infoPanel.add(timeLabel);
			
			TextField timeTextField = new TextField();
			infoPanel.add(timeTextField);
			
			JLabel commentLabel = new JLabel(" Comment (optional):");
			infoPanel.add(commentLabel);
			
			TextField commentTextField = new TextField();
			infoPanel.add(commentTextField);
			
			JLabel catLabel = new JLabel(" Categories:");
			infoPanel.add(catLabel);
			
			categories = new JButton("Click to select categories");
			infoPanel.add(categories);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(35);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(update);
			buttonPanel.add(cancel);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			cancel.setSize(update.getSize());
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
		}
	}
	
	public class SelectCatWindow extends JFrame
	{
		private JButton select, cancel, categories;
		
		public SelectCatWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Select Categories");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,5));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			select = new JButton("Select");
			cancel = new JButton("Cancel");
			
			JLabel label = new JLabel("Select Categories.");
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel catPanel = new JPanel();
			catPanel.setBackground(new Color(132, 227, 255));
			catPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(catPanel, BorderLayout.CENTER);
			
			List catList = new List(7, false);
			catPanel.add(catList);
			catList.setMultipleMode(true);
			catList.addItem("Clothes");
			catList.addItem("Electronics");
			catList.addItem("Accessories");
			catList.addItem("Things");
			catList.addItem("Antiques");
			catList.addItem("Lamps");
			catList.addItem("Furniture");
			catList.addItem("Books");
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(10);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(select);
			buttonPanel.add(cancel);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			select.setSize(cancel.getSize());
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
		}
	}
	
	public class BuyerWindow extends JFrame
	{
		private JButton view, categories;
		
		public BuyerWindow() throws GeoPositionException
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Buyer Mode");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.black));
			
			JMenu menu = new JMenu("Menu");
			JMenuItem buyerMode = new JMenuItem("Switch to Seller Mode");
			JMenuItem print=new JMenuItem("Print");
			JMenuItem exit = new JMenuItem("Exit");
			print.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(e.getID()==MouseEvent.MOUSE_CLICKED){
						//new HTMLPrinter(jxMapKit).print(garageSale);
						
					}
				}
				
				
			});
			menuBar.add(menu);
			menu.add(buyerMode);
			menu.addSeparator();
			menu.add(print);
			menu.addSeparator();
			menu.add(exit);
			
			
			this.setJMenuBar(menuBar);
			JPanel contentPane=new JPanel();
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
			this.setContentPane(contentPane);
			
			JPanel panel = new JPanel(new BorderLayout(0,15));
			panel.setBackground(new Color(132, 227, 255));
			this.getContentPane().add(panel);
			JPanel mapPanel= new JPanel(new BorderLayout(0,15));
			mapPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0,Color.black));
			this.getContentPane().add(mapPanel);
			
	        jxMapKit.setCenterPosition(new GarageGeoPos(43.005, -81.275));
	        
	        
	        
	        jxMapKit.setZoom(3);
	        jxMapKit.setPreferredSize(new Dimension(800,400));
	        
	        Set<Waypoint> waypoints = new HashSet<Waypoint>();
	        waypoints.add(new Waypoint(43.005, -81.275));
	        
	        //crate a WaypointPainter to draw the points
	        WaypointPainter<JXMapViewer> painter = new WaypointPainter<JXMapViewer>();

	        painter.setWaypoints(waypoints);
	        jxMapKit.getMainMap().setOverlayPainter(painter);
	        
	        
	        mapPanel.add(jxMapKit);
			
			view = new JButton("View Garage Sales");
			
			JLabel label = new JLabel("Select zero or many filters.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
						
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(6,2,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			//Radius
			JCheckBox radiusCheckBox = new JCheckBox(" View within radius (Km):");
			radiusCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(radiusCheckBox);
			
			TextField radiusTextField = new TextField();
			infoPanel.add(radiusTextField);
			
			//User
			JCheckBox userCheckBox = new JCheckBox(" View by user ID:");
			userCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(userCheckBox);
			
			TextField userTextField = new TextField();
			infoPanel.add(userTextField);
			
			//Date
			JCheckBox dateCheckBox = new JCheckBox(" View by date range  (DD/MM/YYYY  to  DD/MM/YYYY):");
			dateCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(dateCheckBox);
			
			JPanel datePanel = new JPanel(new GridLayout(1,3));
			datePanel.setBackground(new Color(132, 227, 255));
			
			TextField date1TextField = new TextField();
			datePanel.add(date1TextField);
			
			JLabel date2Label = new JLabel("to", JLabel.CENTER);
			datePanel.add(date2Label);
			
			TextField date2TextField = new TextField();
			datePanel.add(date2TextField);
			
			infoPanel.add(datePanel);
			
			//Seller rank
			JCheckBox sellerRankCheckBox = new JCheckBox(" View by seller rank:");
			sellerRankCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(sellerRankCheckBox);
			
			JPanel sellerRankPanel = new JPanel(new GridLayout(1,2));
			
			JComboBox sellerRank1 = new JComboBox();
			sellerRank1.addItem("1");
			sellerRank1.addItem("2");
			sellerRank1.addItem("3");
			sellerRank1.addItem("4");
			sellerRank1.addItem("5");
			sellerRankPanel.add(sellerRank1);
			
			JComboBox sellerRank2 = new JComboBox();
			sellerRank2.addItem("Equal to");
			sellerRank2.addItem("Worse or equal to");
			sellerRank2.addItem("Better or equal to");
			sellerRankPanel.add(sellerRank2);
			
			infoPanel.add(sellerRankPanel);
			
			//Garage sale rank
			JCheckBox garageRankCheckBox = new JCheckBox(" View by garage sale rank:");
			garageRankCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(garageRankCheckBox);
			
			JPanel garageRankPanel = new JPanel(new GridLayout(1,2));
			
			JComboBox garageRank1 = new JComboBox();
			garageRank1.addItem("1");
			garageRank1.addItem("2");
			garageRank1.addItem("3");
			garageRank1.addItem("4");
			garageRank1.addItem("5");
			garageRankPanel.add(garageRank1);
			
			JComboBox garageRank2 = new JComboBox();
			garageRank2.addItem("Equal to");
			garageRank2.addItem("Worse or equal to");
			garageRank2.addItem("Better or equal to");
			garageRankPanel.add(garageRank2);
			
			infoPanel.add(garageRankPanel);
			
			// Categories
			JCheckBox catCheckBox = new JCheckBox(" View by categories:");
			catCheckBox.setBackground(new Color(132, 227, 255));
			infoPanel.add(catCheckBox);
			
			categories = new JButton("Click to select categories");
			infoPanel.add(categories);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			buttonLayout.setHgap(35);
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(view);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
			
			
			
   		    

		}
	}
	
	public class GarageInfoWindow extends JFrame
	{
		private JButton ok;
		
		public GarageInfoWindow()
		{		
			this.addWindowListener(new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				} 
			});
			this.setTitle("Add New Garage Sale");
			this.setVisible(true);
			this.setBackground(new Color(132, 227, 255));
			
			JPanel panel = new JPanel(new BorderLayout(0,15));;
			panel.setBackground(new Color(132, 227, 255));
			this.add(panel);
			
			ok = new JButton("OK");
			
			JLabel label = new JLabel("Garage sale information.", JLabel.CENTER);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
			panel.add(label, BorderLayout.NORTH);
			
			// Info Panel
			JPanel infoPanel = new JPanel(new GridLayout(8,1,5,5));
			infoPanel.setBackground(new Color(132, 227, 255));
			infoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(infoPanel, BorderLayout.CENTER);
			
			JLabel latitudeLabel = new JLabel(" Latitude: 44.123");
			infoPanel.add(latitudeLabel);
			
			JLabel longitudeLabel = new JLabel(" Longitude: -81.012");
			infoPanel.add(longitudeLabel);
			
			JLabel addressLabel = new JLabel(" Address: 65 Compton Cres");
			infoPanel.add(addressLabel);
			
			JLabel cityLabel = new JLabel(" City: London");
			infoPanel.add(cityLabel);
			
			JLabel dateLabel = new JLabel(" Date: 27/9/2008");
			infoPanel.add(dateLabel);
			
			JLabel timeLabel = new JLabel(" Time: 23:30");
			infoPanel.add(timeLabel);
			
			JLabel catLabel = new JLabel(" Categories: Clothes, Furniture");
			infoPanel.add(catLabel);
			
			JLabel commentLabel = new JLabel(" Comment: ");
			infoPanel.add(commentLabel);
			
			// Button Panel
			FlowLayout buttonLayout = new FlowLayout();
			
			JPanel buttonPanel = new JPanel(buttonLayout);
			buttonPanel.setBackground(new Color(132, 227, 255));
			buttonPanel.add(ok);
			
			panel.add(buttonPanel, BorderLayout.SOUTH);
			
			// Set size and location of window
			this.pack();
			this.setResizable(false);
			
			this.setLocationRelativeTo(null);
			this.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{	
		JOptionPane.showInputDialog(startWindow, "Enter a new Category", "Change Password", JOptionPane.PLAIN_MESSAGE);
	}
	

	
	public static void main(String[] args) throws GeoPositionException
	{
		
		GUI controller = new GUI();
		buyerWindow= controller.new BuyerWindow();
		
	}
}
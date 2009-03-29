package ca.uwo.garage.mapviewer;
import ca.uwo.garage.storage.GarageSale;
import ca.uwo.garage.storage.GeoPosition;
import ca.uwo.garage.storage.GeoPositionException;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;

@SuppressWarnings("serial")
public class MapPanel
	extends JPanel
{
	private transient JXMapKit m_mapKit;
	private Set<GarageWaypoint> waypoints;

	public MapPanel()
	{
		this(new OpenMapProvider());
	}
	public MapPanel(MapProvider prov)
	{
		super();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 400));

		// Create a Tile Factory from our provider tile information
		TileFactory tileFactory = new DefaultTileFactory(prov.getTileFactory());
		m_mapKit = new JXMapKit();
		m_mapKit.setTileFactory(tileFactory);
		m_mapKit.setCenterPosition(new GeoPosition());
        m_mapKit.setZoom(prov.getInitialZoomLevel());
        add(m_mapKit);
        
        //initialize waypoints object
        waypoints = new HashSet<GarageWaypoint>();

         //create a WaypointPainter to draw the points
        WaypointPainter painter = new WaypointPainter();
        painter.setWaypoints(waypoints);

        //paint waypoint over the map
        m_mapKit.getMainMap().setOverlayPainter(painter);
        
        final JLabel hoverLabel =new JLabel();
        hoverLabel.setVisible(false);
        hoverLabel.setSize(new Dimension(40,40));
        hoverLabel.setBorder(BorderFactory.createRaisedBevelBorder());
        hoverLabel.setBackground(Color.blue);
        hoverLabel.setOpaque(false);
      
        m_mapKit.getMainMap().add(hoverLabel);
     
        
        m_mapKit.getMainMap().addMouseListener(new MouseListener(){
            @Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent e) {
	               JXMapViewer map = m_mapKit.getMainMap();
	               hoverLabel.setVisible(false);

	                for(GarageWaypoint wp: waypoints){
	                	org.jdesktop.swingx.mapviewer.GeoPosition gp=wp.getPosition();
				
	                	//convert to world bitmap
	                	Point2D gp_pt = map.getTileFactory().geoToPixel(gp, map.getZoom());
	                	//convert to screen
	                	Rectangle rect = map.getViewportBounds();
	                	//Point cvted_gp_pt = new Point((int)gp_pt.getX(),(int)gp_pt.getY());
	                	Point cvted_gp_pt = new Point((int)gp_pt.getX()-rect.x,
	                                                  (int)gp_pt.getY()-rect.y);
	                	//check if near the mouse
	                	if(cvted_gp_pt.distance(e.getPoint()) < 10) {
	                		hoverLabel.setLocation(e.getPoint());
	                		//change hoverLabel info.
	                		StringBuffer gs_info =new StringBuffer();
	                		gs_info.append(wp.getGarageSale().getinfo());
	                		
	                		hoverLabel.setText(gs_info.toString());
	                		hoverLabel.setVisible(true);
	                		
	                		return;
	                	}
	                }
	                
	                return;
				}
			@Override
			public void mouseClicked(MouseEvent e) {}
	        });
		}

			
	public void addWayPoint(GarageSale gs ){
		GarageWaypoint wayPoint=new GarageWaypoint(gs.location(),gs);
        waypoints.add(wayPoint);
	}
	
	public void removeWayPoint(GarageWaypoint gwp){
		for (GarageWaypoint wp:waypoints){
			if((wp.getPosition().getLatitude()==gwp.getPosition().getLatitude())&&(wp.getPosition().getLongitude()==gwp.getPosition().getLongitude())){
				waypoints.remove(wp);
			}
		}
	}
	
	public GarageWaypoint getWayPoint(){
		GarageWaypoint gwp=null;
			for(GarageWaypoint gw: waypoints){
				gwp=gw;
			}
			return gwp;
	}
	public JXMapKit getJXMapKit(){
		return m_mapKit;
	}
	
}

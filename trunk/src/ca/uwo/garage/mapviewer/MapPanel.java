package ca.uwo.garage.mapviewer;
import ca.uwo.garage.storage.GeoPosition;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;

@SuppressWarnings("serial")
public class MapPanel
	extends JPanel
{
	private transient JXMapKit m_mapKit;
	private Set<Waypoint> waypoints;

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
        
        waypoints = new HashSet<Waypoint>();

         //create a WaypointPainter to draw the points
        WaypointPainter painter = new WaypointPainter();

        painter.setWaypoints(waypoints);

        m_mapKit.getMainMap().setOverlayPainter(painter);
	}
	public void addWayPoint(GeoPosition gp ){
        waypoints.add(new Waypoint(gp.getLatitude(),gp.getLongitude()));
	}
	
	public JXMapKit getJXMapKit(){
		return m_mapKit;
	}
	
}

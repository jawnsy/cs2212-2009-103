package ca.uwo.garage.mapviewer;
import ca.uwo.garage.storage.GeoPosition;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXMapKit;
import org.jdesktop.swingx.mapviewer.DefaultTileFactory;
import org.jdesktop.swingx.mapviewer.TileFactory;

@SuppressWarnings("serial")
public class MapPanel
	extends JPanel
{
	private transient JXMapKit m_mapKit;

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
	}
}

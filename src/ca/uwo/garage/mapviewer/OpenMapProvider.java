package ca.uwo.garage.mapviewer;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OpenMapProvider
	implements MapProvider
{
	public static final int ZOOM_STREET 	= 2;
	public static final int ZOOM_TOWN 		= 5;
	public static final int ZOOM_PROVINCE 	= 8;
	public static final int ZOOM_REGION 	= 10;
	public static final int ZOOM_COUNTRY 	= 12;
	public static final int ZOOM_CONTINENT 	= 14;

	private TileFactoryInfo tileFactory = new OpenTileFactory();

	public TileFactoryInfo getTileFactory() { 
        return tileFactory;
    }
    public String getName() { 
        return "Open Street Maps"; 
    }
    public int getInitialZoomLevel() { 
        return ZOOM_STREET;
    }
    public boolean isSatelliteImageryAvailable() {
        return false;
    }
}

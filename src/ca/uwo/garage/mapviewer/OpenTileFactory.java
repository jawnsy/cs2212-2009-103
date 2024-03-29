package ca.uwo.garage.mapviewer;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public class OpenTileFactory
	extends TileFactoryInfo
{
    private static final int TOP_ZOOM_LEVEL = 17; 
    private static final int MAX_ZOOM_LEVEL = 15; 
    private static final int MIN_ZOOM_LEVEL = 1; 
    private static final int TILE_SIZE = 256; 
    private static final String TILE_SERVER = "http://tile.openstreetmap.org";

	public OpenTileFactory() {
	    super(
	    	MIN_ZOOM_LEVEL,
	    	MAX_ZOOM_LEVEL,
	    	TOP_ZOOM_LEVEL,
	    	TILE_SIZE, // Tile size in pixels

	    	// OSM's tile server has (0,0) as top-left corner
	    	true, // Zero tile is leftmost (abscissa)
	    	true, // Zero tile is topmost
	    	TILE_SERVER, // Server base URL
	    	"x", "y", // X and Y position
	    	"z" // Zoom level
	    );
	}

    public String getTileUrl(int x, int y, int zoom) {
    	return (this.baseURL + "/" + (TOP_ZOOM_LEVEL - zoom) + "/" + x + "/" + y + ".png");
    }
}

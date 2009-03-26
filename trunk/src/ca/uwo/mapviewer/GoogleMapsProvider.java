package ca.uwo.mapviewer;

import org.jdesktop.swingx.mapviewer.TileFactoryInfo; 
import ca.uwo.garage.*;
 
public class GoogleMapsProvider  extends  AbstractMapProvider { 
 
    
  
   
    private static final int TOP_ZOOM_LEVEL = 17; 
    private static final int MAX_ZOOM_LEVEL = 15; 
    private static final int MIN_ZOOM_LEVEL = 1; 
    private static final int TILE_SIZE = 256; 
    private static final String URL_FOR_MAP="http://tile.openstreetmap.org";
    private static final String ZOOM= "z";
    private TileFactoryInfo tileProviderInfo = new TileFactoryInfo
      (MIN_ZOOM_LEVEL, MAX_ZOOM_LEVEL, TOP_ZOOM_LEVEL, TILE_SIZE, true,
       true, URL_FOR_MAP, "x", "y", ZOOM) { 
    public String getTileUrl(int x, int y, int zoom) {
    	String temp;
        zoom = TOP_ZOOM_LEVEL-zoom;
        temp = this.baseURL +"/"+zoom+"/"+x+"/"+y+".png";
        System.out.println(temp);
        return temp;
    }

    };
    public GoogleMapsProvider() { 
        super(); 
        registerZoomLevel(ZoomLevel.STREET, 2); 
        registerZoomLevel(ZoomLevel.TOWN, 5); 
        registerZoomLevel(ZoomLevel.PROVINCE, 8); 
        registerZoomLevel(ZoomLevel.REGION, 10); 
        registerZoomLevel(ZoomLevel.COUNTRY, 12); 
        registerZoomLevel(ZoomLevel.CONTINENT, 14); 
    } 
     
    public TileFactoryInfo getTileProviderInfo() { 
        return tileProviderInfo; 
    } 
     
    public String getName() { 
        return "Google Maps"; 
    } 
     
    public int getInitialZoomLevel() { 
        return 10; 
    } 
     
    public boolean isSatelliteImageryAvailable() { 
        return true; 
    } 
}
 
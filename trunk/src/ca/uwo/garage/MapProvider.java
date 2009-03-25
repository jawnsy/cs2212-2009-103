import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public interface MapProvider { 
    
  
   
     
    public static enum ZoomLevel { 
        STREET, 
        TOWN, 
        PROVINCE, 
        REGION, 
        COUNTRY, 
        CONTINENT; 
         
    }
 
     
    public TileFactoryInfo getTileProviderInfo(); 
     
    public String getName(); 
     
    public int getInitialZoomLevel(); 
     
    public int getZoomLevel(ZoomLevel zoomLevel); 
     
    public boolean isSatelliteImageryAvailable(); 
} 


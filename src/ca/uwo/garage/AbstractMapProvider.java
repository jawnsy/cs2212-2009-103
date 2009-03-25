import java.util.HashMap; 
import java.util.Map; 
 
public abstract class AbstractMapProvider implements MapProvider { 
 

      
  
   
     
    private Map<ZoomLevel, Integer> zoomLevelMap = new HashMap<ZoomLevel, Integer>(); 
     
    public int getZoomLevel(ZoomLevel zoomLevel) { 
        Integer i = zoomLevelMap.get(zoomLevel); 
        return (i != null) ? i : getInitialZoomLevel(); 
    } 
     
    public boolean isSatelliteImageryAvailable() { 
        return false; 
    } 
     
    protected void registerZoomLevel(ZoomLevel zoomLevel, int level) { 
        zoomLevelMap.put(zoomLevel, level); 
    } 
}
 
 
 


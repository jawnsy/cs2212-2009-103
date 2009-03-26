package ca.uwo.garage.mapviewer;
import org.jdesktop.swingx.mapviewer.TileFactoryInfo;

public interface MapProvider
{ 
	public abstract TileFactoryInfo getTileFactory();
    public abstract String getName();
    public abstract int getInitialZoomLevel();
    public abstract boolean isSatelliteImageryAvailable();
}

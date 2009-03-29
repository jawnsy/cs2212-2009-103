package ca.uwo.garage.mapviewer;


import org.jdesktop.swingx.mapviewer.Waypoint;

import ca.uwo.garage.storage.GarageSale;
import ca.uwo.garage.storage.GeoPosition;

public class GarageWaypoint extends Waypoint {
	private GarageSale gs;
	
	public GarageWaypoint(GeoPosition gp, GarageSale gsi){
		super(gp.getLatitude(),gp.getLongitude());
		gs=gsi;
	}
	
	public GarageSale getGarageSale(){
		return gs;
	}
}

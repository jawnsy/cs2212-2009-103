package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class GeoPositionException
	extends GarageException
{
	private static final long serialVersionUID = 1L;

	public GeoPositionException() {
		super("The specified geographic coordinate is invalid");
	}
	public GeoPositionException(String msg) {
		super(msg);
	}
}

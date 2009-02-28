package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class GeoPositionException
	extends GarageException
{
	public GeoPositionException() {
		super("The specified geographic coordinate is invalid");
	}
	public GeoPositionException(String msg) {
		super(msg);
	}
}

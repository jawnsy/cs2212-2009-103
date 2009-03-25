package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class GarageGeoPosException
	extends GarageException
{
	public GarageGeoPosException() {
		super("The specified geographic coordinate is invalid");
	}
	public GarageGeoPosException(String msg) {
		super(msg);
	}
}

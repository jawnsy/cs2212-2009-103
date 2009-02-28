package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class ZoomInvalidException
	extends GarageException
{
	public ZoomInvalidException() {
		super("The specialized Zoom level is invalid");
	}
	public ZoomInvalidException(String msg) {
		super(msg);
	}
}

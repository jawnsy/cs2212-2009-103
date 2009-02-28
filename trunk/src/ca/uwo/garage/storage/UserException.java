package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class UserException
	extends GarageException
{
	public UserException(String msg) {
		super(msg);
	}
}

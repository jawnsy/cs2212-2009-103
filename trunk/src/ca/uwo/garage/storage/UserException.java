package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class UserException
	extends GarageException
{
	private static final long serialVersionUID = 1L;

	public UserException(String msg) {
		super(msg);
	}
}

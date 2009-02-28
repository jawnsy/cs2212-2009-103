package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class StorageException
	extends GarageException
{
	private static final long serialVersionUID = 1L;

	public StorageException() {
		super("Something strange happened with our storage system");
	}
	public StorageException(String msg) {
		super(msg);
	}
}

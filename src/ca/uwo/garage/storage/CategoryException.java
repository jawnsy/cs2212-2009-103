package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class CategoryException
	extends GarageException
{
	public CategoryException() {
		super("Invalid category data specified");
	}
	public CategoryException(String msg) {
		super(msg);
	}
}

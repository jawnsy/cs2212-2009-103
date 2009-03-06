package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized

/**
 * The CategoryException is a subclass of GarageException. As such, it can be
 * treated as a general GarageException if so desired.
 * 
 * This exception occurs when invalid data is being used to construct or modify
 * a Category object.
 *
 * @author Jonathan Yu
 * @extends GarageException
 * @version $Revision$
 */
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

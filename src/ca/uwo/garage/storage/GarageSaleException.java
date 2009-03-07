package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized

/**
 * The GarageSaleException is a subclass of GarageException. As such, it can be
 * treated as a general GarageException if so desired.
 * 
 * This exception occurs when invalid data is being used to construct or modify
 * a GarageSale object.
 *
 * @author Jonathan Yu
 * @extends GarageException
 * @version $Revision: 17 $
 */
public class GarageSaleException
	extends GarageException
{
	public GarageSaleException() {
		super("Invalid garage sale data specified");
	}
	public GarageSaleException(String msg) {
		super(msg);
	}
}

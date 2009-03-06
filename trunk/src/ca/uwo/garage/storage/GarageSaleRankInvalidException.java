package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

/**
 * The GarageSaleRankInvalidException is a subclass of GarageException. As such, it can be
 * treated as a general GarageException if so desired.
 * 
 * This exception occurs when invalid data is being used to construct or modify a
 * GarageSaleRank object.
 *
 * @author Jason Lu, Jonathan Yu
 * @extends GarageException
 * @version $Revision$
 */
public class GarageSaleRankInvalidException
	extends GarageException
{
	private static final long serialVersionUID = 1L;
	
	public GarageSaleRankInvalidException() {
		super ("Invalid garage sale rank information.");
	}
	public GarageSaleRankInvalidException(String msg) {
		super(msg);
	}

}

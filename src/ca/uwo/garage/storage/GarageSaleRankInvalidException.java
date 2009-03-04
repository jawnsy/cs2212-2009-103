package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class GarageSaleRankInvalidException extends GarageException {
	private static final long serialVersionUID = 1L;
	
	public GarageSaleRankInvalidException (){
		super ("Invalid garage sale rank information.");
	}
	
	public GarageSaleRankInvalidException (String msg){
		super(msg);
	}

}

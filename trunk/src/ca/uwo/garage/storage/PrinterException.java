package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class PrinterException extends GarageException {
	private static final long serialVersionUID = 1L;
	
	public PrinterException (){
			super("Unknown error occurs when printing.");
	}
	
	public PrinterException(String msg){
			super(msg);
	}

}

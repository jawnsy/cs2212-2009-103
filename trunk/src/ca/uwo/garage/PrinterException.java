package ca.uwo.garage;


public class PrinterException extends GarageException {
	private static final long serialVersionUID = 1L;
	
	public PrinterException (){
			super("Unknown error occurs when printing.");
	}
	
	public PrinterException(String msg){
			super(msg);
	}

}

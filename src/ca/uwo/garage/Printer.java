package ca.uwo.garage;

import java.util.Collection;

import ca.uwo.garage.storage.GarageSale;

/**
 * this interface is used to provide method to print
 * the garage sale info into HTML format
 * @author Jason Lu, Jonathan Yu
 * @version $Revision$
 */

public interface Printer
{
//HELPER METHOD****************************************
	/**
	 * this print method print a single garage sale information
	 * into HTML format.
	 * @param garageSale the GarageSale object to be printed out
	 * @throws PrinterException 
	 */
	public abstract void print(GarageSale garageSale)
		throws PrinterException;
	
	/**
	 * this print method print a collection of garage sales information
	 * into HTML format
	 * @param sales Collection of GarageSale objects
	 * @throws PrinterException
	 */
	public abstract void print(Collection<GarageSale> sales)
		throws PrinterException;

}

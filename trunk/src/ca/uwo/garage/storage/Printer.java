package ca.uwo.garage.storage;

import java.util.Collection;

/**
 * this interface is used to provide method to print
 * the garage sale info into HTML format
 */

public interface Printer {
//HELPER METHOD****************************************
	/**
	 * this print method print a single garage sale information
	 * into HTML format.
	 * @param garageSale the GarageSale object to be printed out
	 * @throws PrinterException 
	 */
	void print(GarageSale garageSale)
		throws PrinterException;
	
	/**
	 * this print method print a collection of garage sales information
	 * into HTML format
	 * @param sales Collection of GarageSale objects
	 * @throws PrinterException
	 */
	void print(Collection sales)
		throws PrinterException;

}

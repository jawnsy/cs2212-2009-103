package ca.uwo.garage.storage;

import java.util.Collection;

public interface Printer {
	
	void print(GarageSale garageSale)
		throws PrinterException;
	void print(Collection sales)
		throws PrinterException;

}

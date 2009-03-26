/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */

package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class PrinterException extends GarageException {
        private static final long serialVersionUID = 1L;
        
    	/** Constructs a new exception to be thrown when 
	 	 * an error occurs while the garage sale is printing
	 	 */
        public PrinterException (){
                        super("Unknown error occured while printing.");
        }
        
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public PrinterException(String msg){
                        super(msg);
        }

}

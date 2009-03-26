/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */

package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class GeoPositionException
        extends GarageException
{
	 	/** Constructs a new exception to be thrown when a garage sale
	 	 * is given an invalid set of geographic coordinates
	 	 */
        public GeoPositionException() {
                super("The specified geographic coordinate is invalid");
        }
        
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public GeoPositionException(String msg) {
                super(msg);
        }
}
/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class ZoomInvalidException
        extends GarageException
{
		/**Constructs a new exception method to be thrown when
		 * the zoom level is set to a number which is out of 
		 * its boundary limits.
		 */
        public ZoomInvalidException() {
                super("The specialized Zoom level is invalid");
        }
        
        /**Constructs a new exception with the specified 
   	  	* detail message
   	  	* @param msg
   	  	*/
        public ZoomInvalidException(String msg) {
                super(msg);
        }
}

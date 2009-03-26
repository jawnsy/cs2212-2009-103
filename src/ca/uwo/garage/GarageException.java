/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */

package ca.uwo.garage;

@SuppressWarnings("serial") // does not need to be serialized
public class GarageException
        extends Exception
{
		/** Constructs a new exception to be thrown when there is a 
		* generic error within the program
		*/
        public GarageException() {
                super("Unspecified error in UWO Garage System");
        }
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public GarageException(String msg) {
                super(msg);
        }
}
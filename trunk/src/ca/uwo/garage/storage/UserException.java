/**This class represents an exception to occur when a a generic user error has occured
 * 
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized
public class UserException
        extends GarageException
{
		 /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
		public UserException(String msg) {
                super(msg);
        }
}
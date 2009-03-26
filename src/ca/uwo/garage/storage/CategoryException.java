/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */


package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial") // does not need to be serialized

public class CategoryException
        extends GarageException
{
		/**constructs a new exception for when 
		 * the user chooses a category which is invalid
		 */
        public CategoryException() {
                super("Invalid category data specified");
        }
		 
		 /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public CategoryException(String msg) {
                super(msg);
        }
}

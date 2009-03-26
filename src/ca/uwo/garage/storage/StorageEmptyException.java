/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

public class StorageEmptyException
        extends StorageException
{
        private static final long serialVersionUID = 1L;

        /**Constructs a new exception which is to be 
         * thrown when items are trying to be accessed from 
         * an empty storage location
		 */
        public StorageEmptyException() {
                super("Storage does not contain any objects");
        }
}

/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class StorageException
        extends GarageException
{
        private static final long serialVersionUID = 1L;

        /**Constructs a new exception which is to be 
         * thrown when there is an unidentified error
         * in the storage
         */
        public StorageException() {
                super("Something strange happened with our storage system");
        }
        
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public StorageException(String msg) {
                super(msg);
        }
}
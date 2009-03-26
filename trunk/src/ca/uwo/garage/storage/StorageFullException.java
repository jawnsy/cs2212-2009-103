/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

@SuppressWarnings("serial") // does not need to be serialized
public class StorageFullException
        extends StorageException
{
	/**Constructs a new exception which is to be 
	 * thrown when the storage is at maximum capacity
	 * and the user tries to add more data to it
	 */
        public StorageFullException() {
                super("Storage capacity or disk quota exceeded");
        }
}
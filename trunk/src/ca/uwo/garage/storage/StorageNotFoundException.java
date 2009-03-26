/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

public class StorageNotFoundException
        extends StorageException
{
        private static final long serialVersionUID = 1L;

        /**Constructs a new exception with the specified message detail.
         * Occurs when the storage key cannot be found
         * @param namespace
         * @param key
		  */
        public StorageNotFoundException(String namespace, String key) {
                super("The given key was not found: " + key + "@" + namespace);
        }
        
        /**Constructs a new exception with the specified message detail.
         * Occurs when the storage key cannot be found
         * @param namespace
         * @param key
		  */
        public StorageNotFoundException(String namespace, int key) {
                this(namespace, Integer.toString(key));
        }       
}
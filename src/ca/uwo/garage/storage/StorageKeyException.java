/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */
package ca.uwo.garage.storage;

public class StorageKeyException
        extends StorageException
{
        private static final long serialVersionUID = 1L;

        /**Constructs a new exception with the specified message detail.
         * Occurs when there are duplicate storage keys
         * @param namespace
         * @param key
		  */
        public StorageKeyException(String namespace, String key) {
                super("The given key is a duplicate: " + key + "@" + namespace);
        }
        
        /**Constructs a new exception with the specified message detail.
         * Occurs when there are duplicate storage keys
         * @param namespace
         * @param key
		  */
        public StorageKeyException(String namespace, int key) {
                this(namespace, Integer.toString(key));
        }
        
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public StorageKeyException(String msg) {
                super(msg);
        }
}
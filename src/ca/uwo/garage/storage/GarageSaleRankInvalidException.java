/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */

package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

public class GarageSaleRankInvalidException extends GarageException {
        private static final long serialVersionUID = 1L;
        
        /** Constructs a new exception to be thrown when there is a 
		* garage sale given a ranking which is invalid to the program
		*/
        public GarageSaleRankInvalidException (){
                super ("Invalid garage sale rank information.");
        }
        
        /**Constructs a new exception with the specified 
		  * detail message
		  * @param msg
		  */
        public GarageSaleRankInvalidException (String msg){
                super(msg);
        }

}

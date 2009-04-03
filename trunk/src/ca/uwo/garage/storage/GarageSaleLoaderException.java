/**This class represents an exception that our 
 * program would want to catch
 * @author CS2212 Group 103
 * @extends GarageException
 */

package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial")
public class GarageSaleLoaderException
	extends GarageException
{
	/** Constructs a new exception to be thrown if anything happens
	 *  in GarageSaleLoader while bulk loading in the data
 	 */
	public GarageSaleLoaderException()
	{
		super("Failed to load new or save imported GarageSales");
	}
	/**Constructs a new exception with the specified 
	 * detail message
	 * @param msg
	 */
	public GarageSaleLoaderException(String msg)
	{
		super(msg);
	}
}

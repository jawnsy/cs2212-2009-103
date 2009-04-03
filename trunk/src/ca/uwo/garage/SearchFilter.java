package ca.uwo.garage;

import ca.uwo.garage.storage.GarageSale;
/**
 * A class used to help filter garage sales
 * @author James
 *
 */
public class SearchFilter
{
	/**
	 * The constructor for this class
	 */
	public SearchFilter()
	{
		
	}
	/**
	 * A method used to determine whether or not a garage sale
	 * meets the filter requirements
	 * @param sale the garage sale to be checked
	 * @return true if it meets the requirements
	 */
	public boolean isIncluded(GarageSale sale)
	{
		return true;
	}
}

package ca.uwo.garage.storage;
/**
 * the GarageSaleRank class is used for a user to rank a specified garage sale
 * a specified User object, GarageSale object and rank information are bound
 * 
 * @author Jason Lu
 * @version Version 1.0
 */

public class GarageSaleRank {
	//instance variables **********************************
	private GarageSale m_garageSale;	//the specified GarageSale object bound to the rank
	private User m_user;				//the specified User object bound to the rank
	private int m_rank;					//the rank information.
	
	/**
	 * Constructor for GarageSaleRank object
	 * initialize the instance variables
	 * @param garageSale : specified GarageSale object to be bound with the rank.
	 * @param user : specified User object to be bound with the rank.
	 */
	public GarageSaleRank (GarageSale garageSale, User user){
		m_garageSale=garageSale;
		m_user=user;
		m_rank = 3;
	}
//ACCESSOR METHODS ****************************************	
	/**
	 * this method is used to get the GarageSale bundled with the current rank information. 
	 * @return the GarageSale object bundled with the current GarageSaleRank object  
	 */
	public GarageSale garageSale(){
		return m_garageSale; 	//return the private GarageSale object
	}
	/**
	 * this method is used to get the User bundled with the current rank information.
	 * @return the User object bundled with the current GarageSaleRank object
	 */
	public User user(){
		return m_user;			//return the private User object	
	}
	/**
	 * this method is used to get the current rank number
	 * @return the rank number bundled with the current GarageSaleRank object
	 */
	public int rank(){
		return m_rank;			//return the rank number
	}

//MUTATOR METHODS *************************************
	/**
	 * this method set the rank to the specified rank level
	 *  @param level the specified rank level to be set to
	 */
	public void rank(int level){
		m_rank = level;
	}
	
//HELPER METHODS ***************************************
	
}

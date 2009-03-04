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
	private final int RANK_MAX = 10;	//the maximum level of rank
	private final int RANK_MIN = 1;		//the minimum level of rank
	
	/**
	 * Constructor for GarageSaleRank object
	 * initialize the instance variables
	 * rank level is initialized as 3
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
	/**
	 * this method is used to check whether the rank is at maximum level
	 * @return whether the rank is maximum
	 */
	public boolean isMax(){
		return (m_rank==RANK_MAX);
	}
	/**
	 * this method is used to check whether the rank is at minimum level
	 * @return whether the rank is minimum
	 */
	public boolean isMin(){
		return (m_rank==RANK_MIN);
	}

//MUTATOR METHODS *************************************
	/**
	 * this method set the rank to the specified rank level
	 *  @param level the specified rank level to be set to
	 *  @throws GarageSaleRankInvalidException the specified rank level is not within valid range
	 */
	public void rank(int level)
		throws GarageSaleRankInvalidException
	{	
		if (level<RANK_MIN ||level>RANK_MAX){
			throw new GarageSaleRankInvalidException("Rank level must be between "+ RANK_MIN +" and "+ RANK_MIN + " inclusive");
		}
		else {
			m_rank = level;
		}
	}
	/**
	 * this method set the rank up by 1 level
	 * do nothing if the rank level is already at the maximum level
	 */
	public void up(){
		if (isMax()){
			return;
		}
		m_rank++;
	}
	/**
	 * this method set the rank down by 1 level
	 * do nothing if the rank level is already at the minimum level
	 */
	public void down(){
		if (isMin()){
			return;
		}
		m_rank--;
		
	}
	
}

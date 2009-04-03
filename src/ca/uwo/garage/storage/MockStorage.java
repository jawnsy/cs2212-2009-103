package ca.uwo.garage.storage;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import ca.uwo.garage.storage.GeoPosition;

/** The Mock storage class is a mock-up of a Storage implementation, useful for
 * testing. It will pretend to insert properly, but doesn't do anything. It's
 * really only useful for testing retrievals.
 */
public class MockStorage
	implements Storage
{
	private static final long serialVersionUID = 1L;

	// this is the TreeMap that contains our objects
	private transient TreeMap<String, User> m_user;
	private transient TreeMap<Integer, Category> m_category;
	private transient TreeMap<Integer, GarageSale> m_sales;
	private transient LinkedList<GarageSaleRank> m_ratings;
	private transient TreeMap<User, LinkedList<GarageSaleRank>> m_ratingsByUser;
	private transient TreeMap<GarageSale, LinkedList<GarageSaleRank>> m_ratingsBySale;
	private transient SequenceSet m_sequence;

	public void computeUserRating(User user)
	{
		
	}
	public void computeAllUserRatings()
	{
		
	}
	public double getRank(User user)
	{
		return 0.0;
	}

	/**
	 * This Constructor initializes 
	 * all of the attribues 
	 */
	public MockStorage() {
		// Instantiate all of the data structures
		m_user = new TreeMap<String, User>();
		m_category = new TreeMap<Integer, Category>();
		m_sales = new TreeMap<Integer, GarageSale>();

		m_ratings = new LinkedList<GarageSaleRank>();
		m_ratingsByUser = new TreeMap<User, LinkedList<GarageSaleRank>>();
		m_ratingsBySale = new TreeMap<GarageSale, LinkedList<GarageSaleRank>>();
	}

	/**
	 * This method creates a bunch of fake 
	 * objects so we can test our code
	 * @throws StorageException This exception is thrown if 
	 * any of the Storage classes encounter a problem
	 */
	public void connect()
		throws StorageException
	{
		m_sequence = new SequenceSet();

		// Register the sequences we'll use later, to ensure it doesn't conflict with anything else
		m_sequence.register("category_id");
		m_sequence.register("sale_id");

		// Insert some test data
		try {
			User defOwner = new User("ownr");
			defOwner.first_name("System");
			defOwner.last_name("Owner");
			store(defOwner);
			store(new User("abcd"));
			store(new User("test"));
			store(new User("blah"));
			store(new User("user"));

			Category catElec = new Category("Electronics");
			Category catGift = new Category("Gifts");
			Category catCndy = new Category("Candy");
			store(catElec);
			store(catGift);
			store(catCndy);
			store(new Category("Books"));
			store(new Category("Toys"));
			store(new Category("Antiques and Collectibles"));

			GarageSale defSale = new GarageSale(defOwner);
			defSale.address("100 Nowhere St");
			defSale.location(new GeoPosition());
			defSale.addCategory(catElec);
			store(defSale);
			defSale.addCategory(catGift);
			defSale.addCategory(catCndy);

			GarageSale other = new GarageSale(defOwner);
			other.address("127 Fake St");
			other.location(new GeoPosition());
			defSale.addCategory(catElec);
			store(other);
		} catch (Exception e) {
			System.err.println("Exception thrown: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void disconnect()
		throws StorageException
	{
	}

	/**
	 * This method checks to see if 
	 * our collection object is empty
	 * @returns boolean Whether or not the 
	 * collection object was empty
	 */
	public boolean isEmpty() {
		return false;
	}
	
	/**
	 * This method checks to see if the
	 * collection object is full
	 * @returns boolean Whether or not the 
	 * collection was full
	 */
	public boolean isFull() {
		return false; // can never be full, until we run out of memory
	}
	/**
	 * This method adds up the sizes of all
	 * of the categories and returns an int
	 * @returns The sum of the categories sizes
	 */
	public int size() {
		return (
			m_user.size() +
			m_category.size() +
			m_sales.size() +
			m_ratings.size()
		);
	}
	
	public long length() {
		return 0;
	}
	/**
	 * This method looks for a user object
	 * @throws StorageNotFoundException This exception
	 * is thrown if the userid entered does not exist
	 * @param userid The id of the user we are looking for
	 * @returns User The user we are looking for
	 */
	public User findUser(String userid)
		throws StorageNotFoundException
	{
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException(userid, "users");

		return m_user.get(userid);
	}
	/**
	 * This method checks to see if a given
	 * userid has been taken yet
	 * @param userid The id we are checking
	 * @returns boolean Whether or not the 
	 * userid entered exists yet in storage
	 */
	public boolean existsUser(String userid) {
		return m_user.containsKey(userid);
	}
	/**
	 * This method returns all of the users currently
	 * in storage in a Collection object
	 * @returns Collection A Collection object containing
	 * all of the users currently in storage
	 */
	public Collection<User> listUsers() {
		return m_user.values();
	}
	/**
	 * This method adds a user
	 * @param The user object we want to add
	 * @throws StorageKeyException This exception is thrown
	 * if the user object shares an id with an existing one
	 */
	public void store(User user)
		throws StorageKeyException
	{
		String userid = user.id();
		if (m_user.containsKey(userid))
			throw new StorageKeyException("User with id '" + userid + "' already exists!");

		m_user.put(userid, user);
	}
	
	/**
	 * This method removes a user
	 * @throws StorageNotFoundException This exception is
	 * thrown if the user entered doesn't exist
	 * @param user The user we want to remove
	 */
	public void delete(User user)
		throws StorageNotFoundException
	{
		String userid = user.id();
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException("users", userid);
		m_user.remove(userid);
	}
	
	/**
	 * This method returns a category
	 * with the given id
	 * @throws StorageNotFoundException If no Category with 
	 * the given id exists this exception is thrown
	 * @param categoryid The id we are searching for
	 * @return Category The Category we are searching for
	 */
	public Category findCategory(int categoryid)
		throws StorageNotFoundException
	{
		if (!m_category.containsKey(categoryid))
			throw new StorageNotFoundException("categories", categoryid);
		return m_category.get(categoryid);
	}
	/**
	 * This method checks to see if a 
	 * Category with the given id exists
	 * @returns boolean Whether or not 
	 * a Category with the given id exists
	 */
	public boolean existsCategory(int categoryid) {
		return m_category.containsKey(categoryid);
	}
	/**
	 * This method returns all of the 
	 * categories currently in storage
	 * @returns Collection All of the categories
	 */
	public Collection<Category> listCategories() {
		return m_category.values();
	}
	/**
	 * This method adds a category
	 * @throws StorageFullException This exception is 
	 * thrown if there isn't room for another category
	 * @throws StorageKeyException This exception is
	 * thrown if the category's id is taken
	 */
	public void store(Category category)
		throws StorageFullException, StorageKeyException
	{
		int categoryid = category.id();

		// We're trying to store one that doesn't yet have an id, generate one
		if (categoryid == -1) {
			categoryid = m_sequence.nextval("category_id");
			category.id(categoryid);
		}

		/* The categoryid already exists - this shouldn't happen since the sequence generator will
		 * guarantee uniqueness
		 */
		if (m_category.containsKey(categoryid)) {
			throw new StorageKeyException("Category with id '" + categoryid + "' already exists! " +
					"This is a bug; please file a report.");
		}

		m_category.put(categoryid, category);
	}
	
	/**
	 * This method deletes a category
	 * @throws StorageNotFoundException If the category 
	 * object doesn't exist, this exception is thrown
	 * @param category The category we want to remove
	 */
	public void delete(Category category)
		throws StorageNotFoundException
	{
		int categoryid = category.id();

		if (!m_category.containsKey(categoryid))
			throw new StorageNotFoundException("categories", categoryid);

		m_category.remove(categoryid);
	}

	/**
	 * This method adds a GarageSale object
	 * @throws StorageFullException If the storage 
	 * is full this exception is thrown
	 * @throws StorageKeyException If the entered
	 * GarageSale object shares an id with an existing 
	 * GarageSale object this exception is thrown
	 * @param sale The object we want to add
	 */
	public void store(GarageSale sale)
		throws StorageFullException, StorageKeyException
	{
		int saleid = sale.id();

		// We're trying to store one that doesn't yet have an id, generate one
		if (saleid == -1) {
			saleid = m_sequence.nextval("category_id");
			sale.id(saleid);
		}

		/* The saleid already exists - this shouldn't happen since the sequence generator will
		 * guarantee uniqueness
		 */
		if (m_sales.containsKey(saleid)) {
			throw new StorageKeyException("Sale with id '" + saleid + "' already exists! " +
				"This is a bug; please file a report.");
		}

		m_sales.put(saleid, sale);
	}
	/**
	 * This method removes a GarageSale 
	 * @throws StorageNotFoundException This is thrown
	 * if the GarageSale object entered doesn't exist
	 * @param sale The sale we want to remove
	 */
	public void delete(GarageSale sale)
		throws StorageNotFoundException
	{
		int saleid = sale.id();

		if (!m_sales.containsKey(saleid))
			throw new StorageNotFoundException("sales", saleid);

		m_sales.remove(saleid);
	}
	/**
	 * This method loops through all of the existing
	 * GarageSales looking for whether or not a GarageSale
	 * object with the entered id exists
	 * @param saleid The id we're searching for
	 * @returns boolean Whether or not the id exists
	 */
	public boolean existsSale(int saleid) {
		return m_sales.containsKey(saleid);
	}
	/**
	 * This Accessor method returns all of
	 * the GarageSales currently being stored
	 * @throws StorageEmptyException This is thrown 
	 * if there are no GarageSales in the Collection
	 * @returns m_sales.values A collection object that
	 * contains all of the GarageSales
	 */
	public Collection<GarageSale> listGarageSales()
		throws StorageEmptyException
	{
		return m_sales.values();
	}
	
	/**
	 * This method loops through all of the GarageSales
	 * looking for the one that matches the id entered
	 * @throws StorageNotFoundException If the saleid is 
	 * invalid this exception is thrown
	 * @param saleid The id of the GarageSale 
	 * object we're looking for
	 * @returns GarageSale The GarageSale we're looking for
	 */
	public GarageSale findGarageSale(int saleid)
		throws StorageNotFoundException
	{
		if (!m_sales.containsKey(saleid))
			throw new StorageNotFoundException("sales", saleid);
		return m_sales.get(saleid);
	}

	/**
	 * This method deletes a specifc 
	 * GarageSaleRank from a GarageSale
	 * @param gsr The rank we want to remove
	 */
	public void delete(GarageSaleRank gsr)
	{
		m_ratings.remove(gsr);
	}
	
	/**
	 * This method loops through all of the user's 
	 * ratings for their GarageSales and deletes them
	 * @param user The user we want to delete the ratings of
	 */
	public void deleteRatings(User user)
	{
		Iterator<GarageSaleRank> iter = m_ratingsByUser.get(user).iterator();
		while (iter.hasNext())
		{
			delete(iter.next());
		}
		m_ratingsByUser.remove(user);
	}
	
	/**
	 * This method loops through all of the ratings of a given
	 * GarageSale and deletes all of the entries
	 * @param sale The GarageSale we want to wipe the ratings of
	 */
	public void deleteRatings(GarageSale sale)
	{
		Iterator<GarageSaleRank> iter = m_ratingsBySale.get(sale).iterator();
		while (iter.hasNext())
		{
			delete(iter.next());
		}
		m_ratingsBySale.remove(sale);
	}
	/**
	 * This method searches through the Categories and 
	 * returns an int (the id) of the Category object
	 * @param name The name of the category whose id we're looking for
	 * @returns int The id of the category object
	 */
	public int getCategoryIdByName(String name)
	{
		Iterator<Category> iter = m_category.values().iterator();
		while (iter.hasNext())
		{
			Category current = iter.next();
			if (current.name().equals(name))
			{
				return current.id();
			}
		}
		return -1;
	}

	/**
	 * This method gets a GarageSale's rank, but it 
	 * only goes through a specific user's GarageSales
	 * @param user The user who owns the GarageSale
	 * @param sale The sale we want the rank of
	 * @returns rank The tank we're looking for
	 */
	public GarageSaleRank getRating(User user, GarageSale sale)
	{
		LinkedList<GarageSaleRank> ratings = m_ratingsByUser.get(user);
		Iterator<GarageSaleRank> iter = ratings.iterator();

		GarageSaleRank rank = null;
		while (iter.hasNext()) {
			GarageSaleRank currentRank = iter.next();
			// If it equals the sale we're looking for, return it and quit loop
			if (currentRank.garageSale().equals(sale)) {
				rank = currentRank;
				break;
			}
		}

		// If the rating wasn't found, create one and save it
		if (rank == null) {
			rank = new GarageSaleRank(sale, user);
			m_ratings.add(rank);
			m_ratingsByUser.get(user).add(rank);
			m_ratingsBySale.get(sale).add(rank);
		}

		return rank;
	}
	/**
	 * This method gets the average rank of the GarageSale object
	 * @param sale The object we want the rank of
	 * @returns float A float of the rating after the average has been computed
	 */
	public float getAverageRating(GarageSale sale)
	{
		LinkedList<GarageSaleRank> ratings = m_ratingsBySale.get(sale);
		int sum = 0;

		Iterator<GarageSaleRank> iter = ratings.iterator();
		while (iter.hasNext()) {
			sum += iter.next().rank();
		}

		// Average them and return the result
		return ((float) sum / ratings.size());
	}
	
	/**
	 * This method clears the MockStorage object
	 */
	public void clear() {
		m_user.clear();
		m_category.clear();
		m_sales.clear();
		if (m_sales.isEmpty())
			System.err.println("OK");
		m_ratings.clear();
		m_ratingsByUser.clear();
		m_ratingsBySale.clear();

		// reset the sequences
		m_sequence = null;
		m_sequence = new SequenceSet();
		// Register the sequences we'll use later, to ensure it doesn't conflict with anything else
		m_sequence.register("category_id");
		m_sequence.register("sale_id");
	}
}

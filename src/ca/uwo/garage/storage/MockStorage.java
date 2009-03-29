package ca.uwo.garage.storage;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import ca.uwo.garage.storage.GeoPosition;

/* The Mock storage class is a mock-up of a Storage implementation, useful for
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

	public MockStorage() {
		// Instantiate all of the data structures
		m_user = new TreeMap<String, User>();
		m_category = new TreeMap<Integer, Category>();
		m_sales = new TreeMap<Integer, GarageSale>();

		m_ratings = new LinkedList<GarageSaleRank>();
		m_ratingsByUser = new TreeMap<User, LinkedList<GarageSaleRank>>();
		m_ratingsBySale = new TreeMap<GarageSale, LinkedList<GarageSaleRank>>();
	}

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

	public boolean isEmpty() {
		return false;
	}
	public boolean isFull() {
		return false; // can never be full, until we run out of memory
	}
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
	public User findUser(String userid)
		throws StorageNotFoundException
	{
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException(userid, "users");

		return m_user.get(userid);
	}
	public boolean existsUser(String userid) {
		return m_user.containsKey(userid);
	}
	public Collection<User> listUsers() {
		return m_user.values();
	}
	public void store(User user)
		throws StorageKeyException
	{
		String userid = user.id();
		if (m_user.containsKey(userid))
			throw new StorageKeyException("User with id '" + userid + "' already exists!");

		m_user.put(userid, user);
	}
	public void delete(User user)
		throws StorageNotFoundException
	{
		String userid = user.id();
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException("users", userid);
		m_user.remove(userid);
	}
	public Category findCategory(int categoryid)
		throws StorageNotFoundException
	{
		if (!m_category.containsKey(categoryid))
			throw new StorageNotFoundException("categories", categoryid);
		return m_category.get(categoryid);
	}
	public boolean existsCategory(int categoryid) {
		return m_category.containsKey(categoryid);
	}
	public Collection<Category> listCategories() {
		return m_category.values();
	}
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
	public void delete(Category category)
		throws StorageNotFoundException
	{
		int categoryid = category.id();

		if (!m_category.containsKey(categoryid))
			throw new StorageNotFoundException("categories", categoryid);

		m_category.remove(categoryid);
	}

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
	public void delete(GarageSale sale)
		throws StorageNotFoundException
	{
		int saleid = sale.id();

		if (!m_sales.containsKey(saleid))
			throw new StorageNotFoundException("sales", saleid);

		m_sales.remove(saleid);
	}
	public boolean existsSale(int saleid) {
		return m_sales.containsKey(saleid);
	}
	public Collection<GarageSale> listGarageSales()
		throws StorageEmptyException
	{
		return m_sales.values();
	}
	public GarageSale findGarageSale(int saleid)
		throws StorageNotFoundException
	{
		if (!m_sales.containsKey(saleid))
			throw new StorageNotFoundException("sales", saleid);
		return m_sales.get(saleid);
	}

	public void delete(GarageSaleRank gsr)
	{
		m_ratings.remove(gsr);
	}
	public void deleteRatings(User user)
	{
		Iterator<GarageSaleRank> iter = m_ratingsByUser.get(user).iterator();
		while (iter.hasNext())
		{
			delete(iter.next());
		}
		m_ratingsByUser.remove(user);
	}
	public void deleteRatings(GarageSale sale)
	{
		Iterator<GarageSaleRank> iter = m_ratingsBySale.get(sale).iterator();
		while (iter.hasNext())
		{
			delete(iter.next());
		}
		m_ratingsBySale.remove(sale);
	}
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
}

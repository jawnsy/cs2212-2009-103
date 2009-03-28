package ca.uwo.garage.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

/* The Serialized storage mechanism simply flattens data structures into the standard
 * Java Serialization format. This has a few drawbacks, though; namely, for large data
 * sets, memory usage can get pretty bad. There is also no locking, so concurrency
 * might be a nightmare.
 * 
 * In short: this is only suitable for simple storage of a few objects; for larger data
 * sets, this module will cause performance problems. In that case, you will need to use
 * a full Database interface (ie, SQLite or a real RDBMS)
 */
public class SerializedStorage
	implements Storage
{
	private static final long serialVersionUID = 1L;
	private static final String DATABASE = "garage.dat";

	// this is the TreeMap that contains our objects
	private transient TreeMap<String, User> m_user;
	private transient TreeMap<Integer, Category> m_category;
	private transient TreeMap<Integer, GarageSale> m_sales;
	private transient LinkedList<GarageSaleRank> m_ratings;
	private transient TreeMap<User, LinkedList<GarageSaleRank>> m_ratingsByUser;
	private transient TreeMap<GarageSale, LinkedList<GarageSaleRank>> m_ratingsBySale;
	private transient SequenceSet m_sequence;

	/**
	 * This constructs a new SerializedStorage container.
	 */
	public SerializedStorage() {
		// Instantiate all of the data structures
		m_user = new TreeMap<String, User>();
		m_category = new TreeMap<Integer, Category>();
		m_sales = new TreeMap<Integer, GarageSale>();

		m_ratings = new LinkedList<GarageSaleRank>();
		m_ratingsByUser = new TreeMap<User, LinkedList<GarageSaleRank>>();
		m_ratingsBySale = new TreeMap<GarageSale, LinkedList<GarageSaleRank>>();
	}

	/**
	 * This connects to the file database and loads its contents into memory. If the
	 * file does not exist, then it will create it and set its permissions to read/write.
	 * @throws StorageException if there is an error reading the database
	 */
	public void connect()
		throws StorageException
	{
		// Open up the file for reading
		FileInputStream in = null;
		try {
			// Check if the DATABASE file exists, and try to create it otherwise
			File file = new File(DATABASE);

			// true if the file is created, false if it exists
			if (file.createNewFile()) {
				System.out.println("Info: database file '" + DATABASE + "' does not exist; creating");				
				file.setWritable(true);
				file.setReadable(true);
			}

			in = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			// shouldn't get here, the database is created automatically
			System.err.println("A strange error occurred. Please send a bug report.");
			e.printStackTrace();
			System.exit(1);
		}
		catch (IOException e) {
			throw new StorageException("Could not create or read database file '" + DATABASE + "'");
		}

		// Actually do the file reading work here
		try {
			ObjectInputStream reader = new ObjectInputStream(in);
			// Populate the TreeMaps
			load(reader);
		}
		catch (IOException e) {
			throw new StorageException("Problem reading from database file '" + DATABASE + "'");
		}

		// If the Sequence generator did not get loaded from disk, then create one
		if (m_sequence == null)
			m_sequence = new SequenceSet();

		// Register the sequences we'll use later, to ensure it doesn't conflict with anything else
		m_sequence.register("category_id");
		m_sequence.register("sale_id");
	}

	/**
	 * This forces the Storage class to immediately flush its data to disk.
	 */
	public void disconnect()
		throws StorageException
	{
		// Open up the file for reading
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(DATABASE);
		}
		catch (FileNotFoundException e) {
			// shouldn't get here, the database is created automatically
			System.err.println("A strange error occurred. Please send a bug report.");
			e.printStackTrace();
			System.exit(1);
		}

		// Actually do the file writing work here
		try {
			ObjectOutputStream writer = new ObjectOutputStream(out);
			save(writer);
			writer.flush();
		}
		catch (IOException e) {
			throw new StorageException("Problem writing to database file '" + DATABASE + "'");
		}
	}

	/**
	 * This destructor automatically cleans up after the Storage. If there is a problem writing
	 * the database to disk, it simply returns an error and complete stack trace before being
	 * destroyed.
	 */
	public void finalize() {
		try {
			disconnect();
		}
		catch (Exception e) {
			System.err.println("Error: Failed writing database to disk, data may be lost");
			e.printStackTrace();
		}
	}

	// This group saves data OUT of our memory database
	private void save(ObjectOutputStream out)
		throws IOException
	{
		out.writeObject(m_sequence);
		save(out, m_user.values());
		save(out, m_category.values());
		save(out, m_sales.values());
		save(out, m_ratings);
	}
	private void save(ObjectOutputStream out, LinkedList<GarageSaleRank> list)
		throws IOException
	{
		// Write out the size of the block
		out.writeInt(list.size());

		// Dump everything in the Collection by iterating
		Iterator<GarageSaleRank> iter = list.iterator();
		while (iter.hasNext()) {
			GarageSaleRank gsr = iter.next();
			// Write out the GarageSale ID, UserID and Rank
			out.writeInt(gsr.garageSale().id());
			out.writeObject(gsr.user().id());
			out.writeInt(gsr.rank());
		}
	}
	private void save(ObjectOutputStream out, Collection<?> list)
		throws IOException
	{
		// Write out the size of the block
		out.writeInt(list.size());

		// Dump everything in the Collection by iterating
		Iterator<?> iter = list.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			out.writeObject(obj);
		}
	}

	// This group loads data IN to our memory database
	private void load(ObjectInputStream in)
		throws IOException
	{
		try {
			// Read the SequenceSet
			m_sequence = (SequenceSet) in.readObject();

			// Read the User objects
			int size = in.readInt();
			for (int i = 0; i < size; i++) {
				User user = (User) in.readObject();
				m_user.put(user.id(), user);
			}

			// Read the Category objects
			size = in.readInt();
			for (int i = 0; i < size; i++) {
				Category category = (Category) in.readObject();
				m_category.put(category.id(), category);
			}

			// Read the GarageSale objects
			size = in.readInt();
			for (int i = 0; i < size; i++) {
				GarageSale sale = (GarageSale) in.readObject();
				m_sales.put(sale.id(), sale);
			}

			// Read the GarageSale ratings
			size = in.readInt();
			for (int i = 0; i < size; i++) {
				int saleid = in.readInt();
				String userid = (String)in.readObject();
				int rank = in.readInt();

				// This reduces memory usage since references will be kept
				GarageSaleRank gsr;
				GarageSale sale = null;
				User user = null;

				try {
					sale = findGarageSale(saleid);
				} catch (StorageNotFoundException e) {
					System.err.println("Error: invalid garagesale id in database - " + saleid);
					e.printStackTrace();
					System.exit(1);
				}

				try {
					user = findUser(userid);
				} catch (StorageNotFoundException e) {
					System.err.println("Error: invalid user id in database - " + userid);
					e.printStackTrace();
					System.exit(1);
				}

				gsr = new GarageSaleRank(sale, user);
				try {
					gsr.rank(rank);
				} catch (GarageSaleRankInvalidException e) {
					System.err.println("Error: invalid rank saved to database - " + rank);
					e.printStackTrace();
					System.exit(1);
				}

				// restore the rank
				m_ratings.add(gsr);
				m_ratingsByUser.get(user).add(gsr);
				m_ratingsBySale.get(sale).add(gsr);
			}
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error: Serialized Java class cannot be restored because of missing class");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * If  the Storage class does not contain anything in its containers, this will be true.
	 * 
	 * @return true if the Storage class is empty; false otherwise
	 */
	public boolean isEmpty() {
		return (
			m_user.isEmpty()     &&
			m_category.isEmpty() &&
			m_sales.isEmpty()    &&
			m_ratings.isEmpty()  &&
			m_sequence == null
		);
	}

	/**
	 * If the Storage class can no longer store anything new (ie, out of memory), then
	 * this will be true. It is advisable that, when this condition occurs, a user is
	 * informed of this condition and the program exits.
	 * 
	 * @return true if the Storage class is at capacity; false otherwise
	 */
	public boolean isFull() {
		return false; // can never be full, until we run out of memory
	}
	
	/**
	 * Return the total number of entries held by this Storage class
	 * 
	 * @return an int describing the number of items held
	 */
	public int size() {
		return (
			m_user.size() +
			m_category.size() +
			m_sales.size() +
			m_ratings.size()
		);
	}

	/**
	 * @return the size of the database in bytes
	 */
	public long length() {
		File file = new File(DATABASE);
		return file.length();
	}

	/**
	 * This method searches for a user based on their userid, and returns it
	 * if they exist.
	 * 
	 * @param userid The user's unique id
	 * @throws StorageNotFoundException if there is no such userid
	 * @return a User object
	 */
	public User findUser(String userid)
		throws StorageNotFoundException
	{
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException(userid, "users");

		return m_user.get(userid);
	}

	/**
	 * Check if a User exists based on their userid.
	 * 
	 * @param userid The user's unique id
	 * @return true if the userid exists, false otherwise
	 */
	public boolean existsUser(String userid) {
		return m_user.containsKey(userid);
	}

	/**
	 * Get a list of all users. It is a Collection that may vary based on the
	 * storage container.
	 * 
	 * @return A Collection containing a list of User objects
	 */
	public Collection<User> listUsers() {
		return m_user.values();
	}

	/**
	 * Stores a given User object.
	 * 
	 * @throws StorageKeyException if the userid already exists
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
	 * Deletes a given User object.
	 * 
	 * @throws StorageNotFoundException if the User cannot be found in the list
	 */
	public void delete(User user)
		throws StorageNotFoundException
	{
		String userid = user.id();
		if (!m_user.containsKey(userid))
			throw new StorageNotFoundException("users", userid);
		m_user.remove(userid);
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

	/**
	 * Finds a given Category by its integer id and returns it.
	 * 
	 * @return Category object instance
	 * @throws StorageNotFoundException if the category_id cannot be found
	 */
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

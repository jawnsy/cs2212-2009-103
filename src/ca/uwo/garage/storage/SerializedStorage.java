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
	private transient SequenceSet m_sequence;

	public SerializedStorage() {
		// Instantiate all of the data structures
		m_user = new TreeMap<String, User>();
		m_category = new TreeMap<Integer, Category>();
	}

	// this reads everything into our cache
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

		m_sequence.register("category_id");
	}

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
		}
		catch (IOException e) {
			throw new StorageException("Problem writing to database file '" + DATABASE + "'");
		}
	}

	// save everything when we destroy the object
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
		save(out, m_user.values());
		save(out, m_category.values());
		out.writeObject(m_sequence);
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

			// Read the SequenceSet
			m_sequence = (SequenceSet) in.readObject();
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error: Serialized Java class cannot be restored because of missing class");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public boolean isEmpty() {
		return (
			m_user.isEmpty() &&
			m_category.isEmpty() &&
			m_sequence == null
		);
	}
	public boolean isFull() {
		return false; // can never be full, until we run out of memory
	}
	public int size() {
		return (
			m_user.size() +
			m_category.size()
		);
	}
	public long length() {
		File file = new File(DATABASE);
		return file.length();
	}

	// Stuff to do with User objects
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
		throws StorageFullException, StorageKeyException
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

	// Stuff to do with Category objects
	public Category findCategory(int categoryid)
		throws StorageNotFoundException
	{
		if (!m_category.containsKey(categoryid))
			throw new StorageNotFoundException("categories", categoryid);
		return m_category.get(categoryid);
	}
	public boolean existsCategory(int categoryid) {
		return m_user.containsKey(categoryid);
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
}

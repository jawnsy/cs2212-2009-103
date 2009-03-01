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

	public SerializedStorage() {
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

		// Create a new TreeMap instance
		m_user = new TreeMap<String, User>();

		// Actually do the file reading work here
		try {
			ObjectInputStream reader = new ObjectInputStream(in);
			// While our stream has some stuff available for us to read
			load(reader);
		}
		catch (IOException e) {
			throw new StorageException("Problem reading from database file '" + DATABASE + "'");
		}
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
		save(out, m_user.values().iterator());
	}
	private void save(ObjectOutputStream out, Iterator<?> list)
		throws IOException
	{
		while (list.hasNext()) {
			Object obj = list.next();
			out.writeObject(obj);
		}
	}

	// This group loads data IN to our memory database
	private void load(ObjectInputStream in)
		throws IOException
	{
		try {
			while (in.available() > 0) {
				Object obj = in.readObject();
				load(obj);
			}
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error: Serialized Java class cannot be restored because of missing class");
			e.printStackTrace();
			System.exit(1);
		}
	}
	private void load(Object obj) {
		// If we have a User object, then store it in our Cache
		if (obj instanceof User)
			load((User) obj);
	}
	private void load(User user) {
		m_user.put(user.userid(), user);		
	}

	public boolean isEmpty() {
		return (m_user.isEmpty());
	}
	public boolean isFull() {
		return false; // can never be full, until we run out of memory
	}
	public int size() {
		return (m_user.size());
	}
	public long length() {
		File file = new File(DATABASE);
		return file.length();
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
	public void store(User user) {
		
	}
	public void delete(User user) {
		
	}
}

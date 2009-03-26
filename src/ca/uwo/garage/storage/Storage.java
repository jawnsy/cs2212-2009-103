package ca.uwo.garage.storage;
import java.util.Collection;

public interface Storage
{
	/**
	 * If the Storage medium is empty, then return true.
	 * 
	 * @return true if the Storage is empty
	 */
	public abstract boolean isEmpty();

	/**
	 * If the Storage medium is at its capacity, then return true.
	 * 
	 * @return true if the Storage is full
	 */
	public abstract boolean isFull();

	/**
	 * @return The number of objects in our storage
	 */
	public abstract int size();

	/**
	 * This provides some Storage-specific notion of its length.
	 * It could be, for example, the size of the storage on disk,
	 * but that may not be the case.
	 * 
	 * @return a Storage implementation-specific length
	 */
	public abstract long length();

	/**
	 * This initiates a connection to the backing database.
	 * 
	 * @throws StorageException if there is an error connecting
	 */
	public abstract void connect()
		throws StorageException;

	/**
	 * This closes the connection to the database when done, saving any
	 * data if necessary.
	 * 
	 * @throws StorageException if there is an failure
	 */
	public abstract void disconnect()
		throws StorageException;

	/**
	 * This tries to find a user given their unique userid string.
	 * 
	 * @param userid The User's userid string
	 * @return a User object with a matching userid
	 * @throws StorageNotFoundException if the specific userid cannot be found
	 */
	public abstract User findUser(String userid)
		throws StorageNotFoundException;
	public abstract Collection<User> listUsers()
		throws StorageEmptyException;
	public abstract void store(User user)
		throws StorageFullException, StorageKeyException;
	public abstract void delete(User user)
		throws StorageNotFoundException;
	public abstract boolean existsUser(String userid);

	public abstract Category findCategory(int categoryid)
		throws StorageNotFoundException;
	public abstract Collection<Category> listCategories()
		throws StorageEmptyException;
	public abstract void store(Category category)
		throws StorageFullException, StorageKeyException;
	public abstract void delete(Category user)
		throws StorageNotFoundException;
	public abstract boolean existsCategory(int categoryid);

	public abstract GarageSale findGarageSale(int saleid)
		throws StorageNotFoundException;
	public abstract Collection<GarageSale> listGarageSales()
		throws StorageEmptyException;
	public abstract void store(GarageSale sale)
		throws StorageFullException, StorageKeyException;
	public abstract void delete(GarageSale sale)
		throws StorageNotFoundException;
	public abstract boolean existsSale(int saleid);

	public abstract float getAverageRating(GarageSale sale);
	public abstract GarageSaleRank getRating(User user, GarageSale sale);
}

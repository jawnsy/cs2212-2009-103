package ca.uwo.garage.storage;
import java.util.Collection;

public interface Storage
{
	public abstract boolean isEmpty();
	public abstract boolean isFull();

	public abstract int size();
	public abstract long length();

	public abstract void connect()
		throws StorageException;
	public abstract void disconnect()
		throws StorageException;

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
}

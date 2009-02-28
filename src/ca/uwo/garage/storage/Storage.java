package ca.uwo.garage.storage;
import java.util.Collection;

public interface Storage
{
	public abstract boolean isEmpty();
	public abstract boolean isFull();

	public abstract int size();
	public abstract int length();

	public abstract User findUser(String userid)
		throws StorageNotFoundException;
	public abstract Collection<User> listUsers()
		throws StorageEmptyException;
	public abstract void store(User user)
		throws StorageFullException;
	public abstract void delete(User user)
		throws StorageNotFoundException;
	public abstract boolean existsUser(String userid);
}

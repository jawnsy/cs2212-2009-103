package ca.uwo.garage.storage;
import java.util.Collection;

public interface Storage
{
	public abstract boolean isEmpty();
	public abstract boolean isFull();

	public abstract int size();
	public abstract int length();

	public abstract User findUser(String userid);
	public abstract Collection<User> listUsers();
	public abstract void store(User user);
	public abstract void delete(User user);
	public abstract boolean existsUser(String userid);
}

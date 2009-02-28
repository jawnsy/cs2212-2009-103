package ca.uwo.garage.storage;

import java.util.Collection;

public class SerializedStorage
	implements Storage
{
	public boolean isEmpty() {
		return false;
	}
	public boolean isFull() {
		return false;
	}
	public int size() {
		return 0;
	}
	public int length() {
		return 0;
	}

	public User findUser(String userid) {
		return null;
	}
	public boolean existsUser(String userid) {
		return false;
	}
	public Collection<User> listUsers() {
		return null;
	}
	public void store(User user) {
		
	}
	public void delete(User user) {
		
	}
}

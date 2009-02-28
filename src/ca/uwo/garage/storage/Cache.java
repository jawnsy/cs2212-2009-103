package ca.uwo.garage.storage;

import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class Cache
	implements Storage
{
	private Storage m_storage; // our backend storage
	private TreeMap<String, CacheObject> m_cached; // String key; CacheObject values
	private int m_hits, m_misses; // cache hits & misses
	private final int EXPIRE = 10*60*1000; // only keep data for 10 minutes
	private Timer m_cleaner;

	public Cache(Storage stor) {
		m_storage = stor;

		// Create a new Daemon thread (ie, does not prolong application run time)
		m_cleaner = new Timer(true);
		m_cleaner.schedule(new ExpireTask(), 0, 60); // start immediately, repeat every 60 seconds
	}

	public void delete(User user) {
		if (m_cached.containsKey(user.userid()))
			m_cached.remove(user.userid());

		m_storage.delete(user);
	}

	public boolean existsUser(String userid) {
		// Consult our cache first
		if (m_cached.containsKey(userid)) {
			m_hits++;
			return true;
		}

		// Otherwise it's a cache miss, so check our backend
		m_misses++;
		return m_storage.existsUser(userid);
	}

	public User findUser(String userid) {
		if (m_cached.containsKey(userid)) {
			m_hits++;
			return (User) m_cached.get(userid).object();
		}

		User search = m_storage.findUser(userid);
		m_cached.put(userid, new CacheObject(search));

		// Otherwise, it's a cache miss, so check our backend
		return null;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public int length() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Collection<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void store(User user) {
		// TODO Auto-generated method stub
		
	}

	private class ExpireTask
		extends TimerTask
	{
		public void run() {
			Iterator<String> iter = m_cached.keySet().iterator();
			// loop through each key in our cache
			while (iter.hasNext()) {
				String key = iter.next();
	
				// remove object from cache if it's expired
				CacheObject obj = m_cached.get(key);
				if (obj.lastUsedAgoMillis() > EXPIRE)
					m_cached.remove(key);
			}
		}
	}
}

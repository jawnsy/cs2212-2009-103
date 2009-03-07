package ca.uwo.cache;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import ca.uwo.garage.storage.Storage;

public class Cache<T>
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final int EXPIRE = 20*60*1000; // only keep data for 20 minutes
	private transient Timer m_cleaner; // garbage collection thread
	
	// User cache information
	private TreeMap<String, CacheObject<T>> m_cache; // String key; CacheObject values
	private transient int m_hits, m_misses; // cache hits & misses

	public Cache() {
		// Create a new Daemon thread (ie, does not prolong application run time)
		m_cleaner = new Timer(true);
		m_cleaner.schedule(new ExpireTask(), 0, 60); // start immediately, repeat every 60 seconds

		m_cache = new TreeMap<String, CacheObject<T>>();
		m_hits = m_misses = 0;
	}

	public void delete(String key) {
		if (!m_cache.containsKey(key))
			return;
		m_cache.remove(key);
	}

	public boolean exists(String key) {
		if (m_cache.containsKey(key)) {
			m_hits++;
			return true;
		}

		m_misses++;
		return false;
	}

	public T find(String key)
	{
		if (m_cache.containsKey(key)) {
			m_hits++;
			return m_cache.get(key).read();
		}

		m_misses++;
		return null;
	}

	public void store(String key, T obj)
	{
		// if we already have this object, remove it
		if (m_cache.containsKey(key))
			m_cache.remove(key);

		m_cache.put(key, new CacheObject<T>(obj));
	}

	public Set<String> keys() {
		return m_cache.keySet();
	}

	public boolean isEmpty() {
		return m_cache.isEmpty();
	}

	public int size() {
		return m_cache.size();
	}

	private class ExpireTask
		extends TimerTask
	{
		public void run() {
			Iterator<String> iter = m_cache.keySet().iterator();
			// loop through each key in our cache
			while (iter.hasNext()) {
				String key = iter.next();
	
				// remove object from cache if it's expired
				CacheObject<T> obj = m_cache.get(key);
				if (obj.lastUsedAgoMillis() > EXPIRE)
					m_cache.remove(key);
			}
		}
	}
}

package ca.uwo.garage.storage;

class CacheObject<T> { // is a package-visible class
	private T m_object;
	private long m_lastUsed; // last used time, in milliseconds

	public CacheObject(T obj) {
		m_object = obj;
	}

	public long lastUsedAgoMillis() {
		long now = System.currentTimeMillis();
		return (now - m_lastUsed);
	}
	public T read() {
		m_lastUsed = System.currentTimeMillis();
		return m_object;
	}
}

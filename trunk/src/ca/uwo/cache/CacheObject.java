package ca.uwo.cache;

class CacheObject<T> { // is a package-visible class
	private T m_object;
	private long m_lastUsed; // last used time, in milliseconds

	public CacheObject(T obj) {
		m_object = obj;
		m_lastUsed = System.currentTimeMillis();
	}

	public long lastUsedAgoMillis() {
		return (System.currentTimeMillis() - m_lastUsed);
	}
	public T read() {
		m_lastUsed = System.currentTimeMillis();
		return m_object;
	}
}

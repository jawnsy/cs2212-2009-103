package ca.uwo.garage.storage;

class CacheObject { // is a package-visible class
	private Object m_object;
	private long m_lastUsed; // last used time, in milliseconds

	public CacheObject(Object obj) {
		m_object = obj;
	}

	public long lastUsedAgoMillis() {
		long now = System.currentTimeMillis();
		return (now - m_lastUsed);
	}
	public Object object() {
		m_lastUsed = System.currentTimeMillis();
		return m_object;
	}
}

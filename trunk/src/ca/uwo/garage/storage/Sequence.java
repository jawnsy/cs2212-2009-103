package ca.uwo.garage.storage;

public class Sequence {
	private transient int m_value;
	private transient boolean m_registered;

	public Sequence() {
		this(0);
	}
	public int nextval() {
		m_value++;
		return m_value;
	}

	// package-scoped methods; needed for deserialization
	Sequence(int value) {
		m_value = value;
	}
	void register() {
		m_registered = true;
	}
	boolean registered() {
		return m_registered;
	}
	int value() {
		return m_value;
	}
}

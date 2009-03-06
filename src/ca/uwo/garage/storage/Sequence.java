package ca.uwo.garage.storage;

/**
 * The Sequence object provides a unique sequence of numbers by only permitting
 * external classes to perform an increment-and-return operation. In this way,
 * we can guarantee a unique id number is generated to keep track of objects
 * without other unique immutable keys.
 * 
 * This class is not itself Serializable; it is the responsibility of the SequenceSet
 * to take care of that.
 * 
 * @author Jonathan Yu
 * @version $Revision$
 */

public class Sequence
{
	private transient int m_value;
	private transient boolean m_registered;

	/**
	 * Create a new Sequence object with an initial value of 0.
	 */
	public Sequence() {
		this(0);
	}

	/**
	 * Get the next value in the sequence, which is guaranteed to be unique and
	 * larger than all previous values returned by this method.
	 * 
	 * @return an integer value
	 */
	public int nextval() {
		m_value++;
		return m_value;
	}

	// package-scoped methods; needed for deserialization
	/**
	 * PACKAGE-SCOPED METHOD
	 *
	 * This Sequence constructor is capable of setting an id. It is important that only
	 * other classes in this PACKAGE may access this constructor -- namely, the Storage
	 * class.
	 * 
	 * It assumes that the current value valid, and so does not do any error checking.
	 * 
	 * @param value The current value of this Sequence
	 */
	Sequence(int value) {
		m_value = value;
	}

	/**
	 * PACKAGE-SCOPED METHOD
	 * 
	 * This method simply sets the registered flag to true. It is important that only
	 * other classes in this PACKAGE may access this method -- namely, the Storage
	 * class.
	 */
	void register() {
		m_registered = true;
	}

	/**
	 * PACKAGE-SCOPED METHOD
	 * 
	 * This method returns whether or not the current Sequence has been previously
	 * register()'ed by one or more classes. It is important that each key only
	 * gets register()'ed once per session to ensure that the sequences do not
	 * conflict (ie, that each Sequence is used by one and only one class)
	 */
	boolean registered() {
		return m_registered;
	}

	/**
	 * PACKAGE-SCOPED METHOD
	 * 
	 * This method simply returns the current value of the sequence, suitable for
	 * use in deserialization by the Storage class.
	 */
	int value() {
		return m_value;
	}
}

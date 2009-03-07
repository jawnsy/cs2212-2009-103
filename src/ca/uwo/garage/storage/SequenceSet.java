package ca.uwo.garage.storage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeMap;

public class SequenceSet
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private transient TreeMap<String, Sequence> m_sequences;

	/**
	 * This creates a new SequenceSet, which is a set of numeric Sequence
	 * objects that are keyed by a unique id String. This replicates some of
	 * the basic ideas behind a Relational Database's Auto-incrementing
	 * Primary Key system or the sequence generator available in some dialects.
	 * 
	 * It is designed to provide the next number in a sequence suitable for use in
	 * generating a unique, non-reusable id, beginning at 0.
	 */
	public SequenceSet() {
		m_sequences = new TreeMap<String, Sequence>();
	}

	/**
	 * This registers a certain key. When an object wants to make use of a given
	 * sequence name, it must first register the name of the sequence using this
	 * method.
	 * 
	 * If a sequence name is registered more than once, then a warning is printed
	 * to the Standard Error socket -- while not a fatal error, only one part of
	 * the code should be using each sequence name. This registration therefore
	 * prevents strange action-at-a-distance type bugs.
	 * 
	 * @param key The sequence name (key string)
	 */
	public void register(String key) {
		Sequence seq = null;

		/* If the sequence is already loaded, grab it and check if it has been
		 * already registered; emit a warning to STDERR if it has
		 */
		if (m_sequences.containsKey(key)) {
			seq = m_sequences.get(key);
			if (seq.registered())
				System.err.println("Warning: sequence key '" + key + "' registered more than once");
		}
		// create the sequence and save it otherwise
		else {
			seq = new Sequence();
			m_sequences.put(key, seq);
		}

		// seq is guaranteed to exist now; register it
		seq.register();
	}

	/**
	 * Gets the next unique value in the sequence. Sequence numbers begin at 0 and are
	 * incremented every time nextval() is called. Sequence numbers are guaranteed to
	 * be unique and will never be reused.
	 * 
	 * @param key The sequence's unique key name
	 * @return The next value in the sequence
	 */
	public int nextval(String key) {
		Sequence seq = null;

		// If the sequence exists, get it; otherwise, create it with a value 0
		if (m_sequences.containsKey(key))
			seq = m_sequences.get(key);
		else {
			seq = new Sequence();
			m_sequences.put(key, seq);
		}

		if (!seq.registered())
			System.err.println("Warning: sequence key '" + key + "' being used without registration");

		// increment the number, then return it; the sequence begins at '1'
		return seq.nextval();
	}

	// Internal code for storing and restoring the object
	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		// write our element count so we know when reading
		out.writeInt(m_sequences.size());

		// Simply write each name-sequence tuple
		Iterator<String> iter = m_sequences.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			out.writeObject(key);
			out.writeInt(m_sequences.get(key).value());
		}
	}
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		int size = in.readInt();

		// Read back the name-sequence tuples
		for (int i = 0; i < size; i++) {
			String key = (String)in.readObject();
			Sequence seq = new Sequence(in.readInt());
			m_sequences.put(key, seq);
		}
	}

}

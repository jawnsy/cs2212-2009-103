package ca.uwo.garage.storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Category
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int MAXLEN = 50;
	private String m_name;
	private int m_id;

	public Category(String name)
		throws CategoryException
	{
		m_id = -1; // sentinel value
		name(name);
	}
	public int id() {
		return m_id;
	}
	public String name() {
		return m_name;
	}
	public void name(String name)
		throws CategoryException
	{
		if (name == null || name.isEmpty())
			throw new CategoryException("Category names cannot be empty");

		if (name.length() > MAXLEN)
			throw new CategoryException("Category names cannot be longer than " + MAXLEN + " characters");

		m_name = name;
	}

	// these are package methods; only the Storage system is allowed to assign an id
	Category(int id, String name) {
		m_id = id;
		m_name = name;
	}
	void id(int id) {
		m_id = id;
	}

	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		out.writeInt(m_id);
		out.writeObject(m_name);
	}
	
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		m_id = in.readInt();
		m_name = (String)in.readObject();
	}
}

package ca.uwo.garage.storage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The Category object stores some pertinent information relating to a Category.
 * In the Storage class, a unique category_id (in SQL terminology, a PRIMARY KEY)
 * is created to keep track of each category.
 * 
 * The Category object enforces constraints like maximum name length and provides
 * a Serialization interface.
 * 
 * @author Jonathan Yu
 * @implements Serializable
 * @version $Revision$
 */

public class Category
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static final int MAXLEN = 50;
	private String m_name;
	private int m_id;

	/**
	 * This method creates a new Category object with an undefined identification
	 * number. A Category only has a unique code once saved using the Storage class.
	 * 
	 * @param name The name of the Category
	 * @throws CategoryException if there is something wrong with the name
	 */
	public Category(String name)
		throws CategoryException
	{
		m_id = -1; // sentinel value
		name(name);
	}

	/**
	 * This method simply returns the current identification code, which may be
	 * "-1", a sentinel value, in case the identification code has not yet been
	 * set (that is, the Category has not been saved using Storage)
	 * 
	 * @return the current identification code
	 */
	public int id() {
		return m_id;
	}

	/**
	 * This method simply returns the current Category name. It is guaranteed
	 * to be defined, non-zero-length and less than MAXLEN characters.
	 * 
	 * @return the current Category name
	 */
	public String name() {
		return m_name;
	}

	/**
	 * This method changes the name of a Category.
	 *
	 * @param name The new Category name to use
	 * @throws CategoryException if the name is zero length or longer than MAXLEN
	 */
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
	/**
	 * PACKAGE-SCOPED METHOD
	 * 
	 * This Category constructor is capable of setting an id. It is important that only
	 * other classes in this PACKAGE may access this constructor -- namely, the Storage
	 * class.
	 * 
	 * It assumes that the id and name are valid, and so does not do any error checking.
	 * 
	 * @param id The unique category_id pertaining to this Category
	 * @param name The category name
	 */
	Category(int id, String name) {
		m_id = id;
		m_name = name;
	}

	/**
	 * PACKAGE-SCOPED METHOD
	 * 
	 * This id method is capable of setting an id. It is important that only other classes
	 * in this PACKAGE may access this constructor -- namely, the Storage class.
	 * 
	 * It assumes that the id is valid, and so does not do any error checking.
	 * 
	 * @param id The unique category_id pertaining to this Category
	 */
	void id(int id) {
		m_id = id;
	}

	/**
	 * PRIVATE SPECIAL METHOD - For Serialization
	 * 
	 * When this Category object is being serialized, it will store its Id and Object only,
	 * in that order.
	 *
	 * @throws IOException if there was an error with the given ObjectOutputStream
	 */
	private void writeObject(ObjectOutputStream out)
		throws IOException
	{
		out.writeInt(m_id);
		out.writeObject(m_name);
	}

	/**
	 * PRIVATE SPECIAL METHOD - For Serialization
	 * 
	 * When this Category object is being deserialized, it will read its Id and Object only,
	 * in that order.
	 *
	 * @throws IOException if there was an error with the given ObjectInputStream
	 * @throws ClassNotFoundException this case shouldn't ever occur, and would be a bug in Serializable
	 */
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		m_id = in.readInt();
		m_name = (String)in.readObject();
	}
}

package ca.uwo.garage.storage;
import java.io.Serializable;

public class User
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	// These come from the CS2212 project specifications
	private static final int USERIDLEN   = 4;
	private static final int NAMELEN     = 20;
	private static final int PASSWORDLEN = 3;
	private static final int PHONENUMLEN = 10;

	// these are all package variables so that they can be modified by Storage classes
	String m_userid, m_firstName, m_lastName, m_password, m_phone;
	transient GeoPosition m_home;
	transient Zoom m_zoom;

	public User(String userid)
		throws UserIdException
	{
		if (userid == null || userid.isEmpty())
			throw new UserIdException("The userid parameter cannot be null or empty");

		if (userid.length() != USERIDLEN)
			throw new UserIdException("The specified userid must be exactly " + USERIDLEN + "characters long");

		m_userid = userid;
		m_password = "aaa";
	}
	public String id() {
		return m_userid;
	}

	public void first_name(String name)
		throws UserNameException
	{
		if (name == null || name.isEmpty())
			throw new UserNameException("The name parameter cannot be null or empty");

		if (name.length() > NAMELEN)
			throw new UserNameException("The specified name is too long");

		m_lastName = name;

	}
	public String first_name() {
		return m_firstName;
	}

	public void last_name(String name)
		throws UserException
	{
		if (name == null || name.isEmpty())
			throw new UserNameException("The name parameter cannot be null or empty");

		if (name.length() > NAMELEN)
			throw new UserNameException("The specified name is too long");

		m_lastName = name;
	}
	public String last_name() {
		return m_lastName;
	}

	public String name() {
		// don't have a first name, stop now
		if (m_firstName == null)
			return null;

		// don't have a last name yet
		if (m_lastName == null)
			return m_firstName;

		return m_firstName + " " + m_lastName;
	}

	public void password(String passphrase)
		throws UserPasswordException
	{
		if (passphrase == null || passphrase.isEmpty())
			throw new UserPasswordException("The password parameter cannot be null or empty");

		if (passphrase.length() != PASSWORDLEN)
			throw new UserPasswordException("The specified password must be exactly " + PASSWORDLEN + "characters long");

		m_password = passphrase;
	}
	public boolean validPassword(String passphrase) {
		if (m_password == passphrase)
			return true;
		return false;
	}

	public void phone(String phoneNumber)
		throws UserException
	{
		if (phoneNumber == null || phoneNumber.length() == PHONENUMLEN)
			throw new UserException("Phone numbers must be exactly " + PHONENUMLEN + " numbers long");

		m_phone = phoneNumber;
	}
	public String phone() {
		return m_phone;
	}

	public Zoom zoom() {
		if (m_zoom == null)
			m_zoom = new Zoom();
		return m_zoom;
	}

	public GeoPosition home() {
		if (m_home == null)
			m_home = new GeoPosition();
		return m_home;
	}

	public String toString() {
		return m_userid;
	}
}

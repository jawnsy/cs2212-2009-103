package ca.uwo.garage.storage;
import java.io.Serializable;

/**
 * The User class provides a way to represent a given program user and
 * the operations they might perform. It includes validation of their
 * input to ensure they match the assignment length constraints.
 *
 * This class is capable of writing and reading a condensed version of
 * itself as part of serialization.
 * 
 * @author Jonathan Yu
 * @implements Serializable
 * @version $Revision$
 */

public class User
	implements Serializable
{
	private static final long serialVersionUID = 1L;

	// These come from the CS2212 project specifications
	public static final int USERIDLEN   = 4;
	public static final int NAMELEN     = 20;
	public static final int PASSWORDLEN = 3;
	public static final int PHONENUMLEN = 10;

	// these are all package variables so that they can be modified by Storage classes
	String m_userid, m_firstName, m_lastName, m_password, m_phone;
	transient GeoPosition m_home;
	transient Zoom m_zoom;

	/**
	 * This builds a User object with a given userid string. The userid must be exactly
	 * USERIDLEN characters long; a UserIdException will be thrown if this is not true.
	 * 
	 * By default, the password is instantiated to "aaa"; this should be changed
	 * upon the first user login. Thus if the password is "aaa", the user should be
	 * told that their password is insecure and should be changed.
	 * 
	 * @param userid The userid of this User
	 * @throws UserIdException if userid is null, empty or not USERIDLEN characters long
	 */
	public User(String userid)
		throws UserException
	{
		if (userid == null || userid.isEmpty())
			throw new UserException("The userid parameter cannot be blank");

		if (userid.length() != USERIDLEN)
			throw new UserException("The specified userid must be exactly " + USERIDLEN + "characters long");

		m_userid = userid.toLowerCase();
		m_password = "aaa";
	}

	/**
	 * This returns the User's current userid, suitable for use as a key for searching.
	 * 
	 * @return the User's userid
	 */
	public String id() {
		return m_userid;
	}

	/**
	 * This method sets the User's first name according to the assignment constraints.
	 * 
	 * @param name The user's new first name
	 * @throws UserNameException if name is null, empty or longer than NAMELEN characters
	 */
	public void first_name(String name)
		throws UserException
	{
		if (name == null || name.isEmpty())
			throw new UserException("The name parameter cannot be blank");

		if (name.length() > NAMELEN)
			throw new UserException("The specified name is too long");

		m_firstName = name;
	}

	/**
	 * This method returns the current User's first name, or null if not yet set.
	 * 
	 * @return a string containing the user's first name, or null
	 */
	public String first_name() {
		return m_firstName;
	}

	/**
	 * This method sets the User's last name according to the assignment constraints.
	 * 
	 * @param name The user's new last name
	 * @throws UserNameException if name is null, empty or longer than NAMELEN characters
	 */
	public void last_name(String name)
		throws UserException
	{
		if (name == null || name.isEmpty())
			throw new UserException("The name parameter cannot be blank");

		if (name.length() > NAMELEN)
			throw new UserException("The specified name is too long");

		m_lastName = name;
	}

	/**
	 * This method returns the current User's last name, or null if not yet set.
	 * 
	 * @return a string containing the user's last name, or null
	 */
	public String last_name() {
		return m_lastName;
	}

	/**
	 * This method returns the user's full name.
	 * 
	 * If only the firstname is set, then the user's first name is returned.
	 * If both the firstname and lastname are set, then it concatenates both
	 * of them separated by a space.
	 * If the firstname is not set, then a null value is returned.
	 *
	 * @return the User's full name, or null
	 */
	public String name() {
		// don't have a first name, stop now
		if (m_firstName == null)
			return null;

		// don't have a last name yet
		if (m_lastName == null)
			return m_firstName;

		return m_firstName + " " + m_lastName;
	}

	/**
	 * This method stores the user's password or passphrase. There are no guarantees how the password
	 * is represented internally, and there is no way to retrieve a password, since it may (and, in
	 * fact, should) be stored as a one-way cryptographic hash rather than as plaintext.
	 * 
	 * @param passphrase the new password to set for this User
	 * @throws UserPasswordException if the password is null, empty or not equal to PASSWORDLEN characters
	 */
	public void password(String passphrase)
		throws UserException
	{
		if (passphrase == null || passphrase.isEmpty())
			throw new UserException("The password parameter cannot be blank");

		if (passphrase.length() != PASSWORDLEN)
			throw new UserException("The specified password must be exactly " + PASSWORDLEN + "characters long");

		m_password = passphrase;
	}

	/**
	 * This method returns whether or not the given passphrase is valid - that it matches the User's
	 * current set passphrase.
	 * 
	 * @param passphrase to check against the User's passphrase
	 * @return true if the Password is correct; or false otherwise
	 */
	public boolean validPassword(String passphrase) {
		if (m_password.equals(passphrase))
			return true;
		System.out.println("Passwords don't match");
		return false;
	}

	/**
	 * This method verifies that a phone number is valid (currently only checks that it is 10 digits
	 * and numeric), and sets it for this User.
	 * 
	 * @param phoneNumber The new phone number to set for this user, as a 10-digit string
	 * @throws UserException if the phone number appears invalid for some reason
	 */
	public void phone(String phoneNumber)
		throws UserException
	{
		if (phoneNumber == null || phoneNumber.length() != PHONENUMLEN)
			throw new UserException("Phone numbers must be exactly " + PHONENUMLEN + " numbers long");

		// Check that each digit looks like a number
		for (int i = 0; i < phoneNumber.length(); i++) {
			char ch = phoneNumber.charAt(i);
			if (ch < 48 /* 0 */ || ch > 57 /* 9 */)
				throw new UserException("Phone number contains an invalid character: " + ch);
		}

		m_phone = phoneNumber;
	}

	/**
	 * This method simply returns the current phone number associated with this user,
	 * as a 10-digit string. It may be null if no phone number has been set yet.
	 * 
	 * @return the User's phone number (or null)
	 */
	public String phone() {
		return m_phone;
	}

	/**
	 * Returns a Zoom object associated with this user, which specifies this user's
	 * default Zoom level.
	 * 
	 * @return Zoom object associated with this User
	 */
	public Zoom zoom() {
		if (m_zoom == null)
			m_zoom = new Zoom();
		return m_zoom;
	}

	/**
	 * Returns a GeoPosition object associated with this user, which specifies this user's
	 * default home GeoPosition.
	 * 
	 * @return home GeoPosition object associated with this User
	 */
	public GeoPosition home() {
		if (m_home == null)
			m_home = new GeoPosition();
		return m_home;
	}

	/**
	 * Returns something like a string representation of this User. Currently, it just
	 * gives the user's id, but this behaviour may be changed in the future. Don't rely
	 * on it for anything except displaying some general information to the user, or
	 * for debugging purposes.
	 * 
	 * @return A string describing this User
	 */
	public String toString() {
		return m_userid;
	}
}

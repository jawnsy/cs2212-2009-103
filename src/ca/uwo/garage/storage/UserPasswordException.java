package ca.uwo.garage.storage;

@SuppressWarnings("serial") // does not need to be serialized
public class UserPasswordException
	extends UserException
{
	public UserPasswordException() {
		super("The specified password is invalid");
	}
	public UserPasswordException(String msg) {
		super(msg);
	}
}

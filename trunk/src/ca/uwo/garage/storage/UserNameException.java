package ca.uwo.garage.storage;

@SuppressWarnings("serial") // does not need to be serialized
public class UserNameException
	extends UserException
{
	public UserNameException() {
		super("The specified username is invalid");
	}
	public UserNameException(String msg) {
		super(msg);
	}
}

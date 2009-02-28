package ca.uwo.garage.storage;

@SuppressWarnings("serial") // does not need to be serialized
public class UserIdException
	extends UserException
{
	public UserIdException(String msg) {
		super(msg);
	}
}

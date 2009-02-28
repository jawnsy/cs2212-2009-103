package ca.uwo.garage;

@SuppressWarnings("serial") // does not need to be serialized
public class GarageException
	extends Exception
{
	public GarageException() {
		super("Unspecified error in UWO Garage System");
	}
	public GarageException(String msg) {
		super(msg);
	}
}

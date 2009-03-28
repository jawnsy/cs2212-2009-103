package ca.uwo.garage;

@SuppressWarnings("serial")
public class ViewTypeException
	extends GarageException
{
	public ViewTypeException(String expectedClass)
	{
		super("Parameter invalid. Expected a View of type: " + expectedClass);
	}
}

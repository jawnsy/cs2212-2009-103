package ca.uwo.garage;

public class ViewTypeException
	extends GarageException
{
	public ViewTypeException(String expectedClass)
	{
		super("Parameter invalid. Expected a View of type: " + expectedClass);
	}
}

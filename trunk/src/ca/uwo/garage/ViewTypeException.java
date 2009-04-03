package ca.uwo.garage;
/**
 * This is an exception to be thrown when an unexpected view object
 * is being used
 * @author Jon
 *
 */
@SuppressWarnings("serial")
public class ViewTypeException
	extends GarageException
{
	public ViewTypeException(String expectedClass)
	{
		super("Parameter invalid. Expected a View of type: " + expectedClass);
	}
}

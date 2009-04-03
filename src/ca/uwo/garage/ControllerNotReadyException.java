package ca.uwo.garage;

@SuppressWarnings("serial")
/**
 *  This exception is thrown when a Controller is started without sufficient
 *  initialization. See the documentation for details.
 */
public class ControllerNotReadyException
	extends GarageException
{
	public ControllerNotReadyException() {
	    super("Unknown error occured while starting controller.");
	}
	public ControllerNotReadyException(String params) {
		super("The controller is missing one or more parameters: " + params);
	}
}

package ca.uwo.garage;

@SuppressWarnings("serial")
public class ControllerNotReadyException
	extends GarageException
{
	/**
	 * This exception is thrown when a Controller is start()ed without sufficient
	 * initialization. See the documentation for details.
	 */
	public ControllerNotReadyException() {
	    super("Unknown error occured while starting controller.");
	}
	public ControllerNotReadyException(String params) {
		super("The controller is missing one or more parameters: " + params);
	}
}

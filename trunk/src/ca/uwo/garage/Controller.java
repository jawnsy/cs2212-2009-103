package ca.uwo.garage;
/**
 * An interface for controller objects
 * @author Jon
 *
 */
public interface Controller
{
	/**
	 * A method used to start the controller
	 * @throws ControllerNotReadyException
	 */
	public abstract void start()
		throws ControllerNotReadyException;
	/**
	 * A method to display the view object
	 * @param view the view object
	 * @throws ViewTypeException
	 */
	public abstract void view(View view)
		throws ViewTypeException;
	/**
	 * A method used to determine whether or not the controller has started
	 * @return returns true if the controller has been started
	 */
	public abstract boolean isReady();
}

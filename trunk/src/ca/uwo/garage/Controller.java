package ca.uwo.garage;

public interface Controller
{
	public abstract void start()
		throws ControllerNotReadyException;
	public abstract void view(View view)
		throws ViewTypeException;
	public abstract boolean isReady();
}

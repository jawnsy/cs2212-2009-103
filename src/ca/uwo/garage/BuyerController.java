package ca.uwo.garage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ca.uwo.garage.storage.Storage;
/**
 * This class performs all the actions for buyer mode
 * @author Jon
 *
 */
public class BuyerController
	implements Controller
{
	BuyerView m_view; // The view object
	Storage m_storage; // The storage 
	boolean m_ready;

	/**
	 * A method used to determine whether or not the controlles has started
	 */
	public boolean isReady() {
		return m_ready;
	}

	/**
	 * A method used to start the controller
	 */
	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("a BuyerView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");

		m_view.update(m_storage);
	}

	/**
	 * A method used to set the storage object
	 * @param storage the storage object
	 */
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
	/**
	 * A method used to get the storage object
	 * @return the storage object
	 */
	public Storage storage()
	{
		return m_storage;
	}

	/**
	 * A method used to display the view object
	 */
	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof BuyerView))
			throw new ViewTypeException("BuyerView");

		m_view = (BuyerView)view;
		m_view.addWindowListener(new CloseTrigger());
	}

	/**
	 * Closes the program
	 *
	 */
	private class CloseTrigger
		extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) {
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}
}

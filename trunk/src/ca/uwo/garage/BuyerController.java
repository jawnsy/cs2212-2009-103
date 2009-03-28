package ca.uwo.garage;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ca.uwo.garage.storage.Storage;

public class BuyerController
	implements Controller
{
	BuyerView m_view;
	Storage m_storage;
	boolean m_ready;

	public boolean isReady() {
		return m_ready;
	}

	public void start()
		throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("a BuyerView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");

		m_view.update(m_storage);
	}

	public void storage(Storage storage)
	{
		m_storage = storage;
	}
	public Storage storage()
	{
		return m_storage;
	}

	public void view(View view)
		throws ViewTypeException
	{
		if (!(view instanceof BuyerView))
			throw new ViewTypeException("BuyerView");

		m_view = (BuyerView)view;
		m_view.addWindowListener(new CloseTrigger());
	}

	private class CloseTrigger
		extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) {
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}
}

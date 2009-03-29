package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.CategoryException;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;
import ca.uwo.garage.storage.StorageFullException;
import ca.uwo.garage.storage.StorageKeyException;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;
import ca.uwo.garage.storage.UserException;

public class SellerController
implements Controller
{
	private SellerView m_view;
	private Storage m_storage;
	private boolean m_ready;

	public SellerController()
	{
		m_view = null;
		m_ready = false;
		m_storage = null;
	}

	public void start()
	throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("a SellerView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");
	}

	public void view(View view) throws ViewTypeException
	{
		if (!(view instanceof SellerView))
			throw new ViewTypeException("SellerView");

		m_view = (SellerView) view;
		m_view.addWindowListener(new CloseTrigger());
		m_view.addDeleteAction(new DeleteTrigger());
	}

	public boolean isReady() 
	{
		return m_ready;
	}
	private class DeleteTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			String garageName = m_view.getListSelect();
			if (garageName != null)
			{
				
				GarageSale sale = m_storage.findGarageSale(saleid)
			}
			
		}
		
	}
	private class CloseTrigger
	extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) 
		{
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
}
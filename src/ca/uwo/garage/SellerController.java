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

public class BuyerController
	implements Controller
{
	private BuyerView m_view;
	private Storage m_storage;
	private boolean m_ready;
	
	public BuyerController()
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

	try {
		m_view.setUserList(m_storage.listUsers());
		m_view.setCategoryList(m_storage.listCategories());
	} catch (StorageEmptyException e) {
	}
}
	
	public void view(View view) throws ViewTypeException
	{
		
	}
	
	
	
	

}
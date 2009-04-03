package ca.uwo.garage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import ca.uwo.garage.storage.GarageSale;
import ca.uwo.garage.storage.GarageSaleLoader;
import ca.uwo.garage.storage.GarageSaleLoaderException;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageEmptyException;
import ca.uwo.garage.storage.StorageNotFoundException;
import ca.uwo.garage.storage.User;

/**
 * This class performs the actions for the seller view
 * @author Jon
 *
 */
public class SellerController
	implements Controller
{
	private SellerView m_view; //The view object
	private Storage m_storage; //The storage object
	private boolean m_ready;
	private User m_user; //The user object

	public SellerController()
	{
		m_view = null;
		m_ready = false;
		m_storage = null;
		m_user = null;
	}

	/**
	 * A method used to start the controller
	 */
	public void start()
	throws ControllerNotReadyException
	{
		if (m_view == null)
			throw new ControllerNotReadyException("a SellerView");
		if (m_storage == null)
			throw new ControllerNotReadyException("a Storage backend");
		if (m_user == null)
			throw new ControllerNotReadyException("a seller-mode User object");

		try {
			m_view.update(m_storage.listGarageSales(), m_user);
		} catch (StorageEmptyException e) {
		}
	}

	/**
	 * A method used to display the view object
	 */
	public void view(View view) throws ViewTypeException
	{
		if (!(view instanceof SellerView))
			throw new ViewTypeException("SellerView");

		m_view = (SellerView) view;
		m_view.addWindowListener(new CloseTrigger());
		m_view.addDeleteAction(new DeleteTrigger());
		m_view.addBulkLoadAction(new BulkLoadTrigger());
	}

	/**
	 * A method used to determine whether or not the controller has started
	 */
	public boolean isReady() 
	{
		return m_ready;
	}
	/**
	 * The action to be performed when the user clicks the bulk load button
	 *
	 */
	private class BulkLoadTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) 
		{
			try {
				GarageSaleLoader loader = new GarageSaleLoader(m_view.getPath());
				m_storage.clear();
				loader.storage(m_storage);
				loader.owner(m_user);
				loader.save();
			} catch (GarageSaleLoaderException e) {
				JOptionPane.showMessageDialog(
						m_view,
						"Error during bulk load: " +
						e.getMessage(), // Text
						"Garage Sale Bulk Loading Error",
						JOptionPane.ERROR_MESSAGE
					);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						m_view,
						"I/O error during bulk load: " +
						e.getMessage(), // Text
						"Garage Sale Input Error",
						JOptionPane.ERROR_MESSAGE
					);
			}
		}		
	}
	/**
	 * The action to be performed when the delete button is clicked
	 *
	 */
	private class DeleteTrigger
		implements ActionListener
	{
		public void actionPerformed(ActionEvent ev) 
		{
			GarageSale sale = m_view.getListSelect();
			if (sale == null)
			{
				JOptionPane.showMessageDialog(
						m_view,
						"Please select a garage sale to delete.", // Text
						"Garage Sale Deletion Error",
						JOptionPane.ERROR_MESSAGE
					);
				return;
			}

			// not null, so do stuff now
			try {
				m_storage.delete(sale);
				//m_view.updateList()

				m_view.update(m_storage.listGarageSales(), m_user);
			} catch (StorageNotFoundException e) {
				// should not happen
			} catch (StorageEmptyException e) {
				// just ignore this
			}
		}
		
	}

	/**
	 * Closes the program
	 *
	 */
	private class CloseTrigger
		extends WindowAdapter
	{
		public void windowClosing(WindowEvent ev) 
		{
			m_ready = true; // signal work is done
			m_view.dispose();
		}
	}

	/**
	 * A method used to get the storage object
	 * @param storage the storage object
	 */
	public void storage(Storage storage)
	{
		m_storage = storage;
	}
}
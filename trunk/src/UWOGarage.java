import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ca.uwo.garage.AdminController;
import ca.uwo.garage.AdminView;
import ca.uwo.garage.AuthorizationController;
import ca.uwo.garage.AuthorizationView;
import ca.uwo.garage.BuyerController;
import ca.uwo.garage.BuyerView;
import ca.uwo.garage.ControllerNotReadyException;
import ca.uwo.garage.View;
import ca.uwo.garage.ViewTypeException;
import ca.uwo.garage.storage.MockStorage;
import ca.uwo.garage.storage.Storage;
import ca.uwo.garage.storage.StorageException;

public class UWOGarage {
	public static Storage m_storage;

	public static void main(String[] args)
	{
		boolean admin = false;

		try {
			// Scan input arguments to see if we are to run under admin mode
			for (int i = 0; i < args.length; i++)
			{
				if (args[i].equals("-admin"))
				{
					admin = true;
				}
			}

			runBuyer();
			/*
			if (admin)
				runAdmin();
			else {
				if (isBuyerMode())
					runBuyer();
				else
					runSeller();
			}
			*/
		}
		catch (Exception e) {
			reportError(e);
		}
	}
	public static void runAdmin()
		throws StorageException, ViewTypeException, ControllerNotReadyException
	{
		AdminController control = new AdminController();
		AdminView view = new AdminView(control);

		m_storage = new MockStorage();
		m_storage.connect();

		control.view(view);
		control.storage(m_storage);
		control.start();
	}
	public static void runProgram()
		throws Exception
	{
		if (isBuyerMode())
			runBuyer();
		else
			runSeller();
	}

	public static void runBuyer()
		throws ViewTypeException, ControllerNotReadyException, StorageException
	{
		BuyerController control = new BuyerController();
		BuyerView view = new BuyerView(control);

		// usually done by isBuyerMode
		m_storage = new MockStorage();
		m_storage.connect();

		control.view(view);
		control.storage(m_storage);
		control.start();
	}

	public static void runSeller()
	{
		
	}

	// Determine whether we're a Buyer Mode
	public static boolean isBuyerMode()
		// These shouldn't happen, so throw them
		throws StorageException, ViewTypeException, ControllerNotReadyException
	{
		AuthorizationController control = new AuthorizationController();
		View view = new AuthorizationView(control);
		m_storage = new MockStorage();
		m_storage.connect();

		control.view(view);
		control.storage(m_storage);
		control.start();

		// Wait for the Authorization to complete, so we can decide what to do next
		while (!control.isReady()) {
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				// ignore this error
			}
		}

		// When the Controller is ready, it will either be authorized or the user cancelled
		if (!control.isAuthorized()) {
			System.exit(1);
		}

		return control.isBuyerMode();
	}
	public static void reportError(Exception e)
	{
		JTextArea error = new JTextArea(
			e.toString() + "\n\n",
			10 /* rows */, 30 /* columns */
		);

		StackTraceElement stack[] = e.getStackTrace();
		error.append("Stack trace (most recent first):\n");
		for (int i = 0; i < stack.length; i++) {
			error.append(stack[i].getClassName() + "." + stack[i].getMethodName());
			if (stack[i].isNativeMethod())
				error.append(" (native)");
			error.append("\n");
			error.append("    From " + stack[i].getFileName() + ":" + stack[i].getLineNumber() + "\n");
		}

		error.setEditable(false);
		error.setLineWrap(true);
		error.setWrapStyleWord(true);

		Object errorStack[] = {
			new JLabel("It looks like something bad happened. Our fault. We're really sorry."),
			new JLabel("Please copy and paste the entire error report below and send it to us!"),
			new JScrollPane(
					error,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
				)
		};
		JOptionPane.showMessageDialog(
			null,
			errorStack, // Text
			"Oops! We've messed up... =(",
			JOptionPane.ERROR_MESSAGE
		); 

		System.exit(1);
	}
}

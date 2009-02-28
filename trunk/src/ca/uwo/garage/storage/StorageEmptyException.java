package ca.uwo.garage.storage;

public class StorageEmptyException
	extends StorageException
{
	private static final long serialVersionUID = 1L;

	public StorageEmptyException() {
		super("Storage does not contain any objects");
	}
}

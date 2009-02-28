package ca.uwo.garage.storage;

public class StorageFullException
	extends StorageException
{
	private static final long serialVersionUID = 1L;

	public StorageFullException() {
		super("Storage capacity or disk quota exceeded");
	}
}

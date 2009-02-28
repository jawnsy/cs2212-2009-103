package ca.uwo.garage.storage;

public class StorageNotFoundException
	extends StorageException
{
	private static final long serialVersionUID = 1L;

	public StorageNotFoundException(String namespace, String key) {
		super("The given key was not found: " + key + "@" + namespace);
	}
}

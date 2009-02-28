package ca.uwo.garage.storage;

public class StorageKeyException
	extends StorageException
{
	private static final long serialVersionUID = 1L;

	public StorageKeyException(String namespace, String key) {
		super("The given key is a duplicate: " + key + "@" + namespace);
	}
	public StorageKeyException(String msg) {
		super(msg);
	}
}

package ca.uwo.garage.storage;

@SuppressWarnings("serial") // does not need to be serialized
public class StorageFullException
	extends StorageException
{
	public StorageFullException() {
		super("Storage capacity or disk quota exceeded");
	}
}

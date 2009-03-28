package ca.uwo.garage.storage;

import ca.uwo.garage.GarageException;

@SuppressWarnings("serial")
public class GarageSaleLoaderException
	extends GarageException
{
	public GarageSaleLoaderException()
	{
		super("Failed to load new or save imported GarageSales");
	}
	public GarageSaleLoaderException(String msg)
	{
		super(msg);
	}
}

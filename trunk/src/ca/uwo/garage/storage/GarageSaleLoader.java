package ca.uwo.garage.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

public class GarageSaleLoader
{
	private String m_filename;
	private Storage m_storage;
	private Collection<GarageSale> m_sales;
	private FileInputStream m_handle;

	public GarageSaleLoader(String filename)
		throws IOException
	{
		m_filename = filename;
		m_handle = new FileInputStream(filename);
	}
	public GarageSaleLoader(String filename, Storage storage)
		throws IOException
	{
		this(filename);
		m_storage = storage;
	}

	public void storage(Storage storage) {
		m_storage = storage;
	}
	public Storage storage() {
		return m_storage;
	}

	public String filename() {
		return m_filename;
	}

	private void load() {
		
	}
	public void save()
		throws IOException
	{
		Iterator<GarageSale> iter = m_sales.iterator();
		while (iter.hasNext()) {
			//m_storage.store(iter.next());
		}
	}
	public void export(OutputStream writer)
		throws IOException
	{
		
	}
	public Collection<GarageSale> listGarageSales()
	{
		return m_sales;
	}
}

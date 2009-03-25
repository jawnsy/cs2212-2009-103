/**
 * 
 */
package ca.uwo.garage.storage;

import java.util.Collection;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.String;
import ca.uwo.garage.Printer;
import ca.uwo.garage.PrinterException;



/**
 * @author Jason Lu
 *
 */
public class HTMLPrinter implements Printer {
	private static String htmlHead = new String("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><HTML><HEAD><TITLE>Garage Sale</TITLE></HEAD><BODY>");
	private static String htmlTail = new String("</BODY></HTML>");
	private static String err_file_access = new String("file access error!");
	private static String err_file_write = new String("write file error!");
	private static String err_file_close = new String("file close error!");
	private static File file;
	
	/* (non-Javadoc)
	 * @see ca.uwo.garage.Printer#print(ca.uwo.garage.storage.GarageSale)
	 */
	
	private File cTempFile() throws IOException{
		file=File.createTempFile("garagesale", "html");
		file.deleteOnExit();
		return file;
	}
	private StringBuffer genOutputStr(StringBuffer strb, String str){
		strb.append(str);
		return strb;
		
	}
	@Override
	public void print(GarageSale garageSale) throws PrinterException {
		
		StringBuffer strBuf =new StringBuffer();
		strBuf=genOutputStr(strBuf,htmlHead);
		//write properties of the garagesale object
		strBuf=genOutputStr(strBuf,garageSale.address().toString());
		strBuf=genOutputStr(strBuf,htmlTail);
		
		String outStr=strBuf.toString();
		Writer output=null;
		// TODO Auto-generated method stub
		try {
			file=cTempFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_access);
		}
		
		try {
			output=new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_access);
		}
		try {
			output.write(outStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_write);
		}
		
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PrinterException(err_file_close);
		}
		
		try {
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_access);
		}
	
	}

	/* (non-Javadoc)
	 * @see ca.uwo.garage.Printer#print(java.util.Collection)
	 */
	@Override
	public void print(Collection<GarageSale> sales) throws PrinterException {
		// TODO Auto-generated method stub

	}

}

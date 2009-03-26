/**
 * 
 */
package ca.uwo.garage;

import java.util.Collection;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.String;

import javax.imageio.ImageIO;

import org.jdesktop.swingx.JXMapKit;

import ca.uwo.garage.storage.Category;
import ca.uwo.garage.storage.GarageSale;



/**
 * @author Jason Lu
 *
 */
public class HTMLPrinter implements Printer {
	private static String htmlHead = new String("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><HTML><HEAD><TITLE>Garage Sale</TITLE></HEAD><BODY><p><strong>Garage Sale</strong></p>");
	
	private static String htmlTail = new String("</BODY></HTML>");
	private static String err_file_access = new String("file access error!");
	private static String err_file_write = new String("write file error!");
	private static String err_file_close = new String("file close error!");
	private static File file;
	private static JXMapKit jxMapKit;
	
	/* (non-Javadoc)
	 * @see ca.uwo.garage.Printer#print(ca.uwo.garage.storage.GarageSale)
	 */
	public HTMLPrinter(JXMapKit j){
		jxMapKit =j;
		
	}
	private File cTempFile(String prefix, String ext) throws IOException{
		file=File.createTempFile(prefix, ext);
		file.deleteOnExit();
		return file;
	}

	@Override
	public void print(GarageSale garageSale) throws PrinterException {
		//string buffer to build the HTML file
		StringBuffer strBuf =new StringBuffer();
		//write html head part
		strBuf.append(htmlHead);
		
		//write properties of the garagesale object
		//write location
		strBuf.append("<p><strong>Location: </strong>");
		strBuf.append(garageSale.address().toString());
		strBuf.append(", ");
		strBuf.append(garageSale.municipality().toString());
		strBuf.append(", ");
		strBuf.append(garageSale.province().toString());
		
		//write date info.
		strBuf.append("</p><p><strong>Date: </strong>");
		strBuf.append(garageSale.datetime().toString());
		
		//write category info.
		strBuf.append("</p><p><strong>Category: </strong>");
		 
		for (Category c : garageSale.listCategories()){
			strBuf.append("/ ");
			strBuf.append(c.name());
			strBuf.append(" /");
		}
		
		//write note info
		strBuf.append("</p><p><strong>Note: </strong>");
		strBuf.append(garageSale.note());
		strBuf.append("</p>");
		
		//write map picture
		
		BufferedImage bufImg= jxMapKit.createVolatileImage(jxMapKit.getWidth(), jxMapKit.getHeight()).getSnapshot();
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		try {
			ImageIO.write(bufImg, "jpeg", baos);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_access);
		}
		
		try {
			file=cTempFile("map", "jpg");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			throw new PrinterException (err_file_write);
		}
	
		OutputStream out;
		try {
			out = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			throw new PrinterException (err_file_access);
		}
		try {
			out.write(baos.toByteArray());
		} catch (IOException e1) {
			throw new PrinterException (err_file_access);
		}
		try {
			out.close();
		} catch (IOException e1) {
			throw new PrinterException (err_file_write);
		}
		
		strBuf.append("<p><img width=\"");
		strBuf.append(Integer.toString(jxMapKit.getMainMap().getWidth()));
		strBuf.append("\" height=\"");
		strBuf.append(Integer.toString(jxMapKit.getMainMap().getHeight()));
		strBuf.append("\" src=\"");
		strBuf.append(file.getPath());
		strBuf.append(file.getName());
		strBuf.append("\" /></p>");
		
		//write html end
		strBuf.append(htmlTail);
		
		String outStr=strBuf.toString();
		Writer output=null;
		// TODO Auto-generated method stub
		try {
			file=cTempFile("garagesale", "html");
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

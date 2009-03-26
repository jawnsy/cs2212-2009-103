package ca.uwo.garage.mapviewer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MapTest
   {
     public static void main(String args[])
       throws Exception
       {
    	 JFrame frame = new JFrame("Open Street Maps Test");
    	 frame.getContentPane().add(new MapPanel());
         frame.setSize(800, 600);
         frame.setVisible(true);
         
         // Add listener to kill the things if the window dies.
         
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
               System.exit(0);
             }
          });

         // Wait around for a while here. 
         
         while (frame.isVisible())
           {
             Thread.sleep(200);
           }
       }
   }


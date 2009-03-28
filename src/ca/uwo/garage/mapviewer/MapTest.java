package ca.uwo.garage.mapviewer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import ca.uwo.garage.mapviewer.MapPanel;
import ca.uwo.garage.storage.GeoPosition;

public class MapTest
   {
     public static void main(String args[])
       throws Exception
       {
    	 JFrame frame = new JFrame("Open Street Maps Test");
    	 MapPanel mapPanel=new MapPanel();
    	 mapPanel.addWayPoint(new GeoPosition());
    	 frame.getContentPane().add(mapPanel);
         frame.setSize(800, 600);
         frame.setVisible(true);
         mapPanel.addWayPoint(new GeoPosition(43.011583,-81.258586));
     //    mapPanel.repaint();
         //mapPanel.getJXMapKit().getMainMap().repaint();
         
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


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.swingx.*;
import org.jdesktop.swingx.mapviewer.*;

public class GoogleMapTest
   {
     public static void main(String args[])
       throws Exception
       {
         
         // Get us a map provider to get Google maps.
         
         GoogleMapsProvider map = new GoogleMapsProvider();

         // From here, set things up to get the main map viewer using this provider as a source.
         
         TileFactoryInfo tileProviderInfo = map.getTileProviderInfo();
         TileFactory tileFactory = new DefaultTileFactory (tileProviderInfo);
         JXMapKit jxMapKit = new JXMapKit();

         // Create a frame for the application and set up the window appropriately.
         
         JFrame frame = new JFrame("Google Maps Test");
         frame.getContentPane().add(jxMapKit);
         frame.setSize(800, 600);
         frame.setVisible(true);

         // Set up the map viewer, and give a location and zoom level for something familiar.
         
         jxMapKit.setTileFactory(tileFactory);
         jxMapKit.setCenterPosition(new GeoPosition(43.005, -81.275));
         jxMapKit.setZoom(3);

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


package hoipolloi;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a HP logo watermark in the main window.
 * 
 * @author  Brandon Buck
 * @since   Nov 10, 2006 6:19 PM
 * @version 1.0
 */
public class WatermarkPanel extends JSplitPane {
    
    /** The Watermark Image */
    private ImageIcon watermark;
    
    /** Creates a new instance of WatermarkPanel */
    public WatermarkPanel(String imgName) {
        super();
        watermark = new ImageIcon(getClass().getClassLoader().getResource(imgName));
    }

    public WatermarkPanel(int newOrientation, Component newLeftComponent, Component newRightComponent, String imgName) {
        super(newOrientation, newLeftComponent, newRightComponent);
        watermark = new ImageIcon(getClass().getClassLoader().getResource(imgName));
    }
    
    /** {@inheritDoc} */
    @Override public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        int imgWidth = watermark.getIconWidth();
        int imgHeight = watermark.getIconHeight();
        
        int x = (width / 2) - (imgWidth / 2);
        int y = (height / 2) - (imgHeight / 2);
        
        g.drawImage(watermark.getImage(), x, y, watermark.getImageObserver());
    }
}

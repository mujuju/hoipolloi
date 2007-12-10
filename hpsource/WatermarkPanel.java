/*
 * WatermarkPanel.java
 *
 * Created on November 10, 2006, 6:19 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package hoipolloi;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Brandon Buck
 */
public class WatermarkPanel extends JPanel {
    private ImageIcon watermark;
    /** Creates a new instance of WatermarkPanel */
    public WatermarkPanel(String imgName) {
        watermark = new ImageIcon(getClass().getClassLoader().getResource(imgName));
    }
    
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        int imgWidth = watermark.getIconWidth();
        int imgHeight = watermark.getIconHeight();
        
        int x = (width / 2) - (imgWidth / 2);
        int y = (height / 2) - (imgHeight / 2);
        
        g.drawImage(watermark.getImage(), x, y, watermark.getImageObserver());
    }
}

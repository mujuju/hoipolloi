package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Brandon
 */
public class JImageLabel extends JLabel implements MouseListener, MouseMotionListener {
    private Rectangle cropArea;
    private Color rectFillColor;
    private Color rectBorderColor;
    private boolean hasImage;
    private boolean movingCropArea = false;
    int mouseDownX, mouseDownY;
    
    public JImageLabel(int width, int height) {
        cropArea = new Rectangle(0, 0, width, height);
        // blue: 101, 131, 172
        rectFillColor = new Color(236, 238, 123, 100);
        // blue: 49, 64, 84
        rectBorderColor = new Color(115, 116, 60);
        hasImage = false;
        mouseDownX = 0;
        mouseDownY = 0;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void setIcon(Icon img) {
        super.setIcon(img);
        
        
        hasImage = true;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (hasImage) {
            g2.setColor(rectFillColor);
            g2.fill(cropArea);
            g2.setColor(rectBorderColor);
            // Top Border
            g2.fillRect((int)(cropArea.getX()) - 2, (int)(cropArea.getY()) - 2, (int)(cropArea.getWidth()) + 4, 2);
            // Left Border
            g2.fillRect((int)(cropArea.getX()) - 2, (int)(cropArea.getY()) - 2, 2, (int)(cropArea.getHeight() + 4));
            // Right Border
            g2.fillRect((int)(cropArea.getX() + cropArea.getWidth()), (int)(cropArea.getY()) - 2, 2, (int)(cropArea.getHeight() + 4));
            // Bottom Border
            g2.fillRect((int)(cropArea.getX()) - 2, (int)(cropArea.getY() + cropArea.getHeight()), (int)(cropArea.getWidth() + 4), 2);
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {
        if (cropArea.contains(new Point(e.getX(), e.getY()))) {
            movingCropArea = true;
            mouseDownX = e.getX();
            mouseDownY = e.getY();
        }
    }
    public void mouseReleased(MouseEvent e) {
        movingCropArea = false;
    }
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {
        if (movingCropArea && hasImage) {
            int newCropX = (int)(cropArea.getX());
            int newCropY = (int)(cropArea.getY());
            int currentX = e.getX();
            int currentY = e.getY();
            
            // Move the cropArea the same amount the mouse has moved
            if (currentX > mouseDownX)
                newCropX += (currentX - mouseDownX);
            else if (currentX < mouseDownX)
                newCropX -= (mouseDownX - currentX);
            
            if (currentY > mouseDownY)
                newCropY += (currentY - mouseDownY);
            else if (currentY < mouseDownY)
                newCropY -= (mouseDownY - currentY);
            
            // Make sure it does not extend past the bounds of the JImageLabel
            if ((newCropX + cropArea.getWidth()) > getWidth())
                newCropX = (int)(getWidth() - cropArea.getWidth());
            else if (newCropX < 0)
                newCropX = 0;
            
            if ((newCropY + cropArea.getHeight()) > getHeight())
                newCropY = (int)(getHeight() - cropArea.getHeight());
            else if (newCropY < 0)
                newCropY = 0;
            
            // Set the new x and y of the cropArea and mouse
            cropArea.setLocation(newCropX, newCropY);
            mouseDownX = currentX;
            mouseDownY = currentY;
            
            // Redraw
            repaint();
        }
    }
    public void mouseMoved(MouseEvent e) {}
}

package hoipolloi;

import javax.swing.*;
import java.awt.event.*;

/**
 * A JTabbedPane for Addresses.
 *
 * Will have the ability to right click on a tab under EditProfile
 * and do actions like edit and delete.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Dec 28, 2008)
 * @since   December 28, 2008
 */
public class AddressPane extends JTabbedPane implements MouseListener {

    public AddressPane() {
        super();
        this.addMouseListener(this);
    }

    private void tabRightClick(MouseEvent e) {
        Debug.print("right click detected");
    }

    public void mouseClicked(MouseEvent e) {
        // Detect Right Click
        Debug.print("Mouse Clicked");
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            tabRightClick(e);
        }
    }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }


}

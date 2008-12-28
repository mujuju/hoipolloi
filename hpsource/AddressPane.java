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

    JPopupMenu rightClickMenu;

    public AddressPane() {
        super();
        this.addMouseListener(this);
        
        rightClickMenu = new JPopupMenu();
        JMenuItem addAddress = new JMenuItem("Add");
        JMenuItem editAddress = new JMenuItem("Edit");
        JMenuItem delAddress = new JMenuItem("Delete");
        JMenuItem title = new JMenuItem("Address Actions");
        title.setEnabled(false);
        rightClickMenu.add(title);
        rightClickMenu.addSeparator();
        rightClickMenu.add(addAddress);
        rightClickMenu.add(editAddress);
        rightClickMenu.add(delAddress);
        rightClickMenu.addSeparator();
        rightClickMenu.add(new JMenuItem("Google Maps"));
        rightClickMenu.add(new JMenuItem("Mapquest"));
    }

    private void showRightClickMenu(MouseEvent e) {
        rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        // Detect Right Click
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            showRightClickMenu(e);
        }
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}

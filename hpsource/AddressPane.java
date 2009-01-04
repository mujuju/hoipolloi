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
 * @version 1.1 (Jan 4, 2009)
 * @since   December 28, 2008
 */
public class AddressPane extends JTabbedPane implements MouseListener, ActionListener {

    /** The Right Click Popup Menu */
    JPopupMenu rightClickMenu;
    private Person p;
    private MainMenu owner;

    public AddressPane(MainMenu owner, Person p) {
        super();
        this.addMouseListener(this);
        
        // If the person has any addresses, show all popup menu items,
        // otherwise, show only the Add menu item.
        this.buildRightClickMenu(p.hasAddresses());
        
        this.p = p;
        this.owner = owner;
    }


    private void buildRightClickMenu(boolean hasAddresses) {
        rightClickMenu = new JPopupMenu();
        JMenuItem addAddress = new JMenuItem("Add");
        addAddress.addActionListener(this);
        JMenuItem editAddress = new JMenuItem("Edit");
        editAddress.addActionListener(this);
        JMenuItem delAddress = new JMenuItem("Delete");
        delAddress.addActionListener(this);
        JMenuItem title = new JMenuItem("Address Actions");
        rightClickMenu.add(title);
        rightClickMenu.addSeparator();
        rightClickMenu.add(addAddress);
        if (hasAddresses) {
            rightClickMenu.add(editAddress);
            rightClickMenu.add(delAddress);
            rightClickMenu.addSeparator();
            JMenuItem googleMaps = new JMenuItem("Google Maps");
            googleMaps.addActionListener(this);
            JMenuItem mapquest   = new JMenuItem("Mapquest");
            mapquest.addActionListener(this);
            rightClickMenu.add(googleMaps);
            rightClickMenu.add(mapquest);
        }
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

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String sourceText = source.getText();
        Debug.print(sourceText);

        if ("Add".equals(sourceText)) {
            new AddressBox(owner, p);
        }
        else if ("Edit".equals(sourceText)) {
            try {
                AddressPanel panel = (AddressPanel)(this.getSelectedComponent());
                new AddressBox(owner, p, panel.getAddress());
            } catch (Exception exc) { Debug.print("No address to edit!"); }
        }
        else if ("Delete".equals(sourceText)) {
            try {
                AddressPanel panel = (AddressPanel)(this.getSelectedComponent());
                p.removeAddress(panel.getAddress().getAddressID());
                owner.showProfile(p);
            } catch (Exception exc) { Debug.print("No address to delete!"); }
        }
        else if ("Google Maps".equals(sourceText)) {
            Debug.print("GMs PopmenuItem Fired");
            try {
                AddressPanel panel = (AddressPanel)(this.getSelectedComponent());
                String googleMapsURL = panel.getAddress().getGoogleMapsURL();
                BrowserLauncher.openURL(googleMapsURL);
            }
            catch (Exception exc) {
                Debug.print(exc.getMessage());
            }
        }
        else if ("Mapquest".equals(sourceText)) {
            try {
                AddressPanel panel = (AddressPanel)(this.getSelectedComponent());
                String mapquestURL = panel.getAddress().getMapquestURL();
                BrowserLauncher.openURL(mapquestURL);
            }
            catch (Exception exc) {
                Debug.print(exc.getMessage());
            }
        }
    }

}

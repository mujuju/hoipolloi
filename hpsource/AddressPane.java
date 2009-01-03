package hoipolloi;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

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
public class AddressPane extends JTabbedPane implements MouseListener, ActionListener {

    /** The Right Click Popup Menu */
    JPopupMenu rightClickMenu;
    private Person p;
    private MainMenu owner;

    public AddressPane(MainMenu owner, Person p) {
        super();
        this.addMouseListener(this);
        this.buildRightClickMenu();
        this.p = p;
        this.owner = owner;
    }


    private void buildRightClickMenu() {
        rightClickMenu = new JPopupMenu();
        JMenuItem addAddress = new JMenuItem("Add");
        addAddress.addActionListener(this);
        JMenuItem editAddress = new JMenuItem("Edit");
        editAddress.addActionListener(this);
        JMenuItem delAddress = new JMenuItem("Delete");
        delAddress.addActionListener(this);
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

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String sourceText = source.getText();
        Debug.print(sourceText);

        if (sourceText.equals("Add")) {
            new AddressBox(owner, p);
        }
        else if (sourceText.equals("Edit")) {
            try {
                AddressPanel panel = (AddressPanel)(this.getSelectedComponent());
                new AddressBox(owner, p, panel.getAddress());
            } catch (Exception exc) { Debug.print("No address to edit!"); }
        }
        else if (sourceText.equals("Delete")) {

        }
    }

}

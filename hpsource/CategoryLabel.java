package hoipolloi;

import javax.swing.*;
import java.awt.event.*;

/**
 * JTextField that acts like a JLabel, except can be edited on the fly.
 *
 * This is only used for Categories.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Jan 5, 2009)
 * @since   Jan 4, 2009
 */
public class CategoryLabel extends JTextField implements MouseListener, KeyListener {

    /** The referrence to MainMenu */
    private MainMenu mm;
    /** The referrence to the current person */
    private Person   p;
    /** The ID of this category */
    private int categoryID;
    /** The Right Click Popup Menu */
    JPopupMenu rightClickMenu;

    /**
     * Default Constructor.
     *
     * @param label      The Text this Label should contain.
     * @param mm         The referrence to MainMenu.
     * @param p          The referrence to the current person.
     * @param categoryID The Category ID number.
     */
    public CategoryLabel(String label, MainMenu mm, Person p, int categoryID) {
        super(label);
        this.showLabel();
        this.addMouseListener(this);
        this.addKeyListener(this);
        this.mm = mm;
        this.p = p;
        this.categoryID = categoryID;
        this.buildRightClickMenu();
    }

    /** Sets this JTextField to act like a JLabel */
    private void showLabel() {
        this.setEditable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /** Sets this JTextField to act like a normal JTextField */
    private void showTextField() {
        this.setEditable(true);
        this.setColumns(500);
    }

    private void buildRightClickMenu() {
        this.rightClickMenu = new JPopupMenu();
        this.rightClickMenu.add(new JMenuItem("Edit"));
        this.rightClickMenu.add(new JMenuItem("Remove"));
    }

    private void showRightClickMenu(MouseEvent e) {
        rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            // Edit the Category Name Itself
            // this.showTextField();
            this.showRightClickMenu(e);
        }
        
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            // If they right click a category label, show context menu to remove it.
            //this.showRightClickMenu(e);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        //this.showLabel();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (DBHPInterface.updateCategory(this.categoryID, this.getText().trim())) {
                this.mm.updateFilterTree();
                this.mm.updateFilteredList();
                this.mm.updateWindow();
            }
            this.showLabel();
            // Need to call something to resize text field to fit new text
            this.p.loadFromDatabase();
            mm.showProfile(this.p); //easy hack
        }
    }

    

}

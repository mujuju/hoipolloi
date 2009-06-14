package hoipolloi;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * JTextField that acts like a JLabel, except can be edited on the fly.
 *
 * This is only used for Categories.
 *
 * @author  Brandon Tanner
 * @version 1.1 (June 14, 2009)
 * @since   Jan 4, 2009
 */
public class CategoryLabel extends JTextField implements MouseListener, KeyListener, ActionListener {

    /** The referrence to MainMenu */
    private MainMenu mm;
    /** The referrence to the current person */
    private Person   p;
    /** The ID of this category */
    private int categoryID;
    /** The Left Click Popup Menu */
    protected JPopupMenu leftClickMenu;

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
        this.buildLeftClickMenu();
    }

    /** Sets this JTextField to act like a JLabel */
    private void showLabel() {
        this.setEditable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /** Sets this JTextField to act like a normal JTextField */
    private void showTextField() {
        this.setEditable(true);
    }

    public void putPersonIntoCategory() {
        JPanel panel = new JPanel();

        ArrayList cats = DBHPInterface.getListOfCategories();
        JComboBox catComboBox = new JComboBox();
        catComboBox.setEditable(false);
        for (Object foo : cats) {
            catComboBox.addItem(foo);
        }

        panel.add(catComboBox);

        int response = JOptionPane.showConfirmDialog(this.mm, panel, "Categorize "+this.p.getFirstName(), JOptionPane.OK_CANCEL_OPTION);
        if (response == JOptionPane.OK_OPTION) {
            // Add Person Into Category
            KeyValue category = (KeyValue)catComboBox.getSelectedItem();
            this.p.addCategory(category.getKey());
            this.mm.showProfile(this.p);
        }
    }

    private void buildLeftClickMenu() {
        this.leftClickMenu = new JPopupMenu();

        JMenuItem add = new JMenuItem("Add Category");
        add.setToolTipText("Add this person to a category.");
        add.addActionListener(this);


        


        JMenuItem edit = new JMenuItem("Edit Category");
        edit.setToolTipText("Edit the Category name itself (applies globally).");
        edit.addActionListener(this);

        JMenuItem remove = new JMenuItem("Remove");
        remove.setIcon(new ImageIcon(getClass().getClassLoader().getResource("user_edit.png")));
        remove.setToolTipText("Remove this person from this category.");
        remove.addActionListener(this);

        this.leftClickMenu.add(add);
        this.leftClickMenu.add(edit);
        this.leftClickMenu.add(remove);
    }

    private void showLeftClickMenu(MouseEvent e) {
        leftClickMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            this.showLeftClickMenu(e);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
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
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.showLabel();
        }
    }

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String sourceText = source.getText();

        if ("Add Category".equals(sourceText)) {
            // Provide way to add person to new category
            this.putPersonIntoCategory();
        }
        else if ("Edit Category".equals(sourceText)) {
            this.showTextField();
        }
        else if ("Remove".equals(sourceText)) {
            // Remove Person from Category
            this.p.removeCategory(this.categoryID);
            this.mm.updateFilterTree();
            this.mm.updateFilteredList();
            this.setVisible(false);
        }
    }

    

}

package hoipolloi;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.ActionListener;

/**
 * Displays a window to manage categories for a Person.
 *
 * Original Code: http://www.java-forums.org/awt-swing/3030-help-drag-drop-jlist.html
 *
 * @author  hardwired
 * @version 0.8 (Dec 1, 2008)
 */
public class CategoryBox extends JFrame {

    /** The ReportingListTransferHandler */
    public ReportingListTransferHandler arrayListHandler;

    /** Person to Manage Categories for */
    private Person p;

    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnAdd;

    public CategoryBox(JFrame owner, Person person) {
        super();
        if (person.getPersonID() > 0) {
            this.p = person;
        }
        else {
            Debug.print("No Person Supplied to Edit Categories For, exiting app.");
            System.exit(0);
        }
        this.arrayListHandler = new ReportingListTransferHandler();
        btnSave = new JButton("Save");
        //btnSave.addActionListener(this);
        this.setTitle("Edit Categories for "+p.getFirstLastName());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,200);
        this.setResizable(false);
        this.getContentPane().add(this.getContent());
        // Set the location of the Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();
        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));
        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--
        this.setVisible(true);
    }

    private JPanel getContent() {
        JPanel panel = new JPanel(new GridLayout(1,0));
        panel.add(getCategoryPanel("drag"));
        panel.add(getCategoryPanel("drop"));
        return panel;
    }

    private JPanel getCategoryPanel(String type) {
        JPanel panel = new JPanel();
        JList list;
        if (type.equals("drag")) {
            list = new JList(getAvailableCategoriesModel());
            panel.setBorder(BorderFactory.createTitledBorder("Available Categories"));
        }
        else {
            list = new JList(getCurrentCategoriesModel());
            panel.setBorder(BorderFactory.createTitledBorder("Current Categories"));
        }
        list.setName(type);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setTransferHandler(arrayListHandler);
        list.setDragEnabled(true);
        panel.add(new JScrollPane(list));
        return panel;
    }

    private DefaultListModel getAvailableCategoriesModel() {
        DefaultListModel model = new DefaultListModel();
        ArrayList <KeyValue> categories = DBHPInterface.getListOfCategories();
        for (Object value : categories) {
            model.addElement(value);
        }
        return model;
    }

    private DefaultListModel getCurrentCategoriesModel() {
        DefaultListModel model = new DefaultListModel();
        ArrayList <KeyValue> categories = this.p.getCategories();
        for (Object value : categories) {
            model.addElement(value);
        }
        return model;
    }

    /** Performs any actions this object has */
    /*public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnCancel) {
            this.dispose();
        }
    }
    */

}
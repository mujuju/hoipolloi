/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * A window to Add/Edit/Remove a Person from a Category/Group.
 * 
 * @author Brandon Tanner
 */
public class GroupsBox extends JFrame implements ActionListener {
    
    private Person  p;
    private JPanel  groupsPanel;
    private JPanel  buttonPanel;
    private JList   dragGroupsList;
    private JList   dropGroupsList;
    private JButton btnMoveRight;
    private JButton btnMoveLeft;
    private JButton btnSave;
    private JButton btnCancel;
    
    public GroupsBox(JFrame owner, Person person) {
        super();
        
        if (person.getPersonID() > 0) {
            this.p = person;
        }
        else {
            Debug.turnOn();
            Debug.print("No Person Supplied to Edit Groups For, exiting app.");
            System.exit(0);
        }    
        
        this.setTitle("Edit Groups for "+person.getFirstName());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        
        Container cp = this.getContentPane();
        
        // Initialize Vars
        groupsPanel = new JPanel();
        buttonPanel = new JPanel();
        dragGroupsList = new JList();
        dropGroupsList = new JList();
        btnMoveRight = new JButton("-->");
        btnMoveLeft = new JButton("<--");
        btnSave     = new JButton("Save");
        btnCancel   = new JButton("Cancel");
        btnSave     .addActionListener(this);
        btnCancel   .addActionListener(this);
        groupsPanel.setLayout(new BorderLayout());
        groupsPanel.setBorder(BorderFactory.createTitledBorder("Manage Groups"));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        
        //Populate dragGroupsList
        for (Object value : DBHPInterface.getListOfCategories()) {
            dragGroupsList.
        }
        
        //Populate dropGroupsList
        for (Object value : person.getCategories()) {
            dropGroupsList.addItem(value);
        }
        
        groupsPanel.add(dragGroupsList, BorderLayout.WEST);
        groupsPanel.add(btnMoveLeft, BorderLayout.CENTER);
        groupsPanel.add(btnMoveRight, BorderLayout.CENTER);
        groupsPanel.add(dropGroupsList, BorderLayout.EAST);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        cp.add(groupsPanel);
        //cp.add(buttonPanel);
        this.pack();
        
        // Set the location of the Groups Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();
        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));
        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--
        
        this.setVisible(true);
    }
    
    /** Performs any actions this object has */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnCancel) {
            this.dispose();
        }
        else if (pressedButton == btnSave) {
            // Need to Check for Required Parts first ...
                
            
            this.dispose();
        }
    } 
}

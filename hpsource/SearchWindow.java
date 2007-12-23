package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Shows a search window for searching the Hoi Polloi database.
 *
 * @author  Brandon Tanner
 * @version 1.0
 * @since   Dec 23, 2007
 */
public class SearchWindow extends JFrame implements ActionListener {

    private MainMenu   parent;                    
    private JButton    btnSearch;
    private JCheckBox  checkAddresses;
    private JCheckBox  checkContacts;
    private JCheckBox  checkDescription;
    private JCheckBox  checkFirstName;
    private JCheckBox  checkLastName;
    private JCheckBox  checkNickName;
    private JLabel     searchLabel;
    private JPanel     topPanel;
    private JPanel     bottomPanel;
    private JTextField searchQuery;
    
    
    /**
     * Creates a new instance of SearchWindow.
     */
    public SearchWindow(MainMenu parent) {
        this.parent = parent;
        
        this.topPanel         = new JPanel();
        this.bottomPanel      = new JPanel();
        this.searchLabel      = new JLabel();
        this.searchQuery      = new JTextField();
        this.checkFirstName   = new JCheckBox();
        this.checkLastName    = new JCheckBox();
        this.checkNickName    = new JCheckBox();
        this.checkDescription = new JCheckBox();
        this.checkContacts    = new JCheckBox();
        this.checkAddresses   = new JCheckBox();
        this.btnSearch        = new JButton("Search");
        
        this.btnSearch.addActionListener(this);
        this.btnSearch.setToolTipText("Perform a Text Search");
        this.searchQuery.setColumns(15);
        this.searchQuery.addKeyListener(new SearchQueryListener());
        this.searchLabel.setText("Search Query:");
        
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find Someone Among the Hoi Polloi");
        setAlwaysOnTop(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setResizable(false);
        topPanel.setBorder(BorderFactory.createTitledBorder("Search For"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Search In"));
        
        
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());


        checkFirstName.setSelected(true);
        checkFirstName.setText("First Name");
        checkLastName.setSelected(true);
        checkLastName.setText("Last Name");
        checkNickName.setSelected(true);
        checkNickName.setText("Nick Name");
        checkDescription.setSelected(true);
        checkDescription.setText("Description");
        checkContacts.setSelected(true);
        checkContacts.setText("Contacts");
        checkAddresses.setSelected(true);
        checkAddresses.setText("Addresses");
        
        topPanel.add(searchLabel);
        topPanel.add(searchQuery);
        topPanel.add(btnSearch);
        
        bottomPanel.add(checkFirstName);
        bottomPanel.add(checkLastName);
        bottomPanel.add(checkNickName);
        bottomPanel.add(checkDescription);
        bottomPanel.add(checkContacts);
        bottomPanel.add(checkAddresses);
        
        cp.add(topPanel, BorderLayout.NORTH);
        cp.add(bottomPanel, BorderLayout.SOUTH);
        
        
        this.pack();
        this.setVisible(true);
               
        // Set the location of the Search Window relative to the MainMenu
        // --CENTER--
        Point searchBoxLocation = new Point();
        double searchBoxX = parent.getLocation().getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        double searchBoxY = parent.getLocation().getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));
        searchBoxLocation.setLocation(searchBoxX, searchBoxY);
        this.setLocation(searchBoxLocation);
        // --END CENTER--
    }
    
    
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnSearch) {
            searchAction();
        }
    }
    
    /**
     * Method used to search the database with the given query. 
     * 
     * Called when the Search button is pressed or the Enter key pressed.
     */
    public void searchAction() {
        if (searchQuery.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please type something to search, don't be so ignant!??!");
            return;
        }
        Search bar = new Search();
        if (!checkFirstName.isSelected()) {
            bar.searchFirstName(false);
        }
        if (!checkLastName.isSelected()) {
            bar.searchLastName(false);
        }
        if (!checkNickName.isSelected()) {
            bar.searchNickName(false);
        }
        if (!checkDescription.isSelected()) {
            bar.searchDescription(false);
        }
        if (!checkContacts.isSelected()) {
            bar.searchContacts(false);
        }
        if (!checkAddresses.isSelected()) {
            bar.searchAddresses(false);
        }
        ArrayList people = bar.performSearch(searchQuery.getText());
        if (people.size() == 1) {
            try {
                Person singlePerson = new Person(((KeyValue)people.get(0)).getKey());
                parent.showProfile(singlePerson);
            }
            catch (Exception e) {}
        }
        else {
            new JTempFrame(parent, people);
        }
        
        DBHPInterface.printListOfPeopleByLastNameToStdout(people);
        this.dispose();
    }
    
    class SearchQueryListener implements KeyListener {
        public void keyReleased(KeyEvent evt) {}
        public void keyPressed(KeyEvent evt) {
            Debug.print(evt.getKeyCode() + " = " + KeyEvent.VK_ENTER);
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                searchAction();
            }
        }
        public void keyTyped(KeyEvent evt) {}
    }
    
}

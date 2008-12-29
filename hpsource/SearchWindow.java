package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Shows a search window for searching the Hoi Polloi database.
 *
 * @author  Brandon Tanner
 * @version 1.2 (Dec 29, 2008)
 * @since   Dec 22, 2007
 */
public class SearchWindow extends JFrame implements ActionListener {
    
    /** The parent frame object */
    private MainMenu parent;
    
    /** The Search Button */
    private JButton btnSearch;
    
    /** The Search Addresses Checkbox */
    private JCheckBox checkAddresses;
    
    /** The Search Contacts Checkbox */
    private JCheckBox checkContacts;
    
    /** The Search Description Checkbox */
    private JCheckBox checkDescription;
    
    /** The Search First Name Checkbox */
    private JCheckBox checkFirstName;
    
    /** The Search Last Name Checkbox */
    private JCheckBox checkLastName;
    
    /** The Search Nick Name Checkbox */
    private JCheckBox checkNickName;
    
    /** The Search Query Label */
    private JLabel searchLabel;
    
    /** The Top JPanel in the window */
    private JPanel topPanel;
    
    /** The Bottom JPanel in the window */
    private JPanel bottomPanel;
    
    /** The Search Query Textfield */
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
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("magnifier.png"));
        setIconImage(icon.getImage());
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
        
        JPanel bottomGridPanel = new JPanel();
        bottomGridPanel.setLayout(new GridLayout(2, 3));
        
        bottomGridPanel.add(checkFirstName);
        bottomGridPanel.add(checkLastName);
        bottomGridPanel.add(checkNickName);
        bottomGridPanel.add(checkDescription);
        bottomGridPanel.add(checkContacts);
        bottomGridPanel.add(checkAddresses);
        
        bottomPanel.add(bottomGridPanel);
        
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
    
    /** Invoked when an action occurs. */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnSearch) {
            searchAction();
        }
    }
    
    /**
     * Method used to search the database with the given query. 
     * 
     * Called when the Search button is pressed or the Enter key is pressed.
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
        if (people.size() == 0) {
            JOptionPane.showMessageDialog(this, "No Search Results!");
        }
        else if (people.size() == 1) {
            try {
                Person singlePerson = new Person(((KeyValue)people.get(0)).getKey());
                parent.showProfile(singlePerson);
            }
            catch (Exception e) {
                Debug.print(e.getMessage());
            }
        }
        else {
            // Show Search Results in Filter Tree
            parent.showPeopleList(people);

            // Old way...
            //new JTempFrame(parent, people);
        }
        
        Debug.printListToStdout(people);
        this.dispose();
    }
    
    /* The javadoc should be inherited somehow */
    class SearchQueryListener implements KeyListener {
        public void keyReleased(KeyEvent evt) {}
        public void keyPressed(KeyEvent evt) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                searchAction();
            }
        }
        public void keyTyped(KeyEvent evt) {}
    }
    
}

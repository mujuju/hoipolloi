package hoipolloi;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.pagosoft.plaf.*;
import com.pagosoft.plaf.themes.*;
import java.io.*;

/**
 * Draws the main application interface.
 *
 * @author  Brandon Buck
 * @author  Brandon Tanner
 * @version 0.17 (Dec 18, 2007)
 * @since   November 10, 2006
 */
public class MainMenu extends JFrame implements ActionListener {
    
    // Data members for the JFrame
    private WatermarkPanel contentPane;
    private MainMenu THIS;
    
    /** The Properties File */
    private Properties propFile;
      
    /** Contact Info Table */
    private JTable ctnTable;
    
    /** The current person being viewed or edited */
    private Person currentPerson;
    
    
    // Themes
    // Need to install: https://pgslookandfeel.dev.java.net/
    private static final PgsTheme GRAY = ThemeFactory.createTheme("Gray", new Color(0x7997D1), new Color(0xABABAB), Color.GRAY);
    private static final PgsTheme YELLOW = ThemeFactory.createTheme("Yellow", new Color(0xCCAA53), new Color(0xABABAB), Color.BLACK);
    private static final PgsTheme GOLD = ThemeFactory.createTheme("Gold", new Color(0xFFDB29));   
    private static final PgsTheme WIN  = new PgsTheme("Win", new Color(0x6080AC), new Color(0xFFCF31), new Color(0xF9E089), new Color(0x666554), new Color(0xDCDBCB), new Color(0xF1F0E3), Color.black, Color.white, getWinCustomEntries());
    private static final VistaTheme DEFAULT = new VistaTheme();
    private static final SilverTheme SILVER = new SilverTheme();
    private static final CharcoalTheme CHARCOAL = new CharcoalTheme();
    private static final RubyTheme RUBY = new RubyTheme();
    private static final DarudeTheme DARUDE = new DarudeTheme();
    
    // Theme buttons
    private JRadioButtonMenuItem grayTheme;
    private JRadioButtonMenuItem yellowTheme;
    private JRadioButtonMenuItem rubyTheme;
    private JRadioButtonMenuItem goldTheme;
    private JRadioButtonMenuItem winTheme;
    private JRadioButtonMenuItem defaultTheme;
    private JRadioButtonMenuItem silverTheme;
    private JRadioButtonMenuItem charcoalTheme;
    private JRadioButtonMenuItem darudeTheme;
    
    // All of the Buttons
    private JButton quickAddButton;
    private JButton addIndButton;
    private JButton addFamButton;
    private JButton addBusButton;
    private JButton cancelButton;
    private JButton btnAddContact;
    
    /** Creates a new instance of MainMenu */
    public MainMenu() {
        // Basic Information
        THIS = this;
        
        this.currentPerson = null;
        
        contentPane = new WatermarkPanel("wtlogo.png");
        contentPane.setLayout(new BorderLayout());
        contentPane.setOpaque(false);
        
        setTitle("Hoi Polloi");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("group.png"));
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(contentPane.getLocation());
        addWindowListener(new HPWindowListener());
        addComponentListener(new HPComponentListener());
        
        // JMenuBar - Start
            ImageIcon blankIcon = new ImageIcon(getClass().getClassLoader().getResource("blank.png"));
            ImageIcon newIcon = new ImageIcon(getClass().getClassLoader().getResource("group_add.png"));
            ImageIcon openIcon = new ImageIcon(getClass().getClassLoader().getResource("folder_user.png"));
            ImageIcon closeIcon = new ImageIcon(getClass().getClassLoader().getResource("group_delete.png"));
            ImageIcon saveIcon = new ImageIcon(getClass().getClassLoader().getResource("disk.png"));
            ImageIcon helpIcon = new ImageIcon(getClass().getClassLoader().getResource("help.png"));
            ImageIcon infoIcon = new ImageIcon(getClass().getClassLoader().getResource("information.png"));
            ImageIcon addIndIcon = new ImageIcon(getClass().getClassLoader().getResource("user_add.png"));
            ImageIcon addFamIcon = new ImageIcon(getClass().getClassLoader().getResource("family.png"));
            ImageIcon addCorpIcon = new ImageIcon(getClass().getClassLoader().getResource("business.png"));
            ImageIcon quickAddIcon = new ImageIcon(getClass().getClassLoader().getResource("hourglass.png"));
            ImageIcon searchIcon = new ImageIcon(getClass().getClassLoader().getResource("magnifier.png"));
            ImageIcon exitIcon = new ImageIcon(getClass().getClassLoader().getResource("exit.png"));
            ImageIcon reportIcon = new ImageIcon(getClass().getClassLoader().getResource("report.png"));
            ImageIcon importIcon = new ImageIcon(getClass().getClassLoader().getResource("import.png"));
            ImageIcon exportIcon = new ImageIcon(getClass().getClassLoader().getResource("export.png"));
            ImageIcon everyoneIcon = new ImageIcon(getClass().getClassLoader().getResource("everyone.png"));
            ImageIcon syncIcon = new ImageIcon(getClass().getClassLoader().getResource("sync.png"));
            ImageIcon printIcon = new ImageIcon(getClass().getClassLoader().getResource("printer.png"));
            ImageIcon settingsIcon = new ImageIcon(getClass().getClassLoader().getResource("settings.png"));
            ImageIcon editProfileIcon = new ImageIcon(getClass().getClassLoader().getResource("user_edit.png"));
            
            // File Menu - Start
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic('F');

            JMenuItem newItem = new JMenuItem(new MenuAction("New"));
            newItem.setIcon(newIcon);
            newItem.setMnemonic('N');
            newItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));

            JMenuItem openItem = new JMenuItem(new MenuAction("Open"));
            openItem.setIcon(openIcon);
            openItem.setMnemonic('O');
            openItem.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));

            JMenuItem closeItem = new JMenuItem(new MenuAction("Close"));
            closeItem.setIcon(closeIcon);
            closeItem.setMnemonic('C');
            closeItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));

            JMenuItem saveItem = new JMenuItem(new MenuAction("Save"));
            saveItem.setIcon(saveIcon);
            saveItem.setMnemonic('S');
            saveItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));

            JMenuItem saveAsItem = new JMenuItem(new MenuAction("Save As"));
            saveAsItem.setIcon(saveIcon);
            saveAsItem.setMnemonic('a');
            saveAsItem.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.ALT_DOWN_MASK + InputEvent.CTRL_DOWN_MASK));

            JMenuItem importItem = new JMenuItem(new MenuAction("Import"));
            importItem.setIcon(importIcon);
            importItem.setMnemonic('I');
            importItem.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.CTRL_DOWN_MASK));

            JMenuItem exportItem = new JMenuItem(new MenuAction("Export"));
            exportItem.setIcon(exportIcon);
            exportItem.setMnemonic('E');
            exportItem.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));

            JMenuItem syncItem = new JMenuItem(new MenuAction("Synchronize"));
            syncItem.setIcon(syncIcon);
            syncItem.setMnemonic('z');
            syncItem.setAccelerator(KeyStroke.getKeyStroke('z', InputEvent.CTRL_DOWN_MASK));

            JMenuItem printItem = new JMenuItem(new MenuAction("Print"));
            printItem.setIcon(printIcon);
            printItem.setMnemonic('P');
            printItem.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK));

            JMenuItem exitItem = new JMenuItem(new MenuAction("Exit"));
            exitItem.setIcon(exitIcon);
            exitItem.setMnemonic('x');
            exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_DOWN_MASK));

            fileMenu.add(newItem);
            fileMenu.addSeparator();
            fileMenu.add(openItem);
            fileMenu.add(closeItem);
            fileMenu.addSeparator();
            fileMenu.add(saveItem);
            fileMenu.add(saveAsItem);
            fileMenu.addSeparator();
            fileMenu.add(importItem);
            fileMenu.add(exportItem);
            fileMenu.add(syncItem);
            fileMenu.add(printItem);
            fileMenu.addSeparator();
            fileMenu.add(exitItem);
            // File Menu - End
            
            // Edit Menu - Start
            JMenu editMenu = new JMenu("Edit");
            editMenu.setMnemonic('E');
            
            JMenuItem editProfileItem = new JMenuItem(new MenuAction("Edit Profile"));
            editProfileItem.setIcon(editProfileIcon);
            editProfileItem.setMnemonic('E');
            editProfileItem.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));

            JMenuItem quickAddItem = new JMenuItem(new MenuAction("Quick Add"));
            quickAddItem.setIcon(quickAddIcon);
            quickAddItem.setMnemonic('Q');
            quickAddItem.setAccelerator(KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK));

            JMenuItem addIndItem = new JMenuItem(new MenuAction("Add Individual"));
            addIndItem.setIcon(addIndIcon);
            addIndItem.setMnemonic('I');

            JMenuItem addFamItem = new JMenuItem(new MenuAction("Add Family"));
            addFamItem.setIcon(addFamIcon);
            addFamItem.setMnemonic('F');

            JMenuItem addCorpItem = new JMenuItem(new MenuAction("Add Business"));
            addCorpItem.setIcon(addCorpIcon);
            addCorpItem.setMnemonic('B');
                
                // Theme Menu - Start
                    defaultTheme = new JRadioButtonMenuItem("Default");
                    defaultTheme.addActionListener(this);
                    rubyTheme = new JRadioButtonMenuItem("Ruby");
                    rubyTheme.addActionListener(this);
                    yellowTheme = new JRadioButtonMenuItem("Yellow");
                    yellowTheme.addActionListener(this);
                    grayTheme = new JRadioButtonMenuItem("Gray");
                    grayTheme.addActionListener(this);
                    goldTheme = new JRadioButtonMenuItem("Gold");
                    goldTheme.addActionListener(this);
                    silverTheme = new JRadioButtonMenuItem("Silver");
                    silverTheme.addActionListener(this);
                    winTheme = new JRadioButtonMenuItem("Win");
                    winTheme.addActionListener(this);
                    charcoalTheme = new JRadioButtonMenuItem("Charcoal");
                    charcoalTheme.addActionListener(this);
                    darudeTheme = new JRadioButtonMenuItem("Darude");
                    darudeTheme.addActionListener(this);

                    ButtonGroup themeGroup = new ButtonGroup();
                    themeGroup.add(defaultTheme);
                    themeGroup.add(rubyTheme);
                    themeGroup.add(yellowTheme);
                    themeGroup.add(grayTheme);
                    themeGroup.add(goldTheme);
                    themeGroup.add(silverTheme);
                    themeGroup.add(winTheme);
                    themeGroup.add(charcoalTheme);
                    themeGroup.add(darudeTheme);

                    JMenuItem dummyItem = new JMenuItem("Choose A Theme...");
                    
                    JMenu themeMenu = new JMenu("Themes");
                    
                    dummyItem.setFont(new Font(themeMenu.getFont().getFamily(), Font.ITALIC, themeMenu.getFont().getSize()));
                    dummyItem.setEnabled(true); // set to false to not allow clicking
                    
                    themeMenu.add(dummyItem);
                    themeMenu.addSeparator();
                    themeMenu.add(defaultTheme);
                    themeMenu.add(charcoalTheme);
                    themeMenu.add(goldTheme);
                    themeMenu.add(silverTheme);
                    themeMenu.add(rubyTheme);
                    themeMenu.add(winTheme);
                    themeMenu.add(yellowTheme);
                    themeMenu.add(grayTheme);
                    themeMenu.add(darudeTheme);
                // Theme Menu - End
                
            JMenuItem settingsItem = new JMenuItem(new MenuAction("Settings"));
            settingsItem.setIcon(settingsIcon);
            //settingsItem.setMnemonic('');

            editMenu.add(editProfileItem);
            editMenu.addSeparator();
            editMenu.add(quickAddItem);
            editMenu.addSeparator();
            editMenu.add(addIndItem);
            editMenu.add(addFamItem);
            editMenu.add(addCorpItem);
            editMenu.addSeparator();
            editMenu.add(settingsItem);
            editMenu.add(themeMenu);
            // Edit Menu - End
                
            // View Menu - Start
            JMenu viewMenu = new JMenu("View");
            viewMenu.setMnemonic('V');

            JMenuItem everyoneItem = new JMenuItem(new MenuAction("Everyone"));
            everyoneItem.setIcon(everyoneIcon);
            everyoneItem.setMnemonic('E');
            everyoneItem.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_DOWN_MASK));

            JMenuItem categoryItem = new JMenuItem(new MenuAction("By Category"));
            categoryItem.setMnemonic('C');
            categoryItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK));

            JMenuItem locationItem = new JMenuItem(new MenuAction("By Location"));
            locationItem.setMnemonic('L');
            locationItem.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.CTRL_DOWN_MASK));

            //JMenuItem typeItem = new JMenuItem(new MenuAction("By Type"));
            //typeItem.setMnemonic('T');
            //typeItem.setAccelerator(KeyStroke.getKeyStroke('T', InputEvent.CTRL_DOWN_MASK));

            viewMenu.add(everyoneItem);
            viewMenu.addSeparator();
            viewMenu.add(categoryItem);
            viewMenu.add(locationItem);
            //viewMenu.add(typeItem);
            // View Menu - End
                
            // Search Menu - Start
            JMenu searchMenu = new JMenu("Search");
            searchMenu.setMnemonic('S');

            JMenuItem searchItem = new JMenuItem(new MenuAction("Search"));
            searchItem.setIcon(searchIcon);
            searchItem.setMnemonic('e');
            searchItem.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_DOWN_MASK));

            searchMenu.add(searchItem);
            // Search Menu - End
                
            // Info Menu - Start
            JMenu infoMenu = new JMenu("Info");
            infoMenu.setMnemonic('I');

            JMenuItem statItem = new JMenuItem(new MenuAction("Statistics"));
            statItem.setIcon(reportIcon);
            statItem.setMnemonic('S');

            JMenuItem birthdayItem = new JMenuItem(new MenuAction("Birthdays"));
            birthdayItem.setMnemonic('B');

            infoMenu.add(statItem);
            infoMenu.add(birthdayItem);
            // Info Menu - End
                
            // Help Menu - Start
            JMenu helpMenu = new JMenu("Help");
            helpMenu.setMnemonic('H');

            JMenuItem aboutItem = new JMenuItem(new MenuAction("About"));
            aboutItem.setIcon(infoIcon);
            aboutItem.setMnemonic('A');

            JMenuItem helpItem = new JMenuItem(new MenuAction("Help"));
            helpItem.setIcon(helpIcon);
            helpItem.setMnemonic('H');
            helpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));

            helpMenu.add(aboutItem);
            helpMenu.addSeparator();
            helpMenu.add(helpItem);
            // Help Menu - End
                
            JMenuBar menuBar = new JMenuBar();
            menuBar.add(fileMenu);
            menuBar.add(editMenu);
            menuBar.add(viewMenu);
            menuBar.add(searchMenu);
            menuBar.add(infoMenu);
            menuBar.add(helpMenu);
        
            setJMenuBar(menuBar);
            // JMenuBar - End
        
        // Buttons - Start
            addIndButton = new JButton("Add Individual");
            addIndButton.addActionListener(this);
            addFamButton = new JButton("Add Family");
            addFamButton.addActionListener(this);
            addBusButton = new JButton("Add Business");
            addBusButton.addActionListener(this);
            quickAddButton = new JButton("Add");
            quickAddButton.addActionListener(this);
            
            cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(this);
            
            btnAddContact = new JButton("Add Contact");
        // Buttons - End
        
        // Contact Info Table
        ctnTable = new JTable(0, 2);
        
        loadPropertyFile();
            
        // Frame Content - Start

        // Frame Content - End
        
        // Center the Main Menu
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // setSize((int)(screenSize.getWidth() * .7), (int)(screenSize.getHeight() * .8));
        // setLocation((int)(screenSize.getWidth() / 2) - (getWidth() / 2), (int)(screenSize.getHeight() / 2) - (getHeight() / 2));
        setSize(1000, 800);
        setLocation(140,140);
        
        
        // Show the Main Menu
        getContentPane().add(contentPane);
        setVisible(true);
    }
    
    /**
     * Loads the Hoi Polloi properties file.
     * 
     * The filename is hp.properties
     */
    private void loadPropertyFile() {
        try {
            propFile = new Properties();
            propFile.load(new java.io.FileInputStream(new java.io.File("hp.properties")));
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(THIS, "Error loading property file!\nShutting down program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
        setTheme(propFile.getProperty("theme"));
        
        // Show the Last Profile Viewed
        try {
            showProfile(new Person(Integer.parseInt(propFile.getProperty("lastprofile"))));
        }
        catch (Exception e) {}
    }
    
    /**
     * Sets the theme for Hoi Polloi.
     * 
     * @param theme The name of the theme to set.
     */
    private void setTheme(String theme) {
        if (theme.equalsIgnoreCase("default")) {
            PlafOptions.setCurrentTheme(DEFAULT);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            defaultTheme.setSelected(true);
        }
        else if (theme.equalsIgnoreCase("gray")) {
            PlafOptions.setCurrentTheme(GRAY);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            grayTheme.setSelected(true);
        }
        else if (theme.equalsIgnoreCase("yellow")) {
            PlafOptions.setCurrentTheme(YELLOW);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            yellowTheme.setSelected(true);
        }
        else if (theme.equalsIgnoreCase("ruby")) {
            PlafOptions.setCurrentTheme(RUBY);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            rubyTheme.setSelected(true);
        }
        else if (theme.equals("darude")) {
            PlafOptions.setCurrentTheme(DARUDE);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            darudeTheme.setSelected(true);
        }
        else if (theme.equals("win")) {
            PlafOptions.setCurrentTheme(WIN);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            winTheme.setSelected(true);
        }
        else if (theme.equals("silver")) {
            PlafOptions.setCurrentTheme(SILVER);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            silverTheme.setSelected(true);
        }
        else if (theme.equals("gold")) {
            PlafOptions.setCurrentTheme(GOLD);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            goldTheme.setSelected(true);
        }
        else if (theme.equals("charcoal")) {
            PlafOptions.setCurrentTheme(CHARCOAL);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            charcoalTheme.setSelected(true);
        }
        
    }
    
    /**
     * What the hell does this do brandon?
     * 
     * @return Some kind of object array?
     */
    private static Object[] getWinCustomEntries() {
            Color s2 = new Color(0xDCDBCB);
            Color s3 = new Color(0xF1F0E3);
            Color p2 = new Color(0xF9E089);
            Color p3 = new Color(0xFFCF31);
            return new Object[] {
                    "Button.rolloverGradientStart", Color.white,
                    "Button.rolloverGradientEnd", s2,

                    "ToggleButton.rolloverGradientStart", Color.white,
                    "ToggleButton.rolloverGradientEnd", s2,

                    "ToolBar.gradientStart", s3,
                    "ToolBar.gradientEnd", s2,

                    "ToolBarButton.rolloverGradientStart", p3,
                    "ToolBarButton.rolloverGradientEnd", p2
            };
    }

    /**
     * A window listener sub-class for Hoi Polloi.
     * 
     * @author Brandon Buck
     */
    class HPWindowListener implements WindowListener {
        public void windowDeactivated(WindowEvent evt) { }
        public void windowActivated(WindowEvent evt) { }
        public void windowDeiconified(WindowEvent evt) { }
        public void windowIconified(WindowEvent evt) { }
        public void windowClosed(WindowEvent evt) { }
        public void windowClosing(WindowEvent evt) { 
            try {
                propFile.store(new java.io.FileOutputStream(new java.io.File("hp.properties")), "Property File for Hoi Polloi");
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(THIS, "Error saving property file, if you changed\n"
                        + "settings then they have not been saved.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        
        }
        public void windowOpened(WindowEvent evt) { }
    }
 
    /**
     * A component listener sub-class for Hoi Polloi.
     * 
     * @author Brandon Buck
     */
    class HPComponentListener implements ComponentListener {
        public void componentHidden(ComponentEvent evt) { }
        public void componentShown(ComponentEvent evt) { }
        public void componentMoved(ComponentEvent evt) { }
        public void componentResized(ComponentEvent evt) { }
    }
    
    /**
     * Formats a date from YYYY-MM-DD into Month day, Year.
     * 
     * Eg. Takes in 1979-12-12 and spits out December 12, 1979.
     * 
     * @param dateString The date string as YYYY-MM-DD
     * @return The date formatted by Month day, Year
     */
    private String convertDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("MMMMMMMMM dd, yyyy");
        java.sql.Date dateObject = java.sql.Date.valueOf(dateString);
        return format.format(dateObject);
    }
    
    /**
     * A test approach for editing a profile.
     * 
     * Probably won't use this approach, someone make a better idea.
     * 
     * @param person The Person's Profile to Edit.
     */
    public void editProfile(Person person) {
        removeAllComponents();
               
        String prefix = person.getPrefix() + " ";
        if (prefix.equals("null ") || prefix.equals(" "))
            prefix = "";
        
        String firstName =  person.getFirstName();
        if (firstName == null || firstName.equals(""))
            firstName = "";
        
        String middleName = " " + person.getMiddleName();
        if (middleName.equals(" null") || middleName.equals(" "))
            middleName = "";
        
        String lastName = " " + person.getLastName();
        if (lastName.equals(" null") || lastName.equals(" "))
            lastName = "";
        
        String suffix = " " + person.getSuffix();
        if (suffix.equals(" null") || suffix.equals(" "))
            suffix = "";
        
        String name = prefix + firstName + middleName + lastName + suffix;
        if (name == null || name.equals(""))
            name = "Unknown Person";

        String nickName = person.getNickName();
        if (nickName.equals("null") || nickName.equals(""))
            nickName = "";
        
        String eyeColor = person.getEyeColor();
        if (eyeColor == null || eyeColor.equals(""))
            eyeColor = "Unknown";
        
        String hairColor = person.getHairColor();
        if (hairColor == null || hairColor.equals(""))
            hairColor = "Unknown";
        
        String height = person.getHeight();
        if (height == null || height.equals(""))
            height = "Unknown";
        
        String weight = person.getWeight();
        if (weight == null || weight.equals(""))
            weight = "Unknown";
        
        String dob = person.getBirthday();
        if (dob == null || dob.equals(""))
            dob = "0000-00-00";
        
        String maidenName = person.getMaidenName();
        if (maidenName == null || maidenName.equals(""))
            maidenName = "Unknown";
        
        String gender = person.getGender();
        if (gender == null || gender.equals(""))
            gender = "Unknown";
        
        String nation = person.getNationality();
        if (nation == null || nation.equals(""))
            nation = "Unknown";
        
        String desc = person.getDescription();
        if (desc == null || desc.equals(""))
            desc = "You have not entered a description for this person yet!";
        
        String fileName = person.getPhotoFileName();
        if (fileName == null || fileName.equals(""))
            fileName = "unknown.jpg";      
        
        String lastUpdate = person.getLastUpdate();
        if (lastUpdate == null || lastUpdate.equals("") || lastUpdate.equals("0000-00-00"))
            lastUpdate = "Never";
        else
            lastUpdate = convertDate(lastUpdate);
        
        ArrayList cats = person.getCategories();
        String categories = "";
        if (cats == null || cats.isEmpty())
            categories = "This person isn't in any Categories!";
        else {
            for (int i = 0; i < cats.size(); i++) {
                KeyValue temp = (KeyValue)(cats.get(i));
                if (i == cats.size() - 1)
                    categories += temp.getValue();
                else
                    categories += temp.getValue() + ", ";
            }
        }
        
        //Debug.print("Done getting info for this person:");
            
        // Name
        // Prefix, First, Middle, Last, Suffix
        //JLabel nameLabel = new JLabel(name);
        //nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        final JTextField psnPrefixBox     = new JTextField(prefix);
        final JTextField psnFirstNameBox  = new JTextField(firstName);
        final JTextField psnMiddleNameBox = new JTextField(middleName);
        final JTextField psnLastNameBox   = new JTextField(lastName);
        final JTextField psnSuffixBox     = new JTextField(suffix);
        
        psnPrefixBox.setColumns(5);
        psnFirstNameBox.setColumns(5);
        psnMiddleNameBox.setColumns(5);
        psnLastNameBox.setColumns(5);
        psnSuffixBox.setColumns(5);
        
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(psnPrefixBox);
        namePanel.add(psnFirstNameBox);
        namePanel.add(psnMiddleNameBox);
        namePanel.add(psnLastNameBox);
        namePanel.add(psnSuffixBox);
        
        JButton btnUpdateProfile = new JButton("Update Profile");
        //JButton btnCancelEdit = new JButton("Cancel");
        
      
        namePanel.add(btnUpdateProfile);
        //namePanel.add(btnCancelEdit);
        
        // Nick Name
        //JLabel nickLabel = new JLabel(nickName);
        //nickLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        //nickLabel.setForeground(Color.GRAY);
        final JTextField nickLabel = new JTextField(nickName);
        nickLabel.setColumns(5);
        
        JPanel nickLabelPanel = new JPanel();
        nickLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nickLabelPanel.add(nickLabel);
        
        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new BorderLayout());
        nickPanel.add(nickLabelPanel, BorderLayout.SOUTH);
        
        // Add Picture
        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(fileName)));
        picLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
        
        JPanel picPanel = new JPanel();
        picPanel.setLayout(new BorderLayout());
        picPanel.add(picLabel, BorderLayout.EAST);
        
        // Add description
        final JTextArea descArea = new JTextArea();
        descArea.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        descArea.setText(desc);
        descArea.setBackground(Color.WHITE);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEnabled(true);
        descArea.setCaretPosition(desc.length());
        JScrollPane descScroller = new JScrollPane(descArea);
        descScroller.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        
        //picPanel.add(descScroller, BorderLayout.CENTER);
        
        // Add Information
        Font infoFont = new Font("Arial", Font.PLAIN, 12);
        Font boldInfoFont = new Font("Arial", Font.BOLD, 12);
        
        JLabel eyeLabel = new JLabel("Eye Color:");
        eyeLabel.setFont(boldInfoFont);
        //JLabel personEyeLabel = new JLabel(eyeColor);
        final JTextField personEyeLabel = new JTextField(eyeColor);
        //personEyeLabel.setFont(infoFont);
        JPanel eyePanel = new JPanel();
        eyePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        eyePanel.add(eyeLabel);
        eyePanel.add(personEyeLabel);
        
        JLabel hairLabel = new JLabel("Hair Color:");
        hairLabel.setFont(boldInfoFont);
        //JLabel personHairLabel = new JLabel(hairColor);
        final JTextField personHairLabel = new JTextField(hairColor);
        //personHairLabel.setFont(infoFont);
        JPanel hairPanel = new JPanel();
        hairPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        hairPanel.add(hairLabel);
        hairPanel.add(personHairLabel);
        
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(boldInfoFont);
        //JLabel personHeightLabel = new JLabel(height);
        final JTextField personHeightLabel = new JTextField(height);
        //personHeightLabel.setFont(infoFont);
        JPanel heightPanel = new JPanel();
        heightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        heightPanel.add(heightLabel);
        heightPanel.add(personHeightLabel);
        
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(boldInfoFont);
        //JLabel personWeightLabel = new JLabel(weight);
        final JTextField personWeightLabel = new JTextField(weight);
        //personWeightLabel.setFont(infoFont);
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        weightPanel.add(weightLabel);
        weightPanel.add(personWeightLabel);   
        
        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setFont(boldInfoFont);
        //JLabel personDobLabel = new JLabel(dob);
        //personDobLabel.setFont(infoFont);
        
        final JComboBox monthBox = new JComboBox();
        monthBox.setFont(new Font("Arial", Font.PLAIN, 11));
        final JComboBox dayBox   = new JComboBox();
        dayBox.setFont(new Font("Arial", Font.PLAIN, 11));
        final JComboBox yearBox  = new JComboBox();
        yearBox.setFont(new Font("Arial", Font.PLAIN, 11));
        
        String months[] = {"January", "February", "March", "April",
                     "May", "June", "July", "August", "September",
                     "October", "November", "December"};

        int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        Calendar cal = new GregorianCalendar();
        int yearNow = cal.get(Calendar.YEAR);
        
        for (int x=1; x < 10; x++)
            dayBox.addItem("0"+x);
        for (int i=0; i < 12; i++)
            monthBox.addItem(months[i]);
        for (int j=10; j <= 31; j++)
            dayBox.addItem(j);
        for (int k=yearNow; k >= yearNow-100; k--)
            yearBox.addItem(k);
        
        if (!dob.equals("0000-00-00")) {
            int bYear  = Integer.parseInt(dob.substring(0,4));
            int bMonth = Integer.parseInt(dob.substring(5,7));
            int bDay   = Integer.parseInt(dob.substring(8,10));
            yearBox  . setSelectedItem(bYear);
            monthBox . setSelectedIndex(bMonth-1);
            dayBox   . setSelectedItem(bDay);
            Debug.print(bYear+"-"+bMonth+"-"+bDay);
        }  
        
        JPanel dobPanel = new JPanel();
        dobPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dobPanel.add(dobLabel);
        dobPanel.add(monthBox);
        dobPanel.add(dayBox);
        dobPanel.add(yearBox);
        
        JLabel maidenLabel = new JLabel("Maiden Name:");
        maidenLabel.setFont(boldInfoFont);
        //JLabel personMaidenLabel = new JLabel(maidenName);
        final JTextField personMaidenLabel = new JTextField(maidenName);
        //personMaidenLabel.setFont(infoFont);
        JPanel maidenPanel = new JPanel();
        maidenPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        maidenPanel.add(maidenLabel);
        maidenPanel.add(personMaidenLabel);
        
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(boldInfoFont);
        //JLabel personGenderLabel = new JLabel(gender);
        //JTextField personGenderLabel = new JTextField(gender);
        //personGenderLabel.setFont(infoFont);
        final JComboBox personGenderLabel = new JComboBox();
        personGenderLabel.addItem("Male");
        personGenderLabel.addItem("Female");
        
        if (gender.equals("Male"))
            personGenderLabel.setSelectedItem("Male");
        else if (gender.equals("Female"))
            personGenderLabel.setSelectedItem("Female");
        else
            personGenderLabel.setSelectedItem("Male");
        
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        genderPanel.add(genderLabel);
        genderPanel.add(personGenderLabel);
        
        JLabel nationLabel = new JLabel("Nationality:");
        nationLabel.setFont(boldInfoFont);
        //JLabel personNationLabel = new JLabel(nation);
        final JTextField personNationLabel = new JTextField(nation);
        //personNationLabel.setFont(infoFont);
        JPanel nationPanel = new JPanel();
        nationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nationPanel.add(nationLabel);
        nationPanel.add(personNationLabel);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1));
        infoPanel.add(eyePanel);
        infoPanel.add(hairPanel);
        infoPanel.add(heightPanel);
        infoPanel.add(weightPanel);
        infoPanel.add(dobPanel);
        infoPanel.add(maidenPanel);
        infoPanel.add(genderPanel);
        infoPanel.add(nationPanel);
        
        picPanel.add(infoPanel, BorderLayout.WEST);
        
        // Lastupdate/Categories
        Font bottomFont = new Font("Arial", Font.PLAIN, 10);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        
        JLabel lastUpdateLabel = new JLabel("  Last Udated: " + lastUpdate + "  ");
        lastUpdateLabel.setFont(bottomFont);
        lastUpdateLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
        
        JLabel categoryLabel = new JLabel(categories);
        categoryLabel.setFont(bottomFont);
        
        bottomPanel.add(categoryLabel, BorderLayout.WEST);
        bottomPanel.add(lastUpdateLabel, BorderLayout.EAST);
        
        // Add to Frame
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(namePanel, BorderLayout.WEST);
        topPanel.add(nickPanel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        //centerPanel.add(infoPanel, BorderLayout.EAST);
        //centerPanel.add(descScroller, BorderLayout.CENTER);
        
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(picPanel, BorderLayout.NORTH);
        eastPanel.add(descScroller, BorderLayout.CENTER);
        
        // West Panel -- BT
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        JPanel ctnPanel = new JPanel();
        ctnPanel.setLayout(new BorderLayout());
        ctnPanel.setBorder(BorderFactory.createTitledBorder("Contact Info"));

        ArrayList contacts = person.getContacts();
        // Create Table
        JTable ctnTable = new JTable(contacts.size(), 2);
        ctnTable.getColumnModel().getColumn(0).setHeaderValue("Contact Type");
        ctnTable.getColumnModel().getColumn(1).setHeaderValue("Contact Name");
        ctnTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        ctnTable.setGridColor(Color.LIGHT_GRAY);
        // Loop to Populate Table
        for (int i = 0; i < contacts.size(); i++) {
            Contact ctn = (Contact)contacts.get(i);
            ctnTable.setValueAt(ctn.getContactType(),i,0);
            ctnTable.setValueAt(ctn.getContact(),i,1);
        }
        // Add Table to ScrollPane
        JScrollPane ctnScrollPane = new JScrollPane(ctnTable);
        ctnPanel.add(ctnScrollPane, BorderLayout.NORTH);
        westPanel.add(ctnPanel, BorderLayout.NORTH);
        
        // Address Box
        JPanel adrPanel = new JPanel();
        adrPanel.setLayout(new FlowLayout());
        adrPanel.setBorder(BorderFactory.createTitledBorder("Locations"));
        JLabel adrLabel = new JLabel("Select Address Label: ");
        JComboBox addressBox = new JComboBox();
        ArrayList addressList = person.getAddresses();
        if (addressList != null && !addressList.isEmpty()) {
            for (Object value : addressList) {
                addressBox.addItem(((Address)value).getAddressLabel());
            }
        }
        else {
            addressBox.addItem("No Addresses Found");
        }
        adrPanel.add(adrLabel);
        adrPanel.add(addressBox);
        adrPanel.add(new JButton("View"));
        westPanel.add(adrPanel);
        
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel,    BorderLayout.NORTH  );
        contentPane.add(eastPanel,   BorderLayout.EAST   );
        contentPane.add(centerPanel, BorderLayout.CENTER );
        contentPane.add(bottomPanel, BorderLayout.SOUTH  );
        contentPane.add(westPanel,   BorderLayout.WEST   );
        
        // Setup Action for Update Profile Button
        final Person noob = person;
        btnUpdateProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                noob.setPrefix(psnPrefixBox.getText());
                noob.setFirstName(psnFirstNameBox.getText());
                noob.setMiddleName(psnMiddleNameBox.getText());
                noob.setLastName(psnLastNameBox.getText());
                noob.setSuffix(psnSuffixBox.getText());
                noob.setNickName(nickLabel.getText());
                noob.setEyeColor(personEyeLabel.getText());
                noob.setHairColor(personHairLabel.getText());
                noob.setHeight(personHeightLabel.getText());
                noob.setWeight(personWeightLabel.getText());
                noob.setBirthday(yearBox.getSelectedItem().toString()+"-"+DBHPInterface.getMonthNumber(monthBox.getSelectedItem().toString())+"-"+dayBox.getSelectedItem().toString());
                noob.setMaidenName(personMaidenLabel.getText());
                noob.setGender(personGenderLabel.getSelectedItem().toString());
                noob.setNationality(personNationLabel.getText());                  
                noob.setDescription(descArea.getText());
                noob.saveToDatabase();
                showProfile(noob);
            }
        });
        
        updateWindow();
        
    }
    /**
     * Returns the current person.
     */
    public Person getCurrentPerson() {
        return currentPerson;
    }
    /**
     * Display a Person's Profile.
     *
     * @param person The Person whose profile to display.
     */
    public void showProfile(Person person) {
        this.currentPerson = person;
        
        removeAllComponents();
        
        Debug.print("Getting information on person with ID #: "+person.getPersonID());
        
        propFile.setProperty("lastprofile", person.getPersonID()+"");
        
        String prefix = person.getPrefix() + " ";
        if (prefix.equals("null ") || prefix.equals(" "))
            prefix = "";
        
        String firstName =  person.getFirstName();
        if (firstName == null || firstName.equals(""))
            firstName = "";
        
        String middleName = " " + person.getMiddleName();
        if (middleName.equals(" null") || middleName.equals(" "))
            middleName = "";
        
        String lastName = " " + person.getLastName();
        if (lastName.equals(" null") || lastName.equals(" "))
            lastName = "";
        
        String suffix = " " + person.getSuffix();
        if (suffix.equals(" null") || suffix.equals(" "))
            suffix = "";
        
        String name = prefix + firstName + middleName + lastName + suffix;
        if (name == null || name.equals(""))
            name = "Unknown Person";

        String nickName = "\"" + person.getNickName() + "\"";
        if (nickName.equals("\"null\"") || nickName.equals("\"\""))
            nickName = "";
        
        String eyeColor = person.getEyeColor();
        if (eyeColor == null || eyeColor.equals(""))
            eyeColor = "Unknown";
        
        String hairColor = person.getHairColor();
        if (hairColor == null || hairColor.equals(""))
            hairColor = "Unknown";
        
        String height = person.getHeight();
        if (height == null || height.equals(""))
            height = "Unknown";
        
        String weight = person.getWeight();
        if (weight == null || weight.equals(""))
            weight = "Unknown";
        
        String dob = person.getBirthday();
        if (dob == null || dob.equals("") || dob.equals("0000-00-00"))
            dob = "Unknown";
        else
            dob = convertDate(dob)+" ("+person.getCurrentAge()+")";
        
        String maidenName = person.getMaidenName();
        if (maidenName == null || maidenName.equals(""))
            maidenName = "Unknown";
        
        String gender = person.getGender();
        if (gender == null || gender.equals(""))
            gender = "Unknown";
        
        String nation = person.getNationality();
        if (nation == null || nation.equals(""))
            nation = "Unknown";
        
        String desc = person.getDescription();
        if (desc == null || desc.equals(""))
            desc = "You have not entered a description for this person yet!";
        
        String fileName = person.getPhotoFileName();
        Debug.print(fileName); //show filename debug
        if (fileName == null || fileName.equals(""))
            fileName = "unknown.jpg";      
        
        String lastUpdate = person.getLastUpdate();
        if (lastUpdate == null || lastUpdate.equals("") || lastUpdate.equals("0000-00-00"))
            lastUpdate = "Never";
        else
            lastUpdate = convertDate(lastUpdate);
        
        ArrayList cats = person.getCategories();
        String categories = "";
        if (cats == null || cats.isEmpty())
            categories = "This person isn't in any Categories!";
        else {
            for (int i = 0; i < cats.size(); i++) {
                KeyValue temp = (KeyValue)(cats.get(i));
                if (i == cats.size() - 1)
                    categories += temp.getValue();
                else
                    categories += temp.getValue() + ", ";
            }
        }
        
        Debug.print("Done getting info for this person:");
            
        // Name
        // Prefix, First, Middle, Last, Suffix
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(nameLabel);
        
        // Nick Name
        JLabel nickLabel = new JLabel(nickName);
        nickLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        nickLabel.setForeground(Color.GRAY);
        
        JPanel nickLabelPanel = new JPanel();
        nickLabelPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nickLabelPanel.add(nickLabel);
        
        JPanel nickPanel = new JPanel();
        nickPanel.setLayout(new BorderLayout());
        nickPanel.add(nickLabelPanel, BorderLayout.SOUTH);
        
        // Add Picture
        JLabel picLabel = new JLabel();
        picLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(fileName)));
        picLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0, Color.BLACK));
        
        JPanel picPanel = new JPanel();
        picPanel.setLayout(new BorderLayout());
        picPanel.add(picLabel, BorderLayout.EAST);
        
        picLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    HPPictureEditor.showHPPictureEditor(THIS);
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}      
        });
        
        // Add description
        JTextArea descArea = new JTextArea();
        descArea.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        descArea.setText(desc);
        descArea.setBackground(contentPane.getBackground());
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEnabled(false);
        descArea.setDisabledTextColor(Color.BLACK);
        descArea.setCaretPosition(0);
        JScrollPane descScroller = new JScrollPane(descArea);
        descScroller.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        
        //picPanel.add(descScroller, BorderLayout.CENTER);
        
        // Add Information
        Font infoFont = new Font("Arial", Font.PLAIN, 12);
        Font boldInfoFont = new Font("Arial", Font.BOLD, 12);
        
        JLabel eyeLabel = new JLabel("Eye Color:");
        eyeLabel.setFont(boldInfoFont);
        JLabel personEyeLabel = new JLabel(eyeColor);
        personEyeLabel.setFont(infoFont);
        JPanel eyePanel = new JPanel();
        eyePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        eyePanel.add(eyeLabel);
        eyePanel.add(personEyeLabel);
        
        JLabel hairLabel = new JLabel("Hair Color:");
        hairLabel.setFont(boldInfoFont);
        JLabel personHairLabel = new JLabel(hairColor);
        personHairLabel.setFont(infoFont);
        JPanel hairPanel = new JPanel();
        hairPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        hairPanel.add(hairLabel);
        hairPanel.add(personHairLabel);
        
        JLabel heightLabel = new JLabel("Height:");
        heightLabel.setFont(boldInfoFont);
        JLabel personHeightLabel = new JLabel(height);
        personHeightLabel.setFont(infoFont);
        JPanel heightPanel = new JPanel();
        heightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        heightPanel.add(heightLabel);
        heightPanel.add(personHeightLabel);
        
        JLabel weightLabel = new JLabel("Weight:");
        weightLabel.setFont(boldInfoFont);
        JLabel personWeightLabel = new JLabel(weight);
        personWeightLabel.setFont(infoFont);
        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        weightPanel.add(weightLabel);
        weightPanel.add(personWeightLabel);   
        
        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setFont(boldInfoFont);
        JLabel personDobLabel = new JLabel(dob);
        personDobLabel.setFont(infoFont);
        JPanel dobPanel = new JPanel();
        dobPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dobPanel.add(dobLabel);
        dobPanel.add(personDobLabel);
        
        JLabel maidenLabel = new JLabel("Maiden Name:");
        maidenLabel.setFont(boldInfoFont);
        JLabel personMaidenLabel = new JLabel(maidenName);
        personMaidenLabel.setFont(infoFont);
        JPanel maidenPanel = new JPanel();
        maidenPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        maidenPanel.add(maidenLabel);
        maidenPanel.add(personMaidenLabel);
        
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(boldInfoFont);
        JLabel personGenderLabel = new JLabel(gender);
        personGenderLabel.setFont(infoFont);
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        genderPanel.add(genderLabel);
        genderPanel.add(personGenderLabel);
        
        JLabel nationLabel = new JLabel("Nationality:");
        nationLabel.setFont(boldInfoFont);
        JLabel personNationLabel = new JLabel(nation);
        personNationLabel.setFont(infoFont);
        JPanel nationPanel = new JPanel();
        nationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        nationPanel.add(nationLabel);
        nationPanel.add(personNationLabel);
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(8, 1));
        infoPanel.add(eyePanel);
        infoPanel.add(hairPanel);
        infoPanel.add(heightPanel);
        infoPanel.add(weightPanel);
        infoPanel.add(dobPanel);
        infoPanel.add(maidenPanel);
        infoPanel.add(genderPanel);
        infoPanel.add(nationPanel);
        
        picPanel.add(infoPanel, BorderLayout.WEST);
        
        // Lastupdate/Categories
        Font bottomFont = new Font("Arial", Font.PLAIN, 10);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
        
        JLabel lastUpdateLabel = new JLabel("  Last Udated: " + lastUpdate + "  ");
        lastUpdateLabel.setFont(bottomFont);
        lastUpdateLabel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
        
        JLabel categoryLabel = new JLabel(categories);
        categoryLabel.setFont(bottomFont);
        
        bottomPanel.add(categoryLabel, BorderLayout.WEST);
        bottomPanel.add(lastUpdateLabel, BorderLayout.EAST);
        
        // Add to Frame
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(namePanel, BorderLayout.WEST);
        topPanel.add(nickPanel, BorderLayout.EAST);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        //centerPanel.add(infoPanel, BorderLayout.EAST);
        //centerPanel.add(descScroller, BorderLayout.CENTER);
        
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(picPanel, BorderLayout.NORTH);
        eastPanel.add(descScroller, BorderLayout.CENTER);
        
        // West Panel -- BT
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BorderLayout());
        JPanel ctnPanel = new JPanel();
        ctnPanel.setLayout(new BorderLayout());
        ctnPanel.setBorder(BorderFactory.createTitledBorder("Contact Info"));

        
        // Create Table
        ArrayList contacts = person.getContacts();
        final DefaultTableModel model = new DefaultTableModel();
        ctnTable = new JTable(model);
        
        model.addColumn("Contact Type");
        model.addColumn("Contact Name");
        
        ctnTable.setPreferredScrollableViewportSize(new Dimension(400, 200));
        ctnTable.setGridColor(Color.LIGHT_GRAY);
        
        // Loop to Populate Contacts Table
        for (int i = 0; i < contacts.size(); i++) {
            Contact ctn = (Contact)contacts.get(i);
            model.addRow(new Object[] {ctn.getContactType(), ctn.getContact()});
        }
        
        // Set Col Widths based on Content
        ColumnResizer.adjustColumnPreferredWidths(ctnTable);
        
        // Add Table to ScrollPane
        JScrollPane ctnScrollPane = new JScrollPane(ctnTable);
        ctnPanel.add(ctnScrollPane, BorderLayout.NORTH);
        
        
        // New Contact Stuff ***********************************
        JPanel newContactPanel = new JPanel();
        newContactPanel.setLayout(new FlowLayout());
        
        final JComboBox ctnTypeComboBox = new JComboBox();
        ctnTypeComboBox.setRenderer(new HPCellRenderer());
        
        ArrayList ctnTypes = Contact.getContactTypes();

        // Populate the JComboBox (Contact Types)
        for (Object value : ctnTypes) {
            ctnTypeComboBox.addItem(value);
        }
        
        // todo: need to not use absolute width somehow
        final JTextField ctnText = new JTextField(15);
        
        // not sure, but inner class needs this to be final, so i create a copy
        final Person noob = person;
        
        // Action of Add Contact
        btnAddContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
                // do stuff for add new contact
                if (ctnText != null) {
                    // Contact Type ID (in Database)
                    int ctnTypeID = ((KeyValue)ctnTypeComboBox.getSelectedItem()).getKey();
                                 
                    noob.addContact(ctnTypeID, ctnText.getText());
                    noob.saveToDatabase();
                    
                    //JOptionPane.showMessageDialog(THIS, "Success, new contact added!");
                    
                    // Update the contacts table
                    model.addRow(new Object[]{((KeyValue)ctnTypeComboBox.getSelectedItem()).getValue(), ctnText.getText()});
                    ctnText.setText(null);
                    ctnTypeComboBox.setSelectedIndex(0);
                }               
            }
        });
        
        ctnText.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_ENTER) {
                    // do stuff for add new contact
                    if (ctnText != null) {
                        // Contact Type ID (in Database)
                        int ctnTypeID = ((KeyValue)ctnTypeComboBox.getSelectedItem()).getKey();

                        noob.addContact(ctnTypeID, ctnText.getText());
                        noob.saveToDatabase();

                        //JOptionPane.showMessageDialog(THIS, "Success, new contact added!");

                        // Update the contacts table
                        model.addRow(new Object[]{((KeyValue)ctnTypeComboBox.getSelectedItem()).getValue(), ctnText.getText()});
                        ctnText.setText(null);
                        ctnTypeComboBox.setSelectedIndex(0);
                    }
                }
            }
            public void keyReleased(KeyEvent evt) {}
            public void keyTyped(KeyEvent evt) {}
        });
        
        newContactPanel.add(ctnTypeComboBox);
        newContactPanel.add(ctnText);
        newContactPanel.add(btnAddContact);
        //newContactPanel.add(new JButton("Delete"));
               
        ctnPanel.add(newContactPanel, BorderLayout.SOUTH);
        
        // End New Contact Stuff **********************************
        
        
        westPanel.add(ctnPanel, BorderLayout.NORTH);
        
        // Address Box
        JPanel adrPanel = new JPanel();
        adrPanel.setLayout(new FlowLayout());
        adrPanel.setBorder(BorderFactory.createTitledBorder("Locations"));
        JLabel adrLabel = new JLabel("Select Address Label: ");
        JComboBox addressBox = new JComboBox();
        ArrayList addressList = person.getAddresses();
        if (addressList != null && !addressList.isEmpty()) {
            for (Object value : addressList) {
                addressBox.addItem(((Address)value).getAddressLabel());
            }
        }
        else {
            addressBox.addItem("No Addresses Found");
        }
        adrPanel.add(adrLabel);
        adrPanel.add(addressBox);
        adrPanel.add(new JButton("View"));
        westPanel.add(adrPanel);
        
        
        contentPane.setLayout(new BorderLayout());
        contentPane.add(topPanel,    BorderLayout.NORTH  );
        contentPane.add(eastPanel,   BorderLayout.EAST   );
        contentPane.add(centerPanel, BorderLayout.CENTER );
        contentPane.add(bottomPanel, BorderLayout.SOUTH  );
        contentPane.add(westPanel,   BorderLayout.WEST   );
        
        updateWindow();
    }
    
    /** Clears the main application window screen and shows the HP logo in the center.*/
    public void removeAllComponents() {
        contentPane.removeAll();
    }
    
    public void updateWindow() {
        update(getGraphics());
        setVisible(true);
    }
    
    /**
     * Clears and updates the main application window.
     */
    public void clearWindow() {
        removeAllComponents();
        updateWindow();
    }
    
    class FocusSelectAll implements FocusListener {
        public void focusGained(FocusEvent e) { 
            JTextField source = (JTextField)(e.getSource());
            
            source.select(0, source.getText().length());
        }
        
        public void focusLost(FocusEvent e) { }
    }
    
    /** The Profile Quick-add Feature */
    public void quickAdd() {
        
        // Since they are no longer viewing somebody, set this to null.
        this.currentPerson = null;
                
        removeAllComponents();
        
        contentPane.setLayout(new BorderLayout());
        
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BorderLayout());
        JPanel comboPanel = new JPanel();
        JPanel quickAddInfo = new JPanel();
        JPanel buttonPanel = new JPanel();
        
        JComboBox categoryBox = new JComboBox();
        categoryBox.addItem("Select Category");
        categoryBox.addItem("New Category");
        categoryBox.addItem("----------------");
        
        JTextField firstNameField = new JTextField(15);
        firstNameField.addFocusListener(new FocusSelectAll());
        JTextField lastNameField = new JTextField(15);
        lastNameField.addFocusListener(new FocusSelectAll());
        JTextField newCatField = new JTextField(15);
        newCatField.addFocusListener(new FocusSelectAll());
        newCatField.setText("New Category Name");
        ArrayList tempCatList = DBHPInterface.getListOfCategories();
        if (!(tempCatList == null) || !(tempCatList.isEmpty())) {
            for (Object value : tempCatList) {
                categoryBox.addItem(((KeyValue)value).getValue());
            }
        }
        JTextArea descArea = new JTextArea(5, 20);
        
        final JPanel INFO_PANEL_REF = quickAddInfo;
        final JPanel BUTTON_PANEL_REF = buttonPanel;
        final JTextField FIRST_NAME_REF = firstNameField;
        final JTextField LAST_NAME_REF = lastNameField;
        final JTextField NEWCAT_TEXT_REF = newCatField;
        final JComboBox CAT_COMBO_REF = categoryBox;
        final JTextArea DESC_REF = descArea;
        
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Select Profile Type");
        comboBox.addItem("--------------------");
        comboBox.addItem("Individual");
        comboBox.addItem("Family");
        comboBox.addItem("Business");
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                JPanel inputPanel = INFO_PANEL_REF;
                JPanel buttonPanel = BUTTON_PANEL_REF;

                String selected = (String)(e.getItem());
                
                if (selected.equals("Individual")) {
                    inputPanel.removeAll();
                    buttonPanel.removeAll();
                    
                    JTextField firstName = FIRST_NAME_REF;
                    JTextField lastName = LAST_NAME_REF;
                    JTextArea descArea = DESC_REF;
                    
                    JComboBox catCombo = CAT_COMBO_REF;
                    catCombo.addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            if (((String)(e.getItem())).equals("New Category"))
                                NEWCAT_TEXT_REF.setEditable(true);
                            else
                                NEWCAT_TEXT_REF.setEditable(false);
                        }
                    });
                    
                    JTextField newCatField = NEWCAT_TEXT_REF;
                    newCatField.setEditable(false);
                    
                    JButton addButton = new JButton("Add Profile");
                    addButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            boolean canAdd = true;
                            
                            String firstName = FIRST_NAME_REF.getText().trim();
                            String lastName = LAST_NAME_REF.getText().trim();
                            String catAdd = (String)(CAT_COMBO_REF.getSelectedItem());
                            String newCatName = NEWCAT_TEXT_REF.getText().trim();
                            String description = DESC_REF.getText().trim();
                            
                            if (firstName == null || firstName.equals(""))
                                canAdd = false;
                            if (lastName == null || lastName.equals(""))
                                canAdd = false;
                            if (catAdd.equals("Select Category") || catAdd.equals("----------------"))
                                canAdd = false;
                            if (catAdd.equals("New Category") && (newCatName == null || newCatName.equals("")))
                                canAdd = false;
                            
                            if (canAdd) {
                                Person newPerson = new Person();
                                KeyValue category;
                                int catID = -1;
                                if (catAdd.equals("New Category")) {
                                    catID = DBHPInterface.addNewCategoryToDB(newCatName);
                                    //int catID = DBHPInterface.getIDOfCategory(newCatName);
                                    //category = new KeyValue(catID, newCatName);
                                }
                                else {
                                    catID = DBHPInterface.getIDOfCategory(catAdd);
                                    //category = new KeyValue(catID, catAdd);
                                }
                                
                                
                                //newPerson.addCategory(category);
                                newPerson.setFirstName(firstName);
                                newPerson.setLastName(lastName);
                                newPerson.setDescription(description);
                                boolean success = newPerson.saveToDatabase();
                                
                                JOptionPane.showMessageDialog(THIS, newPerson.addCategory(catID));
                                
                                if (success)
                                    JOptionPane.showMessageDialog(THIS, firstName + " " + lastName + " was added to the database!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                                else
                                    JOptionPane.showMessageDialog(THIS, "There was a problem saving this person to the database, please try again.", "Failure", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                                JOptionPane.showMessageDialog(THIS, "The First Name, Last Name, and Category must have valid entries!", "Cannot Add", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    JButton clearButton = new JButton("Clear Form");
                    clearButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            FIRST_NAME_REF.setText("");
                            LAST_NAME_REF.setText("");
                            DESC_REF.setText("");
                            CAT_COMBO_REF.setSelectedIndex(0);
                            NEWCAT_TEXT_REF.setText("New Category Name");
                        }
                    });
                    JButton exitButton = new JButton("Exit Quick-Add");
                    exitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            THIS.clearWindow();
                        }
                    });
                    
                    JPanel newCatPanel = new JPanel();
                    newCatPanel.setLayout(new VerticalFlowLayout());
                    newCatPanel.add(catCombo);
                    newCatPanel.add(newCatField);
                    
                    JPanel firstNamePanel = new JPanel();
                    firstNamePanel.add(new JLabel("First Name:"));
                    firstNamePanel.add(firstName);
                    
                    JPanel lastNamePanel = new JPanel();
                    lastNamePanel.add(new JLabel("Last Name:"));
                    lastNamePanel.add(lastName);
                    
                    JPanel namePanel = new JPanel();
                    namePanel.setLayout(new VerticalFlowLayout());
                    namePanel.add(firstNamePanel);
                    namePanel.add(lastNamePanel);
                    
                    JPanel descPanel = new JPanel();
                    descPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK), "Description:"));
                    JScrollPane descScroller = new JScrollPane(descArea);
                    descPanel.add(descScroller);
                   
                    inputPanel.setLayout(new VerticalFlowLayout());
                    inputPanel.add(namePanel);
                    inputPanel.add(newCatPanel);
                    inputPanel.add(descPanel);
                    
                    buttonPanel.add(addButton);
                    buttonPanel.add(clearButton);
                    buttonPanel.add(exitButton);
                    
                    updateWindow();
                }
            }
        });
        
        comboPanel.add(comboBox);
        
        addPanel.add(comboPanel, BorderLayout.NORTH);
        addPanel.add(quickAddInfo, BorderLayout.CENTER);
        addPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        contentPane.add(addPanel, BorderLayout.NORTH);
        
        updateWindow();
    }
    
    public void actionPerformed(ActionEvent evt) {
        boolean button = true;
        Component source = null;
        try {
            source = (JButton)(evt.getSource());
        } catch (Exception e) {
            button = false;
        }
        
        if (button) {
            // Button Pressed
            if (source == cancelButton) {
                contentPane.removeAll();
                THIS.setVisible(true);
            }
        }
        
        // Theme Buttons
        if (defaultTheme.isSelected()) {
            PlafOptions.setCurrentTheme(DEFAULT);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "default");
            clearWindow();
        }
        else if (goldTheme.isSelected()) {
            PlafOptions.setCurrentTheme(GOLD);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "gold");
            clearWindow();
        }
        else if (silverTheme.isSelected()) {
            PlafOptions.setCurrentTheme(SILVER);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "silver");
            clearWindow();
        }
        else if (rubyTheme.isSelected()) {
            PlafOptions.setCurrentTheme(RUBY);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "ruby");
            clearWindow();
        }
        else if (darudeTheme.isSelected()) {
            PlafOptions.setCurrentTheme(DARUDE);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "darude");
            clearWindow();
        }
        else if (yellowTheme.isSelected()) {
            PlafOptions.setCurrentTheme(YELLOW);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "yellow");
            clearWindow();
        }
        else if (grayTheme.isSelected()) {
            PlafOptions.setCurrentTheme(GRAY);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "gray");
            clearWindow();
        }
        else if (winTheme.isSelected()) {
            PlafOptions.setCurrentTheme(WIN);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "win");
            clearWindow();
        }
        else if (charcoalTheme.isSelected()) {
            PlafOptions.setCurrentTheme(CHARCOAL);
            PlafOptions.setAsLookAndFeel();
            PlafOptions.updateAllUIs();
            propFile.setProperty("theme", "charcoal");
            clearWindow();
        }
    }
        
    class MenuAction extends AbstractAction {
        public MenuAction(String n) {
            super(n);
        }
        
        public void actionPerformed(ActionEvent evt) {
            String selection = (String)getValue(Action.NAME);
            
            if (selection.equals("Exit"))
                System.exit(0);
            else if (selection.equals("Search")) {
                new SearchBox(THIS).setVisible(true);
            }
            else if (selection.equals("Statistics")) {
                Stats.showStats(THIS);
            }
            else if (selection.equals("Everyone")) {
                new JTempFrame(THIS, DBHPInterface.getListOfPeopleByLastName());
            }
            else if (selection.equals("Edit Profile")) {
                if (currentPerson != null) {
                    editProfile(currentPerson);
                }
            }
            else if (selection.equals("Quick Add")) {
                quickAdd();
            }
            else if (selection.equals("About")) {
                About.showAboutWindow(THIS);
            }
            else if (selection.equals("Birthdays")) {
                new JTempFrame(THIS, DBHPInterface.getBirthdaysThisMonth());
            }
        }
    }
}

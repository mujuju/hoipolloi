package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.io.*;

/**
 * The Import Box/Window.
 * This class isn't finished yet.
 *
 * @author  Brandon Tanner
 * @version 1.0 (March 8, 2008)
 */
public class Import extends JFrame implements ActionListener {

    /** The parent frame object */
    private MainMenu parent;

    /** The Search Button */
    private JButton btnImport;

    /** The Exit Button */
    private JButton btnExit;

    private JList groupList;
    private JList fieldList;
    private JScrollPane groupSP;
    private JScrollPane fieldSP;
    private DefaultListModel groupListModel;
    private DefaultListModel fieldListModel;
    private JPanel instrPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel groupPanel;
    private JPanel fieldPanel;
    private JPanel buttonPanel;
    private JPanel optionPanel;

    private JCheckBox st509Check;


    public Import(MainMenu parent) {
        this.parent = parent;
        southPanel = new JPanel();
        buttonPanel = new JPanel();
        btnImport = new JButton("Import");
        btnExit = new JButton("Exit");
        btnImport.addActionListener(this);
        btnExit.addActionListener(this);

        // Set some parameters of the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Import CSV Data");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("export.png"));
        setIconImage(icon.getImage());
        setAlwaysOnTop(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setResizable(true);

        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        // Start Creating Window

        buttonPanel.add(btnImport);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(btnExit);

        
        // End Creating Window
        southPanel.add(buttonPanel);

        cp.add(southPanel, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);

        // Set the location of the Export Window relative to the MainMenu
        // --CENTER--
        Point exportBoxLocation = new Point();
        double exportBoxX = parent.getLocation().getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        double exportBoxY = parent.getLocation().getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));
        exportBoxLocation.setLocation(exportBoxX, exportBoxY);
        this.setLocation(exportBoxLocation);
        // --END CENTER--

    }

    private void getFile() {
        ArrayList <KeyValue> people = DBHPInterface.getListOfPeopleByLastName();
        String[] columns = "Name#FirstName#Phone No.#Home Tel.#Office Tel.#Fax No#Other Tel.#E-Mail#URL#Memo".split("#");
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("yourfile2.csv"), ',');
            // feed in your array (or convert your data to an array)
            //String[] entries = "first#second#third".split("#");
            writer.writeNext(columns);
            for (KeyValue p : people) {
                Person person = new Person(p.getKey());
                if (!person.getContacts().isEmpty()) {
                    String cellPhone = person.getCellPhone();
                    String homePhone = person.getHomePhone();
                    if (!cellPhone.isEmpty() || !homePhone.isEmpty()) {
                        // Tested: Spaces, Parenthesis, Dashes OK in Phone Numbers.
                        // Tested: Not having cellphone but having home phone.
                        // Tested: Spaces OK in Names.
                        String data = person.getLastName()+"#"+person.getFirstName()+"#"+person.getCellPhone()+"#"+person.getHomePhone()+"# # # # # # #";
                        String[] line = data.split("#");
                        writer.writeNext(line);
                    }
                }
            }
            writer.close();
        }
        catch (Exception e) {
            Debug.print("Failure in writeAll");
            Debug.print(e.getMessage());
        }


    }

    /**
     * Parse a CSV File to Import.
     *
     * @param filePath
     */
    private void readFile(String filePath) {
        // Samsung SGH-T509 Export Format
        // "Name","FirstName","Phone No.","Home Tel.","Office Tel.","Fax No","Other Tel.","E-Mail","URL","Memo"
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                if (!nextLine[1].equals("") && !nextLine[1].equals("FirstName")) {
                    Debug.print("Importing: "+nextLine[0]+" "+nextLine[1]);
                }
            }
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
        }
    }

    /** Invoked when an action occurs. */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnImport) {
            getFile();
            String filePath = "";
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Select CSV File to Import");
                chooser.setFileSelectionMode(0);
                int doit = chooser.showOpenDialog(this);
                if (doit == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                }
            }
            catch (Exception e) {
                Debug.print("An error occured selecting the file to export.");
                Debug.print(e.getMessage());
            }

            if (!filePath.equals("")) {
                readFile(filePath);
            }
        }
        else if (pressedButton == btnExit) {
            this.dispose();
        }
    }

}


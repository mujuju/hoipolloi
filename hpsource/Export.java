package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * The Export Box/Window.
 * 
 * @author  Brandon Tanner
 * @version 1.0 (March 8, 2008)
 */
public class Export extends JFrame implements ActionListener {
    
    /** The parent frame object */
    private MainMenu parent;
    
    /** The Search Button */
    private JButton btnExport;
    
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
    
    public Export(MainMenu parent) {
        this.parent = parent;
        instrPanel = new JPanel();
        northPanel = new JPanel();
        southPanel = new JPanel();
        groupPanel = new JPanel();
        fieldPanel = new JPanel();
        buttonPanel = new JPanel();
        groupListModel = new DefaultListModel();
        fieldListModel = new DefaultListModel();
        btnExport = new JButton("Export");
        btnExit = new JButton("Done");
        btnExport.addActionListener(this);
        btnExit.addActionListener(this);
        
        // Set some parameters of the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Export Categories");
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("export.png"));
        setIconImage(icon.getImage());
        setAlwaysOnTop(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setResizable(true);
        instrPanel.setBorder(BorderFactory.createTitledBorder(""));
        northPanel.setBorder(BorderFactory.createTitledBorder("Choose"));
        southPanel.setBorder(BorderFactory.createTitledBorder("Export"));
        
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        // Start Creating Window
        
        // Create Group List Data
        ArrayList groups = DBHPInterface.getListOfCategories();
        for (Object c : groups) {
            groupListModel.addElement(c);
        }
        
        //Create the Group List and put it in a scroll pane.
        groupList = new JList(groupListModel);
        groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        groupList.setSelectedIndex(0);
        groupList.setVisibleRowCount(8);
        groupSP = new JScrollPane(groupList);
        groupPanel.add(groupSP);
        
        // Create Field List Data
        ArrayList fields = Person.getColumnsForExport();
        for (Object c : fields) {
            fieldListModel.addElement(c);
        }
        
        //Create the Field List and put it in a scroll pane.
        fieldList = new JList(fieldListModel);
        fieldList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        fieldList.setSelectedIndex(0);
        fieldList.setVisibleRowCount(8);
        fieldSP = new JScrollPane(fieldList);
        fieldPanel.add(fieldSP);
        
        buttonPanel.add(btnExport);
        buttonPanel.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(btnExit);
        
        JLabel instructions = new JLabel("<html>Select the category on the left (only one may be selected at a time)<br> and the colunns you want to export on the right. To select multiple <br>columns on the right, hold down the Control key.</html>");
        // End Creating Window
        
        
        instrPanel.add(instructions);
        northPanel.add(groupPanel);
        northPanel.add(fieldPanel);
        southPanel.add(buttonPanel);
        
        cp.add(instrPanel, BorderLayout.NORTH);
        cp.add(northPanel, BorderLayout.CENTER);
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
    
    /** Invoked when an action occurs. */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == btnExport) {
            String filePath = "";
            try {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Export People to Excel");
                chooser.setFileSelectionMode(0);
                chooser.setSelectedFile(new File("People.xls"));
                int doit = chooser.showSaveDialog(this);
                if (doit == JFileChooser.APPROVE_OPTION) {
                    filePath = chooser.getSelectedFile().getAbsolutePath();
                }
            }
            catch (Exception e) {
                Debug.print("An error occured selecting the file to export.");
                Debug.print(e.getMessage());
            }
            ExcelExporter exporter = new ExcelExporter();
            exporter.exportCategory(groupList.getSelectedValue(), fieldList.getSelectedValues(), filePath);
            JOptionPane.showMessageDialog(this, "Success!");
        }
        else if (pressedButton == btnExit) {
            this.dispose();
        }
    }
    
}

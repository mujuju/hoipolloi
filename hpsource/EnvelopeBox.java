package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

import javax.print.*;
import java.awt.print.*;


/**
 * The Print Envelopes Window.
 * 
 * Specify From Address, To Person or Group, Address Type, Printer, Envelope Size, Font etc.
 * 
 * Should have it tell them how many envelopes will be printed after they select a group.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Mar 24, 2008)
 * @since   March 24, 2008
 */
public class EnvelopeBox extends JDialog implements ActionListener, MouseListener {
    
    /** The Exit Button */
    private JButton closeButton;
    
    /** The Print Button */
    private JButton printButton;
    
    private JLabel dataLabel;
    private JLabel orLabel;
    private JLabel printerLabel;
    private JLabel fontLabel;
    private JLabel envelopeLabel;
    
    private JTextArea fromTA;
    
    private JComboBox personComboBox;
    
    private JComboBox groupComboBox;
    
    private JComboBox addressComboBox;
    private JComboBox envelopeComboBox;
    
    private JComboBox printerComboBox;
    private JTextField fontTextField;
    
    /** Constructor */
    public EnvelopeBox(Frame owner) {
        super(owner);
        this.setTitle("Print");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setResizable(false);
        
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel(), eastPanel = new JPanel(), westPanel = new JPanel(), southPanel = new JPanel(), centerPanel = new JPanel();
        
        northPanel.setLayout(new GridLayout(2,2));
        westPanel.setLayout(new GridLayout(3,2));
        eastPanel.setLayout(new FlowLayout());
        southPanel.setLayout(new FlowLayout());
        centerPanel.setLayout(new FlowLayout());
        
        northPanel.setBorder(BorderFactory.createTitledBorder("Data to Print"));
        westPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        eastPanel.setBorder(BorderFactory.createTitledBorder("Address Type"));
        southPanel.setBorder(BorderFactory.createTitledBorder("."));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Return Address"));
        
        dataLabel    = new JLabel("Choose Person", JLabel.TRAILING);
        orLabel      = new JLabel("or Choose Group", JLabel.TRAILING);
        printerLabel = new JLabel("Printer", JLabel.TRAILING);
        envelopeLabel = new JLabel("Envelope Size", JLabel.TRAILING);
        fontLabel = new JLabel("Font Size", JLabel.TRAILING);
        fontTextField = new JTextField("12");
        personComboBox = new JComboBox();
        groupComboBox = new JComboBox();
        addressComboBox = new JComboBox();
        envelopeComboBox = new JComboBox();
        printerComboBox = new JComboBox();
        fromTA = new JTextArea();
        fromTA.setColumns(10);
        fromTA.setRows(5);
        fromTA.setEditable(true);
        fromTA.setLineWrap(false);
        
        /* Populate People Combo Box */
        personComboBox.addItem(new KeyValue(-1, "Select Person..."));
        for (Object value : DBHPInterface.getListOfPeopleByLastName()) {
            personComboBox.addItem(value);
        }
        
        /* Populate Category Combo Box */
        groupComboBox.addItem(new KeyValue(-1, "Select Category..."));
        for (Object value : DBHPInterface.getListOfCategories()) {
            groupComboBox.addItem(value);
        }
        
        /* Populate Address Combo Box */
        for (Object value : AddressType.getAddressTypes()) {
            addressComboBox.addItem(value);
        }
        
        /* Populate Printers Combo Box */
        PrintService[] printers = PrinterJob.lookupPrintServices();
        for (PrintService printer : printers) {
            printerComboBox.addItem(printer.getName());
        }
        
        envelopeComboBox.addItem("A2");
        envelopeComboBox.addItem("A6");
        envelopeComboBox.addItem("A7");
        envelopeComboBox.addItem("No. 6¾");
        envelopeComboBox.addItem("No. 9");
        envelopeComboBox.addItem("No. 10");
        

        
        northPanel.add(dataLabel);
        northPanel.add(personComboBox);
        northPanel.add(orLabel);
        northPanel.add(groupComboBox);
        
        centerPanel.add(fromTA);
        eastPanel.add(addressComboBox);
        
        westPanel.add(printerLabel);
        westPanel.add(printerComboBox);
        westPanel.add(envelopeLabel);
        westPanel.add(envelopeComboBox);
        westPanel.add(fontLabel);
        westPanel.add(fontTextField);
        

        printButton = new JButton("Print");
        closeButton = new JButton("Done");
        printButton.addActionListener(this);
        closeButton.addActionListener(this);
        southPanel.add(printButton);
        southPanel.add(closeButton);

        
        cp.add(northPanel, BorderLayout.NORTH);
        cp.add(westPanel,  BorderLayout.WEST);
        cp.add(eastPanel,  BorderLayout.EAST);
        cp.add(centerPanel, BorderLayout.CENTER);
        cp.add(southPanel, BorderLayout.SOUTH);
        
        this.pack();
              
        // Set the location of the About Window centered relative to the MainMenu
        // --CENTER--
        Point aboutBoxLocation = new Point();
        
        double aboutBoxX = owner.getLocation().getX() + ((owner.getWidth() / 2) - (this.getWidth() / 2));
        double aboutBoxY = owner.getLocation().getY() + ((owner.getHeight() / 2) - (this.getHeight() / 2));
        
        aboutBoxLocation.setLocation(aboutBoxX, aboutBoxY);
        this.setLocation(aboutBoxLocation);
        // --END CENTER--
        
        this.setVisible(true);
    }
    
    public void mouseExited   (MouseEvent event) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void mouseEntered  (MouseEvent event) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public void mouseReleased (MouseEvent event) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void mousePressed  (MouseEvent event) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    public void mouseClicked  (MouseEvent event) {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
    }
    
    /** Performs any actions this object has */
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == closeButton) {
            this.dispose();
        }
    }
    
    /** Displays the Print Envelopes Window */
    public static void showEnvelopeBox(Frame owner) {
        new EnvelopeBox(owner);
    }

}

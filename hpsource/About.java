package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;


/**
 * The About Hoi Polloi Window
 *
 * @author Brandon Tanner
 * @version 1.1
 * @since December 12, 2006
 */
public class About extends JDialog implements ActionListener, MouseListener {
    
    protected JButton closeButton;
    
    protected JLabel skype1, skype2;
    
    protected String definition = "Hoi polloi (Greek:), an expression meaning \"the many\" in both Ancient Greek and Modern Greek, is used in English to denote \"the masses\" or \"the people.\"";
    
    /** Creates a new instance of About */
    public About(Frame owner) {
        super(owner);
        this.setTitle("About Hoi Polloi");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setResizable(false);
        
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        
        JPanel northPanel = new JPanel(), eastPanel = new JPanel(), westPanel = new JPanel(), southPanel = new JPanel();
        
        northPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER));
        westPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER));
        eastPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER));
        
        JLabel name1, name2, desc, spec1, spec2, def;
        name1  = new JLabel("Name: Brandon Tanner");
        name2  = new JLabel("Name: Brandon Buck");
        skype1 = new JLabel("<html><font color='green'>Skype</font>: <a href='skype:orangepyrite?chat'>orangepyrite</a></html>");
        skype2 = new JLabel("<html><font color='green'>Skype</font>: <a href='skype:lord.izuriel?chat'>lord.izuriel</a></html>");
        desc   = new JLabel("<html>A people database written by the Brandon's for <i>the masses</i></html>");
        def    = new JLabel(definition);
        
        spec1 = new JLabel("Specialty: Engine");
        spec2 = new JLabel("Specialty: Visual");
        
        skype1.setToolTipText("Click to Chat on Skype with Brandon Tanner");
        skype2.setToolTipText("Click to Chat on Skype with Brandon Buck");
        
        JTextArea ta = new JTextArea(3,35);
        ta.setText(definition);
        ta.setEditable(false);
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setBackground(Color.GRAY);
        ta.setForeground(Color.WHITE);
        //ta.setLocale(Locale.FRENCH);
        //ta.setFont();
       
        ImageIcon logo = new ImageIcon();
        try {
            logo = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
        } 
        catch (Exception e) {}
        JLabel logoLabel = new JLabel(logo);
        
        ImageIcon b1Photo = new ImageIcon();
        try {
            b1Photo = new ImageIcon(getClass().getClassLoader().getResource("btanner.jpg"));
        } 
        catch (Exception e) {}
        JLabel b1PhotoLabel = new JLabel(b1Photo);
        
        ImageIcon b2Photo = new ImageIcon();
        try {
            b2Photo = new ImageIcon(getClass().getClassLoader().getResource("bbuck.jpg"));
        } 
        catch (Exception e) {}
        JLabel b2PhotoLabel = new JLabel(b2Photo);
        
        northPanel.add(logoLabel);
        northPanel.add(ta);
        
        westPanel.add(b1PhotoLabel);
        westPanel.add(name1);
        westPanel.add(skype1);
        westPanel.add(spec1);
        
        eastPanel.add(b2PhotoLabel);
        eastPanel.add(name2);
        eastPanel.add(skype2);
        eastPanel.add(spec2);
        
        skype1.addMouseListener(this);
        skype2.addMouseListener(this);
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        
        southPanel.setLayout(new VerticalFlowLayout(VerticalFlowLayout.CENTER, VerticalFlowLayout.CENTER));
        southPanel.add(desc);
        southPanel.add(closeButton);
        
        
        
        cp.add(northPanel, BorderLayout.NORTH);
        cp.add(westPanel,  BorderLayout.WEST);
        cp.add(eastPanel,  BorderLayout.EAST);
        cp.add(southPanel, BorderLayout.SOUTH);
        
        
        this.pack();
        
        // Center the Window (Whatever Its Size) on the Screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)((screenSize.getWidth()/2)-this.getWidth()/2), (int)((screenSize.getHeight()/2)-this.getHeight()/2) );
        
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
        if (event.getSource() == skype1) {
            try { BrowserLauncher.openURL("skype:orangepyrite?chat"); } catch (Exception e) {Debug.print(e.getMessage());}
        }
        else if (event.getSource() == skype2) {
            try { BrowserLauncher.openURL("skype:lord.izuriel?chat"); } catch (Exception e) {Debug.print(e.getMessage());}
        }
    }
    
    
    public void actionPerformed(ActionEvent event) {
        AbstractButton pressedButton = (AbstractButton)event.getSource();
        if (pressedButton == closeButton) {
            this.dispose();
        }
    }
    
    public static void showAboutWindow(Frame owner) {
        new About(owner);
    }
    
}

package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Hoi Polloi picture editor, mainly used to crop/resize pictures to fit the
 * size specified by the program for the profile picture.
 * 
 * Cropping the picture will consist of dragging a correctly sized square around
 * a picture and the selecting the "crop" option to cut out the portion of the
 * picture in the bounds of the rectangle.
 * 
 * Resizing will be used for pictures that are large to allow faces to fit
 * within the bounds of the cropping rectangle.
 * 
 * @author Brandon
 * @version 1.0 (Dec 18, 2007)
 * @since December 18, 2007
 */
public class HPPictureEditor extends JDialog implements ActionListener {
    private MainMenu parent;
    
    // Buttons
    private JButton acceptButton = new JButton("Accept");
    private JButton cancelButton = new JButton("Cancel");
    
    /**
     * Default constructor for the HPPictureEditor. This constructer sets up
     * frames and frame components for use with the editor.
     * 
     * @param parent The Frame that is HPPictureEditor belongs to.
     */
    public HPPictureEditor(Frame parent) {
        super(parent);
        this.parent = (MainMenu)parent;
        this.setTitle("Picture Editor");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        
        // JMenu
            // File menu
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic('F');
                
                JMenuItem loadImageItem = new JMenuItem(new MenuAction("Load Pic..."));
                
        // End JMenu
        
        // Component set up
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);
        // End Component set up
        
        // Frame Contents
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        
        this.add(BorderLayout.SOUTH, buttonPanel);
        // End Frame Contents
        
        // Frame size and location
        
        //this.setSize(100, 100);
        this.pack();
        
        // Set the location of the frame relative to the MainMenu
        // --CENTER--
        Point frameLocation = new Point();
        
        double frameX = parent.getLocation().getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        double frameY = parent.getLocation().getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));
        
        frameLocation.setLocation(frameX, frameY);
        this.setLocation(frameLocation);
        // --END CENTER--
        this.setVisible(true);
    }
    /**
     * Displays the HPPictureEditor witht he given owner.
     * 
     * @param owner The frame that this HPPictureEditor belongs to.
     */
    public static void showHPPictureEditor(Frame owner) {
        new HPPictureEditor(owner);
    }
    /**
     * Handles any events that may be generated or used by this HPPictureEditor.
     * 
     * @param evt The ActionEvent that called this method.
     */
    public void actionPerformed(ActionEvent evt) {
        JButton sourceButton = (JButton)(evt.getSource());
        
        if (sourceButton == acceptButton) {
            
        } else if (sourceButton == cancelButton) {
            this.dispose();
        }
    }

    class MenuAction extends AbstractAction {
        public MenuAction(String name) {
            super(name);
        }
        
        public void actionPerformed(ActionEvent evt) {
            String selection = (String)getValue(Action.NAME);
        }
    }
}

package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import java.beans.*;
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
    private JLabel imageLabel = new JLabel();
    private BufferedImage currentImage = null;
    private java.io.File lastSelectedFile = null;
    
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
        this.setResizable(false);
        
        // JMenu
            ImageIcon loadImageIcon = new ImageIcon(getClass().getClassLoader().getResource("picture.png"));
            ImageIcon resizeImageIcon = new ImageIcon(getClass().getClassLoader().getResource("shape_handles.png"));
            ImageIcon blankImageIcon = new ImageIcon(getClass().getClassLoader().getResource("blank.png"));
            
            // File menu
            JMenu fileMenu = new JMenu("File");
            fileMenu.setMnemonic('F');
                
            JMenuItem loadImageItem = new JMenuItem(new MenuAction("Open Picture"));
            loadImageItem.setIcon(loadImageIcon);
            loadImageItem.setMnemonic('O');
            loadImageItem.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
            
            JMenuItem disposeItem = new JMenuItem(new MenuAction("Close"));
            disposeItem.setIcon(blankImageIcon);
            disposeItem.setAccelerator(KeyStroke.getKeyStroke('D', KeyEvent.CTRL_DOWN_MASK + KeyEvent.ALT_DOWN_MASK));
            disposeItem.setMnemonic('C');
            
            fileMenu.add(loadImageItem);
            fileMenu.addSeparator();
            fileMenu.add(disposeItem);
            
            // Edit Menu
            JMenu editMenu = new JMenu("Edit");
            editMenu.setMnemonic('E');
            
            JMenuItem resizeImageItem = new JMenuItem(new MenuAction("Resize"));
            resizeImageItem.setIcon(resizeImageIcon);
            resizeImageItem.setMnemonic('R');
            resizeImageItem.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.ALT_DOWN_MASK));
                
            editMenu.add(resizeImageItem);
                
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        this.setJMenuBar(menuBar);
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
        
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        imagePanel.add(BorderLayout.CENTER, imageLabel);
        
        this.add(BorderLayout.CENTER, imagePanel);
        this.add(BorderLayout.SOUTH, buttonPanel);
        // End Frame Contents
        
        // Frame size and location
        
        this.setSize(400, 400);
        //this.pack();
        
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
    
    public void resizeFrame() {
        this.pack();
        
        // Set the location of the frame relative to the MainMenu
        // --CENTER--
        Point frameLocation = new Point();
        
        double frameX = parent.getLocation().getX() + ((parent.getWidth() / 2) - (this.getWidth() / 2));
        double frameY = parent.getLocation().getY() + ((parent.getHeight() / 2) - (this.getHeight() / 2));
        
        frameLocation.setLocation(frameX, frameY);
        this.setLocation(frameLocation);
        // --END CENTER--
    }
    
    public void disposeEditor() {
        this.dispose();
    }
    
    class MenuAction extends AbstractAction {
        public MenuAction(String name) {
            super(name);
        }
        
        public void actionPerformed(ActionEvent evt) {
            String selection = (String)getValue(Action.NAME);
            
            if (selection.equals("Open Picture")) {
                JFileChooser browser = new JFileChooser();
                browser.setFileFilter(new ImageFileFilter());
                browser.setAcceptAllFileFilterUsed(false);
                ImagePreview preview = new ImagePreview(browser);
                browser.addPropertyChangeListener(preview);
                browser.setAccessory(preview);
                
                if (lastSelectedFile != null)
                    browser.setSelectedFile(lastSelectedFile);
                
                int browserSelection = browser.showDialog(parent, "Open");
            
                if (browserSelection == JFileChooser.APPROVE_OPTION) {
                    try {
                        lastSelectedFile = browser.getSelectedFile();
                        currentImage = ImageIO.read(lastSelectedFile);
                        imageLabel.setIcon(new ImageIcon(lastSelectedFile.getPath()));
                        resizeFrame();
                    } catch(java.io.IOException exc) {
                        Debug.print("ERROR: Problem reading file in.");
                    }
                }
            } else if (selection.equals("Close")) {
                disposeEditor();
            }
        }
    }
    
    class ImageFileFilter extends javax.swing.filechooser.FileFilter {
        public ImageFileFilter() { super(); }
        
        public boolean accept(java.io.File file) {
            String filePath = file.getPath();
            String fileType = "";
            if (!file.isDirectory()) {
                if (filePath.length() > 5)
                    fileType = filePath.substring(filePath.lastIndexOf('.'));
                else
                    return false;
            }
            //Debug.print("fileType: " + fileType);
            boolean answer = false;
            
            if (fileType.equalsIgnoreCase(".jpg") ||
                    fileType.equalsIgnoreCase(".jpeg") || 
                    fileType.equalsIgnoreCase(".gif") || 
                    fileType.equalsIgnoreCase(".png") || file.isDirectory())
                answer = true;
            
            return answer;
        }
                
        public String getDescription() {
            return "Picture files (JPG, JPEG, PNG, GIF)";
        }
    }
    /**
     * Following code is Hack #31 from "Swing Hacks" by Chris Adamson and
     * Joshua Marinacci. Minor edits include making the classes internal instead
     * of separate files.
     * 
     * This adds a preview image to the JFileChooser so that the user may
     * preview a thumbnail of the selected image instead of guessing or
     * memorizing the names of the picture they are looking for.
     * 
     */
    class ImagePreview extends JPanel implements PropertyChangeListener {
        private JFileChooser jfc;
        private Image img;

        public ImagePreview(JFileChooser jfc) {
            this.jfc = jfc;
            Dimension sz = new Dimension(200,200);
            setPreferredSize(sz);
        }
        
        public void propertyChange(PropertyChangeEvent evt) {
            try {
                Debug.print("Updating.");
                java.io.File file = jfc.getSelectedFile();
                updateImage(file);
            } catch (java.io.IOException ex) {
                    Debug.print(ex.getMessage());
            }
        }
        
        public void updateImage(java.io.File file) throws java.io.IOException {
            if(file == null) {
                return;
            }
            
            img = ImageIO.read(file);
            repaint();
        }
        
        public void paintComponent(Graphics g) {
            // fill the background
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        
            if(img != null) {
                // calculate the scaling factor
                int imgW = img.getWidth(null);
                int imgH = img.getHeight(null);
                int side = Math.max(imgW, imgH);
                double scale = 200.0 / (double)side;
                int w = (int)(scale * (double)imgW);
                int h = (int)(scale * (double)imgH);

                // draw the image
                g.drawImage(img, 0, 0, w, h, null);
                
                // draw the image dimensions
                String dim = imgW + " x " + imgH;
                g.setColor(Color.BLACK);
                g.drawString(dim, 31, 196);
                g.setColor(Color.WHITE);
                g.drawString(dim, 30, 195);
            } //else {
                // print a message
                //g.setColor(Color.BLACK);
                //g.drawString("Not an image", 30, 100);
            //}
        }
    }
}

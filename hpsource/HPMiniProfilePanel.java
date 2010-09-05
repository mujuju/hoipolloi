package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
/**
 * Creates a JPanel that contains a Miniature version of a Profile for the
 * Person provided.
 * @author Brandon Buck
 * @version 1.0
 * @since August 30, 2010
 */
public class HPMiniProfilePanel extends GradientPanel implements MouseListener {
    private final int MINI_HEIGHT = 100;
    private final int MINI_WIDTH = 860;
    private final Dimension MAX_SIZE = new Dimension(MINI_WIDTH, MINI_HEIGHT);
    private final Dimension MINI_IMAGE_SIZE = new Dimension(72, 88);
    private boolean mousedOverFlag = false;
    private Color backgroundColor;
    private MainMenu parentMenu;

    private Person person = null;

    private JLabel miniImageLabel = new JLabel();
    private JLabel nameLabel = new JLabel();

    public HPMiniProfilePanel(Person person, MainMenu parentMenu) {
        super();
        this.parentMenu = parentMenu;
        this.person = person;
        this.backgroundColor = this.getBackground();
        super.setEndColor(backgroundColor);
        super.setStartColor(new Color(56, 117, 201));
        super.setFillDirection(GradientPanel.LEFT_RIGHT_CENTER);

        this.setMaximumSize(MAX_SIZE);
        this.setMinimumSize(MAX_SIZE);
        this.setPreferredSize(MAX_SIZE);
        this.setSize(MAX_SIZE);
        this.initialize();
        this.build();
    }
    /**
     *
     */
    private void initialize() {
        addMouseListener(this);
        setMiniProfileImage();

        nameLabel.setText(person.getFirstLastName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 30));
    }

    private void build() {

        JPanel profilePanel = new JPanel();
        profilePanel.setOpaque(false);
        profilePanel.setLayout(new BorderLayout());
        profilePanel.add(buildWestPanel(), BorderLayout.WEST);

        this.setLayout(new GridLayout(1, 1));
        this.add(profilePanel);
        this.setBorder(BorderFactory.createMatteBorder(0, 1, 3, 1, Color.BLACK));
    }

    private JPanel buildWestPanel() {
        JPanel westInfoPanel = new JPanel();
        westInfoPanel.setOpaque(false);
        westInfoPanel.setLayout(new BorderLayout());
        westInfoPanel.add(nameLabel, BorderLayout.NORTH);

        JPanel miniImagePanel = new JPanel();
        miniImagePanel.setOpaque(false);
        Dimension miniImagePanelSize = new Dimension(80, 100);
        miniImagePanel.setPreferredSize(miniImagePanelSize);
        miniImagePanel.setMaximumSize(miniImagePanelSize);
        miniImagePanel.setMinimumSize(miniImagePanelSize);
        miniImagePanel.add(miniImageLabel);

        JPanel westPanel = new JPanel();
        westPanel.setOpaque(false);
        westPanel.setLayout(new BorderLayout());
        westPanel.add(miniImagePanel, BorderLayout.WEST);
        westPanel.add(westInfoPanel, BorderLayout.EAST);

        return westPanel;
    }

    private void setMiniProfileImage() {
        int newWidth = (int)(MINI_IMAGE_SIZE.getWidth());
        int newHeight = (int)(MINI_IMAGE_SIZE.getHeight());

        String fileName = person.getPhotoFileName();
        Debug.print("MiniProfile: " + fileName); //show filename debug
        if (fileName == null || fileName.equals(""))
            fileName = "pictures/unknown.jpg";

        try {
            BufferedImage originalImage = ImageIO.read(new File(fileName));
            final BufferedImage miniImage = createBlankImage(originalImage, newWidth, newHeight);
            final Graphics2D g2 = miniImage.createGraphics();
            //g2.drawImage(originalImage, 0, 0, newWidth, newHeight, 0, 0, originalImage.getWidth(), originalImage.getHeight(), null);
            g2.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2.dispose();
            miniImageLabel.setIcon(new ImageIcon(miniImage));
            miniImageLabel.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static BufferedImage createBlankImage(BufferedImage src, int w, int h) {
        int type = src.getType();
        if (type != BufferedImage.TYPE_CUSTOM)
            return new BufferedImage(w, h, type);
        else {
            ColorModel cm = src.getColorModel();
            WritableRaster raster = src.getRaster().createCompatibleWritableRaster(w, h);
            boolean isRasterPremultiplied = src.isAlphaPremultiplied();
            return new BufferedImage(cm, raster, isRasterPremultiplied, null);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        int width = (int)(MAX_SIZE.getWidth());
        int height = (int)(MAX_SIZE.getHeight());

        if (mousedOverFlag)
            super.paintPanel(g2);
        else {
            g2.setColor(backgroundColor);
            g2.fillRect(0, 0, width, height);
        }
    }

    public void mouseExited(MouseEvent evt) {
        mousedOverFlag = false;
        repaint();
    }
    public void mouseEntered(MouseEvent evt) {
        mousedOverFlag = true;
        repaint();
    }
    public void mouseReleased(MouseEvent evt) {}
    public void mousePressed(MouseEvent evt) {}
    public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2)
            parentMenu.showProfile(person);
    }

}

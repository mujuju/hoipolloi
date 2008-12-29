package hoipolloi;

import javax.swing.*;
import java.awt.event.*;

/**
 * JLabel that can be Clicked.
 *
 * @author Brandon Tanner
 */
public class CategoryLabel extends JLabel implements MouseListener {

    private MainMenu mm;
    private Person p;

    public CategoryLabel(String label, MainMenu mm, Person p) {
        super(label);
        this.addMouseListener(this);
        this.mm = mm;
        this.p = p;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            new CategoryBox(mm, p);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}

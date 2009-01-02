package hoipolloi;
import java.awt.event.*;
import javax.swing.JFrame;
/**
 *
 * @author Brandon Buck
 * @since January 2, 2009
 * @version 1.0
 */
public class TrayMouseListener implements MouseListener {
    private MainMenu parent;
    
    public TrayMouseListener(MainMenu parent) {
        this.parent = parent;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            if (parent.isVisible()) {
                parent.setExtendedState(JFrame.ICONIFIED);
                parent.setVisible(false);
            }
            else {
                parent.setExtendedState(JFrame.NORMAL);
                parent.setVisible(true);
                parent.toFront();
            }
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

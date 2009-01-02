package hoipolloi;
import javax.swing.*;
import java.awt.event.*;
/** Overides JList to display the HP people list with mouse listener and cell renderer
 * Overides JList to display people for Hoi Polloi using the HP Cell Renderer
 * as well as a mouse listener used to display profiles on double click.
 * @author Brandon Buck
 */
public class PeopleList extends JList implements MouseListener {
    private MainMenu parent;

    public PeopleList(MainMenu parent) {
        super();
        this.parent = parent;
        this.setCellRenderer(new HPCellRenderer());
        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int index = this.locationToIndex(e.getPoint());
            this.setSelectedIndex(index);
            KeyValue value = (KeyValue)(this.getSelectedValue());
            try {
                parent.showProfile(new Person(value.getKey()));
            }
            catch (Exception ex) {
                Debug.print(ex.getMessage());
            }
        }
        if (e.getClickCount() == 1) {
            int index = this.locationToIndex(e.getPoint());
            this.setSelectedIndex(index);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

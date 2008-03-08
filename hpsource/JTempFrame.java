package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * A temporary frame for showing lists of things.
 *
 * @author  Brandon Buck
 * @since   Dec 8, 2006 7:44 PM
 * @version 1.0
 */
public class JTempFrame extends JFrame {
    private MainMenu parent;
    private JList list;
    
    /** Creates a new instance of JTempFrame */
    public JTempFrame(MainMenu parent, ArrayList peeps) {
        this.parent = parent;
        setTitle("Search Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout());
        
        Object[] people = peeps.toArray();
        
        list = new JList();
        list.setCellRenderer(new HPCellRenderer());
        list.setListData(people);
        
        list.addMouseListener(new ClickListener());
        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(220, parent.getContentPane().getHeight()));
        
        add(listScroller, BorderLayout.CENTER);
        
        pack();
        setLocation(parent.getX() + (parent.getWidth() / 2) - (getWidth() / 2), parent.getY() + (parent.getHeight() / 2) - (getHeight() / 2));
        setVisible(true);
    }
    
    class ClickListener implements MouseListener {
        public void mouseExited(MouseEvent e) { }
        public void mouseEntered(MouseEvent e) { }
        public void mouseMoved(MouseEvent e) { }
        public void mouseReleased(MouseEvent e) { }
        public void mousePressed(MouseEvent e) { }
        public void mouseClicked(MouseEvent e) { 
            if (e.getClickCount() == 2) {
                int index = list.locationToIndex(e.getPoint());
                list.setSelectedIndex(index);
                KeyValue value = (KeyValue)(list.getSelectedValue());
                try {
                    parent.showProfile(new Person(value.getKey()));
                }
                catch (Exception ex) { }
                finally { setVisible(false); }
            }
            if (e.getClickCount() == 1) {
                int index = list.locationToIndex(e.getPoint());
                list.setSelectedIndex(index);
            }
        }
        
        public void mouseDragged(MouseEvent e) { }
    }
}

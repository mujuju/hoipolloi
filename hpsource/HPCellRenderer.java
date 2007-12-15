package hoipolloi;

import javax.swing.*;
import java.awt.*;

/**
 * A Cell Renderer for Hoi Polloi Lists.
 * 
 * @author  Brandon Buck
 * @since   Dec 8, 2006 7:44PM
 * @version 1.1
 */
public class HPCellRenderer extends JLabel implements ListCellRenderer {
    public Component getListCellRendererComponent(JList list, Object value, int index,  boolean isSelected, boolean cellHasFocus) {
        KeyValue thisValue = (KeyValue)value;
        
        setText(thisValue.getValue());
        
        if (isSelected) {
            setForeground(list.getSelectionForeground());
            setBackground(list.getSelectionBackground());
        }
        else {
            setForeground(list.getForeground());
            setBackground(list.getBackground());
        }
        
        setEnabled(list.isEnabled());
        setFont(list.getFont());
        setOpaque(true);
        
        return this;
    }
}

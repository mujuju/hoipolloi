/*
 * HPCellRenderer.java
 *
 * Created on December 8, 2006, 7:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package hoipolloi;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Brandon Buck
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

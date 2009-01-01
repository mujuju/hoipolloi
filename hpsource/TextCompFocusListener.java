/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hoipolloi;
import java.awt.event.*;
import javax.swing.text.JTextComponent;

/** Selects all the text entered into a Text Component when focus is gained
 *
 * @author Brandon Buck
 */
public class TextCompFocusListener implements FocusListener{
    public TextCompFocusListener() { super(); }

    public void focusGained(FocusEvent e) {
        JTextComponent source = (JTextComponent)(e.getSource());
        source.select(0, source.getText().length());
    }
    public void focusLost(FocusEvent e) { }
}

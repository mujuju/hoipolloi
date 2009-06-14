package hoipolloi;

import java.awt.event.*;
import javax.swing.text.JTextComponent;

/** 
 * Selects all the text entered into a Text Component when focus is gained.
 *
 * @author Brandon Buck
 */
public class TextCompFocusListener implements FocusListener {

    /** Default Constructor */
    public TextCompFocusListener() {
        super();
    }

    /** Executed when focus is gained */
    public void focusGained(FocusEvent e) {
        JTextComponent source = (JTextComponent)(e.getSource());
        source.select(0, source.getText().length());
    }

    /** Executed when focus is lost */
    public void focusLost(FocusEvent e) {}
    
}

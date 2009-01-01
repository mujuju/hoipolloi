/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hoipolloi;
import java.awt.event.*;
import java.awt.Toolkit;
/** Key Listener for the filter tree to negate left and right arrow key functionality.
 * Used by the Filter JTree in MainMenu to remove left and right arrow key
 * functionality which was given to browsing profiles.
 * @author Brandon Buck
 * @version 1.0
 * @since December 31, 2008
 */
public class FilterTreeKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) { }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK) && (e.getKeyCode() == KeyEvent.VK_NUMPAD6)))
            e.consume();

        if (e.getKeyCode() == KeyEvent.VK_LEFT || (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_NUM_LOCK) && (e.getKeyCode() == KeyEvent.VK_NUMPAD4)))
            e.consume();
    }

    public void keyReleased(KeyEvent e) { }

}

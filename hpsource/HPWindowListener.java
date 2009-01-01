/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hoipolloi;
import java.awt.event.*;
/** Listens for the window being closed so HP can save the property file before exiting
 * Listens for the user to close the application via the OS "exit" button and
 * when this action is fired it saves the property file before exiting.
 *
 * @author Brandon Buck
 */
public class HPWindowListener implements WindowListener {
    private MainMenu parent;

    public HPWindowListener(MainMenu parent) {
        super();

        this.parent = parent;
    }
    public void windowDeactivated(WindowEvent evt) { }
    public void windowActivated(WindowEvent evt) { }
    public void windowDeiconified(WindowEvent evt) { }
    public void windowIconified(WindowEvent evt) { }
    public void windowClosed(WindowEvent evt) { }
    public void windowClosing(WindowEvent evt) {
        parent.savePropertyFile();
    }
    public void windowOpened(WindowEvent evt) { }
}

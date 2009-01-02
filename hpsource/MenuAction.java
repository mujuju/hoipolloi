/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hoipolloi;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author Brandon Buck
 * @since January 2, 2009
 */
public class MenuAction extends AbstractAction {
    private MainMenu parent;

    public MenuAction(String n, MainMenu parent) {
        super(n);
        this.parent = parent;
    }

    private void deiconifyParent() {
        if (!parent.isVisible() && java.awt.SystemTray.isSupported()) {
            parent.setExtendedState(JFrame.NORMAL);
            parent.setVisible(true);
            parent.toFront();
        }
    }

    public void actionPerformed(ActionEvent evt) {
        String selection = (String)getValue(Action.NAME);

        if (selection.equals("Exit")) {
            parent.savePropertyFile();
            System.exit(0);
        }
        else if (selection.equals("Search")) {
            deiconifyParent();
            new SearchWindow(parent);
        }
        else if (selection.equals("Export")) {
            new Export(parent);
        }
        else if (selection.equals("Clear")) {
            parent.clearWindow();
        }
        else if (selection.equals("Statistics")) {
            Stats.showStats(parent);
        }
        else if (selection.equals("Everyone")) {
            parent.showPeopleList(DBHPInterface.getListOfPeopleByLastName());
        }
        else if (selection.equals("Edit Profile")) {
            if (parent.getCurrentPerson() != null) {
                parent.editProfile(parent.getCurrentPerson());
            }
        }
        else if (selection.equals("Quick Add")) {
            deiconifyParent();
            parent.quickAdd();
        }
        else if (selection.equals("About")) {
            deiconifyParent();
            About.showAboutWindow(parent, parent.getVersion());
        }
        else if (selection.equals("Print")) {
            EnvelopeBox.showEnvelopeBox(parent);
        }
        else if (selection.equals("Birthdays")) {
            new JTempFrame(parent, DBHPInterface.getBirthdaysThisMonth());
        }
        else if (selection.equals("Help")) {
            JOptionPane.showMessageDialog(parent, "Help is on the way ...");
        }
        else if (selection.equals("World Clocks")) {
            JOptionPane.showMessageDialog(parent, "Coming soon!");
        }
        else if (selection.equals("Synchronize")) {
            JOptionPane.showMessageDialog(parent, "Please donate a blue tooth phone or dongle!");
        }
        else {
            deiconifyParent();
            JOptionPane.showMessageDialog(parent, "This feature will be coming in a later version!", selection.toString(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

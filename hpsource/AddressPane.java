package hoipolloi;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

/**
 * A JTabbedPane for Addresses.
 *
 * Will have the ability to right click on a tab under EditProfile
 * and do actions like edit and delete.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Dec 28, 2008)
 * @since   December 28, 2008
 */
public class AddressPane extends JTabbedPane implements MouseListener, ActionListener {

    /** The Right Click Popup Menu */
    JPopupMenu rightClickMenu;

    public AddressPane() {
        super();
        this.addMouseListener(this);
        this.buildRightClickMenu();
    }

    /**
     * Initially copied in whole from MainMenu.java
     * 
     * @param person
     * @return
     */
    public AddressPane getAddressPane(Person person) {
        // Address Box *******************************************************
        AddressPane addressPane = new AddressPane();
        ArrayList <Address> addressList = person.getAddresses();

        // Loop to Create Tabs
        if (addressList != null && !addressList.isEmpty()) {
            for (Address address : addressList) {
                // Get Address Parts
                String addressType     = address.getAddressType().getValue();
                String addressLabel    = address.getAddressLabel();
                String addressLine1    = address.getAddressLine1();
                String addressLine2    = address.getAddressLine2();
                String addressLine3    = address.getAddressLine3();
                String addressCity     = address.getAddressCity();
                String addressState    = address.getAddressState();
                String addressZip      = address.getAddressZip();
                String addressCountry  = address.getAddressCountry().getValue();
                String addressDistrict = address.getAddressDistrict();

                // Make Panel to Display Address (should really make our own component for this)
                JPanel addressPanel = new JPanel();
                addressPanel.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                Font addressFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

                JLabel label = new JLabel(addressLabel);
                label.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 0;
                addressPanel.add(label, c);

                JLabel a1 = new JLabel(addressLine1);
                a1.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 1;
                addressPanel.add(a1, c);

                JLabel a2 = new JLabel(addressLine2);
                a2.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 2;
                addressPanel.add(a2, c);

                JLabel a3 = new JLabel(addressLine3);
                a3.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 3;
                addressPanel.add(a3, c);

                JLabel city  = new JLabel(addressCity+", ");
                JLabel state = new JLabel(addressState);
                JLabel zip   = new JLabel(" "+addressZip);
                city.setFont(addressFont);
                state.setFont(addressFont);
                zip.setFont(addressFont);
                JPanel CityStateZip = new JPanel();
                CityStateZip.setLayout(new BoxLayout(CityStateZip, BoxLayout.X_AXIS));
                CityStateZip.add(city);
                CityStateZip.add(state);
                CityStateZip.add(zip);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 4;
                addressPanel.add(CityStateZip, c);

                JLabel district = new JLabel(addressDistrict);
                district.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 5;
                addressPanel.add(district, c);

                JLabel country = new JLabel(addressCountry);
                country.setFont(addressFont);
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 6;
                addressPanel.add(country, c);

                JPanel mapPanel = new JPanel(new FlowLayout());
                ButtonGroup maps = new ButtonGroup();

                JButton googlemaps = new JButton("Google Maps");
                googlemaps.setToolTipText("View this Address in Google Maps");

                JButton mapquest = new JButton("MapQuest");
                mapquest.setToolTipText("MapQuest this Address");

                maps.add(googlemaps);
                maps.add(mapquest);

                mapPanel.add(googlemaps);
                mapPanel.add(mapquest);



                final Address a = address;
                googlemaps.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        try {
                            BrowserLauncher.openURL(a.getGoogleMapsURL());
                        }
                        catch (Exception e) {
                            Debug.print(e.getMessage());
                        }
                    }
                });

                mapquest.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        try {
                            BrowserLauncher.openURL(a.getMapquestURL());
                        }
                        catch (Exception e) {
                            Debug.print(e.getMessage());
                        }
                    }
                });


                c.fill  = GridBagConstraints.HORIZONTAL;
                c.gridx = 0;
                c.gridy = 7;
                addressPanel.add(mapPanel, c);

                addressPane.addTab(addressType, addressPanel);
            }
        }
        else {
            JPanel noAddressPanel = new JPanel(new BorderLayout());
            noAddressPanel.add(new JLabel("     Edit Profile to Add Addresses"), BorderLayout.CENTER);
            addressPane.addTab("Address", noAddressPanel);
        }
        // End Address Box ***************************************************

        return addressPane;
    }

    private void buildRightClickMenu() {
        rightClickMenu = new JPopupMenu();
        JMenuItem addAddress = new JMenuItem("Add");
        addAddress.addActionListener(this);
        JMenuItem editAddress = new JMenuItem("Edit");
        editAddress.addActionListener(this);
        JMenuItem delAddress = new JMenuItem("Delete");
        delAddress.addActionListener(this);
        JMenuItem title = new JMenuItem("Address Actions");
        title.setEnabled(false);
        rightClickMenu.add(title);
        rightClickMenu.addSeparator();
        rightClickMenu.add(addAddress);
        rightClickMenu.add(editAddress);
        rightClickMenu.add(delAddress);
        rightClickMenu.addSeparator();
        rightClickMenu.add(new JMenuItem("Google Maps"));
        rightClickMenu.add(new JMenuItem("Mapquest"));
    }

    private void showRightClickMenu(MouseEvent e) {
        rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
    }

    public void mouseClicked(MouseEvent e) {
        // Detect Right Click
        if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
            showRightClickMenu(e);
        }
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String sourceText = source.getText();
        Debug.print(sourceText);
    }

}

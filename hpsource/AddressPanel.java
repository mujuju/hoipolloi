package hoipolloi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A panel that displays individual components to an Address.
 *
 * @author  Brandon Buck
 * @since   January 2, 2009
 * @version 1.1
 */
public class AddressPanel extends JPanel {
    private Address address;

    public AddressPanel(Address address) {
        super();
        this.address = address;
        this.getDisplay();
    }

    public void getDisplay() {
        // Get Address Parts
        String addressLabel    = this.address.getAddressLabel();
        String addressLine1    = this.address.getAddressLine1();
        String addressLine2    = this.address.getAddressLine2();
        String addressLine3    = this.address.getAddressLine3();
        String addressCity     = this.address.getAddressCity();
        String addressState    = this.address.getAddressState();
        String addressZip      = this.address.getAddressZip();
        String addressCountry  = this.address.getAddressCountry().getValue();
        String addressDistrict = this.address.getAddressDistrict();

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        Font addressFont = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

        JLabel label = new JLabel(addressLabel);
        label.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(label, c);

        JLabel a1 = new JLabel(addressLine1);
        a1.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(a1, c);

        JLabel a2 = new JLabel(addressLine2);
        a2.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        this.add(a2, c);

        JLabel a3 = new JLabel(addressLine3);
        a3.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        this.add(a3, c);

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
        this.add(CityStateZip, c);

        JLabel district = new JLabel(addressDistrict);
        district.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        this.add(district, c);

        JLabel country = new JLabel(addressCountry);
        country.setFont(addressFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        this.add(country, c);
    }

    public Address getAddress() {
        return this.address;
    }
}

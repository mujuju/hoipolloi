package hoipolloi;

/**
 * An Address datatype consisting of Label, 
 * Address Line 1, 2, and 3, City, State, Zip, 
 * Country, District and Type (Residential, Billing, Mailing etc).
 *
 * @author  Brandon Tanner
 * @version 1.1 Dec 12, 2007
 * @since   December 12, 2006
 */
public class Address {
    
    protected KeyValue addressType;  // Required
    protected String   addressLabel;
    protected String   addressLine1; // Required
    protected String   addressLine2;
    protected String   addressLine3;
    protected String   city;         // Required
    protected String   state;        // Required
    protected String   zip;          // Required
    protected KeyValue country;      // Required
    protected String   district;
    
    /** Creates a new instance of Address */
    public Address() {
        this.addressType   = new KeyValue();
        this.addressLabel  = "";
        this.addressLine1  = "";
        this.addressLine2  = "";
        this.addressLine3  = "";
        this.city          = "";
        this.state         = "";
        this.zip           = "";
        this.country       = new KeyValue();
        this.district      = "";
    }
    
    /**
     * Checks if all the required parts of this Address are filled.
     * 
     * The minimum required parts are: 
     * Type, Line 1, City, State, Zip and Country.
     * The rest of the parts are optional.
     * 
     * @return True if all the required parts are filled, otherwise false.
     */
    public boolean areRequiredPartsFilled() {
        if (this.addressLine1.equals("") || this.addressLine1 == null)
            return false;
        else
            return true;
    }
    
    /**
     * Gets the Address Type.
     *
     * @return The Address Type
     */
    public KeyValue getAddressType() {
        return this.addressType;
    }
    
    /**
     * Gets the Address Label.
     *
     * @return The Address Label
     */
    public String getAddressLabel() {
        return this.addressLabel;
    }
    
    public String getAddressLine1() {
        return this.addressLine1;
    }
    
    public String getAddressLine2() {
        return this.addressLine2;
    }
    
    public String getAddressLine3() {
        return this.addressLine3;
    }
    
    public String getAddressCity() {
        return this.city;
    }
    
    public String getAddressState() {
        return this.state;
    }
    
    public String getAddressZip() {
        return this.zip;
    }
    
    public KeyValue getAddressCountry() {
        return this.country;
    }
    
    public String getAddressDistrict() {
        return this.district;
    }
    
    public void setAddressType(KeyValue type) {
        this.addressType = type;
    }
    
    public void setAddressLabel(String label) {
        this.addressLabel = label;
    }
    
    public void setAddressLine1(String line1) {
        this.addressLine1 = line1;
    }
    
    public void setAddressLine2(String line2) {
        this.addressLine2 = line2;
    }
    
    public void setAddressLine3(String line3) {
        this.addressLine3 = line3;
    }
    
    public void setAddressCity(String city) {
        this.city = city;
    }
    
    public void setAddressState(String state) {
        this.state = state;
    }
    
    public void setAddressZip(String zip) {
        this.zip = zip;
    }
    
    public void setAddressCountry(KeyValue country) {
        this.country = country;
    }
    
    public void setAddressDistrict(String district) {
        this.district = district;
    }
    
    public String toString() {
        return this.addressLabel+"\n"+this.addressLine1+"\n"+this.addressLine2+"\n"+this.addressLine3+"\n"+this.city+", "+this.state+" "+this.zip+"\n"+this.country.getValue();
    }
    
    public static void main(String[] args) {
        Debug.turnOn();
        Address add = new Address();
        add.addressLabel = "Dr. & Mrs. Paul Tanner";
        add.addressLine1 = "23262 County Road 181";
        add.city         = "Bullard";
        add.state        = "TX";
        add.zip          = "75757-8169";
        add.country.setKey(223);
        add.country.setValue("United States of America");
        
        javax.swing.JOptionPane.showMessageDialog(null, add);
        
    }
    
}

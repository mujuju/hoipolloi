package hoipolloi;

/**
 * An Address datatype consisting of Label, 
 * Address Line 1, 2, and 3, City, State, Zip, 
 * Country, District and Type (Residential, Billing, Mailing etc).
 *
 * @author  Brandon Tanner
 * @version 1.4a Jan 4, 2009
 * @since   December 12, 2006
 */
public class Address {

    protected int      addressID;
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
        this.addressID     = -1;
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
     * Checks to see if this Address is empty (contains no information).
     * 
     * @return True if this Address contains no useful information, false otherwise.
     */
    public boolean isEmpty() {
        // Need to think about how to implement this first.
        return false;
    }

    /**
     * Checks if all the required parts of this Address are filled.
     *
     * This method is not implemented. Plans to do so are uncertain.
     * 
     * The minimum required parts are: 
     * Type, Line 1, City, State, Zip and Country.
     * The rest of the parts are optional.
     * 
     * @return True if all the required parts are filled, otherwise false.
     */
    public boolean areRequiredPartsFilled() {
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
     * Gets the Address ID.
     */
    public int getAddressID() {
        return this.addressID;
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
    
    /**
     * Gets the Address District.
     * 
     * @return The District
     */
    public String getAddressDistrict() {
        return this.district;
    }
    
    /**
     * Gets a URL to view this address at Google Maps.
     * 
     * Need to check for illegal characters like the hash #.
     * 
     * @return The Google Maps URL
     */
    public String getGoogleMapsURL() {
        String url = "http://maps.google.com/maps?f=q&hl=en&q=";
        if (addressLine1 != null && !addressLine1.isEmpty())
            url += this.addressLine1+",";
        if (addressLine2 != null && !addressLine2.isEmpty())
            url += this.addressLine2+",";
        if (addressLine3 != null && !addressLine3.isEmpty())
            url += this.addressLine3+",";
        if (city != null && !city.isEmpty())
            url += this.city+",";
        if (state != null && !state.isEmpty())
            url += this.state+",";
        if (zip != null && !zip.isEmpty())
            url += this.zip+",";
        if (country != null && !country.getValue().isEmpty())
            url += this.country.getValue();
        return url;
    }
    
    /**
     * Gets a URL to view this address at Mapquest.
     * 
     * @return The Mapquest URL
     */
    public String getMapquestURL() {
        //http://www.mapquest.com/maps/map.adp?address=1815%20Garland%20Dr&city=Longview&state=TX&zipcode=75602%2d3038&country=US&title=%3cb%20class%3d%22fn%20org%22%3e1815%20Garland%20Dr%3c%2fb%3e%3cbr%20%2f%3e%20%3cspan%20style%3d%22display%3ainline%3bmargin%2dbottom%3a0px%3b%22%20class%3d%22locality%22%3eLongview%3c%2fspan%3e%2c%20%3cspan%20style%3d%22display%3ainline%3bmargin%2dbottom%3a0px%3b%22%20class%3d%22region%22%3eTX%3c%2fspan%3e%20%3cspan%20style%3d%22display%3ainline%3bmargin%2dbottom%3a0px%3b%22%20class%3d%22postal%2dcode%22%3e75602%2d3038%3c%2fspan%3e%2c%20%20%3cspan%20style%3d%22display%3ainline%3bmargin%2dbottom%3a0px%3b%22%20class%3d%22country%2dname%22%3eUS%3c%2fspan%3e%3c%2fspan%3e&cid=lfmaplink2&name=&dtype=s
        String url = "http://www.mapquest.com/maps/map.adp?formtype=address";
        url += "&address="+addressLine1;
        url += "&city="+city;
        url += "&state="+state;
        url += "&zip="+zip;
        return url;
    }

    /**
     * Gets the World Time Clock URL.
     * 
     * @return
     */
    public String getWorldTimeURL() {
        return "http://www.timeanddate.com/worldclock/results.html?query="+this.city;
    }

    public void setAddressID(int aid) {
        this.addressID = aid;
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
    
    @Override public String toString() {
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

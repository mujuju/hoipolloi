package hoipolloi;

import java.util.*;
import java.sql.*;
import org.sqlite.JDBC;

/**
 * Stores Contacts for People
 * This class however is not used to add 
 * or remove contacts from the database.
 *
 * @version 1.0
 * @since December 15, 2006
 * @author Brandon Tanner
 */
public class Contact {
    
    protected int    contactTypeID;
    protected String contactType;
    protected int    contactID;
    protected String contact;
    
    /** Creates a new instance of Contact */
    public Contact() {
        this.contactTypeID = -1;
        this.contactType   = "";
        this.contactID     = -1;
        this.contact       = "";
    }
    
    /**
     * Gets a List of Contact Types
     *
     * @return A keyvalue list of contact types.
     */
    public static ArrayList getContactTypes() {
        ArrayList <KeyValue> contactTypes = new ArrayList();
        String sql = "SELECT typContactTypeID, typContactType FROM pmp_ctypes";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                contactTypes.add( new KeyValue( rs.getInt("typContactTypeID"), rs.getString("typContactType") ) );
            }
            return contactTypes;
        }
        catch (Exception e) { return null; }
    }
    
    public int getContactTypeID() {
        return this.contactTypeID;
    }
    
    public String getContactType() {
        return this.contactType;
    }
    
    public int getContactID() {
        return this.contactID;
    }
    
    public String getContact() {
        return this.contact;
    }
    
    public void setContactTypeID(int typeID) {
        this.contactTypeID = typeID;
    }
    
    public void setContactType(String type) {
        this.contactType = type;
    }
    
    public void setContactID(int cid) {
        this.contactID = cid;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String toString() {
        String ctn = new String();
        ctn = "Contact ID: "+this.contactID+" "+this.contactType+": "+this.contact;
        return ctn;
    }
    
}

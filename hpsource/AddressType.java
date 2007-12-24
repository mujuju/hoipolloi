package hoipolloi;

import java.util.*;
import java.sql.*;

/**
 * Stores Address Types
 *
 * @author Brandon Tanner
 * @version 1.0
 * @since December 12, 2006
 */
public class AddressType {
    
    // Some Constants for Printing/Display
    protected static final String MAILING     = "Mailing";
    protected static final String BUSINESS    = "Business";
    protected static final String RESIDENTIAL = "Residential";
    protected static final String PERMANENT   = "Permanent";

    public static ArrayList getAddressTypes() {
        ArrayList <KeyValue> addressTypes = new ArrayList<KeyValue>();
        String sql = "SELECT atpAddressTypeID, atpAddressType FROM pmp_addresstypes";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                addressTypes.add( new KeyValue( rs.getInt("atpAddressTypeID"), rs.getString("atpAddressType") ) );
            }
            return addressTypes;
        }
        catch (Exception e) { return null; }
    }

}



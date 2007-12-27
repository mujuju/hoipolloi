package hoipolloi;

import java.sql.*;

/**
 * A static class to delete things from the Hoi Polloi database.
 *
 * @author  Brandon Tanner
 * @version 1.1 (Dec 27, 2007)
 * @since   1.0 (Jan 16, 2007)
 */
public class Death {
      
    /**
     * This class should be never be instantiated; this just ensures so.
     */
    private Death() {}
    
    /**
     * Purges a person from the Hoi Polloi database.
     * 
     * @param person The Person to Purge.
     * @return       True if successful, false otherwise.
     */
    public static boolean purgePerson(Person person) {
        int personID = person.getPersonID();
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            stmt.executeUpdate("DELETE FROM pmp_addresses WHERE (adrPersonID = \""+personID+"\")");
            stmt.executeUpdate("DELETE FROM pmp_categorylink WHERE (clkPersonID = \""+personID+"\")");
            stmt.executeUpdate("DELETE FROM pmp_contacts WHERE (ctnPersonID = \""+personID+"\")");
            stmt.executeUpdate("DELETE FROM pmp_people WHERE (psnPersonID = \""+personID+"\")");
            return true;
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
            return false;
        }
        finally {
            db.closeConnection();
        }
    }
    
}

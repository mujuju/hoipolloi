package hoipolloi;

/**
 * A static class to delete things from the Hoi Polloi database.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Jan 16, 2007)
 * @since   January 16, 2007
 */
public class Death {
      
    /**
     * This class should be never be instantiated; this just ensures so.
     */
    private Death() { }
    
    public static boolean purgeCategory(int categoryID) {
        return false;
    }
    
    public static boolean purgeCategory(int categoryID, int replacementCategory) {
        return false;
    }
    
    public static boolean purgePerson(int personID) {
        // Remove Contacts for this Person
        // Remove Addresses for this Person
        // Remove Categories for this Person
        // Remove this Person
        return false;
    }
    
}

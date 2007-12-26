package hoipolloi;

import java.util.ArrayList;

/**
 * The super class for all profile types.
 * 
 * Need to think about the design of this for awhile.
 *
 * @author Brandon Tanner
 */
public abstract class Profile {
    
    protected String profileTitle;
    protected String profileType;
    protected ArrayList <Contact> profileContacts;
    
    public Profile() {}
    

}

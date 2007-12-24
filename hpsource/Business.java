package hoipolloi;

import java.util.*;
import java.sql.*;

/**
 * A datatype to store a business or company.
 * 
 * @author  Brandon Tanner
 * @version 1.1 (Dec 23, 2007)
 * @since   Dec 23, 2007
 */
public class Business implements Comparable {
    
    /** The ID number of this Business from the Database */
    protected int businessID;
    
    /** The Business Name */
    protected String name;
    
    /** Primary Address of this Business */
    protected Address address;
    
    /** Contact information for this Business */
    protected ArrayList <Contact> contacts;
    
    /** The employees of this Business */
    protected ArrayList <Person> employees;
    
    /** The description of this Business */
    protected String description;
    
    /** The photo file name of this Business */
    protected String photoFileName;
    
    /** The Date When this Business Profile Was Last Updated in ISO-8601 Format YYYY-MM-DD */
    protected String lastUpdate;
    
    /** The List of Categories that this Business is in (each category is a KeyValue pair in this ArrayList) */
    protected ArrayList <KeyValue> categories;
    
    /** Creates a new instance of Business (for Adding a new one) */
    public Business() {
        this.businessID    = -1;
        this.name          = "";
        this.address       = new Address();
        this.contacts      = new ArrayList<Contact>();
        this.employees     = new ArrayList<Person>();
        this.description   = "";
        this.photoFileName = "";
        this.lastUpdate    = "0000-00-00";
        this.categories    = new ArrayList<KeyValue>();
    }
    
    /** 
     * Creates a new instance of Business given a Business ID and populates it from the database.
     *
     * @param bid The Business ID
     * @throws Throws an EmptyQueryException if there is an SQL error or if the PersonID doesn't exist.
     */
    public Business(int bid) throws EmptyQueryException {
        this();                          // Set Default Values
        this.businessID = bid;           // Set the Business ID     
        if (!loadFromDatabase()) {       // Load Business Info from Database
            throw new EmptyQueryException("Sorry, there was either an SQL Query Error or that Business ID doesn't exist.");
        }
    }
    
    /**
     * Loads all the information for this Business from the Database.
     *
     * @return True if Successful, otherwise false.
     */
    protected boolean loadFromDatabase() {
        return false;
    }

    /** {@inheritDoc} */
    public int compareTo(Object o) {
        return -1;
    }
    
    /**
     * Gets a string representation of this object, in this case, the business name.
     *
     * @return The name of this Business.
     */
    @Override public String toString() {
        if (!name.equals("")) {
            return name.trim();
        } 
        else {
            return "This Business doesn't have a name yet.";
        }
    }

}

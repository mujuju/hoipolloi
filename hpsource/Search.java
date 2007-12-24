package hoipolloi;

import java.util.*;
import java.sql.*;
import org.sqlite.JDBC;

/**
 * A Class to Perform Text Searches on the Hoi Polloi Database.
 *
 * @author  Brandon Tanner
 * @version 2.1 (Dec 28, 2006)
 * @since   December 12, 2006
 */
public class Search {
    
    /** The First Name Column Flag */
    protected boolean searchFirstName;
    /** The Last Name Column Flag */
    protected boolean searchLastName;
    /** The Nick Name Column Flag */
    protected boolean searchNickName;
    /** The Description Column Flag */
    protected boolean searchDescription;
    /** The Contacts Table Flag */
    protected boolean searchContacts;
    /** The Addresses Table Flag */
    protected boolean searchAddresses;
    /** The Last Searched Query Performed */
    protected String lastSearchQuery;
    /** The Number of Records the Last Query Returned */
    protected int lastSearchQueryCount;
    /** The Results of the Last Query Performed */
    protected ArrayList <KeyValue> lastSearchQueryResults;
    
    /**
     * Creates a new search object.
     * All of the columns to search in are enabled by default.
     * This includes first name, last name, nick name, description,
     * and various columns in the address and contact tables.
     */
    public Search() {
        this.searchFirstName   = true;
        this.searchLastName    = true;
        this.searchNickName    = true;
        this.searchDescription = true;
        this.searchContacts    = true;
        this.searchAddresses   = true;
    }
    
    /**
     * Gets the Last Search Query Text
     *
     * @return The Last Search Query Performed.
     */
    public String getLastSearchQuery() {
        return this.lastSearchQuery;
    }
    
    /**
     * Gets the number of records the last search query returned.
     *
     * @return The number of records the last search returned.
     */
    public int getLastSearchQueryCount() {
        return this.lastSearchQueryCount;
    }
    
    /**
     * Gets the results of the last search performed.
     *
     * @return The records the last search returned.
     */
    public ArrayList getLastSearchQueryResults() {
        return this.lastSearchQueryResults;
    }
    
    /**
     * A private helper method to see how many of the columns to search in are enabled.
     *
     * @return The number of columns to search in that are enabled.
     */
    private int howManyAreOn() {
        int flag = 0;
        if (this.searchFirstName)
            flag++;
        if (this.searchLastName)
            flag++;
        if (this.searchNickName)
            flag++;
        if (this.searchDescription)
            flag++;
        return flag;
    }
    
    /**
     * Performs a text search on the Hoi Polloi database.
     * 
     * @param  searchQuery  The text to search for.
     * @return              The List of people matching the search text.
     */
    public ArrayList performSearch(String searchQuery) {
        if (howManyAreOn() < 1 && !searchContacts && !searchAddresses) {
            return null;
        }
        /* From phpMyPeople */
        String query_array[] = searchQuery.split(" ");
        String words = "";
        int count = query_array.length;
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName FROM pmp_people WHERE (";
        for (int i=0; i < count; i++) {
            int howManyAreOn = howManyAreOn();
            words = query_array[i];
            if (this.searchFirstName) {
                sql += "psnFirstName LIKE '%"+words+"%'";
                howManyAreOn--;
                if (howManyAreOn > 0) {
                    sql += " OR ";
                }
            }
            if (this.searchLastName) {
                sql += "psnLastName LIKE '%"+words+"%'";
                howManyAreOn--;
                if (howManyAreOn > 0) {
                    sql += " OR ";
                }
            }
            if (this.searchNickName) {
                sql += "psnNickName LIKE '%"+words+"%'";
                howManyAreOn--;
                if (howManyAreOn > 0) {
                    sql += " OR ";
                }
            }
            if (this.searchDescription) {
                sql += "psnDescription LIKE '%"+words+"%'";
                howManyAreOn--;
                if (howManyAreOn > 0) {
                    sql += " OR ";
                }
            }
            sql += ")";
            if (i < count-1) {
                sql += " OR (";
            }
        }
        sql += " ORDER BY psnLastName";
        Debug.print(sql);
        /* End Build SQL from phpMyPeople */
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        DBConnection db = new DBConnection();
        try {
            if (howManyAreOn() > 0) {
                Statement stmt  = db.getDBStatement();
                ResultSet rs    = stmt.executeQuery(sql);
                while (rs.next()) {
                    people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
                }
            }
            
            if (this.searchAddresses && searchAddressesTable(query_array) != null) {
                people.addAll(searchAddressesTable(query_array));
            }
            if (this.searchContacts && searchContactsTable(query_array) != null) {
                people.addAll(searchContactsTable(query_array));
            }
            
            // Remove Duplicates
            ArrayList newList = new ArrayList();
            for (int i=0; i < people.size(); i++) {
                if (!newList.contains(people.get(i))) newList.add(people.get(i));
            }
            
            this.lastSearchQuery        = searchQuery;
            this.lastSearchQueryCount   = newList.size();
            this.lastSearchQueryResults = newList;
            
            return newList;
        }
        catch (Exception e) { Debug.print(e.getMessage()); return null; }
    }
    
    /**
     * A private helper method to search in the Addresses table.
     *
     * @param query  The Search Query text.
     * @return       The list of people that match the search query.
     */
    private ArrayList searchAddressesTable(String query[]) {
        if (query.length < 1) {
            return null;
        }
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName FROM pmp_people, pmp_addresses, pmp_countries WHERE (psnPersonID = adrPersonID) AND (adrCountryID = ctyCountryID) AND (";
        for (int i=0; i < query.length; i++) {
            String word = query[i];
            sql += "adrAddressLabel LIKE '%"+word+"%' OR adrAddressLine1 LIKE '%"+word+"%' OR adrAddressLine2 LIKE '%"+word+"%' OR adrAddressLine3 LIKE '%"+word+"%' OR adrCity LIKE '%"+word+"%' OR adrState LIKE '%"+word+"%' OR adrZip LIKE '%"+word+"%' OR ctyCountryName LIKE '%"+word+"%' OR adrDistrict LIKE '%"+word+"%'";
            if (i < query.length-1) {
                sql += " OR ";
            }
        }
        sql += ") ORDER BY psnLastName";
        Debug.print(sql);
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) { Debug.print(e.getMessage()); return null; }
    }
    
    /**
     * A private helper method to search in the contacts table.
     *
     * @param query  The search query.
     * @return       The list of records found.
     */
    private ArrayList searchContactsTable(String query[]) {
        if (query.length < 1) {
            return null;
        }
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName FROM pmp_people, pmp_contacts WHERE (psnPersonID = ctnPersonID) AND (";
        for (int i=0; i < query.length; i++) {
            String word = query[i];
            sql += "ctnContact LIKE '%"+word+"%'";
            if (i < query.length-1) {
                sql += " OR ";
            }
        }
        sql += ") ORDER BY psnLastName";
        Debug.print(sql);
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) { Debug.print(e.getMessage()); return null; }
    }
    
    /**
     * Enable/Disable searching the first name column.
     *
     * @param flag True or False
     */
    protected void searchFirstName(boolean flag) {
        this.searchFirstName = flag;
    }
    
    /**
     * Enable/Disable searching the last name column.
     *
     * @param flag True or False
     */
    protected void searchLastName(boolean flag) {
        this.searchLastName = flag;
    }
    
    /**
     * Enable/Disable searching the nick name column.
     *
     * @param flag True or False
     */
    protected void searchNickName(boolean flag) {
        this.searchNickName = flag;
    }
    
    /**
     * Enable/Disable searching the description column.
     *
     * @param flag True or False
     */
    protected void searchDescription(boolean flag) {
        this.searchDescription = flag;
    }
    
    /**
     * Enable/Disable searching the contacts table.
     *
     * @param flag True or False
     */
    protected void searchContacts(boolean flag) {
        this.searchContacts = flag;
    }
    
    /**
     * Enable/Disable searching the addresses table.
     *
     * @param flag True or False
     */
    protected void searchAddresses(boolean flag) {
        this.searchAddresses = flag;
    }
    
    /**
     * A main method used for testing.
     *
     * @param args Not Used.
     */
    public static void main(String[] args) {
        Debug.turnOn();
        Search bar = new Search();
        bar.searchFirstName(false);
        bar.searchLastName(false);
        bar.searchNickName(false);
        bar.searchDescription(false);
        bar.searchAddresses(false);
        //bar.searchContacts(false);
        ArrayList people = bar.performSearch("23262 CR 181 Bullard Texas TX USA");
        Debug.print(people.size());
        DBHPInterface.printListOfPeopleByLastNameToStdout(people);
    }
}

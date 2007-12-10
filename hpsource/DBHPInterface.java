package hoipolloi;

import java.util.*;
import java.sql.*;
import org.sqlite.JDBC;


/**
 * A class with various static methods to interact with the database.
 *
 * @author  Brandon Tanner
 * @version 1.1 (Dec 28, 2006)
 * @since   December 12, 2006
 */
public class DBHPInterface {
    
    /**
     * Gets a List of people sorted by Last Name.
     *
     * @return An ArrayList of KeyValue pairs of the people in the database.
     */
    protected static ArrayList getListOfPeopleByLastName() {
        ArrayList <KeyValue> people = new ArrayList();
        String sql = "SELECT * FROM pmp_people ORDER BY psnLastName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) { return null; }
    }
    
    protected static ArrayList getListOfCategories() {
        ArrayList <KeyValue> categories = new ArrayList();
        String sql = "SELECT * FROM pmp_categories";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                categories.add( new KeyValue( rs.getInt("catCategoryID"), rs.getString("catCategoryName") ) );
            }
            return categories;
        }
        catch (Exception e) { return null; }
    }
    
    protected static void printListOfPeopleByLastNameToStdout(ArrayList list) {
        if (list != null && list.size() > 0) {
            for (Object item: list) {
                System.out.println(item);
            }
        }
        else {
            System.out.println("There is nothing to Print.");
        }
    }
    
    /**
     * Gets a list of people in a category.
     *
     * @param CategoryID The Category ID to get the List of People in.
     * @return           The list of people who are in the given category.
     */
    protected static ArrayList getPeopleInCategory(int CategoryID) {
        ArrayList <KeyValue> people = new ArrayList();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people, pmp_categorylink WHERE (clkCategoryID = '"+CategoryID+"') AND (clkPersonID = psnPersonID) ORDER BY psnLastName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) { return null; }
    }
    
    /**
     * Finds the ID of a given Category Name in the Database.
     * Mostly used as a helper method for other methods.
     *
     * @since December 11, 2006
     * @param categoryQuery The category to find the ID for.
     * @return              The ID of the queried category.
     */
    protected static int getIDOfCategory(String categoryQuery) {
        String sql = "SELECT catCategoryID FROM pmp_categories WHERE (catCategoryName = '"+categoryQuery+"')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("catCategoryID");
        }
        catch (Exception e) {return -1;}
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Adds a New Category to the Database and returns its primary key.
     * If the new category already exists, it simply returns the primary
     * key of the existing category.
     *
     * @since December 11, 2006
     * @param cat The new category to add.
     * @return    The primary key ID of the newly added category.
     */
    protected static int addNewCategoryToDB(String cat) {
        String sql = "SELECT catCategoryID, COUNT(catCategoryID) AS numrows FROM pmp_categories WHERE (catCategoryName = '"+cat+"')";
        DBConnection db = new DBConnection();
        try {
            int possibleIDOfCategory = getIDOfCategory(cat);
            if (possibleIDOfCategory != 0) {
                // This Category already exists, just return its ID
                return possibleIDOfCategory;
            }
            else {
                // The Category doesn't exist, so Add it.
                Statement stmt  = db.getDBStatement();
                ResultSet rs    = stmt.executeQuery(sql);
                stmt.executeUpdate("INSERT INTO pmp_categories (catCategoryName) VALUES (\""+cat+"\")");
                int newID = getIDOfCategory(cat);
                if (newID != 0) {
                    return newID;
                }
                else {
                    return -1;
                }
            }
        }
        catch (Exception e) { Debug.print(e.getMessage()); return -1; }
        finally { db.closeConnection(); }
    }
    
    /**
     * Removes a Category from the Database *BEWARE - SEE Notes Below*
     * This method will remove a category and remove all the links to it
     * for each person. Be careful when using this method. Mostly made for
     * administration purposes.
     *
     * @since December 11, 2006
     * @param categoryToRemove The Category Name to Remove
     * @return                 True if successfull, otherwise false.
     */
    protected static boolean removeCategoryFromDB(String categoryToRemove) {
        int idOfCategoryToRemove = getIDOfCategory(categoryToRemove);
        if (idOfCategoryToRemove != 0) {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("DELETE FROM pmp_categorylink WHERE (clkCategoryID = '"+idOfCategoryToRemove+"')");
                stmt.executeUpdate("DELETE FROM pmp_categories WHERE (catCategoryID = '"+idOfCategoryToRemove+"')");
                return true;
            }
            catch (Exception e) { Debug.print(e.getMessage()); return false; }
            finally { db.closeConnection(); }
        }
        else {
            return false;
        }
    }
    
    protected static ArrayList getBirthdaysNextMonth() {
        // Figure Out Next Month in two digit format MM
        GregorianCalendar d = new GregorianCalendar();
        int curMonth        = d.get(Calendar.MONTH)+1; // Calendar Months are 0-11
        String nextMonth    = (curMonth < 12) ? String.valueOf(curMonth+1) : "01";
        return getBirthdaysInMonth(nextMonth);
    }
    
    protected static ArrayList getBirthdaysThisMonth() {
        return getBirthdaysInMonth("current");
    }
       
    protected static ArrayList getBirthdaysInMonth(String month) {
        if (month.equals("current")) {
            month = "strftime('%m', 'now', 'localtime')";
        }
        else {
            // Make Sure There is a Leading Zero
            if (month.length() < 2) {
                month = "0"+month;
            }
            month = "'"+month+"'";
        }
        ArrayList <KeyValue> people = new ArrayList();
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName, psnBirthday FROM pmp_people WHERE ("+month+" = strftime('%m', psnBirthday)) ORDER BY psnLastName ASC";
        Debug.print(sql);
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName")+" - "+rs.getString("psnBirthday")+" (27)" ) );
            }
            return people;
        }
        catch (Exception e) { return null; }
    }
    
    /**
     * Gets a List of Countries
     *
     * @return A List of Countries
     */
    protected static ArrayList getListOfCountries() {
        ArrayList <KeyValue> countries = new ArrayList();
        String sql = "SELECT ctyCountryID, ctyCountryName FROM pmp_countries";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                countries.add( new KeyValue( rs.getInt("ctyCountryID"), rs.getString("ctyCountryName") ) );
            }
            return countries;
        }
        catch (Exception e) { return null; }
    }
    
    /**
     * Gets the current Operating System.
     *
     * @return WINDOWS or UNIX.
     */
    protected static String getOS() {
        if (System.getProperty("os.name").toUpperCase() == "WINDOWS") {
            return "WINDOWS";
        }
        else {
            return "UNIX";
        }
    }
    
    public static void main(String[] args) {
        Debug.turnOn();
        
        //printListOfPeopleByLastNameToStdout(getListOfCategories());
        //printListOfPeopleByLastNameToStdout(getPeopleInCategory(1));
        //printListOfPeopleByLastNameToStdout(getListOfCountries());
        printListOfPeopleByLastNameToStdout(getBirthdaysInMonth("1"));
    }
    
}

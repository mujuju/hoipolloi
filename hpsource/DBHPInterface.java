package hoipolloi;

import java.util.*;
import java.sql.*;


/**
 * A class with various static methods to interact with the database.
 *
 * @author  Brandon Tanner
 * @version 1.4a (Nov 30, 2008)
 * @since   December 12, 2006
 */
public class DBHPInterface {
    
    /**
     * Gets the month number given a textual month.
     * 
     * If it can't recognize the parameter, it returns 00.
     * 
     * @param month The Textual Month (January, February ... etc)
     * @return      The month number (01, 02 ... etc)
     */
    protected static String getMonthNumber(String month) {
        if (month.equals("January")) {
            return "01";
        }
        else if (month.equals("February")) {
            return "02";
        }
        else if (month.equals("March")) {
            return "03";
        }
        else if (month.equals("April")) {
            return "04";
        }
        else if (month.equals("May")) {
            return "05";
        }
        else if (month.equals("June")) {
            return "06";
        }
        else if (month.equals("July")) {
            return "07";
        }
        else if (month.equals("August")) {
            return "08";
        }
        else if (month.equals("September")) {
            return "09";
        }
        else if (month.equals("October")) {
            return "10";
        }
        else if (month.equals("November")) {
            return "11";
        }
        else if (month.equals("December")) {
            return "12";
        }
        else {
            return "00";
        }
    }
    
    /**
     * Gets a List of people sorted by Last Name.
     *
     * @return An ArrayList of KeyValue pairs of the people in the database.
     */
    protected static ArrayList getListOfPeopleByLastName() {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
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
        ArrayList <KeyValue> categories = new ArrayList<KeyValue>();
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
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
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
     * Finds the ID of a given Demonym name in the Database.
     * 
     * @param demonym The Demonym Name String
     * @return The database id of the given demonym
     */
    public static int getIDOfDemonym(String demonym) {
        String sql = "SELECT demID FROM pmp_demonyms WHERE (demDemonym = '"+demonym+"')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("demID");
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
    
    /**
     * Gets the list of people with birthdays in the given month.
     * 
     * @param month Month as String
     * @return List of people with birthdays in given month
     */
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
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName, psnBirthday FROM pmp_people WHERE ("+month+" = strftime('%m', psnBirthday)) ORDER BY psnLastName ASC";
        Debug.print(sql);
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                int age = getAgeThisYear(rs.getString("psnBirthday"));
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName")+" - "+rs.getString("psnBirthday")+" ("+age+")" ) );
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
    public static ArrayList getListOfCountries() {
        ArrayList <KeyValue> countries = new ArrayList<KeyValue>();
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
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Gets a List of Demonyms.
     * 
     * @return A List of Demonyms sorted alphabetically
     */
    public static ArrayList getListOfDemonyms() {
        ArrayList <KeyValue> demonyms = new ArrayList<KeyValue>();
        String sql = "SELECT demID, demDemonym FROM pmp_demonyms ORDER BY demDemonym ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                demonyms.add( new KeyValue( rs.getInt("demID"), rs.getString("demDemonym") ) );
            }
            return demonyms;
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
            return null;
        }
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Gets the list of cities people have addresses in.
     * 
     * @return The City List
     */
    public static ArrayList getCitiesPeopleAreIn() {
        ArrayList <String> locations = new ArrayList<String>();
        String sql = "SELECT DISTINCT adrCity FROM pmp_addresses WHERE (adrCity <> '') ORDER BY adrCity";
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            ResultSet rs   = stmt.executeQuery(sql);
            while (rs.next()) {
                locations.add(rs.getString("adrCity"));
            }
            return locations;
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
            return null;
        }
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Gets "x" most recently added people.
     * 
     * @param count The Number of Recent People to Get
     * @return      The List of x Most recently added people
     */
    public static ArrayList getMostRecentlyAdded(int count) {
        if (count < 1) {
            count = 1;
        }
        ArrayList <String> newfish = new ArrayList <String>();
        String sql = "SELECT psnFirstName, psnLastName FROM pmp_people ORDER BY psnPersonID DESC LIMIT "+count;
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            ResultSet rs   = stmt.executeQuery(sql);
            while (rs.next()) {
                newfish.add(rs.getString("psnFirstName")+" "+rs.getString("psnLastName"));
            }
            return newfish;
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
            return null;
        }
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Gets the current Operating System.
     *
     * @return WINDOWS or UNIX.
     */
    public static String getOS() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return "WINDOWS";
        }
        else {
            return "UNIX";
        }
    }
    
    /**
     * Gets a person's age given a birthday.
     * 
     * @param strBirthday  A string of the form YYYY-MM-DD
     * @return             Age in Years
     */
    public static int getAge(String strBirthday) {
        if (strBirthday.equals("0000-00-00") || strBirthday.equals("")) {
            return -1;
        }
        else {
            int ageYears  = 0;
            Calendar cd   = Calendar.getInstance(); // Today's Date
            int year      = Integer.parseInt(strBirthday.substring(0,4));
            int month     = Integer.parseInt(strBirthday.substring(5,7));
            int day       = Integer.parseInt(strBirthday.substring(8));
            Calendar bd   = new GregorianCalendar(year, month-1, day);
            ageYears      = cd.get(Calendar.YEAR) - bd.get(Calendar.YEAR); // This can be a year too old.
            if (bd.get(Calendar.MONTH) < cd.get(Calendar.MONTH)) {
                return ageYears;
            }
            else if (bd.get(Calendar.MONTH) > cd.get(Calendar.MONTH)) {
                ageYears--; 
                return ageYears;
            } // over by a year
            else { // it's the month the user was born. we need to check the day
                if (bd.get(Calendar.DAY_OF_MONTH) > cd.get(Calendar.DAY_OF_MONTH)) {
                    ageYears--; 
                    return ageYears;
                } // over by a year
                else {
                    return ageYears;
                }
            }
        }
    }
    
    /**
     * Gets the Person's Age this Year.
     * 
     * Used for showing people with birthdays this month and how old they will be.
     *
     * @return The Person's Age this Year or -1 if their birthday isn't set.
     */
    public static int getAgeThisYear(String strBirthday) {
        if (strBirthday.equals("0000-00-00") || strBirthday.equals("")) {
            return -1;
        }
        else {
            Calendar cd   = Calendar.getInstance(); // Today's Date
            int year      = Integer.parseInt(strBirthday.substring(0,4));
            int month     = Integer.parseInt(strBirthday.substring(5,7));
            int day       = Integer.parseInt(strBirthday.substring(8));
            Calendar bd   = new GregorianCalendar(year, month-1, day);
            return          cd.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
        }
    }
    
}

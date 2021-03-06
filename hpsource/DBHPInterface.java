package hoipolloi;

import java.util.*;
import java.sql.*;


/**
 * A class with various static methods to interact with the database.
 *
 * @author  Brandon Tanner
 * @version 1.5b (Jan 5, 2009)
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
     * Gets a List of all people sorted by First Name.
     *
     * @return An ArrayList of KeyValue pairs of the people in the database.
     */
    protected static ArrayList getListOfPeopleByFirstName() {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people ORDER BY psnFirstName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;

        }
        catch (Exception e) { return null; }
        finally {
            db.closeConnection();
        }
    }
   
    /**
     * Gets a List of all people sorted by Last Name.
     *
     * @return An ArrayList of KeyValue pairs of the people in the database.
     */
    protected static ArrayList getListOfPeopleByLastName() {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people ORDER BY psnLastName ASC";
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
        finally {
            db.closeConnection();
        }
    }

    /**
     * Gets a list of all categories in the database.
     *
     * @return List of Categories.
     */
    protected static ArrayList getListOfCategories() {
        ArrayList <KeyValue> categories = new ArrayList<KeyValue>();
        String sql = "SELECT catCategoryID, catCategoryName FROM pmp_categories ORDER BY catCategoryName ASC";
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
        finally { db.closeConnection(); }
    }
    
    /**
     * Gets a list of people in a category.
     *
     * @param CategoryID The Category ID to get the List of People in.
     * @return           The list of people who are in the given category sorted by lastname.
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
     * Gets a list of people in a district sorted by lastname.
     *
     * @param district The District
     * @return The List of People sorted by last name.
     */
    protected static ArrayList getPeopleInDistrict(String district) {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people, pmp_addresses WHERE (adrPersonID = psnPersonID) AND (adrDistrict = '"+district+"') ORDER BY psnLastName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) {
            // Nobody Found in Given District
            return null;
        }
        finally {
            db.closeConnection();
        }
    }

    /**
     * Gets a list of people in a city sorted by lastname.
     *
     * @param city The City.
     * @return     The List of People that have addresses in the given city sorted by lastname.
     */
    protected static ArrayList getPeopleInCity(String city) {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people, pmp_addresses WHERE (adrPersonID = psnPersonID) AND (adrCity = '"+city+"') ORDER BY psnLastName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) {
            // Nobody Found in Given City
            return null;
        }
        finally {
            db.closeConnection();
        }
    }

    /**
     * Gets the list of people in a given country sorted by lastname.
     *
     * @param countryID The ID of the Country.
     * @return          The List of People in the Country sorted by lastname.
     */
    protected static ArrayList getPeopleInCountry(int countryID) {
        ArrayList <KeyValue> people = new ArrayList<KeyValue>();
        String sql = "SELECT psnPersonID, psnLastName, psnFirstName FROM pmp_people, pmp_addresses WHERE (adrPersonID = psnPersonID) AND (adrCountryID = '"+countryID+"') ORDER BY psnLastName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                people.add( new KeyValue( rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName") ) );
            }
            return people;
        }
        catch (Exception e) {
            // Nobody Found in Given Country
            return null;
        }
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Finds the ID of a given Category Name in the Database.
     *
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
        catch (Exception e) {
            // Not really an error, just can't find the category, so returns -1 to indicate that.
            return -1;
        }
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
     * @param cat The new category to add.
     * @return    The primary key ID of the newly added category.
     */
    protected static int addNewCategoryToDB(String cat) {
        String sql = "SELECT catCategoryID, COUNT(catCategoryID) AS numrows FROM pmp_categories WHERE (catCategoryName = '"+cat+"')";
        DBConnection db = new DBConnection();
        try {
            int possibleIDOfCategory = getIDOfCategory(cat);
            if (possibleIDOfCategory > 0) {
                // This Category already exists, just return its ID
                return possibleIDOfCategory;
            }
            else {
                Debug.print("here here here");
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
        catch (Exception e) {
            Debug.print("Error: Add New Category to the Database");
            Debug.print(e.getMessage());
            return -1;
        }
        finally {
            db.closeConnection();
        }
    }

    /**
     * Update a Category in the Hoi Polloi Database.
     * 
     * @param categoryID      The ID of the Category to Update.
     * @param newCategoryText The new category text.
     * @return                True if successful, false if not.
     */
    protected static boolean updateCategory(int categoryID, String newCategoryText) {
        if (categoryID < 1) {
            Debug.print("Parameter: categoryID of category to update is less than 1.");
            return false;
        }
        else if (newCategoryText.isEmpty()) {
            Debug.print("Parameter: newCategoryText is empty.");
            return false;
        }
        else {
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                stmt.executeUpdate("UPDATE pmp_categories SET catCategoryName = \""+newCategoryText.trim()+"\" WHERE (catCategoryID = \""+categoryID+"\")");
                return true;
            }
            catch (Exception e) {
                Debug.print("Update Category Failed in DBHPInterface.updateCategory()");
                Debug.print(e.getMessage());
                return false;
            }
            finally {
                // Close the Connection to the Database
                db.closeConnection();
            }
        }
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

    /**
     * Gets a list of people with birthdays next month.
     *
     * @return The List of People sorted by last name.
     */
    protected static ArrayList getBirthdaysNextMonth() {
        // Figure Out Next Month in two digit format MM
        GregorianCalendar d = new GregorianCalendar();
        int curMonth        = d.get(Calendar.MONTH)+1; // Calendar Months are 0-11
        String nextMonth    = (curMonth < 12) ? String.valueOf(curMonth+1) : "01";
        return getBirthdaysInMonth(nextMonth);
    }

    /**
     * Gets a list of people with birthdays this month.
     *
     * @return The List of People sorted by last name.
     */
    protected static ArrayList getBirthdaysThisMonth() {
        return getBirthdaysInMonth("current");
    }

    /**
     * Gets a list of people with birthdays last month.
     *
     * @return The List of People sorted by last name.
     */
    protected static ArrayList getBirthdaysPrevMonth() {
        // Figure Out Last Month in two digit format MM
        GregorianCalendar d = new GregorianCalendar();
        int curMonth        = d.get(Calendar.MONTH)+1; // Calendar Months are 0-11
        String prevMonth    = (curMonth > 1) ? String.valueOf(curMonth-1) : "12";
        return getBirthdaysInMonth(prevMonth);
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
     * Gets a List of All Countries.
     *
     * @return A List of All Countries.
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
     * Gets a list of Districts people are in.
     *
     * @return ArrayList of Districts.
     */
    public static ArrayList getListOfDistricts() {
        ArrayList districts = new ArrayList();
        String sql = "SELECT DISTINCT adrDistrict FROM pmp_addresses ORDER BY adrDistrict ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                districts.add(rs.getString("adrDistrict"));
            }
            return districts;
        }
        catch (Exception e) { return null; }
        finally {
            db.closeConnection();
        }
    }
    
    /**
     * Gets a List of All Demonyms.
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
     * @return The City List sorted Alphabetically.
     */
    public static ArrayList getCitiesPeopleAreIn() {
        ArrayList <String> locations = new ArrayList<String>();
        String sql = "SELECT DISTINCT adrCity FROM pmp_addresses WHERE (adrCity <> '') ORDER BY adrCity ASC";
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
     * Gets a list of countries people have addresses in.
     *
     * @return The List of Countries sorted Alphabetically.
     */
    public static ArrayList getCountriesPeopleAreIn() {
        ArrayList <KeyValue> countries = new ArrayList<KeyValue>();
        String sql = "SELECT DISTINCT ctyCountryID, ctyCountryName FROM pmp_countries, pmp_addresses WHERE (adrCountryID = ctyCountryID) ORDER BY ctyCountryName ASC";
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            ResultSet rs   = stmt.executeQuery(sql);
            while (rs.next()) {
                countries.add(new KeyValue(rs.getInt("ctyCountryID"), rs.getString("ctyCountryName")));
            }
            return countries;
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
        ArrayList <KeyValue> newfish = new ArrayList <KeyValue>();
        String sql = "SELECT psnPersonID, psnFirstName, psnLastName FROM pmp_people ORDER BY psnPersonID DESC LIMIT "+count;
        DBConnection db = new DBConnection();
        try {
            Statement stmt = db.getDBStatement();
            ResultSet rs   = stmt.executeQuery(sql);
            while (rs.next()) {
                newfish.add(new KeyValue(rs.getInt("psnPersonID"), rs.getString("psnLastName")+", "+rs.getString("psnFirstName")));
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
        return (System.getProperty("os.name").startsWith("Windows")) ? "WINDOWS" : "UNIX";
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

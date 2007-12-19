package hoipolloi;

import java.util.*;
import java.sql.*;
import javax.swing.*;

/**
 * A static class containing various methods to get statistical data about the database.
 *
 * @author  Brandon Tanner
 * @version 1.1 Dec 19, 2007
 * @since   Dec 12, 2006
 */
public class Stats {

    protected int numberOfProfilesWithoutPhotos;
    protected int numberOfPeopleNotInAnyCategories;
    protected int numberOfCountriesWithPeople;
    // Then List the Categories with number of records in each one
    
    /** Gets the Number of Categories */
    public static int getNumberOfCategories() {
        return DBHPInterface.getListOfCategories().size();
    }
    
    public static String listCategoriesByPeopleNumber() {
        String sql = "SELECT catCategoryName, count(*) AS 'Number of People' FROM pmp_categories AS l INNER JOIN pmp_categorylink AS c ON c.clkCategoryID = l.catCategoryID GROUP BY catCategoryName";
        String foo = "";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            while (rs.next()) {
                foo += "<tr><td>"+rs.getString("catCategoryName")+"</td><td>:</td><td>"+rs.getString("Number of People")+"</td></tr>";
            }
            return foo;
        }
        catch (Exception e) { return "false"; }
        finally { db.closeConnection(); }          
    }
    
    public static int getNumberOfPeopleWithoutBirthdays() {
        String sql = "SELECT COUNT(*) AS total FROM pmp_people WHERE (psnBirthday IS NULL) OR (psnBirthday = '') OR (psnBirthday = '0000-00-00') OR (psnBirthday = '2006-11-25')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("total");
        }
        catch (Exception e) { return -1; }
        finally { db.closeConnection(); }        
    }
    
    public static int getNumberOfMales() {
        String sql = "SELECT COUNT(*) AS total FROM pmp_people WHERE (psnGender = 'Male') OR (psnGender = 'male')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("total");
        }
        catch (Exception e) { return -1; }
        finally { db.closeConnection(); }
    }
    
    public static int getNumberOfFemales() {
        String sql = "SELECT COUNT(*) AS total FROM pmp_people WHERE (psnGender = 'Female') OR (psnGender = 'female')";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("total");
        }
        catch (Exception e) { return -1; }
        finally { db.closeConnection(); }
    }
    
    /**
     * Gets the Number of People in the Database.
     *
     * @return The integer value of the number of people in the database.
     */
    public static int getNumberOfPeople() {
        String sql = "SELECT COUNT(*) AS total FROM pmp_people";
        DBConnection db = new DBConnection();
        try {
            Statement stmt  = db.getDBStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            return rs.getInt("total");
        }
        catch (Exception e) { return -1; }
        finally { db.closeConnection(); }
    }
    
    /** Used for debugging and testing purposes */
    public static void main(String[] args) {
        Debug.turnOn();
        Debug.print(getNumberOfPeople());
        showStats(null);
    }
    
    /** Shows a Dialog Box with some Statistics */
    public static void showStats(MainMenu owner) {
        String summary = "<html><table>";
        summary += "<tr><td>Number of Profiles</td><td>:</td><td>"+getNumberOfPeople()+"</td></tr>";
        summary += "<tr><td>Number of Categories</td><td>:</td><td>"+getNumberOfCategories()+"</td></tr>";
        summary += "<tr><td>People without Birthdays</td><td>:</td><td>"+getNumberOfPeopleWithoutBirthdays()+"</td></tr>";
        summary += "<tr><td>Number of Males</td><td>:</td><td>"+getNumberOfMales()+"</td></tr>";
        summary += "<tr><td>Number of Females</td><td>:</td><td>"+getNumberOfFemales()+"</td></tr>";
        summary += "<tr><td>-----------------</td><td>:</td><td>-----</td></tr>";
        //summary += listCategoriesByPeopleNumber();
        summary += "</table></html>";
        JOptionPane.showMessageDialog(owner, summary, "Hoi Polloi Statistics", JOptionPane.DEFAULT_OPTION);
    }
    
}

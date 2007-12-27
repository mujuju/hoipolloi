package hoipolloi;

import java.sql.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * SQLite Database Connection Class.
 *
 * @author  Brandon Tanner
 * @version 1.1 (Dec 16, 2007)
 * @since   November 20, 2006
 */
public class DBConnection {
    
    /** A database connection */
    private Connection conn;
    
    /** The path to the database file */
    private String DBFilePath;
    
    /** Creates a new instance of DBConnection */
    public DBConnection() {
        loadDBFilePath();
    }
    
    /** Gets the database statement */
    public Statement getDBStatement() {
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+this.DBFilePath);
            stmt = conn.createStatement();
        }
        catch (Exception e) {}
        return stmt;
    }
    
    /** Closes the database connection */
    public void closeConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {
            Debug.print(e.getMessage());
        }
    }
    
    /** Loads the File Path to the Database */
    private void loadDBFilePath() {
        Properties propFile;
        try {
            propFile = new Properties();
            propFile.load(new java.io.FileInputStream(new java.io.File("hp.properties")));
            this.DBFilePath = propFile.getProperty("dbfilepath");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to Located the Database File\nShutting down program.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
}

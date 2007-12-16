package hoipolloi;

import java.sql.*;

/**
 * SQLite Database Connection Class.
 * 
 * Be sure to edit the fileName path to your SQLite Database file.
 *
 * @author  Brandon Tanner
 * @version 1.0 (Dec 9, 2007)
 * @since   November 20, 2006
 */
public class DBConnection {
    
    private Connection conn;
    
    /** Creates a new instance of DBConnection */
    public DBConnection() {
    }
    
    public Statement getDBStatement() {
        Statement stmt = null;
        try {
            String fileName = "C:\\Users\\Brandon\\Desktop\\Programs\\Old Programs\\College Programs\\hoipolloi\\pmp.s3db";
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:"+fileName);
            stmt = conn.createStatement();
        }
        catch (Exception e) {}
        return stmt;
    }
    
    public void closeConnection() {
        try {
            conn.close();
        }
        catch (Exception e) {}
    }
    
}

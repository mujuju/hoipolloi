package hoipolloi;

import java.sql.*;
import org.sqlite.JDBC;

/**
 * SQLite Database Connection Class
 *
 * @author Brandon Tanner
 * @version 1.0
 * @since November 20, 2006
 */
public class DBConnection {
    
    private Connection conn;
    
    /** Creates a new instance of DBConnection */
    public DBConnection() {
    }
    
    public Statement getDBStatement() {
        Statement stmt = null;
        try {
            String fileName = "/home/pyrite/Projects/pmp.s3db";
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

package hoipolloi;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.util.*;
import java.sql.*;

/**
 * Export a JTable to an Excel (CSV) File.
 * 
 * Most of this code is taken from the book
 * Swing Hacks by Chris Adamson and Joshua Marinacci.
 * 
 * Todo: Perhaps a helper method to ask where to save the file.
 * 
 * @author  Brandon Tanner
 * @author  Chris Adamson
 * @author  Joshua Marinacci
 * @version 1.0 (Dec 11, 2007) 
 */
public class ExcelExporter {
    
    /** Default Constructor */
    public ExcelExporter() {}

    /**
     * Exports a JTable to an Excel (CSV) file.
     * 
     * @param  table The JTable to Export.
     * @param  file  The Excel (CSV) file to write to.
     * @throws java.io.IOException
     */
    public void exportTable(JTable table, File file) throws IOException { 
        TableModel model = table.getModel(); 
        FileWriter out = new FileWriter(file);
        // Write the Column Name Row
        for(int i=0; i < model.getColumnCount(); i++) { 
            out.write(model.getColumnName(i) + "\t"); 
        } 
        out.write("\n");
        // Write Each Row of Data
        for(int i=0; i< model.getRowCount(); i++) { 
            for(int j=0; j < model.getColumnCount(); j++) { 
                out.write(model.getValueAt(i,j).toString()+"\t"); 
            } 
            out.write("\n"); 
        } 
        out.close(); 
        Debug.print("write out to: " + file); 
    }
    
    /**
     * Export a Category of People to Samsung SGH-T509 Phone Format.
     * 
     * @param category The Category of People to Export.
     * @param format   The Format to use (SIM or PHONE).
     * @param path     The file path of the CSV file.
     */
    public void exportT509(Object category, String format, String path) {
         // based on the columns, get group data
        int categoryID = ((KeyValue)category).getKey();
        
        // Construct People to Export
        ArrayList people = DBHPInterface.getPeopleInCategory(categoryID);
        
        // Construct Column Names for use in SQL Query
        ArrayList cNames = new ArrayList();
        for(Object c : cols) {
            String col = (String)c;
            String dbColName = "psn"+col;
            dbColName = dbColName.replaceAll(" ", "");
            dbColName = dbColName.trim();
            cNames.add(dbColName);
        }
        
        // Construct SQL Query
        String sql = "SELECT ";
        for (Object c : cNames) {
            sql += (String)c+", ";
        }
        sql = sql.trim();
        sql = sql.substring(0, sql.lastIndexOf(','));
        sql += " FROM pmp_people WHERE (psnPersonID = '";
        
        ArrayList data = new ArrayList();
        String isql = "";
        for (Object p : people) {
            int uid = ((KeyValue)p).getKey();
            isql = sql;
            isql += uid+"')";
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                ResultSet rs    = stmt.executeQuery(isql);
                while (rs.next()) {
                    String row = "";
                    for (int i=1; i <= cols.length; i++) {
                        row += "\""+rs.getString(i)+"\"";
                        if (i < cols.length) {
                            row += "\t";
                        }
                    }
                    row += "\n";
                    data.add(row);
                }
            }
            catch (Exception e) { Debug.print(e.getMessage()); }
        }
        
        
        try {
            File oFile = new File(path);
            FileWriter out = new FileWriter(oFile);
            // Write Column Names Row
            for (Object c : cols) {
                out.write("\""+(String)c + "\"\t");
            }
            out.write("\n");
            // Write Each Row of Data
            for (Object d : data) {
                out.write((String)d);
            }
            out.close();
        }
        catch (Exception e) {
            Debug.print("Oh shit, we got problems");
            Debug.print(e.getMessage());
        }       
    }
    
    /**
     * Export a Category of People to CSV format.
     * 
     * @param category The Category of People to Export.
     * @param cols     The columns to use.
     * @param path     The file path of the CSV file.
     */
    public void exportCategory(Object category, Object[] cols, String path) {
        // based on the columns, get group data
        int categoryID = ((KeyValue)category).getKey();
        
        // Construct People to Export
        ArrayList people = DBHPInterface.getPeopleInCategory(categoryID);
        
        // Construct Column Names for use in SQL Query
        ArrayList cNames = new ArrayList();
        for(Object c : cols) {
            String col = (String)c;
            String dbColName = "psn"+col;
            dbColName = dbColName.replaceAll(" ", "");
            dbColName = dbColName.trim();
            cNames.add(dbColName);
        }
        
        // Construct SQL Query
        String sql = "SELECT ";
        for (Object c : cNames) {
            sql += (String)c+", ";
        }
        sql = sql.trim();
        sql = sql.substring(0, sql.lastIndexOf(','));
        sql += " FROM pmp_people WHERE (psnPersonID = '";
        
        ArrayList data = new ArrayList();
        String isql = "";
        for (Object p : people) {
            int uid = ((KeyValue)p).getKey();
            isql = sql;
            isql += uid+"')";
            DBConnection db = new DBConnection();
            try {
                Statement stmt  = db.getDBStatement();
                ResultSet rs    = stmt.executeQuery(isql);
                while (rs.next()) {
                    String row = "";
                    for (int i=1; i <= cols.length; i++) {
                        row += "\""+rs.getString(i)+"\"";
                        if (i < cols.length) {
                            row += "\t";
                        }
                    }
                    row += "\n";
                    data.add(row);
                }
            }
            catch (Exception e) { Debug.print(e.getMessage()); }
        }
        
        
        try {
            File oFile = new File(path);
            FileWriter out = new FileWriter(oFile);
            // Write Column Names Row
            for (Object c : cols) {
                out.write("\""+(String)c + "\"\t");
            }
            out.write("\n");
            // Write Each Row of Data
            for (Object d : data) {
                out.write((String)d);
            }
            out.close();
        }
        catch (Exception e) {
            Debug.print("Oh shit, we got problems");
            Debug.print(e.getMessage());
        }
    }
    
}

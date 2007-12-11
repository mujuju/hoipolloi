package hoipolloi;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

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
        for(int i=0; i < model.getColumnCount(); i++) { 
            out.write(model.getColumnName(i) + "\t"); 
        } 
        out.write("\n"); 
        for(int i=0; i< model.getRowCount(); i++) { 
            for(int j=0; j < model.getColumnCount(); j++) { 
                out.write(model.getValueAt(i,j).toString()+"\t"); 
            } 
            out.write("\n"); 
        } 
        out.close(); 
        Debug.print("write out to: " + file); 
    }
    
}

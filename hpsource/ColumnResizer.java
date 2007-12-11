package hoipolloi;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Component;

/**
 * A static class to resize a JTable's columns to suit the table contents.
 * 
 * From the book, Swing Hacks, Hack #21.
 * 
 * @author  Chris Adamson
 * @author  Joshua Marinacci
 * @version 1.0 (Dec 11, 2007)
 */
public class ColumnResizer {                 
    public static void adjustColumnPreferredWidths(JTable table) { 
        // strategy - get max width for cells in column and 
        // make that the preferred width 
        TableColumnModel columnModel = table.getColumnModel(); 
        for (int col=0; col<table.getColumnCount(); col++) { 
            int maxwidth = 0;             
            for (int row=0; row<table.getRowCount(); row++) { 
                TableCellRenderer rend = table.getCellRenderer(row, col);  
                Object value = table.getValueAt (row, col);  
                Component comp = rend.getTableCellRendererComponent (table, value, false, false, row, col); 
                maxwidth = Math.max (comp.getPreferredSize().width, maxwidth);  
            } // for row 
            TableColumn column = columnModel.getColumn (col);  
            column.setPreferredWidth (maxwidth); 
        } // for col  
    }
} 

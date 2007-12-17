/*
 * How to Install/Setup with Netbeans:
 * 
 * Requirements:
 *    1. Install 100% Pure Java SQLite JDBC Package into your classpath.
 *       http://www.zentus.com/sqlitejdbc/ (use the 100% pure NestedVM one)
 * 
 *    2. Install the pgslookandfeel package into your classpath.
 *       https://pgslookandfeel.dev.java.net/
 * 
 *    3. Install the swing-layout package into your classpath.
 *       It comes with netbeans in the platform(x)/modules/ext directory.
 *       The filename is called "swing-layout-1.0.3.jar"
 * 
 *    4. Get the example SQLite Database file and put it somewhere.
 *       Then edit the fileName path to it in the DBConnection.java
 *       The empty/blank database file is in the trunk/misc repository.
 * 
 *    5. Get all the images from the trunk/images repository.
 *       Netbeans wants them in the project folder/src directory.
 * 
 *    6. Put the hp.properties file in the main netbeans project folder.
 * 
 *    7. Last, just checkout the trunk/hpsource files into your project.
 * 
 */

package hoipolloi;

/**
 * The main entry point for the Hoi Polloi application.
 * 
 * View the source of this file for install/setup instructions.
 *
 * @author  Brandon Buck
 * @author  Brandon Tanner
 * @version 1.1 (Dec 16, 2007)
 */
public class Main {
    
    /**
     * Runs the Hoi Polloi application.
     * 
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        Debug.turnOn();
        new MainMenu();
    }
    
}

package hoipolloi;

/**
 * This class was made to display to the default output device if set to on.
 * 
 * Debug makes it easier to have println statements in your program that you
 * can turn on and off from one location in any program.
 *
 * @author Brandon Buck
 * @since  Sep 19, 2006
 */
public class Debug  {
    /** Holds the state of this object */
    private static boolean  on = false;
    
    /** Creates a new instance of Debug */
    public Debug() {}
    
    /**
     * Turns the debug printer on.
     */
    public static void turnOn() {
        on = true;
    }
    
    /**
     * Turns the debug printer off.
     */
    public static void turnOff() {
        on = false;
    }
    
    /**
     * Prints a statement to the system provided that Debug is on.
     * 
     * @param s String to be printed
     */
    public static void print(String s) {
        if (on)
            System.out.println(s);
    }
    
    /**
     * Prints the toString representation of an object, provided that Debug is on.
     * 
     * @param o Object to be printed
     */
    public static void print(Object o) {
        if (on)
            System.out.println(o.toString());
    }
}

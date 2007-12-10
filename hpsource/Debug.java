/*
 * Debug.java
 *
 * Created on September 19, 2006, 6:54 PM
 *
 */
package hoipolloi;
/**
 * This class was made to display to the default output device if set ot on.
 * Debug makes it easier to have println statements in your program that you
 * can turn on and off from one location in any program.
 *
 * @author Brandon Buck
 */
public class Debug  {
    /** Holds the state of this object */
    private static boolean  on = false;
    /** Creates a new instance of Debug */
    public Debug() { }
    /**
     * Turns the debug printer on
     */
    public static void turnOn() {
        on = true;
    }
    /**
     * Turns the debug printer off
     */
    public static void turnOff() {
        on = false;
    }
    /**
     * Prints a statement to the system provided that this Debug is on
     * @param s String to be printed
     */
    public static void print(String s) {
        if (on)
            System.out.println(s);
    }
    
    public static void print(Object o) {
        if (on)
            System.out.println(o.toString());
    }
}

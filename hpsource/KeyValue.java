package hoipolloi;

/**
 * A KeyValue Class to store integer key and string value pairs.
 * This comes in handy when getting info from a database and you want to 
 * store the key from the database and a column of some text. The reason
 * I made this is because Swing combo boxes don't store two seperate values
 * only one for text and a generic index, so instead of setting the index
 * to be the same as my keys from the database, I just have an ArrayList or
 * other collection of KeyValue objects.
 *
 * @author  Brandon Tanner
 * @version 1.1
 * @since November 26, 2006
 */
public class KeyValue implements Comparable {
    
    /** The Integer Key */
    protected int key;
    /** The String Value */
    protected String value;
    
    /** 
     * Creates a new instance of KeyValue given a key and a value 
     *
     * @param key   The Integer Key
     * @param value The String Value
     */
    public KeyValue(int key, String value) {
        this.key   = key;
        this.value = value;
    }
    
    /** 
     * Creates a new instance of KeyValue.
     * The key is defaulted to negative one and the value to a blank string.
     */
    public KeyValue() {
        this.key   = -1;
        this.value = "";
    }
    
    /**
     * Gets the Integer Key
     *
     * @return The Integer Key of this KeyValue Object
     */
    public int getKey() {
        return this.key;
    }
    
    /**
     * Gets the String Value
     *
     * @return The String Value of this KeyValue Object
     */
    public String getValue() {
        return this.value;
    }
    
    /**
     * Sets the Integer Key of this KeyValue Object
     *
     * @param key The Integer Key to Set
     */
    public void setKey(int key) {
        this.key = key;
    }
    
    /**
     * Sets the String Value of this KeyValue Object
     *
     * @param value The String Value to Set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    /* Documentation for this Method is Inherited */
    public int compareTo(Object o) {
        try {
            return this.value.compareTo(((KeyValue)o).getValue());
        }
        catch (Exception e) { return -1; }
    }
    
    /**
     * Returns a String of the Key and Value separated by a space.
     *
     * @return The Key and Value separated by a space.
     */
    public String toString() {
        return this.key + " " + this.value;
    }
    
    /**
     * Finds out if the keys and values match for this KeyValue and another KeyValue object.
     *
     * @param o The object to compare with this one.
     * @return True if both keys and values match, otherwise false.
     */
    public boolean equals(Object o) {
        if (this.key == ((KeyValue)o).getKey() && this.value.equals(((KeyValue)o).getValue()))
            return true;
        else
            return false;
    }
    
}

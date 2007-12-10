package hoipolloi;

/**
 * A custom Exception for an empty or null database query.
 *
 * @author Brandon Tanner
 * @version 1.1
 * @since November 20, 2006
 */
public class EmptyQueryException extends Exception {
    
    /** Creates a new instance of EmptyQueryException */
    public EmptyQueryException() {
        super();
    }
    
    public EmptyQueryException(String msg) {
        super(msg);
    }
    
}

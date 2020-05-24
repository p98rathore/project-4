/*
 * 
 */
package in.co.sunrays.proj4.exception;

// TODO: Auto-generated Javadoc
/**
 * ApplicationException is propogated by DAO classes when an unhandled Database
 * exception occurred.
 *
 * @author Facade
 * @version 1.0
 * Facade
 */
public class ApplicationException extends Exception {

    /**
     * Instantiates a new application exception.
     *
     * @param msg            : Error message
     */
    public ApplicationException(String msg) {
        super(msg);
    }
}
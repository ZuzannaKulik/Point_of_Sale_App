package Exceptions;

//ExitException - when the input is 'exit', so the recepit for the client should be printed and the next scanned product is for a new client
public class ExitException extends Exception {

    public ExitException(String message) {
        super(message);
    }

    public ExitException(String message, Throwable cause) {
        super(message, cause);
    }

}

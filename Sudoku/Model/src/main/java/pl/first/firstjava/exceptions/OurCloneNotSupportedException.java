package pl.first.firstjava.exceptions;

public class OurCloneNotSupportedException extends SudokuApplicationException {
    public OurCloneNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }
}

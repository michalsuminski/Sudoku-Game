package pl.first.firstjava.exceptions;

public class OurSqlException extends SudokuApplicationException {
    public OurSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}

package pl.first.firstjava.exceptions;

public class SudokuApplicationException extends Exception {

    public SudokuApplicationException() {
    }

    public SudokuApplicationException(String message) {
        super(message);
    }

    public SudokuApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

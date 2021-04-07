package pl.first.firstjava.exceptions;

public class NoSuchFileException extends SudokuApplicationException {

    public NoSuchFileException() {
        super();
    }

    public NoSuchFileException(String message) {
        super(message);
    }

    public NoSuchFileException(String message, Throwable cause) {
        super(message, cause);
    }
}

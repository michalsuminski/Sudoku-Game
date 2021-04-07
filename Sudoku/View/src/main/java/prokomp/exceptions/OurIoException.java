package prokomp.exceptions;

import pl.first.firstjava.exceptions.SudokuApplicationException;

public class OurIoException extends SudokuApplicationException {

    public OurIoException(String message, Throwable cause) {
        super(message, cause);
    }
}

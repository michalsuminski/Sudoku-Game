package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import pl.first.firstjava.exceptions.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {
    FileSudokuBoardDao f = new FileSudokuBoardDao("Dane");
    FileSudokuBoardDao fdao = new FileSudokuBoardDao("XD");
    BacktrackingSudokuSolver b = new BacktrackingSudokuSolver();
    SudokuBoard s = new SudokuBoard(b);

    @Test
    void readTest() throws NoSuchFileException{
        s.solveGame();
        try {
            f.write(s);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(s.toString(), f.read().toString());
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
        try {
            fdao.read();
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
    }
}
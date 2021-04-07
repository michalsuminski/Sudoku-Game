package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SudokuBoardTest {
    BacktrackingSudokuSolver bTest = new BacktrackingSudokuSolver();

    SudokuBoard sTest = new SudokuBoard(bTest);

    @Test
    void getTest() {
        sTest.set(5,8,8);
        assertTrue(sTest.get(5,8) == 8);
    }

    @Test
    void getTestNegative() {
        sTest.set(5,8,8);
        assertFalse(sTest.get(5,8)!=8);
    }

    @Test
    void isProperlySolved() {
        sTest.solveGame();
        assertTrue(sTest.checkBoard());
        sTest.set(0, 0, 4);
        sTest.set(0, 1, 4);
        sTest.set(0, 2, 4);
        assertFalse(sTest.checkBoard());
        sTest.solveGame();
        sTest.set(0, 0, 4);
        sTest.set(1, 0, 4);
        sTest.set(2, 0, 4);
        assertFalse(sTest.checkBoard());
        sTest.solveGame();
        sTest.set(0, 0, 4);
        sTest.set(1, 1, 4);
        sTest.set(2, 2, 4);
        assertFalse(sTest.checkBoard());
    }

    @Test
    void getRowTest() {
        sTest.solveGame();
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(sTest.get(1, i));
        }
        SudokuRow sudokuRow = new SudokuRow(fields);
        assertEquals(sTest.getRow(1).toString(), sudokuRow.toString());
    }

    @Test
    void getColumnTest() {
        sTest.solveGame();
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(sTest.get(i, 1));
        }
        SudokuColumn sudokuColumn = new SudokuColumn(fields);
        assertEquals(sTest.getColumn(1).toString(), sudokuColumn.toString());
    }
    @Test
    void getBoxTest() {
        sTest.solveGame();
        List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
        int iter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields.set(iter, new SudokuField());
                fields.get(iter).setValue(sTest.get(i, j));
                iter++;
            }
        }
        SudokuBox sudokuBox = new SudokuBox(fields);
        assertEquals(sTest.getBox(1,1).toString(), sudokuBox.toString());
    }

    @Test
    void testEquals() {
        sTest.solveGame();
        SudokuBoard newS = sTest;
        assertTrue(sTest.equals(newS));
    }

    @Test
    void negativeTestEquals() {
        sTest.solveGame();
        SudokuBoard newS = new SudokuBoard(bTest);
        newS.solveGame();
        assertFalse(sTest.equals(newS));
    }

    @Test
    void testEqualsObjectAndNull() {
        sTest.solveGame();
        SudokuBoard newS = null;
        assertNotEquals(newS, sTest);
    }
    @Test
    void testEqualsNull() {
        sTest.solveGame();
        assertFalse(sTest.equals(null));
    }

    @Test
    void testHashCode() {
        sTest.solveGame();
        SudokuBoard newS = sTest;
        assertEquals(sTest.hashCode(), newS.hashCode());
    }

    @Test
    void negativeTestHashCode() {
        sTest.solveGame();
        SudokuBoard newS = new SudokuBoard(bTest);
        newS.solveGame();
        assertNotEquals(sTest.hashCode(), newS.hashCode());
    }

    @Test
    void toStringTest() {
        sTest.solveGame();
        System.out.println(sTest.toString());
        assertNotNull(sTest.toString());
    }

    @Test
    void getFieldTest() {
        SudokuField field = sTest.getField(1,1);
        assertEquals(field.getValue(), sTest.get(1,1));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        SudokuBoard sudoku = sTest.clone();
        assertEquals(sudoku.get(1,1), sTest.get(1,1));
        assertNotSame(sudoku, sTest);
    }
}

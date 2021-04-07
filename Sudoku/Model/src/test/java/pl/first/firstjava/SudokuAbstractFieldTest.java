package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuAbstractFieldTest {
    List<SudokuField> fields = Arrays.asList(new SudokuField[9]);
    SudokuAbstractField testing = new SudokuAbstractField(fields);
    @Test
    void verifyNegativeTest() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(1);
        }
        SudokuRow s = new SudokuRow(fields);
        assertFalse(s.verify());
    }

    @Test
    void verifyTest() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(i);
        }
        SudokuRow s = new SudokuRow(fields);
        assertTrue(s.verify());
    }

    @Test
    void testEquals() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(1);
        }
        SudokuAbstractField testing1 = testing;
        assertTrue(testing1.equals(testing));
    }

    @Test
    void negativeTestEquals() {
        List<SudokuField> newList = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            newList.set(i, new SudokuField());
            fields.get(i).setValue(1);
            newList.get(i).setValue(2);
        }
        SudokuAbstractField testing1 = new SudokuAbstractField(newList);
        assertFalse(testing.equals(testing1));
    }

    @Test
    void testNullEquals() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(1);
        }
        SudokuAbstractField testing1 = null;
        assertFalse(testing.equals(testing1));
    }

    @Test
    void testHashCode() {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(1);
        }
        SudokuAbstractField testing1 = testing;
        assertEquals(testing.hashCode(), testing1.hashCode());
    }

    @Test
    void negativeTestHashCode() {
        List<SudokuField> newList = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            newList.set(i, new SudokuField());
            fields.get(i).setValue(1);
            newList.get(i).setValue(2);
        }
        SudokuAbstractField testing1 = new SudokuAbstractField(newList);
        assertNotEquals(testing.hashCode(), testing1.hashCode());
    }
    @Test
    void testClone() throws CloneNotSupportedException {
        for (int i = 0; i < 9; i++) {
            fields.set(i, new SudokuField());
            fields.get(i).setValue(i);
        }
        SudokuRow row = new SudokuRow(fields);
        SudokuBox box = new SudokuBox(fields);
        SudokuColumn column = new SudokuColumn(fields);

        assertEquals(row.clone(), row);
        assertEquals(box.clone(), box);
        assertEquals(column.clone(), column);
    }
}
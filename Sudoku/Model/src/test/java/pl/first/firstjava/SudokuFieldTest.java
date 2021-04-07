package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {
    SudokuField field = new SudokuField();

    @Test
    void testConstructor() {
        int valueField = 4;
        SudokuField fieldTest = new SudokuField(valueField);
        int result = fieldTest.getValue();
        assertEquals(valueField,result);
    }

    @Test
    void getFieldValue() {
        assertTrue(field.getValue() == 0);
    }

    @Test
    void getFieldValueNegative() {
        assertFalse(field.getValue() != 0);
    }

    @Test
    void setFieldValue() {
        field.setValue(5);
        assertTrue(field.getValue() == 5);
    }

    @Test
    void setFieldValueNegative() {
        field.setValue(5);
        assertFalse(field.getValue() != 5);
    }
    @Test
    void setFieldValueWrongValue() {
        field.setValue(5);
        field.setValue(10);
        assertTrue(field.getValue()==5);
        field.setValue(-1);
        assertTrue(field.getValue()==5);
    }

    @Test
    void testEquals() {
        SudokuField field2 = field;
        field.setValue(1);
        field2.setValue(1);
        assertTrue(field.equals(field2));
    }

    @Test
    void testEqualsNegative() {
        SudokuField field2 = new SudokuField();
        field.setValue(1);
        field2.setValue(5);
        assertFalse(field.equals(field2));
    }

    @Test
    void testNullEquals() {
        SudokuField field2 = null;
        assertFalse(field.equals(field2));
    }

    @Test
    void testHashCode() {
        SudokuField field2 = field;
        field.setValue(1);
        field2.setValue(1);
        assertEquals(field.hashCode(),field2.hashCode());

    }
    @Test
    void testHashCodeNegative() {
        SudokuField field2 = new SudokuField();
        field.setValue(1);
        field2.setValue(5);;
        assertNotEquals(field.hashCode(),field2.hashCode());
    }

    @Test
    void toStringTest() {
        field.setValue(1);
        String result = "SudokuField{value=1}";
        assertEquals(field.toString(),result);
    }

    @Test
    void toStringTestNegative() {
        field.setValue(1);
        String result = "SudokuField{value=2}";
        assertNotEquals(field.toString(),result);
    }

    @Test
    void compareTo() {
        field.setValue(1);
        SudokuField n = new SudokuField();
        n.setValue(1);
        assertEquals(field.compareTo(n), 0);
    }

    @Test
    void compareToNegative() throws NullPointerException {
        field.setValue(1);
        SudokuField n = new SudokuField();
        n.setValue(2);
        try {
            field.compareTo(null);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        assertEquals(field.compareTo(n), (field.getValue() - n.getValue()));
    }

    @Test
    void testCompareTo() {
        SudokuField s = new SudokuField(1);
        SudokuField s1 = new SudokuField(2);
        SudokuField s2 = new SudokuField(3);
        SudokuField s3 = new SudokuField(4);
        List<SudokuField> fields = new ArrayList<>();
        fields.add(s2);
        fields.add(s3);
        fields.add(s);
        fields.add(s1);
        Collections.sort(fields);
        System.out.println(fields);
        assertSame(fields.get(0), s);
        assertSame(fields.get(1), s1);
        assertSame(fields.get(2), s2);
        assertSame(fields.get(3), s3);
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        SudokuField s = new SudokuField(1);
        SudokuField s1 = (SudokuField) s.clone();
        assertEquals(s1, s);
    }
}
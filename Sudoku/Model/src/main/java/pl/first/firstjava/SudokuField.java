package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;


public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;


    public SudokuField(int value) {
        this.value = value;
    }

    public SudokuField() {

    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int val) {
        if (val < 0 || val > 9) {
            return;
        }
        value = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new NullPointerException();
        } else {
            return (this.value - o.getValue());
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

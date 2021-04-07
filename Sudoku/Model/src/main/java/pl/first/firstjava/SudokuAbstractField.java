package pl.first.firstjava;

import com.google.common.base.Objects;
import java.util.List;

public class SudokuAbstractField implements Cloneable {

    public static final int SIZE = 9;

    protected List<SudokuField> fields;

    public SudokuAbstractField(final List<SudokuField> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuAbstractField that = (SudokuAbstractField) o;
        return Objects.equal(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    public boolean verify() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (fields.get(i).getValue() == fields.get(j).getValue()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sudokuBoard = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sudokuBoard.append(fields.get(i).getValue());
            sudokuBoard.append(" ");
        }
        return new String(sudokuBoard);
    }

}

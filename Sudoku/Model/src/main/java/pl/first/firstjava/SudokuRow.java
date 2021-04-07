package pl.first.firstjava;

import java.util.Arrays;
import java.util.List;

public class SudokuRow extends SudokuAbstractField {
    public SudokuRow(final List<SudokuField> fields) {
        super(fields);
    }

    @Override
    protected SudokuRow clone() throws CloneNotSupportedException {
        List<SudokuField> cloneFields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            cloneFields.set(i, (SudokuField) fields.get(i).clone());
        }
        return new SudokuRow(cloneFields);
    }
}


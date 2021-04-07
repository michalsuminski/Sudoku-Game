package pl.first.firstjava;

import java.util.Arrays;
import java.util.List;

public class SudokuColumn extends SudokuAbstractField {
    public SudokuColumn(final List<SudokuField> fields) {
        super(fields);
    }

    @Override
    protected SudokuColumn clone() throws CloneNotSupportedException {
        List<SudokuField> cloneFields = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            cloneFields.set(i, (SudokuField) fields.get(i).clone());
        }
        return new SudokuColumn(cloneFields);
    }
}

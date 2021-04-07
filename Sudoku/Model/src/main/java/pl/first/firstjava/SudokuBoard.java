package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class SudokuBoard implements Serializable, Cloneable {

    public static final int SIZE = 9;

    private List<List<SudokuField>> board = Arrays.asList(new List[SIZE]);

    private BacktrackingSudokuSolver solver;

    public SudokuBoard(){

    }

    @Override
    public SudokuBoard clone() {
        SudokuBoard s = new SudokuBoard(this.solver);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                s.set(i, j, get(i, j));
            }
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        return Objects.equal(board, that.board)
                && Objects.equal(solver, that.solver);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board, solver);
    }

    public SudokuBoard(BacktrackingSudokuSolver solver) {
        this.solver = solver;
        for (int i = 0; i < SIZE; i++) {
            board.set(i, Arrays.asList(new SudokuField[SIZE]));
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board.get(i).set(j, new SudokuField());
            }
        }
    }

    public int get(int i, int j) {
        return board.get(i).get(j).getValue();
    }

    public SudokuField getField(int i, int j) {
        return board.get(i).get(j);
    }

    public void set(int i, int j, int value) {
        this.board.get(i).get(j).setValue(value);
    }

    public SudokuRow getRow(int row) {
        return new SudokuRow(board.get(row));
    }

    public SudokuColumn getColumn(int column) {
        List<SudokuField> fields = Arrays.asList(new SudokuField[SIZE]);
        for (int i = 0; i < SIZE; i++) {
            fields.set(i, board.get(i).get(column));
        }

        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] sudokuFields = new SudokuField[9];
        int baseRowIndex = x - (x % 3);
        int baseColumnIndex = y - (y % 3);
        int iter = 0;
        for (int i = baseRowIndex; i < baseRowIndex + 3; i++) {
            for (int j = baseColumnIndex; j < baseColumnIndex + 3; j++) {
                sudokuFields[iter++] = board.get(i).get(j);
            }
        }
        SudokuBox sudokuBox = new SudokuBox(Arrays.asList(sudokuFields));
        return sudokuBox;
    }

    private boolean isSafe(int rowIndex, int columnIndex, int num) {
        for (int i = 0; i < 9; i++) {
            if (i == rowIndex) {
                continue; // żeby nie sprawdzało właśnie wstawianej liczby bo zawsze wywali false
            }
            if (board.get(i).get(columnIndex).getValue() == num) {
                return false; // sprawdzenie kolumn
            }
        }
        for (int j = 0; j < 9; j++) {
            if (j == columnIndex) {
                continue;  // żeby nie sprawdzało właśnie wstawianej liczby bo zawsze wywali false
            }
            if (board.get(rowIndex).get(j).getValue() == num) {
                return false; // sprawdzenie wierszy
            }
        }
        int baseRowIndex = rowIndex - (rowIndex % 3);
        int baseColumnIndex = columnIndex - (columnIndex % 3);
        for (int i = baseRowIndex; i < baseRowIndex + 3; i++) {
            for (int j = baseColumnIndex; j < baseColumnIndex + 3; j++) {
                if (i == rowIndex && j == columnIndex) {
                    continue;
                }
                if (board.get(i).get(j).getValue() == num) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!isSafe(i, j, board.get(i).get(j).getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .add("solver", solver)
                .toString();
    }

    public String convertToString() {
        return new Gson().toJson(this);
    }

    public SudokuBoard convertToSudoku(String str) {
        return new Gson().fromJson(str,SudokuBoard.class);
    }

    public void solveGame() {
        SudokuBoard matrix = new SudokuBoard(this.solver);
        solver.solve(matrix);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board.get(i).get(j).setValue(matrix.get(i, j));
            }
        }
    }
}
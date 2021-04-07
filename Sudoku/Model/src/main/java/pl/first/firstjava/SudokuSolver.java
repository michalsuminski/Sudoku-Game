package pl.first.firstjava;

import java.io.Serializable;

public interface SudokuSolver extends Serializable {
    boolean solve(SudokuBoard board);
}

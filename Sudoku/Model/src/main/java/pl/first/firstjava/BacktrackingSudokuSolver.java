package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {


    boolean isSafe(SudokuBoard board, int rowIndex, int columnIndex, int num) {
        for (int i = 0; i < 9; i++) {
            if (i == rowIndex) {
                continue;
            }
            if (board.get(i,columnIndex) == num) {
                return false;
            }
        }
        for (int j = 0; j < 9; j++) {
            if (j == columnIndex) {
                continue;
            }
            if (board.get(rowIndex,j) == num) {
                return false;
            }
        }
        int baseRowIndex = rowIndex - (rowIndex % 3);
        int baseColumnIndex = columnIndex - (columnIndex % 3);
        for (int i = baseRowIndex; i < baseRowIndex + 3; i++) {
            for (int j = baseColumnIndex; j < baseColumnIndex + 3; j++) {
                if (i == rowIndex && j == columnIndex) {
                    continue;
                }
                if (board.get(i,j) == num) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public boolean solve(SudokuBoard board) {
        // lista o zadeklarowanej pojemnosci 9
        List<Integer> rands = Arrays.asList(new Integer[9]);
        for (int i = 0; i < 9; i++) {
            rands.set(i, i + 1);
        }
        // zapewnienie losowosci
        Collections.shuffle(rands);

        int rowIndex = -1;
        int columnIndex = -1;
        int i = 0;
        int j = 0;

        for (i = 0; i < 9; i++) {
            for (j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    rowIndex = i;
                    columnIndex = j;
                    break;
                }
            }
            if (rowIndex != -1) {
                break;
            }
        }
        if (i == 9 && j == 9) {
            return true;
        } else {
            for (int num = 0; num < 9; num++) {
                if (isSafe(board, rowIndex, columnIndex, rands.get(num))) {
                    board.set(rowIndex,columnIndex,rands.get(num));
                    if (!solve(board)) {
                        board.set(rowIndex,columnIndex,0);
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }
}



package pl.first.firstjava;

public class SudokuBoardDaoFactory {
    FileSudokuBoardDao getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    JdbcSudokuBoardDao getDbDao(String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }
}

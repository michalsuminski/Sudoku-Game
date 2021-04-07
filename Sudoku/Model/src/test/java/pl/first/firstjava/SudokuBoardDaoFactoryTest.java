package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {
    SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    void getFileDao() {
        FileSudokuBoardDao file = factory.getFileDao("./Model/Dane (1)");
        assertNotEquals(file, null);
    }

    @Test
    void getDataBaseDao() {
        JdbcSudokuBoardDao jdbc = factory.getDbDao("./test.db");
        assertNotEquals(jdbc, null);
    }
}
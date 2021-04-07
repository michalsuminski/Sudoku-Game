package pl.first.firstjava;

import org.junit.jupiter.api.Test;
import pl.first.firstjava.exceptions.OurSqlException;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ResourceBundle;

class JdbcSudokuBoardDaoTest {
    private SudokuBoardDaoFactory factory= new SudokuBoardDaoFactory();
    private BacktrackingSudokuSolver b = new BacktrackingSudokuSolver();
    private SudokuBoard s= new SudokuBoard(b);
    private SudokuBoard news;
    JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("tablica.db");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("myBundle");

    @Test
    void getNameTest() {
        jdbc.setSudokuname("testowe");
        assertSame("testowe", jdbc.getSudokuname());
    }

    @Test
    void writeReadTest() throws NullPointerException {
        s.solveGame();
        jdbc.createNewDatabase();
        jdbc.createNewTable();
//        przy każdym uruchomieniu testu trzeba dać nową nazwe
        jdbc.setSudokuname("xd");
        try {
            jdbc.write(s);
        } catch (OurSqlException ourSQLException) {
            ourSQLException.printStackTrace();
        }
        try {
            news = jdbc.read();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertFalse(s.equals(news));
        System.out.println(news.toString());
//        assertEquals(s.toString(),news.toString());

        try {
            jdbc.setSudokuname("blad");
            news = jdbc.read();
            System.out.println(news.toString());
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }
}
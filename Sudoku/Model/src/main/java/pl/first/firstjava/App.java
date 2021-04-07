package pl.first.firstjava;


import java.sql.SQLException;
import pl.first.firstjava.exceptions.OurSqlException;

/**
 * Klasa główna z metodą main.
 * @author Marcin Kwapisz
 */
public class App {
    public static void main(final String[] args) throws SQLException, OurSqlException {
        BacktrackingSudokuSolver b = new BacktrackingSudokuSolver();
        SudokuBoard s = new SudokuBoard(b);
        s.solveGame();
        JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("tablica.db");
        jdbc.createNewDatabase();
        jdbc.createNewTable();
        jdbc.setSudokuname("pierwsza");
        jdbc.write(s);
        s.solveGame();
        jdbc.setSudokuname("druga");
        jdbc.write(s);
        s.solveGame();
        jdbc.setSudokuname("trzecia");
        jdbc.write(s);
        s.solveGame();
        jdbc.setSudokuname("czwarta");
        jdbc.write(s);
        jdbc.setSudokuname("trzecia");
        SudokuBoard news = jdbc.read();
        System.out.println("Odczytane sudoku: " + news.toString());
        System.out.println(s.equals(news));
    }
}
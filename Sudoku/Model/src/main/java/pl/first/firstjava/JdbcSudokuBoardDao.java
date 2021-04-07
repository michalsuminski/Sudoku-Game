package pl.first.firstjava;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.first.firstjava.exceptions.OurSqlException;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    public static final String db = "SudokuBoard";
    private final String filename;
    private String sudokuname;
    private String url;
    private ResourceBundle resourceBundle = java.util.ResourceBundle.getBundle("myBundle");
    private static final Logger logger = LogManager.getLogger(JdbcSudokuBoardDao.class.getName());

    public JdbcSudokuBoardDao(String filename) {
        this.filename = filename;
        this.url = "jdbc:sqlite:" + filename;
    }

    public String getSudokuname() {
        return sudokuname;
    }

    public void setSudokuname(String sudokuname) {
        this.sudokuname = sudokuname;
    }


    public void createNewDatabase() {

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                logger.info(resourceBundle.getString("db_info") + meta.getDriverName());
                logger.info(resourceBundle.getString("db_info2") + filename);
            }
        } catch (SQLException e) {
            logger.info(resourceBundle.getString("db_info3") + filename);
        }
    }

    public void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "id integer PRIMARY KEY,\n"
                + "name text NOT NULL,\n"
                + "fields text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
        } catch (SQLException e) {
            logger.info(resourceBundle.getString("db_info4"));
        }
    }

    @Override
    public SudokuBoard read() throws SQLException {
        SudokuBoard sudokuBoard = new SudokuBoard();
        String sudoFromDB = null;
        String sql = "SELECT fields "
                + "FROM warehouses WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt  = conn.prepareStatement(sql)) {

            pstmt.setString(1,sudokuname);
            ResultSet rs  = pstmt.executeQuery();
            sudoFromDB = rs.getString(1);
        } catch (SQLException e) {
            logger.info(resourceBundle.getString("db_info4"));
        }
        return sudokuBoard.convertToSudoku(sudoFromDB);
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws OurSqlException {
        String sql = "INSERT INTO warehouses(name,fields) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sudokuname);
            pstmt.setString(2, sudokuBoard.convertToString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.info(resourceBundle.getString("db_info4"));
        }
    }

    @Override
    public void close() throws Exception {

    }
}
package prokomp;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.first.firstjava.BacktrackingSudokuSolver;
import pl.first.firstjava.FileSudokuBoardDao;
import pl.first.firstjava.JdbcSudokuBoardDao;
import pl.first.firstjava.SudokuBoard;
import pl.first.firstjava.exceptions.NoSuchFileException;
import pl.first.firstjava.exceptions.OurNoSuchMethodException;

public class LevelController implements Initializable {

    public TextField namofsudokufrombase;

    public enum Level {
        Easy,
        Medium,
        Hard;
    }

    private Level level;

    FileSudokuBoardDao fileSudokuBoardDao = new
            FileSudokuBoardDao("/home/student/Pulpit/an_pn_1145_05/View/Dane (1)");

    JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("tablica.db");

    BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudokuboard = new SudokuBoard(solver);
    private static final Logger logger = LogManager.getLogger(LevelController.class.getName());

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");

    private SudokuBoardView boardView;
    //LISTA JAVA BEAN INTEGER PROPERTY
    List<JavaBeanIntegerProperty> lista = new ArrayList<>();

    public void setLevel(Level level) {
        this.level = level;
    }

    public LevelController() {
        sudokuboard.solveGame();
        boardView = new SudokuBoardView(sudokuboard);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //lista textfieldow przyjmuje textfieldy ulozonych textfieldow boardview
        List<TextField> sudoku = boardView.getTextFields();
        StringConverter converter = new IntegerStringConverter();
        logger.info(resourceBundle.getString("initialization"));
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                try {
                    JavaBeanIntegerPropertyBuilder builder =
                            JavaBeanIntegerPropertyBuilder.create();
                    JavaBeanIntegerProperty integerProperty = builder.bean(
                            sudokuboard.getField(col, row)).name("value").build();
                    sudoku.get(col * 9 + row).textProperty().bindBidirectional(
                            integerProperty, converter);
                    lista.add(integerProperty);
                } catch (NoSuchMethodException ex) {
                    try {
                        throw new OurNoSuchMethodException(
                                resourceBundle.getString("nsme_write"), ex);
                    } catch (OurNoSuchMethodException e) {
                        logger.error(resourceBundle.getString("nsme_message"));
                        System.exit(-1);
                    }
                }
            }
        }
    }

    private SudokuBoard initializeSudokuBoard(SudokuBoard s) {
        List<Integer> rands = Arrays.asList(new Integer[81]);
        for (int i = 0; i < 81; i++) {
            rands.set(i, i);
        }
        Collections.shuffle(rands);
        if (level == Level.Easy) {
            for (int i = 0; i < 9; i++) {
                lista.get(rands.get(i)).set(0);
            }
        }
        if (level == Level.Medium) {
            for (int i = 0; i < 18; i++) {
                lista.get(rands.get(i)).set(0);
            }
        } else if (level == Level.Hard) {
            for (int i = 0; i < 27; i++) {
                lista.get(rands.get(i)).set(0);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                lista.get(i * 9 + j).set(s.get(i, j));
            }
        }
        return s;
    }

    @FXML
    public void onActionButtonEasyGenerate(ActionEvent e) throws NoSuchFileException {
        setLevel(Level.Easy);
        logger.info(resourceBundle.getString("level_easy_gen"));
        try {
            FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
        } catch (IOException ioException) {
            throw new NoSuchFileException(resourceBundle.getString(
                    "nsfe_file_not_found"), ioException);
        }
    }


    @FXML
    public void onActionButtonEasyFromFile(ActionEvent e) throws IOException {
        setLevel(Level.Easy);
        logger.info(resourceBundle.getString("level_easy_file"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,fileSudokuBoardDao.read().get(i,j));
                } catch (NoSuchFileException nsfe) {
                    nsfe.printStackTrace();
                    logger.error(resourceBundle.getString("nsfe_message"));
                    System.exit(-1);
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }


    @FXML
    public void onActionButtonMediumGenerate(ActionEvent e) throws IOException {
        setLevel(Level.Medium);
        logger.info(resourceBundle.getString("level_medium_gen"));
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }


    @FXML
    public void onActionButtonMediumFromFile(ActionEvent e) throws IOException {
        setLevel(Level.Medium);
        logger.info(resourceBundle.getString("level_medium_file"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,fileSudokuBoardDao.read().get(i,j));
                } catch (NoSuchFileException noSuchFileException) {
                    noSuchFileException.printStackTrace();
                    logger.error("NoSuchFileException caught");
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }


    @FXML
    public void onActionButtonHardGenerate(ActionEvent e) throws IOException {
        setLevel(Level.Hard);
        logger.info(resourceBundle.getString("level_hard_gen"));
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }

    @FXML
    public void onActionButtonHardFromFile(ActionEvent e) throws IOException {
        setLevel(Level.Hard);
        logger.info(resourceBundle.getString("level_hard_file"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,fileSudokuBoardDao.read().get(i,j));
                } catch (NoSuchFileException noSuchFileException) {
                    noSuchFileException.printStackTrace();
                    logger.error("NoSuchFileException caught");
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }


    @FXML
    public void onActionButtonLang_PL(ActionEvent actionEvent) throws IOException {
        ResourceBundle.clearCache();
        Locale.setDefault(new Locale("pl","PL"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");
        FxmlOperator.choice("/prokomp/Main.fxml",resourceBundle);
    }


    @FXML
    public void onActionButtonLang_EN(ActionEvent actionEvent) throws IOException {
        ResourceBundle.clearCache();
        Locale.setDefault(new Locale("en","GB"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");
        FxmlOperator.choice("/prokomp/Main.fxml",resourceBundle);
    }


    @FXML
    public void onActionButtonAuthors(ActionEvent actionEvent) {
        Locale locale = Locale.getDefault();
        logger.info(locale.toString());
        ResourceBundle authorsBundle = ResourceBundle.getBundle("prokomp.AuthorsBundle", locale);
        logger.info(authorsBundle.getObject(
                "Authors") + ": " + authorsBundle.getObject("author_first")
                + ", " + authorsBundle.getObject("author_second"));
    }

    public void onActionTextFieldDatabase(ActionEvent actionEvent) {
        jdbc.setSudokuname(namofsudokufrombase.getText());
        logger.info(jdbc.getSudokuname());
    }

    @FXML
    public void onActionButtonEasyFromDatabase(ActionEvent actionEvent) throws IOException {
        setLevel(Level.Easy);
        logger.info(resourceBundle.getString("level_easy_db"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,jdbc.read().get(j,i));
                } catch (SQLException nsfe) {
                    nsfe.printStackTrace();
                    logger.error(resourceBundle.getString("dbe_message"));
                    System.exit(-1);
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }

    @FXML
    public void onActionButtonMediumFromDatabase(ActionEvent actionEvent) throws IOException {
        setLevel(Level.Medium);
        logger.info(resourceBundle.getString("level_medium_db"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,jdbc.read().get(j,i));
                } catch (SQLException nsfe) {
                    nsfe.printStackTrace();
                    logger.error(resourceBundle.getString("dbe_message"));
                    System.exit(-1);
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }

    @FXML
    public void onActionButtonHardFromDatabase(ActionEvent actionEvent) throws IOException {
        setLevel(Level.Hard);
        logger.info(resourceBundle.getString("level_hard_db"));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                try {
                    sudokuboard.set(i,j,jdbc.read().get(j,i));
                } catch (SQLException nsfe) {
                    nsfe.printStackTrace();
                    logger.error(resourceBundle.getString("dbe_message"));
                    System.exit(-1);
                }
            }
        }
        FxmlOperator.choice(boardView.displayBoard(initializeSudokuBoard(sudokuboard)));
    }
}

package prokomp;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.first.firstjava.FileSudokuBoardDao;
import pl.first.firstjava.JdbcSudokuBoardDao;
import pl.first.firstjava.SudokuBoard;
import pl.first.firstjava.exceptions.NoSuchFileException;
import pl.first.firstjava.exceptions.OurSqlException;


public class SudokuBoardView {

    private static final Logger logger = LogManager.getLogger(SudokuBoardView.class.getName());

    FileSudokuBoardDao fileSudokuBoardDao =
            new FileSudokuBoardDao("/home/student/Pulpit/an_pn_1145_05/View/Dane (1)");
    JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("tablica.db");
    ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");
    //lista textField
    List<TextField> sudoku = new ArrayList<>();

    SudokuBoard sboard;

    public SudokuBoardView(SudokuBoard s) {
        this.sboard = s;
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                // do listy dodaj textFieldy
                sudoku.add(createTextField(s.get(col, row)));
            }
        }
    }

    public List<TextField> getTextFields() {
        return this.sudoku;
    }

    public SudokuBoard getSudoku() {
        return sboard;
    }

    public Scene displayBoard(SudokuBoard s) {
        Menu plik = new Menu(resourceBundle.getString("file"));
        MenuItem zapisDoPliku = new MenuItem(resourceBundle.getString("save_sudoku"));
        MenuItem zapisDoBazy = new MenuItem(resourceBundle.getString("save_to_db"));
        TextField nameofdatabase = new TextField();
        plik.setGraphic(nameofdatabase);
        plik.getItems().add(zapisDoPliku);
        plik.getItems().add(zapisDoBazy);
        MenuItem wyswietl = new MenuItem(resourceBundle.getString("display_board"));
        plik.getItems().add(wyswietl);
        MenuBar menu = new MenuBar();
        menu.getMenus().add(plik);

        zapisDoBazy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                jdbc.setSudokuname(nameofdatabase.getText());
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        sboard.set(i, j, sboard.get(j, i));
                    }
                }
                try {
                    jdbc.write(s);
                } catch (OurSqlException e) {
                    e.printStackTrace();
                    logger.error(resourceBundle.getString("dbfe_write"));
                }
                logger.info(resourceBundle.getString("save_to_db"));
            }
        });

        zapisDoPliku.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    fileSudokuBoardDao.write(s);
                } catch (NoSuchFileException e) {
                    e.printStackTrace();
                    logger.error(resourceBundle.getString("nsfe_write"));
                }
                logger.info(resourceBundle.getString("save_sudoku"));
            }
        });

        wyswietl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (int i = 0; i < 9; i++) {
                    System.out.println(s.getColumn(i));
                }
                System.out.println("\n");
            }
        });

        VBox root = new VBox(menu);

        GridPane board = new GridPane();

        root.getChildren().add(board);

        PseudoClass right = PseudoClass.getPseudoClass("right");
        PseudoClass bottom = PseudoClass.getPseudoClass("bottom");

        int i = 0;
        for (int col = 0; col < 9; col++) {
            for (int row = 0; row < 9; row++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("cell");
                cell.pseudoClassStateChanged(right, col == 2 || col == 5);
                cell.pseudoClassStateChanged(bottom, row == 2 || row == 5);



                int value = s.get(col, row);
                if (value != 0) {
                    sudoku.get(i).setEditable(false);
                    sudoku.get(i).setStyle("-fx-background-color: #7dfab1;");
                } else {
                    sudoku.get(i).setText("");
                }
                cell.getChildren().add(sudoku.get(i));
                i++;

                board.add(cell, col, row);
            }
        }

        Scene scene = new Scene(root);
        scene.getStylesheets().add("prokomp/sudoku.css");
        return scene;
    }

    private TextField createTextField(int value) {
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);

        textField.setTextFormatter(new TextFormatter<Integer>(c -> {
            if (c.getControlNewText().matches("\\d?")) { //"\\d?"
                return c;
            } else {
                return null;
            }
        }));
        return textField;
    }
}

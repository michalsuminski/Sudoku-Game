package prokomp;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import pl.first.firstjava.exceptions.NoSuchFileException;


public class Main extends Application {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    Locale locale = new Locale("pl","PL");
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("lang",locale);
    String fileName = "/prokomp/Main.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws NoSuchFileException {
        try {
            FxmlOperator.choice(primaryStage, fileName,
                    resourceBundle.getString("title"),resourceBundle);
        } catch (IOException e) {
            throw new NoSuchFileException(resourceBundle.getString("nsfe_file_not_found")
                    + fileName, e);
        }
    }
}
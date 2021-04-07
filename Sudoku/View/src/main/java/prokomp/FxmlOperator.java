package prokomp;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlOperator {

    private static Stage stage;

    private static Parent loadFxml(String fxmlBoard,
                                   ResourceBundle resourceBundle) throws IOException {
        return new FXMLLoader(FxmlOperator.class.getResource(fxmlBoard),
                resourceBundle).load();
    }

    public static void choice(Scene scene) throws IOException {
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void choice(String fxmlBoard, ResourceBundle resourceBundle) throws IOException {
        stage.setScene(new Scene(loadFxml(fxmlBoard,resourceBundle)));
        stage.sizeToScene();
        stage.show();
    }

    public static void choice(Stage stage, String filefxml,
                              String title, ResourceBundle resourceBundle) throws IOException {
        FxmlOperator.stage = stage;
        stage.setScene(new Scene(loadFxml(filefxml,resourceBundle)));
        stage.setTitle(title);
        stage.show();
    }

}

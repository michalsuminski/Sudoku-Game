package pl.first.firstjava;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import pl.first.firstjava.exceptions.NoSuchFileException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("myBundle");

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws NoSuchFileException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            SudokuBoard result = (SudokuBoard) inputStream.readObject();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new NoSuchFileException(resourceBundle.getString("nsfe_file_not_found")
                    + fileName,e);
        }
    }

    @Override
    public void write(SudokuBoard s) throws NoSuchFileException {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(s);
        } catch (IOException e) {
            throw new NoSuchFileException(resourceBundle.getString("nsfe_write") + fileName,e);
        }
    }

    @Override//    @Override protected void finalize() throws Throwable { super.finalize(); }
    public void close() throws Exception {
    }

}

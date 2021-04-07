package pl.first.firstjava;

import java.sql.SQLException;
import pl.first.firstjava.exceptions.NoSuchFileException;
import pl.first.firstjava.exceptions.OurSqlException;

public interface Dao<T> extends AutoCloseable {
    T read() throws NoSuchFileException, SQLException;

    void write(T obj) throws NoSuchFileException, SQLException, OurSqlException;
}

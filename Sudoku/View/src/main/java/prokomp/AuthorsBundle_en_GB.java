package prokomp;

import java.util.ListResourceBundle;

public class AuthorsBundle_en_GB extends ListResourceBundle {
    private final Object[][] resources = {{"Authors","Authors"},{"author_first","Jan Płoszaj"},
            {"author_second","Michał Sumiński"}};

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}

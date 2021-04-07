package prokomp;

import java.util.ListResourceBundle;

public class AuthorsBundle extends ListResourceBundle {
    private final Object[][] resources = {{"Authors","Autorzy"},{"author_first","Jan Płoszaj"},
            {"author_second","Michał Sumiński"}};

    @Override
    protected Object[][] getContents() {
        return resources;
    }
}

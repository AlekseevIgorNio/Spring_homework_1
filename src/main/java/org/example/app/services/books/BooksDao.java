package org.example.app.services.books;

import java.util.List;

public interface BooksDao<T> {
    List<T> getAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);
}

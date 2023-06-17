package org.example.app.services.books;

import java.util.List;

public interface BooksRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(Integer bookIdToRemove);
}

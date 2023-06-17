package org.example.app.services.books;

import org.example.web.dto.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    void saveBook(Book book);

    boolean removeBookById(Integer bookIdToRemove);

    boolean removeByRegex(String queryRegexRemove);
}
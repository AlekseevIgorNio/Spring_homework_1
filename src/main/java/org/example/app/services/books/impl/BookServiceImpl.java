package org.example.app.services.books.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.example.app.services.books.BookService;
import org.example.app.services.books.BooksDao;
import org.example.web.dto.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksDao<Book> bookRepo;
    private Logger logger = Logger.getLogger(BookServiceImpl.class);

    public List<Book> getAllBooks() {
        return bookRepo.getAll();
    }

    public void saveBook(Book book) {
        boolean emptyBook = StringUtils.isNotEmpty(book.getAuthor())
                || StringUtils.isNotEmpty(book.getTitle())
                || book.getSize() != null;
        if (emptyBook) {
            bookRepo.store(book);
        }
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    @Override
    public boolean removeByRegex(String queryRegexRemove) {
        List<Book> allBooks = bookRepo.getAll();

        int original = allBooks.size();

        allBooks.forEach(book -> {
            if (StringUtils.equalsIgnoreCase(book.getAuthor(), queryRegexRemove)
                    || StringUtils.equalsIgnoreCase(book.getTitle(), queryRegexRemove)
                    || StringUtils.equalsIgnoreCase(book.getSize() == null ? "" : book.getSize().toString(), queryRegexRemove)) {
                bookRepo.removeItemById(book.getId());
            }
        });

        int end = bookRepo.getAll().size() - original;

        if (end != 0) {
            logger.info("Remotely using a regular expression of " + end + "books, successfully");
            return true;
        }
        return false;
    }
}
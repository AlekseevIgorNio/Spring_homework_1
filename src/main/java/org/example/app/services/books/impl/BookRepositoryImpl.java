package org.example.app.services.books.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.example.app.services.books.BooksRepository;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BooksRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepositoryImpl.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeByRegex(String queryRegexRemove) {
//        int original = repo.size();
//        retreiveAll().forEach(book -> {
//            if (StringUtils.equalsIgnoreCase(book.getAuthor(), queryRegexRemove)
//                    || StringUtils.equalsIgnoreCase(book.getTitle(), queryRegexRemove)
//                    || ) {
//
//            }
//        });

        return false;
    }
}

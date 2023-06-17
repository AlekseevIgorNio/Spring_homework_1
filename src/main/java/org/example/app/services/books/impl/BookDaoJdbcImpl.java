package org.example.app.services.books.impl;

import lombok.RequiredArgsConstructor;
import org.example.app.services.books.BooksDao;
import org.example.app.services.books.mappers.BooksMapper;
import org.example.web.dto.Book;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbcImpl implements BooksDao<Book> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Book> getAll() {
        String sql = "SELECT * FROM books";
        return jdbcTemplate.query(sql, new BooksMapper());
    }

    @Override
    public void store(Book book) {
        String sql = "INSERT INTO books (author, title, size) VALUES (:author, :title, :size)";
        jdbcTemplate.update(sql, getMapSqlParameterSource(book));
    }

    private MapSqlParameterSource getMapSqlParameterSource(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("author", book.getAuthor());
        params.addValue("title", book.getTitle());
        params.addValue("size", book.getSize());
        return params;
    }

    @Override
    public boolean removeItemById(Integer id) {
        String sql = "DELETE books WHERE id = :id";
        return jdbcTemplate.update(sql, new MapSqlParameterSource("id", id)) == 1;
    }
}
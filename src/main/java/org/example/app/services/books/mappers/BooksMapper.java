package org.example.app.services.books.mappers;

import org.example.web.dto.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Book.builder()
                .id(rs.getInt("id"))
                .author(rs.getString("author"))
                .title(rs.getString("title"))
                .size(rs.getInt("size"))
                .build();
    }
}
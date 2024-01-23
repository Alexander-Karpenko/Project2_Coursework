package ru.karpenko.practice.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper  implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setBook_id(resultSet.getInt("book_id"));
        book.setPerson_id(resultSet.getInt("person_id"));
        book.setName(resultSet.getString("name"));
        book.setAuthor(resultSet.getString("author"));
        book.setYear_of_issue(resultSet.getInt("year_of_issue"));

        return book;
    }
}

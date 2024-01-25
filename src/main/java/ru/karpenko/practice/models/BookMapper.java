//package ru.karpenko.practice.models;
//
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class BookMapper  implements RowMapper<Book> {
//    @Override
//    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
//        Book book = new Book();
//
//        book.setId(resultSet.getInt("book_id"));
////        book.setOwner(resultSet.getInt("person_id"));
//        book.setName(resultSet.getString("name"));
//        book.setAuthor(resultSet.getString("author"));
//        book.setYearOfIssue(resultSet.getInt("year_of_issue"));
//
//        return book;
//    }
//}

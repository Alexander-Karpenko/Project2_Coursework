package ru.karpenko.practice.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.BookMapper;
import ru.karpenko.practice.models.Person;

import java.util.List;


@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }
    public Book show(int book_id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{book_id}, new BookMapper())
                .stream().findAny().orElse(null);
    }
    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book(name, author, year_of_issue) VALUES (?, ?, ?)", book.getName(), book.getAuthor(), book.getYear_of_issue());
    }
    public void update(int  book_id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year_of_issue=? WHERE book_id=?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getYear_of_issue(), book_id);
    }
    public void delete(int book_id){
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", book_id);
    }
    public void appointBook(int person_id, int book_id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person_id, book_id);
        //присвоить колонке книги, которая получена по book_id, person_id
    }
    public void release( int book_id){
        jdbcTemplate.update("UPDATE Book SET person_id=NULL WHERE book_id=?", book_id);
    }


}

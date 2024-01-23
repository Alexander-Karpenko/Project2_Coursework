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
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int  person_id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?", new Object[]{ person_id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, age) VALUES(?, ?)", person.getName(), person.getAge());

    }

    public void update(int  person_id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE person_id=?", updatedPerson.getName(),
                updatedPerson.getAge(),  person_id);
    }

    public void delete(int  person_id) {
        jdbcTemplate.update("DELETE FROM Person WHERE  person_id=?",  person_id);
    }

    public List<Book> booksNameSearch(int person_id){
        return jdbcTemplate.query("SELECT * FROM book JOIN public.person p on p.person_id = book.person_id WHERE p.person_id=?",new Object[]{ person_id}, new BookMapper());
    }
}

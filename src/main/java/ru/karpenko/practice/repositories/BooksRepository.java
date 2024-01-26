package ru.karpenko.practice.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner (Person person);
    Page<Book> findAll(Pageable pageable);
    List<Book> findAll(Sort sort);
    List<Book> findByNameStartingWith (String string);
}

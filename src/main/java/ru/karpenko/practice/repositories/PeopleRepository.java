package ru.karpenko.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.karpenko.practice.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Person findByUsername (String username);
}

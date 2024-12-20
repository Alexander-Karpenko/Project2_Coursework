package ru.karpenko.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpenko.practice.models.Genre;

public interface GenresRepository extends JpaRepository<Genre, Long> {
}

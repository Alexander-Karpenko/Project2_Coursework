package ru.karpenko.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpenko.practice.models.Vote;

public interface VotesRepository extends JpaRepository<Vote, Long> {
}

package ru.karpenko.practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.karpenko.practice.models.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}

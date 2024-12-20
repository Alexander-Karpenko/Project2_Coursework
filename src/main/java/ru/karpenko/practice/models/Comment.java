package ru.karpenko.practice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private Book commented_book;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person commented_person;

    @Column(name = "created_at")
    private Date created_at;
}

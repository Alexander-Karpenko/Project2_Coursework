package ru.karpenko.practice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bio")
    private String bio;

    @OneToMany(mappedBy = "book_genre")
    private List<Book> books;
}

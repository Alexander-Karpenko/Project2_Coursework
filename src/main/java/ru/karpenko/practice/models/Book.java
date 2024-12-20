package ru.karpenko.practice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre book_genre;

    @OneToMany(mappedBy = "commented_book")
    private List<Comment> comments;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name is too short")
    private String name;

    @Column(name = "author")
    @NotEmpty(message = "Author's Name should not be empty")
    @Size(min = 2, max = 50, message = "Author's name is too short")
    private String author;

    @Column(name = "year_of_issue")
    @Min(value = 1, message = "Age should be greater than 0")
    private int yearOfIssue;

    @Column(name = "appoint_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date appointTime;

    @Transient
    private boolean overdue;


}

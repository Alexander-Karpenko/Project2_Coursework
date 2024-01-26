package ru.karpenko.practice.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

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

    public Book(){

    }

    public Book(Person owner, String name, String author, int yearOfIssue) {
        this.owner = owner;
        this.name = name;
        this.author = author;
        this.yearOfIssue = yearOfIssue;
    }

    public int getId() {
        return id;
    }

    public void setId(int bookId) {
        this.id = bookId;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Date getAppointTime() {
        return appointTime;
    }

    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", yearOfIssue=" + yearOfIssue +
                '}';
    }
}

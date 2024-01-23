package ru.karpenko.practice.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int book_id;
    private int person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 50, message = "Name is too short")
    private String name;
    @NotEmpty(message = "Author's Name should not be empty")
    @Size(min = 2, max = 50, message = "Author's name is too short")
    private String author;

    @Min(value = 1, message = "Age should be greater than 0")
    private int year_of_issue;

    public Book(){

    }

    public Book(int book_id, int person_id, String name, String author, int year_of_issue) {
        this.book_id = book_id;
        this.person_id = person_id;
        this.name = name;
        this.author = author;
        this.year_of_issue = year_of_issue;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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

    public int getYear_of_issue() {
        return year_of_issue;
    }

    public void setYear_of_issue(int year_of_issue) {
        this.year_of_issue = year_of_issue;
    }
}

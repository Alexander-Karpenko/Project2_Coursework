package ru.karpenko.practice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person", schema = "public")
public class Person {
    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 60, message = "Name should be between 2 and 30 characters")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;


    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "voted_person")
    private List<Vote> votes;

    @OneToMany(mappedBy = "commented_person")
    private List<Comment> comments;



}

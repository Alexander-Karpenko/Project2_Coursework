package ru.karpenko.practice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @Column(name = "vote_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person voted_person;

    @Column(name = "vote_scale")
    private Double vote_scale;
}

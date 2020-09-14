package sberbank.internship.dkomshina.model;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name="task")
public class Task {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    private Set<Stage> stage;
}

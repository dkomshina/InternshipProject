package sberbank.internship.dkomshina.model;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @OneToMany
    private Set<Stage> stage;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String name;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;
}

package sberbank.internship.dkomshina.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class Task {

    @OneToMany(mappedBy = "task", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Stage> stages = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String name;

    private Date createTime;

    private Date startTime;

    private Date endTime;
}

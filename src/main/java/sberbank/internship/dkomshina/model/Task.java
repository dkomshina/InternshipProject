package sberbank.internship.dkomshina.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId")
    private List<Stage> stages;

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

//    public void addStage(Stage stage) {
//        if (stages != null) {
//            stages = new ArrayList<>();
//        }
//        stages.add(stage);
//    }
}

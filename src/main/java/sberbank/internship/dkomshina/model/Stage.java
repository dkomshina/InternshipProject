package sberbank.internship.dkomshina.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stage {

//    @ManyToOne
//    private Task task;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    private String name;

    private String status;

    private String script;

    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date startTime;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;
}

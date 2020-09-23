package sberbank.internship.dkomshina.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;
import sberbank.internship.dkomshina.model.db.Task;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Stage {

    //(fetch = FetchType.EAGER)
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String status;

    private String script;

    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date startTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;

//
//    @Column(name = "task_id")
//    private Long taskId;
}

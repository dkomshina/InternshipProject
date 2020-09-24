package sberbank.internship.dkomshina.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class Stage {

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

    @Column(name = "task_id")
    private Long taskId;
}

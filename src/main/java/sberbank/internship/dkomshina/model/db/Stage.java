package sberbank.internship.dkomshina.model.db;

import lombok.*;
import sberbank.internship.dkomshina.util.StatusType;

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

    private String script;

    private String description;

    private Date startTime;

    private Date endTime;

    @ManyToOne
    private Task task;
}

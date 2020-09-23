package sberbank.internship.dkomshina.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
    private List<Stage> stages;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time",
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date startTime;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date endTime;

//    public void addStage(Stage stage) {
//        if (stages != null) {
//            stages = new ArrayList<>();
//        }
//        stages.add(stage);
//    }
}

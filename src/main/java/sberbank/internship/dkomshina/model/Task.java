package sberbank.internship.dkomshina.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @JsonBackReference
    private List<Stage> stages;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
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

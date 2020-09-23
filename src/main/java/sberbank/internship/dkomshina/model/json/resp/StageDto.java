package sberbank.internship.dkomshina.model.json.resp;

import lombok.*;
import sberbank.internship.dkomshina.model.db.Task;

import java.util.Date;

@Data
@ToString(exclude = "task")
@EqualsAndHashCode(exclude = "task")
@NoArgsConstructor
@AllArgsConstructor
public class StageDto {

    private Task task;
    private Long id;
    private String name;
    private String status;
    private String script;
    private String description;
    private Date startTime;
    private Date endTime;

}

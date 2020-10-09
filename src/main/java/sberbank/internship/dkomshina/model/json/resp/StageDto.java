package sberbank.internship.dkomshina.model.json.resp;

import lombok.*;
import sberbank.internship.dkomshina.util.StatusType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDto {
    private Long id;
    private String name;
    private String script;
    private String description;
    private Date startTime;
    private Date endTime;
    private TaskDto task;
    private StatusType status;
}

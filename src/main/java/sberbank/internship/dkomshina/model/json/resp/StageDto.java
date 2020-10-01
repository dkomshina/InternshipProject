package sberbank.internship.dkomshina.model.json.resp;

import lombok.*;
import sberbank.internship.dkomshina.util.StageStatusType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDto {
    private Long id;
    private String name;
    private String script;
    private StageStatusType status;
    private String description;
    private Date startTime;
    private Date endTime;
    private TaskDto task;
}

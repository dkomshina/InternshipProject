package sberbank.internship.dkomshina.model.json.resp;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StageDto {
    private Long id;
    private String name;
    private String status;
    private String script;
    private String description;
    private Date startTime;
    private Date endTime;
    private Long taskId;
}

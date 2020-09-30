package sberbank.internship.dkomshina.model.json.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private Long id;

    private String description;
    private String name;
    private List<StageDto> stages = new ArrayList<>();
    private Date createTime;
    private Date startTime;
    private Date endTime;
}

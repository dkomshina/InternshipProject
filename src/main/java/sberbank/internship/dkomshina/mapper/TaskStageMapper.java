package sberbank.internship.dkomshina.mapper;

import org.springframework.stereotype.Component;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.util.StatusType;

import java.util.stream.Collectors;

@Component
public class TaskStageMapper {

    public TaskDto map(Task task) {

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setName(task.getName());
        taskDto.setStages(task.getStages().stream().map(this::map).collect(Collectors.toList()));
        taskDto.setStartTime(task.getStartTime());
        taskDto.setEndTime(task.getEndTime());
        taskDto.setCreateTime(task.getCreateTime());
        taskDto.setStatus(task.getStatus());
        taskDto.setStageNumber(task.getStageNumber());
        return taskDto;
    }

    public Task map(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setDescription(taskDto.getDescription());
        task.setName(taskDto.getName());
        task.setStages(taskDto.getStages().stream().map(this::map).collect(Collectors.toList()));
        task.setStartTime(taskDto.getStartTime());
        task.setEndTime(taskDto.getEndTime());
        task.setCreateTime(taskDto.getCreateTime());
        task.setStatus(taskDto.getStatus());
        task.setStageNumber(taskDto.getStageNumber());
        return task;
    }

    public StageDto map(Stage stage) {
        StageDto stageDto = new StageDto();
        stageDto.setId(stage.getId());
        stageDto.setName(stage.getName());
        stageDto.setDescription(stage.getDescription());
        stageDto.setScript(stage.getScript());
        stageDto.setStartTime(stage.getStartTime());
        stageDto.setEndTime(stage.getEndTime());
        return stageDto;
    }

    public Stage map(StageDto stageDto) {
        Stage stage = new Stage();
        stage.setId(stageDto.getId());
        stage.setName(stageDto.getName());
        stage.setDescription(stageDto.getDescription());
        stage.setScript(stageDto.getScript());
        stage.setStartTime(stageDto.getStartTime());
        stage.setEndTime(stageDto.getEndTime());
        return stage;
    }
}

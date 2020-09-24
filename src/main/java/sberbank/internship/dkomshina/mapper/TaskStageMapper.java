package sberbank.internship.dkomshina.mapper;

import org.springframework.stereotype.Component;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;

import java.util.stream.Collectors;

@Component
public class TaskStageMapper {

    public TaskDto map(Task task) {

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setName(task.getName());
        taskDto.setStages(task.getStages().stream().map(this::map).collect(Collectors.toList()));
        return taskDto;
    }

    public Task map(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStages(taskDto.getStages().stream().map(this::map).collect(Collectors.toList()));
        return task;
    }

    public Task map(Task task, Task taskMappedTo) {
        task.setName(taskMappedTo.getName());
        task.setDescription(taskMappedTo.getDescription());
        task.setStages(taskMappedTo.getStages());
        return task;
    }

    public StageDto map(Stage stage) {
        StageDto stageDto = new StageDto();
        stageDto.setId(stage.getId());
        stageDto.setName(stage.getName());
        stageDto.setDescription(stage.getDescription());
        stageDto.setStatus(stage.getStatus());
        stageDto.setScript(stage.getScript());
        stageDto.setTaskId(stage.getTaskId());
        return stageDto;
    }

    public Stage map(StageDto stageDto) {
        Stage stage = new Stage();
        stage.setId(stageDto.getId());
        stage.setName(stageDto.getName());
        stage.setDescription(stageDto.getDescription());
        stage.setStatus(stageDto.getStatus());
        stage.setScript(stageDto.getScript());
        stage.setTaskId(stageDto.getTaskId());
        return stage;
    }

    public Stage map(Stage stage, Stage stageMappedTo) {
        stage.setName(stageMappedTo.getName());
        stage.setDescription(stageMappedTo.getDescription());
        stage.setStatus(stageMappedTo.getStatus());
        stage.setScript(stageMappedTo.getScript());
        stage.setTaskId(stageMappedTo.getTaskId());
        return stage;
    }
}

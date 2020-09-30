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
        taskDto.setStartTime(task.getStartTime());
        taskDto.setEndTime(task.getEndTime());
        taskDto.setCreateTime(task.getCreateTime());
        return taskDto;
    }

    public Task map(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setStages(taskDto.getStages().stream().map(this::map).collect(Collectors.toList()));
        task.setStartTime(taskDto.getStartTime());
        task.setEndTime(taskDto.getEndTime());
        task.setCreateTime(taskDto.getCreateTime());
        return task;
    }

    public Task map(Task task, TaskDto taskMappedTo) {
        task.setName(taskMappedTo.getName());
        task.setDescription(taskMappedTo.getDescription());
        task.setStages(taskMappedTo.getStages().stream().map(this::map).collect(Collectors.toList()));
        task.setStartTime(taskMappedTo.getStartTime());
        task.setEndTime(taskMappedTo.getEndTime());
        task.setCreateTime(taskMappedTo.getCreateTime());
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
        stageDto.setStartTime(stage.getStartTime());
        stageDto.setEndTime(stage.getEndTime());
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
        stage.setStartTime(stageDto.getStartTime());
        stage.setEndTime(stageDto.getEndTime());
        return stage;
    }

    public Stage map(Stage stage, StageDto stageMappedTo) {
        stage.setName(stageMappedTo.getName());
        stage.setDescription(stageMappedTo.getDescription());
        stage.setStatus(stageMappedTo.getStatus());
        stage.setScript(stageMappedTo.getScript());
        stage.setStartTime(stageMappedTo.getStartTime());
        stage.setEndTime(stageMappedTo.getEndTime());
        return stage;
    }
}

package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskStageService {

    private final TaskRepository taskRepository;
    private final StageRepository stageRepository;
    private final TaskStageMapper taskStageMapper;

    @Autowired
    public TaskStageService(
            TaskRepository taskRepository,
            StageRepository stageRepository,
            TaskStageMapper taskStageMapper) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
        this.taskStageMapper = taskStageMapper;
    }

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream().map(taskStageMapper::map).collect(Collectors.toList());
    }

    public TaskDto getTask(Long taskId) {
        return taskRepository.findById(taskId).map(taskStageMapper::map).orElseThrow(NoSuchElementException::new);
    }

    public TaskDto addTask(Task task) {
        task.setCreateTime(new Date());
//        task.setCreateTime(Calendar.getInstance().getTime());
//        task.setCreateTime(LocalDateTime.now().);
        return taskStageMapper.map(taskRepository.save(task));
    }

//    public Optional<Task> updateTask(Long taskId, Task taskRequest) {
//        return taskRepository.findById(taskId).map(
//                newTask -> {
//                    //TODO добавить еще
//                    newTask.setName(taskRequest.getName());
//                    return taskRepository.save(newTask);
//                });
//    }

    public void deleteTask(Long taskId) {
        taskRepository.findById(taskId).ifPresent(taskRepository::delete);
    }

    public StageDto addStage(Long taskId, Stage stage) {
        Task task = taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        stage.setTask(task);
        task.getStages().add(stage);
        return taskStageMapper.map(stageRepository.save(stage));
    }

    public List<Stage> findAllStages(Long taskId) {
        return stageRepository.findByTaskId(taskId);
    }

    public Optional<Stage> findStageById(Long taskId, Long stageId) {
        return stageRepository.findStageByTaskIdAndId(taskId, stageId);
    }

    public Optional<Stage> updateStage(Long taskId, Long stageId, Stage stageRequest) {
        return stageRepository.findStageByTaskIdAndId(taskId, stageId).map(
                newStage -> {
                    newStage.setDescription(stageRequest.getDescription());
                    newStage.setName(stageRequest.getName());
                    return stageRepository.save(newStage);
                });
    }

    public Boolean deleteStage(Long taskId, Long stageId) {
        return stageRepository.findStageByTaskIdAndId(taskId, stageId).map(
                deletingStage -> {
                    stageRepository.delete(deletingStage);
                    return true;
                }).orElse(false);
    }
}

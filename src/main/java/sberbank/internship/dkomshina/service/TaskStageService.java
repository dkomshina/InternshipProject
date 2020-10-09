package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.event.StartTaskEvent;
import sberbank.internship.dkomshina.event.StopTaskEvent;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;
import sberbank.internship.dkomshina.util.StatusType;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TaskStageService {

    private final TaskRepository taskRepository;
    private final StageRepository stageRepository;
    private final TaskStageMapper taskStageMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TaskStageService(TaskRepository taskRepository, StageRepository stageRepository, TaskStageMapper taskStageMapper, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
        this.taskStageMapper = taskStageMapper;
        this.eventPublisher = eventPublisher;
    }

    @Async
    public void startTask(Long taskId) {
        eventPublisher.publishEvent(new StartTaskEvent(this, taskId));
    }

    @Async
    public void stopTask(Long taskId) {
        eventPublisher.publishEvent(new StopTaskEvent(this, taskId));
    }

    public ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        taskDto.setCreateTime(new Date());
        taskDto.setStageNumber(0);
        taskDto.setStatus(StatusType.PENDING);
        return new ResponseEntity<>(taskStageMapper.map(taskRepository.save(taskStageMapper.map(taskDto))), HttpStatus.CREATED);
    }

    public ResponseEntity<List<TaskDto>> getTasks() {
        return new ResponseEntity<>(taskRepository.findAll().stream()
                .map(taskStageMapper::map).collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<TaskDto> getTaskById(Long taskId) {
        return new ResponseEntity<>(taskStageMapper
                .map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new)), HttpStatus.OK);
    }

    public ResponseEntity<TaskDto> updateTask(Long taskId, TaskDto requestTask) {
        return new ResponseEntity<>(taskStageMapper.map(taskRepository.save(map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new), requestTask))), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteTask(Long taskId) {
        taskRepository.delete(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<StageDto> createStage(StageDto stageDto, Long taskId) {
        final Task task = taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        final Stage stage = taskStageMapper.map(stageDto);
        stage.setTask(task);
        stage.setStatus(StatusType.PENDING);
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.save(stage)), HttpStatus.CREATED);
    }

    public ResponseEntity<List<StageDto>> getStages(Long taskId) {
        return new ResponseEntity<>(stageRepository.findAllByTaskId(taskId).stream()
                .map(taskStageMapper::map).collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<StageDto> getStageById(Long taskId, Long stageId) {
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.findByTaskIdAndId(taskId, stageId)
                .orElseThrow(NoSuchElementException::new)), HttpStatus.OK);
    }

    public ResponseEntity<StageDto> updateStage(
            Long taskId, Long stageId, StageDto stageRequest) {
        final Task task = taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        task.setStatus(StatusType.UPDATED);
        task.setStageNumber(0);
        taskRepository.save(task);
        return new ResponseEntity<>(taskStageMapper.map(stageRepository
                .save(map(stageRepository.findByTaskIdAndId(taskId, stageId)
                        .orElseThrow(NoSuchElementException::new), stageRequest))), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteStage(Long taskId, Long stageId) {
        stageRepository.delete(stageRepository.findByTaskIdAndId(taskId, stageId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Task map(Task task, TaskDto taskMappedTo) {
        task.setName(taskMappedTo.getName());
        task.setDescription(taskMappedTo.getDescription());
        task.setStages(taskMappedTo.getStages().stream().map(taskStageMapper::map).collect(Collectors.toList()));
        task.setStartTime(taskMappedTo.getStartTime());
        task.setEndTime(taskMappedTo.getEndTime());
        task.setCreateTime(taskMappedTo.getCreateTime());
        task.setStatus(StatusType.UPDATED);
        task.setStageNumber(0);
        return task;
    }

    private Stage map(Stage stage, StageDto stageMappedTo) {
        stage.setName(stageMappedTo.getName());
        stage.setDescription(stageMappedTo.getDescription());
        stage.setScript(stageMappedTo.getScript());
        stage.setStartTime(stageMappedTo.getStartTime());
        stage.setEndTime(stageMappedTo.getEndTime());
        return stage;
    }
}

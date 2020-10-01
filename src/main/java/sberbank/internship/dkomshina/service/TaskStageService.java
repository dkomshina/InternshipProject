package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.event.TaskStartedEvent;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
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

    public ResponseEntity<?> startTask(Long taskId) {
        eventPublisher.publishEvent(new TaskStartedEvent(this, taskId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        taskDto.setCreateTime(new Date());
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
        return new ResponseEntity<>(taskStageMapper.map(taskRepository.save(taskStageMapper.map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new), requestTask))), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteTask(Long taskId) {
        taskRepository.delete(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<StageDto> createStage(StageDto stageDto, Long taskId) {
        final Task task = taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        final Stage stage = taskStageMapper.map(stageDto);
        stage.setTask(task);
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
        return new ResponseEntity<>(taskStageMapper.map(stageRepository
                .save(taskStageMapper.map(stageRepository.findByTaskIdAndId(taskId, stageId)
                        .orElseThrow(NoSuchElementException::new), stageRequest))), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteStage(Long taskId, Long stageId) {
        stageRepository.delete(stageRepository.findByTaskIdAndId(taskId, stageId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

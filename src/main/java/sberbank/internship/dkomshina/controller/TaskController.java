package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskStageMapper taskStageMapper;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskStageMapper taskStageMapper) {
        this.taskRepository = taskRepository;
        this.taskStageMapper = taskStageMapper;
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskStageMapper.map(taskRepository.save(task)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        return new ResponseEntity<>(taskRepository.findAll().stream()
                .map(taskStageMapper::map).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskStageMapper
                .map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new)), HttpStatus.OK);
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody Task requestTask) {
        return new ResponseEntity<>(taskStageMapper.map(taskRepository.save(taskStageMapper
                .map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new), requestTask))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskRepository.delete(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

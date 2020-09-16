package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.Task;
import sberbank.internship.dkomshina.service.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ResponseEntity<Task> postTask(@RequestBody Task task) {
        taskRepository.save(task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (!tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable(name = "id") Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(name = "id") Long id) {
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

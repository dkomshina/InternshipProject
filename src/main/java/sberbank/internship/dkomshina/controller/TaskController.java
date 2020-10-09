package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.service.TaskStageService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

//еще один сервис, который останавливает stage а второй, который начинает
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskStageService taskStageService;

    @Autowired
    public TaskController(TaskStageService taskStageService) {
        this.taskStageService = taskStageService;
    }

    @PostMapping(value = "{taskId}/start")
    public void startTask(@PathVariable Long taskId) {
        taskStageService.startTask(taskId);
    }

    @PostMapping(value = "{taskId}/stop")
    public void stopTask(@PathVariable Long taskId) {
        taskStageService.stopTask(taskId);
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return taskStageService.createTask(taskDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        return taskStageService.getTasks();
    }

    @GetMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
        return taskStageService.getTaskById(taskId);
    }

    @PutMapping(value = "/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId, @RequestBody TaskDto requestTask) {
        return taskStageService.updateTask(taskId, requestTask);
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        return taskStageService.deleteTask(taskId);
    }
}

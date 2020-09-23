package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks/{taskId}/stages")
public class StageController {
    private final StageRepository stageRepository;
    private final TaskRepository taskRepository;
    private final TaskStageMapper taskStageMapper;

    @Autowired
    public StageController(StageRepository stageRepository, TaskRepository taskRepository, TaskStageMapper taskStageMapper) {
        this.stageRepository = stageRepository;
        this.taskRepository = taskRepository;
        this.taskStageMapper = taskStageMapper;
    }

    @PostMapping
    public ResponseEntity<?> createStage(@RequestBody Stage stage, @PathVariable Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new);
        stage.setTask(task);
        task.getStages().add(stage);
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.save(stage)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StageDto>> getStages(@PathVariable Long taskId) {
        List<StageDto> stages = stageRepository.findAll().stream().map(taskStageMapper::map).collect(Collectors.toList());
        if (!stages.isEmpty()) {
            return new ResponseEntity<>(stages, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{stageId}")
    public ResponseEntity<Stage> getStageById(@PathVariable Long taskId, @PathVariable Long stageId) {
        Optional<Stage> stage = stageRepository.findStageById(taskId, stageId);
        return stage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{stageId}")
    public ResponseEntity<Stage> updateStage(
            @PathVariable Long taskId, @PathVariable Long stageId, @RequestBody Stage stageRequest) {
        Optional<Stage> stage = stageRepository.updateStage(taskId, stageId, stageRequest);
        return stage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{stageId}")
    public ResponseEntity<?> deleteStage(@PathVariable Long taskId, @PathVariable Long stageId) {
        if (stageRepository.deleteStage(taskId, stageId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

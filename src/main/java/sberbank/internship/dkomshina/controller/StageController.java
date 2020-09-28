package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.mapper.TaskStageMapper;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.model.json.resp.TaskDto;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
    public ResponseEntity<StageDto> createStage(@RequestBody StageDto stageDto, @PathVariable Long taskId) {
        TaskDto taskDto = taskStageMapper.map(taskRepository.findById(taskId).orElseThrow(NoSuchElementException::new));
        taskDto.getStages().add(stageDto);
        stageDto.setTaskId(taskId);
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.save(taskStageMapper.map(stageDto))), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StageDto>> getStages(@PathVariable Long taskId) {
        return new ResponseEntity<>(stageRepository.findAllByTaskId(taskId).stream()
                .map(taskStageMapper::map).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{stageId}")
    public ResponseEntity<StageDto> getStageById(@PathVariable Long taskId, @PathVariable Long stageId) {
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.findByTaskIdAndId(taskId, stageId)
                .orElseThrow(NoSuchElementException::new)), HttpStatus.OK);
    }


    @PutMapping(value = "/{stageId}")
    public ResponseEntity<StageDto> updateStage(
            @PathVariable Long taskId, @PathVariable Long stageId, @RequestBody Stage stageRequest) {
        return new ResponseEntity<>(taskStageMapper.map(stageRepository.save(taskStageMapper
                .map(stageRepository.findByTaskIdAndId(taskId, stageId).orElseThrow(NoSuchElementException::new), stageRequest))), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{stageId}")
    public ResponseEntity<?> deleteStage(@PathVariable Long taskId, @PathVariable Long stageId) {
        stageRepository.delete(stageRepository.findByTaskIdAndId(taskId, stageId).orElseThrow(NoSuchElementException::new));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

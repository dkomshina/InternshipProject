package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.json.resp.StageDto;
import sberbank.internship.dkomshina.service.TaskStageService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/{taskId}/stages")
public class StageController {
    private final TaskStageService taskStageService;

    @Autowired
    public StageController(TaskStageService taskStageService) {
        this.taskStageService = taskStageService;
    }

    @PostMapping
    public ResponseEntity<StageDto> createStage(@RequestBody StageDto stageDto, @PathVariable Long taskId) {
        return taskStageService.createStage(stageDto, taskId);
    }

    @GetMapping
    public ResponseEntity<List<StageDto>> getStages(@PathVariable Long taskId) {
        return taskStageService.getStages(taskId);
    }

    @GetMapping(value = "/{stageId}")
    public ResponseEntity<StageDto> getStageById(@PathVariable Long taskId, @PathVariable Long stageId) {
        return taskStageService.getStageById(taskId, stageId);
    }


    @PutMapping(value = "/{stageId}")
    public ResponseEntity<StageDto> updateStage(@PathVariable Long taskId, @PathVariable Long stageId, @RequestBody StageDto stageRequest) {
        return taskStageService.updateStage(taskId, stageId, stageRequest);
    }

    @DeleteMapping(value = "/{stageId}")
    public ResponseEntity<?> deleteStage(@PathVariable Long taskId, @PathVariable Long stageId) {
        return taskStageService.deleteStage(taskId, stageId);
    }
}

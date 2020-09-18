package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.Stage;
import sberbank.internship.dkomshina.service.StageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stages")
public class StageController {
    private StageService stageService;

    @Autowired
    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @PostMapping
    public ResponseEntity<?> createStage(@RequestBody Stage stage) {
        return new ResponseEntity<>(stageService.addStage(stage), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Stage>> getStages() {
        List<Stage> stages = stageService.findAllStages();
        if (!stages.isEmpty()) {
            return new ResponseEntity<>(stages, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{stageId}")
    public ResponseEntity<Stage> getStageById(@PathVariable Long stageId) {
        Optional<Stage> stage = stageService.findStageById(stageId);
        return stage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{stageId}")
    public ResponseEntity<Stage> updateStage(@PathVariable Long stageId, @RequestBody Stage stageRequest) {
        Optional<Stage> stage = stageService.updateStage(stageId, stageRequest);
        return stage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{stageId}")
    public ResponseEntity<?> deleteStage(@PathVariable Long stageId) {
        if (stageService.deleteStage(stageId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

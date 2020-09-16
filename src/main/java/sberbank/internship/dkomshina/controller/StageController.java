package sberbank.internship.dkomshina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sberbank.internship.dkomshina.model.Stage;
import sberbank.internship.dkomshina.service.StageRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stages")
public class StageController {
    private StageRepository stageRepository;

    @Autowired
    public StageController(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @RequestMapping(name = "/post", method = RequestMethod.POST)
    public ResponseEntity<Stage> saveStage(@RequestBody Stage stage) {
        stageRepository.save(stage);
        return new ResponseEntity<>(stage, HttpStatus.CREATED);
    }

    @GetMapping(value = "/stages/get")
    public ResponseEntity<List<Stage>> getAllStages() {
        List<Stage> stages = stageRepository.findAll();
        if (!stages.isEmpty()) {
            return new ResponseEntity<>(stages, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/stages/get/{id}")
    public ResponseEntity<Stage> getStageById(@PathVariable(name = "id") Long id) {
        Optional<Stage> stage = stageRepository.findById(id);
        return stage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/stages/delete/{id}")
    public ResponseEntity<?> deleteStage(@PathVariable(name = "id") Long id) {
        stageRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

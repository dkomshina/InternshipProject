package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.model.Stage;
import sberbank.internship.dkomshina.repository.StageRepository;

import java.util.List;
import java.util.Optional;

//выполнение бизнес логики (?)
@Service
public class StageService {

    private final StageRepository stageRepository;

    @Autowired
    public StageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    public List<Stage> findAllStages() {
        return stageRepository.findAll();
    }

    public Optional<Stage> findStageById(Long stageId) {
        return stageRepository.findById(stageId);
    }

    public Stage addStage(Stage stage) {
        return stageRepository.save(stage);
    }

    public Optional<Stage> updateStage(Long stageId, Stage stageRequest) {
        return stageRepository.findById(stageId).map(
                newStage -> {
                    //TODO добавить еще
                    newStage.setName(stageRequest.getName());
                    return stageRepository.save(newStage);
                });
    }

    public Boolean deleteStage(Long stageId) {
        return stageRepository.findById(stageId).map(
                deletingStage -> {
                    stageRepository.delete(deletingStage);
                    return true;
                }).orElse(false);
    }
}

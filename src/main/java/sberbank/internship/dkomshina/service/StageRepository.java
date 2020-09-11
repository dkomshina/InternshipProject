package sberbank.internship.dkomshina.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import sberbank.internship.dkomshina.model.Stage;

import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Long> {

}

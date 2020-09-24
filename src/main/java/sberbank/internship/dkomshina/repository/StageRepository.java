package sberbank.internship.dkomshina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sberbank.internship.dkomshina.model.db.Stage;

import java.util.List;
import java.util.Optional;

//непосредственная работа с базой данных
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findAllByTaskId(Long taskId);
    Optional<Stage> findByTaskIdAndId(Long taskId, Long id);
}

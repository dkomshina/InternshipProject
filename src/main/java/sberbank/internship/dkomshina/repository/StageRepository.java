package sberbank.internship.dkomshina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sberbank.internship.dkomshina.model.Stage;

//непосредственная работа с базой данных
@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
}

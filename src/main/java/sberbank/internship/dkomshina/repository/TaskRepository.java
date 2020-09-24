package sberbank.internship.dkomshina.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sberbank.internship.dkomshina.model.db.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

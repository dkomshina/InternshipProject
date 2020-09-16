package sberbank.internship.dkomshina.service;

import org.springframework.data.jpa.repository.JpaRepository;
import sberbank.internship.dkomshina.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

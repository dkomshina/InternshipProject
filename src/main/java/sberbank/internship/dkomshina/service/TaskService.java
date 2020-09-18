package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.model.Task;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long taskId, Task taskRequest) {
        return taskRepository.findById(taskId).map(
                newTask -> {
                    //TODO добавить еще
                    newTask.setName(taskRequest.getName());
                    return taskRepository.save(newTask);
                });
    }

    public Boolean deleteTask(Long taskId) {
        return taskRepository.findById(taskId).map(
                deletingTask -> {
                    taskRepository.delete(deletingTask);
                    return true;
                }).orElse(false);
    }
}

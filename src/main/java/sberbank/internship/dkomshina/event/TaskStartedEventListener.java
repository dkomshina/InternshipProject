package sberbank.internship.dkomshina.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;
import sberbank.internship.dkomshina.util.StageStatusType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class TaskStartedEventListener {

    private final TaskRepository taskRepository;
    private final StageRepository stageRepository;

    @Autowired
    public TaskStartedEventListener(TaskRepository taskRepository, StageRepository stageRepository) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
    }

    @Async
    @EventListener
    public void startTask(TaskStartedEvent taskStartedEvent) {
        Task task = taskRepository.findById(taskStartedEvent.getTaskId()).orElseThrow(NoSuchElementException::new);
        List<Stage> stages = task.getStages();
        task.setStartTime(new Date());
        taskRepository.save(task);
        for (Stage stage : stages) {
            if (stage != null) {
                stage.setStartTime(new Date());
                stage.setStatus(StageStatusType.STARTED);
                stageRepository.save(stage);
                executeCommand(stage.getScript());
                stage.setEndTime(new Date());
                stage.setStatus(StageStatusType.STARTED);
                stageRepository.save(stage);
            }
        }
        task.setEndTime(new Date());
        task.setStages(stages);
        taskRepository.save(task);
    }

    private void executeCommand(String command) {
        try {
            log(command);
            Process process = Runtime.getRuntime().exec("cmd /c" + command);
            logOutput(process.getInputStream(), "");
            logOutput(process.getErrorStream(), "Error: ");
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void logOutput(InputStream inputStream, String prefix) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "IBM866"));
            bufferedReader.lines().forEach(line -> log(prefix + line));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SSS");

    private static void log(String message) {
        System.out.println(format.format(new Date()) + ": " + message);
    }
}

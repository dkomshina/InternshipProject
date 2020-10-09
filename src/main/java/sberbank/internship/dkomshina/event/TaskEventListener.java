package sberbank.internship.dkomshina.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.model.db.Task;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;
import sberbank.internship.dkomshina.util.StatusType;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskEventListener {

    private final TaskRepository taskRepository;
    private final StageRepository stageRepository;
    //concurrency hash map

    @Autowired
    public TaskEventListener(TaskRepository taskRepository, StageRepository stageRepository) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
    }

    @EventListener
    public void startTask(StartTaskEvent startTaskEvent) {
        Task task = taskRepository.findById(startTaskEvent.getTaskId()).orElseThrow(NoSuchElementException::new);
        List<Stage> stages = task.getStages();
        task.setStartTime(new Date());
        StatusType taskStatus = StatusType.RUNNING;
        task.setStatus(taskStatus);
        taskRepository.save(task);
        for (int i = task.getStageNumber(); i < stages.size(); i++) {
            final Stage stage = stages.get(i);
            if (stage != null) {
                task.setStageNumber(i);
                taskRepository.save(task);
                taskStatus = executeCommand(stage.getScript(), stage);
                if(taskStatus == StatusType.STOPPED || taskStatus == StatusType.BROKEN || taskStatus == StatusType.PAUSED){
                    break;
                }
                //вернуть что-то
                //условие выхода из stage
                //хранить процесс Id и останавливать процесс
            }
        }
        task.setStatus(taskStatus);
        task.setStages(stages);
        taskRepository.save(task);
    }

    private StatusType executeCommand(String command, Stage stage) {
        try {
            log(command);
            //todo 'web socket' spring or 'long poling'
            Process process = Runtime.getRuntime().exec("cmd /c" + command);
            stage.setPid(process.pid());
            stage.setStartTime(new Date());
            stage.setStatus(StatusType.RUNNING);
            stageRepository.save(stage);

            InputStream input = process.getInputStream();
            input.mark(1);
            final int bytesRead = input.read(new byte[1]);
            input.reset();
            if (bytesRead != -1) {
                logOutput(input, "Output: ");
                if (process.exitValue() != 0) {
                    stage.setStatus(StatusType.STOPPED);
                    stageRepository.save(stage);
                    return StatusType.STOPPED;
                }
            } else {
                logOutput(process.getErrorStream(), "Error: ");
                stage.setStatus(StatusType.BROKEN);
                stageRepository.save(stage);
                return StatusType.BROKEN;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setEndTime(new Date());
        stage.setStatus(StatusType.COMPLETED);
        stageRepository.save(stage);
        return StatusType.COMPLETED;
    }

    @EventListener
    public void stopTask(StopTaskEvent stopTaskEvent) {
        Task task = taskRepository.findById(stopTaskEvent.getTaskId()).orElseThrow(NoSuchElementException::new);
        ProcessHandle.of(task.getStages().get(task.getStageNumber()).getPid()).ifPresent(ProcessHandle::destroy);
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

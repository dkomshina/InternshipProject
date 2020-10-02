package sberbank.internship.dkomshina.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
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

@Component
public class TaskEventListener {

    private final TaskRepository taskRepository;
    private final StageRepository stageRepository;
    private final HashMap<Long, Thread> threadHashMap = new HashMap<>();

    @Autowired
    public TaskEventListener(TaskRepository taskRepository, StageRepository stageRepository) {
        this.taskRepository = taskRepository;
        this.stageRepository = stageRepository;
    }

    @EventListener
    public void startTask(StartTaskEvent startTaskEvent) {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                Task task = taskRepository.findById(startTaskEvent.getTaskId()).orElseThrow(NoSuchElementException::new);
                List<Stage> stages = task.getStages();
                task.setStartTime(new Date());
                taskRepository.save(task);
                task.setStatus(StatusType.RUNNING);
                boolean isStageWorks = true;
                for (int i = task.getStageNumber();
                     !Thread.currentThread().isInterrupted() && isStageWorks && i < stages.size(); i++) {
                    final Stage stage = stages.get(i);
                    if (stage != null) {
                        stage.setStartTime(new Date());
                        task.setStageNumber(i);
                        stageRepository.save(stage);
                        isStageWorks = executeCommand(stage.getScript(), task);
                        stage.setEndTime(new Date());
                        stageRepository.save(stage);
                    }
                }
                if (isStageWorks) {
                    task.setStatus(StatusType.ENDED);
                    task.setEndTime(new Date());
                }
                task.setStages(stages);
                taskRepository.save(task);
            }
        });

        threadHashMap.put(startTaskEvent.getTaskId(), thread);
        thread.start();
//        Task task = taskRepository.findById(startTaskEvent.getTaskId()).orElseThrow(NoSuchElementException::new);
//        List<Stage> stages = task.getStages();
//        task.setStartTime(new Date());
//        taskRepository.save(task);
//        task.setStatus(StatusType.RUNNING);
//        boolean isStageWorks = true;
//        for (int i = task.getStageNumber(); isStageWorks && i < stages.size(); i++) {
//            final Stage stage = stages.get(i);
//            if (stage != null) {
//                stage.setStartTime(new Date());
//                task.setStageNumber(i);
//                stageRepository.save(stage);
//                isStageWorks = executeCommand(stage.getScript(), task);
//                stage.setEndTime(new Date());
//                stageRepository.save(stage);
//            }
//        }
//        if (isStageWorks) {
//            task.setStatus(StatusType.ENDED);
//            task.setEndTime(new Date());
//        }
//        task.setStages(stages);
//        taskRepository.save(task);
    }

    @EventListener
    public void stopTask(StopTaskEvent stopTaskEvent) {
        if (threadHashMap.containsKey(stopTaskEvent.getTaskId())) {
            threadHashMap.get(stopTaskEvent.getTaskId()).interrupt();
        }
    }

    private boolean executeCommand(String command, Task task) {
        try {
            log(command);
            Process process = Runtime.getRuntime().exec("cmd /c" + command);

            InputStream input = process.getInputStream();
            input.mark(1);
            final int bytesRead = input.read(new byte[1]);
            input.reset();
            if (bytesRead != -1) {
                logOutput(input, "Output: ");
                process.waitFor();
                return true;
            }
            logOutput(process.getErrorStream(), "Error: ");
            process.waitFor();
            task.setStatus(StatusType.BROKEN);
            return false;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
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

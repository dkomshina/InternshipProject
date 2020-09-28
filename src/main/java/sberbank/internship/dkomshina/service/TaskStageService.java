package sberbank.internship.dkomshina.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sberbank.internship.dkomshina.model.db.Stage;
import sberbank.internship.dkomshina.repository.StageRepository;
import sberbank.internship.dkomshina.repository.TaskRepository;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class TaskStageService {

    private final StageRepository stageRepository;

    @Autowired
    public TaskStageService(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }


    public void startTask(Long taskId) {
        List<Stage> stages = stageRepository.findAllByTaskId(taskId);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cmd /c/Users/18693236/dir");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (process != null) {
            Charset sc = StandardCharsets.ISO_8859_1;
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), sc));
                 BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), sc));
                 BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream(), sc))) {
                for (Stage stage : stages) {
                    bw.write(stage.getScript());
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    while ((line = er.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}

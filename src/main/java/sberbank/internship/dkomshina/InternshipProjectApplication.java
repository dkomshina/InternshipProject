package sberbank.internship.dkomshina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import sberbank.internship.dkomshina.model.db.Task;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@SpringBootApplication
public class InternshipProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternshipProjectApplication.class, args);
    }
}

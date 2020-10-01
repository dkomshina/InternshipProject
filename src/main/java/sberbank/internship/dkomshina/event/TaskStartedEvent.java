package sberbank.internship.dkomshina.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import sberbank.internship.dkomshina.service.TaskStageService;

@Getter
public class TaskStartedEvent extends ApplicationEvent {

    private Long taskId;

    public TaskStartedEvent(Object source, Long taskId) {
        super(source);

        this.taskId = taskId;
    }
}
